import os
import time
from collections import OrderedDict
from datetime import datetime

import numpy as np
import torch
import torch.nn.functional as F
import torch.optim as optim
import torchvision.utils as vutils
from torch.autograd import Variable
from torch.optim.lr_scheduler import *
from tqdm import tqdm

from script.evaluate import roc, calculate_confusion_matrix
from script.networks_4_human import P_net, Q_net, D_net
from script.util import report_loss
from script.visualizer import Visualizer


def weights_init(mod):
    """
    Custom weights initialization called on netG, netD and netE
    :param m:
    :return:
    """
    classname = mod.__class__.__name__
    if classname.find('Conv') != -1:
        mod.weight.data.normal_(0.0, 0.02)
    elif classname.find('BatchNorm') != -1:
        mod.weight.data.normal_(1.0, 0.02)
        mod.bias.data.fill_(0)


class AAE_basic:
    @staticmethod
    def name():
        """Return name of the class.
        """
        return 'aae_anomaly'

    def __init__(self, opt, dataloader=None):
        super(AAE_basic, self).__init__()

        self.opt = opt

        self.model_saved = False
        self.d_loss = None
        self.g_loss = None
        self.recon_loss = None
        self.z_loss = None
        self.dataloader = dataloader
        self.total_steps = None
        self.epoch = 0
        self.visualizer = Visualizer(opt)
        self.cuda = torch.cuda.is_available()
        self.N = 1000
        self.fake = None
        self.P = None
        self.Q = None
        self.D = None

        self.Q = Q_net(self.opt.iheight, self.opt.iwidth, self.opt.zdim, self.opt.nc, self.opt.ngf, self.opt.ngpu,
                       self.opt.extralayers)
        self.P = P_net(self.opt.iheight, self.opt.iwidth, self.opt.zdim, self.opt.nc, self.opt.ngf, self.opt.ngpu,
                       self.opt.extralayers)
        self.D = D_net(self.opt.zdim, self.N)
        self.P.apply(weights_init)
        self.Q.apply(weights_init)
        # self.D.apply(weights_init)

        if self.opt.resume != '':
            print("\nLoading pre-trained networks.")
            self.opt.iter = torch.load(os.path.join(self.opt.resume, 'Q.pth'))['epoch']
            self.Q.load_state_dict(torch.load(os.path.join(self.opt.resume, 'Q.pth'))['state_dict'])
            self.P.load_state_dict(torch.load(os.path.join(self.opt.resume, 'P.pth'))['state_dict'])
            self.D.load_state_dict(torch.load(os.path.join(self.opt.resume, 'D.pth'))['state_dict'])
            print("\tDone.\n")

        print(self.P)
        print(self.Q)
        print(self.D)

        if self.cuda:
            self.Q = self.Q.cuda()
            self.P = self.P.cuda()
            self.D = self.D.cuda()

        self.TINY = 1e-15
        self.X = torch.zeros(self.opt.batchsize, 3, self.opt.iheight, self.opt.iwidth)
        self.gt = torch.LongTensor(self.opt.batchsize)
        self.X = Variable(self.X, requires_grad=False)
        self.gt = Variable(self.gt, requires_grad=False)
        self.total_steps = 0
        self.green = torch.zeros(3, 1, self.opt.iwidth)
        self.green[1:3, 0, :] = 1
        self.red = torch.zeros(3, 1, self.opt.iwidth)
        self.red[0, 0, :] = 1
        self.gt_colors = (self.green, self.red)

