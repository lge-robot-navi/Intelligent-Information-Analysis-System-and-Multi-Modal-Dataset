""" Options

This script is largely based on junyanz/pytorch-CycleGAN-and-pix2pix.

Returns:
    [argparse]: Class containing argparse
"""

import argparse
import os
from datetime import datetime

import torch


# pylint: disable=C0103,C0301,R0903,W0622

class Options():
    """Options class

    Returns:
        [argparse]: argparse containing train and test options
    """

    def __init__(self):
        ##
        #
        self.parser = argparse.ArgumentParser(formatter_class=argparse.ArgumentDefaultsHelpFormatter)

        ##
        # Base
        self.parser.add_argument('--dataset', default='lobby', help='folder | cifar10 | mnist ')
        self.parser.add_argument('--dataroot', default='', help='path to dataset')
        self.parser.add_argument('--batchsize', type=int, default=64, help='input batch size')
        self.parser.add_argument('--workers', type=int, help='number of data loading workers', default=8)
        self.parser.add_argument('--droplast', action='store_true', default=True, help='Drop last batch size.')
        # self.parser.add_argument('--isize', type=int, default=32, help='input image size.')
        self.parser.add_argument('--iwidth', type=int, default=64, help='input image width network after transformation.')
        self.parser.add_argument('--iheight', type=int, default=64, help='input image height for network after transformation.')
        # self.parser.add_argument('--nc', type=int, default=1, help='input image channels')
        self.parser.add_argument('--nc', type=int, default=3, help='input image channels')
        self.parser.add_argument('--ngf', type=int, default=64)
        #self.parser.add_argument('--ndf', type=int, default=64)
        self.parser.add_argument('--extralayers', type=int, default=0, help='Number of extra layers on gen and disc')
        self.parser.add_argument('--gpu_ids', type=str, default='0', help='gpu ids: e.g. 0  0,1,2, 0,2. use -1 for CPU')
        self.parser.add_argument('--ngpu', type=int, default=1, help='number of GPUs to use')
        # self.parser.add_argument('--name', type=str, default='experiment_name', help='name of the experiment')
        self.parser.add_argument('--model', type=str, default='aae_anomaly', help='chooses which model to use.')
        """
        use visdom in this sequence
        1. python -m visdom.server
        2. http://localhost:8097
        3. turn on the disply option below
        """
        self.parser.add_argument('--display_server', type=str, default="http://localhost",
                                 help='visdom server of the web display')
        self.parser.add_argument('--display_port', type=int, default=8097, help='visdom port of the web display')
        self.parser.add_argument('--display_id', type=int, default=0, help='window id of the web display')
        self.parser.add_argument('--display', action='store_true', default=False, help='Use visdom.')
        self.parser.add_argument('--manualseed', type=int, help='manual seed')
        self.parser.add_argument('--anomaly_class', default='car',
                                 help='Anomaly class idx for mnist and cifar datasets')

        ##
        # Train
        self.parser.add_argument('--outf', type=str, help='visdom server of the web display')
        self.parser.add_argument('--print_freq', type=int, default=100,
                                 help='frequency of showing training results on console')
        self.parser.add_argument('--save_image_freq', type=int, default=50,
                                 help='frequency of saving real and fake images')
        self.parser.add_argument('--save_test_images', default=True, action='store_true',
                                 help='Save test images for demo.')
        self.parser.add_argument('--load_weights', action='store_true', help='Load the pretrained weights')
        self.parser.add_argument('--pretrained_dir', type=str, help='Load the pretrained weights at the dir')
        self.parser.add_argument('--resume', default='', help="path to checkpoints (to continue training)")
        self.parser.add_argument('--phase', type=str, default='train', help='train, val, test, etc')
        self.parser.add_argument('--iter', type=int, default=0, help='Start from iteration i')
        self.parser.add_argument('--epochs', type=int, default=500, help='number of epochs to train for')
        self.parser.add_argument('--beta1', type=float, default=0.5, help='momentum term of adam')
        self.parser.add_argument('--lr', type=float, default=0.01, help='initial learning rate for adam')
        self.parser.add_argument('--gen_lr', type=float, default=0.001, help='initial learning rate for adam')
        self.parser.add_argument('--reg_lr', type=float, default=0.001, help='initial learning rate for adam')
        self.parser.add_argument('--alpha', type=float, default=75, help='alpha to weight l1 loss. default=500')
        self.parser.add_argument('--threshold', type=float, default=0.00, help='threshold for binary classification')
        self.parser.add_argument('--size_average', action='store_true', default=False,
                                 help='use average error over the mini-batch')
        self.parser.add_argument('--pretrained_model_folder', type=str, help='use average error over the mini-batch')
        self.parser.add_argument('--z_test', action='store_true', default=False,
                                 help='Use z value for test instead image.')
        self.parser.add_argument('--z_loss', action='store_true', default=False, help='Add z loss for training.')
        self.parser.add_argument('--z_multiplier', type=float, default=5.0, help='initial learning rate for adam')
        self.parser.add_argument('--zdim', type=int, default=150, help='size of the latent z vector')
        self.opt = None

    def parse(self):
        """ Parse Arguments.
        """
        # Training settings
        """
        seed = 10

        kwargs = {'num_workers': 1, 'pin_memory': True} if cuda else {}
        n_classes = 10
        z_dim = 2
        X_dim = 784
        y_dim = 10
        train_batch_size = args.batch_size
        valid_batch_size = args.batch_size
        N = 1000
        epochs = args.epochs



        self.opt = self.parser.parse_args()
        """
        self.opt = self.parser.parse_args()
        str_ids = self.opt.gpu_ids.split(',')
        self.opt.gpu_ids = []
        for str_id in str_ids:
            id = int(str_id)
            if id >= 0:
                self.opt.gpu_ids.append(id)

        # set gpu ids
        if len(self.opt.gpu_ids) > 0:
            torch.cuda.set_device(self.opt.gpu_ids[0])

        args = vars(self.opt)

        print('------------ Options -------------')
        for k, v in sorted(args.items()):
            print('%s: %s' % (str(k), str(v)))
        print('-------------- End ----------------')

        if self.opt.outf is None:
            now = datetime.now()
            self.opt.outf = "{}/{}/{}/{}".format("./output", self.opt.dataset, self.opt.model,
                                                 now.strftime('%y%m%d_%H%M'))

        if self.opt.pretrained_model_folder is None:
            self.opt.pretrained_model_folder = "{}/train/weights".format(self.opt.outf)

        expr_dir = os.path.join(self.opt.outf, 'train')
        test_dir = os.path.join(self.opt.outf, 'test')

        if not os.path.isdir(expr_dir):
            os.makedirs(expr_dir)
        if not os.path.isdir(test_dir):
            os.makedirs(test_dir)

        file_name = os.path.join(expr_dir, 'opt.txt')

        if self.opt.phase == 'train':
            with open(file_name, 'wt') as opt_file:
                opt_file.write('------------ Options -------------\n')
                for k, v in sorted(args.items()):
                    opt_file.write('%s: %s\n' % (str(k), str(v)))
                opt_file.write('-------------- End ----------------\n')

        return self.opt