#    def visualize(self, epoch_iter):
#        if self.total_steps % self.opt.print_freq == 0:
#            errors = self.get_errors()
#            if self.opt.display:
#                counter_ratio = float(epoch_iter) / len(self.dataloader['train'].dataset)
#                self.visualizer.plot_current_errors(self.epoch, counter_ratio, errors)
#        if self.total_steps % self.opt.save_image_freq == 0:
#            reals, fakes = self.get_current_images()
#            reals = self.expand_image_5x(reals, self.gt.data)
#            fakes = self.expand_image_5x(fakes, self.gt.data)
#            self.visualizer.save_current_images(self.epoch, reals, fakes)
#            if self.opt.display:
#                self.visualizer.display_current_images(reals, fakes)

    def visualize(self, epoch_iter):
        errors = self.get_errors()
        if self.opt.display:
            counter_ratio = float(epoch_iter) / len(self.dataloader['train'].dataset)
            self.visualizer.plot_current_errors(self.epoch, counter_ratio, errors)

        reals, fakes = self.get_current_images()
        reals = self.expand_image_4_human(reals, self.gt.data)
        fakes = self.expand_image_4_human(fakes, self.gt.data)
        self.visualizer.save_current_images(self.epoch, reals, fakes)
        if self.opt.display:
            self.visualizer.display_current_images(reals, fakes)
    ####################
    # Train procedure
    ####################

    def train(self):
        if self.opt.manualseed is not None:
            torch.manual_seed(self.opt.manualseed)

        # def __init__(self, iheight, iwidth, zdim, nc, ndf, ngpu, n_extra_layers=0, add_final_conv=True):

        # self.Q = Q_net(self.opt.iheight, self.opt.iwidth, self.opt.zdim, self.opt.nc, self.opt.ngf, self.opt.ngpu, self.opt.extralayers)
        # self.P = P_net(self.opt.iheight, self.opt.iwidth, self.opt.zdim, self.opt.nc, self.opt.ngf, self.opt.ngpu, self.opt.extralayers)
        # self.D = D_net_gauss(self.opt.zdim, self.N)

        # if self.cuda:
        #    self.Q = self.Q.cuda()
        #    self.P = self.P.cuda()
        #    self.D = self.D.cuda()

        # Set learning rates
        # 아래에서 reg_lr을 0.01로 하면 g_loss가 -0.0으로 나오는데..희안하네.

        # Set optimizators
        P_decoder = optim.Adam(self.P.parameters(), lr=self.opt.gen_lr, betas=(self.opt.beta1, 0.999))
        Q_encoder = optim.Adam(self.Q.parameters(), lr=self.opt.gen_lr, betas=(self.opt.beta1, 0.999))

        Q_generator = optim.Adam(self.Q.parameters(), lr=self.opt.reg_lr, betas=(self.opt.beta1, 0.999))
        D_gauss_solver = optim.Adam(self.D.parameters(), lr=self.opt.reg_lr, betas=(self.opt.beta1, 0.999))
        # Q_generator = optim.Adam(self.Q.parameters(), lr=0.01, betas=(self.opt.beta1, 0.999))
        # Q_generator = optim.Adam(self.Q.parameters(), lr=0.01)
        # D_gauss_solver = optim.Adam(self.D.parameters(), lr=0.001)

        best_auc = 0

        scheduler_1 = MultiStepLR(P_decoder, milestones=[50, 1000], gamma=0.1)
        scheduler_2 = MultiStepLR(Q_encoder, milestones=[50, 1000], gamma=0.1)
        scheduler_3 = MultiStepLR(Q_generator, milestones=[50, 1000], gamma=0.1)
        scheduler_4 = MultiStepLR(D_gauss_solver, milestones=[50, 1000], gamma=0.1)

        for self.epoch in range(self.opt.epochs):

            scheduler_1.step()
            scheduler_2.step()
            scheduler_3.step()
            scheduler_4.step()

            self.train_epoch(self.P, self.Q, self.D, P_decoder, Q_encoder, Q_generator, D_gauss_solver)

            # if epoch % 10 == 0:
            report_loss(self.epoch, self.d_loss, self.g_loss, self.recon_loss, self.z_loss)

            self.visualize(self.epoch)

            res = self.test()
            if res['AUC'] > best_auc:
                best_auc = res['AUC']
                self.save_weights(self.epoch)
            self.visualizer.print_current_performance(res, best_auc)

        print("Training finished: at {}, saved at output={}".format(datetime.now().strftime('%y%m%d_%H%M'),
                                                                    self.opt.outf))

    def train_epoch(self, P, Q, D_gauss, P_decoder_solver, Q_encoder_solver, Q_generator_solver, D_gauss_solver):
        '''
        Train procedure for one epoch.
        '''
        # Set the networks in train mode (apply dropout when needed)
        Q.train()
        P.train()
        D_gauss.train()

        epoch_iter = 0
        # Loop through the labeled and unlabeled dataset getting one batch of samples from each
        # The batch size has to be a divisor of the size of the dataset or it will return
        # invalid samples
        # for X, target in data_loader['train']:
        for data in tqdm(self.dataloader['train'], leave=False, total=len(self.dataloader['train'])):
            self.total_steps += self.opt.batchsize
            epoch_iter += self.opt.batchsize
            # Load batch and normalize samples to be between 0 and 1
            # silee. this normalization is specialized only with MNIST, taking its distribution into account

            self.set_input(data)

            # Init gradients
            P.zero_grad()
            Q.zero_grad()
            D_gauss.zero_grad()

            #######################
            # Reconstruction phase
            #######################
            z_sample = Q(self.X)
            self.fake = P(z_sample)
            # self.recon_loss = F.l1_loss(self.fake, self.X, reduce=True, size_average=False)


            k = F.mse_loss(self.fake, self.X, reduce=False, size_average=self.opt.size_average)
            for i, where in enumerate(data[2]):
                if "1.abnormal" in where:
                    # k[i] = -10*k[i]  # Weighted Naive Negative loss
                    k[i] = -k[i]    # Naive Negative loss
                    # k[i] = torch.exp(-k[i]) # ABC loss?
                    # k[i] = min(1, -torch.log(1-torch.exp(-k[i]))) # ABC loss => inf loss problem => 너무 커지지 않게 1로 제한
                    #print(where + "\n" + str(k[i])) # just for check loss...

            self.recon_loss = torch.mean(k)
            # self.recon_loss = torch.sum(k) # changed to mean. ABC loss

            # 1 line below is the original code! modified in April.5 because of negative learning adjust
            # self.recon_loss = F.mse_loss(self.fake,self.X, reduce=True, size_average=self.opt.size_average)

            # self.recon_loss = F.l1_loss(self.fake, self.X, reduce=True, size_average=False)

            # self.recon_loss.backward(retain_graph=True)
            self.recon_loss.backward(retain_graph=self.opt.z_loss)
            P_decoder_solver.step()
            Q_encoder_solver.step()

            P.zero_grad()
            Q.zero_grad()
            D_gauss.zero_grad()

            if self.opt.z_loss:
                z_fake_sample = Q(self.fake)
                self.z_loss = F.mse_loss(z_fake_sample, z_sample, reduce=True, size_average=self.opt.size_average)
                self.z_loss.backward()
                Q_encoder_solver.step()
                Q.zero_grad()

            #######################
            # Regularization phase
            #######################
            # Discriminator
            Q.eval()
            # sample from N(0,5). Note torch.randn samples from N(0,1)
            z_real_gauss = Variable(torch.randn(self.opt.batchsize, self.opt.zdim) * self.opt.z_multiplier)
            if self.cuda:
                z_real_gauss = z_real_gauss.cuda()

            z_fake_gauss = Q(self.X)

            D_real_gauss = D_gauss(z_real_gauss)
            D_fake_gauss = D_gauss(z_fake_gauss)

            self.d_loss = -torch.mean(torch.log(D_real_gauss + self.TINY) + torch.log(1 - D_fake_gauss + self.TINY))

            self.d_loss.backward()
            D_gauss_solver.step()

            P.zero_grad()
            Q.zero_grad()
            D_gauss.zero_grad()

            # Generator
            Q.train()
            z_fake_gauss = Q(self.X)

            D_fake_gauss = D_gauss(z_fake_gauss)
            self.g_loss = -torch.mean(torch.log(D_fake_gauss + self.TINY))

            self.g_loss.backward()
            Q_generator_solver.step()

            P.zero_grad()
            Q.zero_grad()
            D_gauss.zero_grad()

            #self.visualize(epoch_iter)
        return

    def test(self):
        """ Test aae_anoamly model.

        Args:
            dataloader ([type]): Dataloader for the test set

        Raises:
            IOError: Model weights not found.
        """
        target_data_set = 'val' if self.opt.phase == 'train' else 'test'

        if self.opt.load_weights:
            P_net_path = "{}/P.pth".format(self.opt.pretrained_model_folder)
            Q_net_path = "{}/Q.pth".format(self.opt.pretrained_model_folder)
            P_pretrained_dict = torch.load(P_net_path)['state_dict']
            Q_pretrained_dict = torch.load(Q_net_path)['state_dict']

            try:
                self.P.load_state_dict(P_pretrained_dict)
                self.Q.load_state_dict(Q_pretrained_dict)
                target_data_set = "test"
            except IOError:
                raise IOError("weights not found")
            print('   Loaded weights.')

        # Create big error tensor for the test set.
        self.an_scores = torch.FloatTensor(len(self.dataloader[target_data_set].dataset), 1).zero_()
        self.gt_labels = torch.LongTensor(len(self.dataloader[target_data_set].dataset), 1).zero_()
        self.filenames = [None]*len(self.dataloader[target_data_set].dataset)

        if self.opt.gpu_ids:
            self.an_scores = self.an_scores.cuda()

        print("   Testing model %s." % self.name())
        self.times = []
        self.total_steps = 0
        epoch_iter = 0
        threshold = self.opt.threshold
        use_eer = True if self.opt.threshold == 0.0 else False

        self.Q.eval()
        self.P.eval()
        # for i, data in tqdm(self.dataloader['test'], leave=False, total=len(self.dataloader['test'])):
        # for i, data in enumerate(self.dataloader[target_data_set], 0):
        for i, data in enumerate(
                tqdm(self.dataloader[target_data_set], leave=False, total=len(self.dataloader[target_data_set])), 0):
            self.total_steps += self.opt.batchsize
            epoch_iter += self.opt.batchsize
            time_i = time.time()
            self.set_input(data)


            z_sample = self.Q(self.X)
            self.fake = self.P(z_sample)

            if self.opt.z_test:
                fake_z_sample = self.Q(self.fake)
                error = torch.pow((fake_z_sample - z_sample), 2).view(z_sample.size(0), -1).sum(1)
            else:
                error = torch.pow((self.fake - self.X), 2).view(self.X.size(0), -1).sum(1)
            # error = torch.sum(error)

            time_o = time.time()

            interval = error.size(0)

            self.filenames[i * self.opt.batchsize: i * self.opt.batchsize + error.size(0)] = data[2]
            if self.opt.gpu_ids:
                #self.an_scores[i*self.opt.batchsize : i*self.opt.batchsize+error.size(0)] = error.data.view(error.size(0), 1)
                # unsqueeze(1) added to below 2 lines
                self.an_scores[i * self.opt.batchsize: i * self.opt.batchsize + error.size(0)] = error.data.unsqueeze(1)
                self.gt_labels[i * self.opt.batchsize: i * self.opt.batchsize + error.size(0)] = self.gt.data.unsqueeze(1)
            else:
                self.an_scores[i * self.opt.batchsize: i * self.opt.batchsize + error.size(0),
                1] = error.cpu().data.view(error.size(0), 1)
                self.gt_labels[i * self.opt.batchsize: i * self.opt.batchsize + error.size(0)] = self.gt.cpu().data

            self.times.append(time_o - time_i)

            # Save test images.
            if self.opt.save_test_images and i % self.opt.save_image_freq == 0:
                dst = os.path.join(self.opt.outf, target_data_set, 'images')
                if not os.path.isdir(dst):
                    os.makedirs(dst)
                real, fake = self.get_current_images()
                real = self.expand_image_4_human(real, self.gt.data)
                fake = self.expand_image_4_human(fake, self.gt.data)
                # vutils.save_image(real, '%s/real_%03d.eps' % (dst, i+1), normalize=True)
                # vutils.save_image(real, '%s/%03d_real.png' % (dst, i+1), normalize=True, range=(-1, 1))
                # vutils.save_image(fake, '%s/%03d_fake.png' % (dst, i+1), normalize=True, range=(-1, 1))
                vutils.save_image(real, '%s/%03d_%03d_real.png' % (dst, self.epoch, i + 1), normalize=True)
                vutils.save_image(fake, '%s/%03d_%03d_fake.png' % (dst, self.epoch, i + 1), normalize=True)

        # Measure inference time.
        self.times = np.array(self.times)
        self.times = np.mean(self.times[:100] * 1000)

        # Scale error vector between [0, 1]
        self.an_scores = (self.an_scores - torch.min(self.an_scores)) / (
                torch.max(self.an_scores) - torch.min(self.an_scores))
        auc, eer, thr90 = roc(self.gt_labels, self.an_scores, saveto=dst)
        if use_eer:
            threshold = eer
        else:
            threshold = self.opt.threshold

        # reset threshold temporirty for experiemtns
        # auc, eer = roc(self.gt_labels, self.an_scores)
        cm = calculate_confusion_matrix(self.gt_labels, self.an_scores, threshold)
        performance = OrderedDict([('Avg Run Time (ms/batch)', self.times), ('EER', eer), ('AUC', auc)])
        print("TPR(=Recall)={0:0.4f}, FPR={1:0.4f}, Prec={2:0.4f}, Accuracy={3:0.4f} (thr={4:0.4f})".format(
            cm[1][1] / cm[1].sum(), cm[0][1] / cm[0].sum(), cm[1][1] / cm.sum(axis=0)[1],
            (cm[1][1] + cm[0][0]) / cm.sum(), threshold))
        print("Confusion matrix\n {0}".format(cm))

        self.save_score_to_file(self.filenames, self.an_scores.cpu().numpy())

        if self.opt.display_id > 0 and self.opt.phase == 'test':
            counter_ratio = float(epoch_iter) / len(self.dataloader[target_data_set].dataset)
            self.visualizer.plot_performance(self.epoch, counter_ratio, performance)

        return performance

    def save_score_to_file(self, paths, scores):
        score_dir = os.path.join(self.opt.outf, 'test')
        if not os.path.exists(score_dir):
            os.makedirs(score_dir)

        # save to the disk
        name = os.path.join(score_dir, 'scores.csv')
        with open(name, 'wt') as score_file:
            score_file.write('------------ filename score -------------\n')

            for path, score in zip(paths, scores):
                test_file_name = os.path.basename(path)

                msg = "{:s},{:0.10f}\n".format(test_file_name, score[0])
                score_file.write(msg)

    def get_errors(self):
        """ Get netD and netG errors.

        Returns:
            [OrderedDict]: Dictionary containing errors.
        """

        errors = OrderedDict([('d_loss', self.d_loss.item()),
                              ('g_loss', self.g_loss.item()),
                              ('recon_loss', self.recon_loss.item())]
                             )

        # errors = OrderedDict([('d_loss', self.d_loss.data[0]),
        #                       ('g_loss', self.g_loss.data[0]),
        #                       ('recon_loss', self.recon_loss.data[0])]
        #                      )

        return errors

    def get_current_images(self):
        """ Returns current images.

        Returns:
            [reals, fakes, fixed]
        """

        reals = self.X.data
        fakes = self.fake.data
        # fixed = self.netg(self.fixed_input)[0].data

        return reals, fakes

    def set_input(self, input):
        """ Set input and ground truth

        Args:
            input (FloatTensor): Input data for batch i.
        """

        self.X.data.resize_(input[0].size()).copy_(input[0])
        self.gt.data.resize_(input[1].size()).copy_(input[1])

        if self.cuda:
            self.X, self.gt = self.X.cuda(), self.gt.cuda()

    def save_weights(self, epoch):
        """Save netG and netD weights for the current epoch.

        Args:
            epoch ([int]): Current epoch number.
        """

        weight_dir = os.path.join(self.opt.outf, 'train', 'weights')
        if not os.path.exists(weight_dir):
            os.makedirs(weight_dir)
        if not self.model_saved:
            self.save_model()
            self.model_saved = True
        torch.save({'epoch': epoch + 1, 'state_dict': self.Q.state_dict()},
                   '%s/Q.pth' % (weight_dir))
        torch.save({'epoch': epoch + 1, 'state_dict': self.P.state_dict()},
                   '%s/P.pth' % (weight_dir))
        torch.save({'epoch': epoch + 1, 'state_dict': self.D.state_dict()},
                   '%s/D.pth' % (weight_dir))

    # by silee
    def save_model(self):
        weight_dir = os.path.join(self.opt.outf, 'train', 'weights')
        if not os.path.exists(weight_dir):
            os.makedirs(weight_dir)

        # save to the disk
        file_name = os.path.join(weight_dir, 'model.txt')
        with open(file_name, 'wt') as model_file:
            model_file.write('------------ Model-------------\n')
            print(self.P, file=model_file)
            print(self.Q, file=model_file)
            print(self.D, file=model_file)
            model_file.write('-------------- End ----------------\n')

    def expand_image(self, image, gt):
        chunks = torch.chunk(image, chunks=2, dim=1)
        result = torch.cat(chunks, dim=3)

        gt_colors = torch.zeros(result.size(0), result.size(1), 1, result.size(3))
        for i, label in enumerate(gt):
            gt_colors[i] = self.gt_colors[label]

        result = torch.cat((result, gt_colors.cuda()), dim=2)
        return result

    # BxCxHxW
    def expand_image_5x(self, data, gt):
        chunks1 = torch.chunk(data, chunks=5, dim=1)

        my_list = []
        for each in chunks1:
            chunks2 = torch.chunk(each, chunks=2, dim=1)
            one = torch.cat(chunks2, dim=3)
            my_list.append(one)

        combined = torch.cat(my_list, dim=2)

        gt_colors = torch.zeros(combined.size(0), combined.size(1), 1, combined.size(3))
        for i, label in enumerate(gt):
            gt_colors[i] = self.gt_colors[label]

        result = torch.cat((combined, gt_colors.cuda()), dim=2)
        return result

    def expand_image_4_human(self, image, gt):
        # image: BCHW
        return self.add_gt_lines(image, gt)

    def add_gt_lines(self, image, gt):
        gt_colors = torch.zeros(image.size(0), image.size(1), 1, image.size(3))
        for i, label in enumerate(gt):
            gt_colors[i] = self.gt_colors[label]

        result = torch.cat((image, gt_colors.cuda()), dim=2)
        return result
