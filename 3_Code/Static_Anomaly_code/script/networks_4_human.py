""" Network architectures.
"""
# pylint: disable=W0221,W0622,C0103,R0913

##
import torch
import torch.nn as nn
import torch.nn.functional as F
import torch.nn.parallel

##

N = 1000  # For discriminator?

# now image size 50x120

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
    elif classname.find('Linear') != -1:
        mod.weight.data.normal_(0.0, 0.01)


###
class Q_net(nn.Module):
    """
    DCGAN ENCODER NETWORK
    """

    # (opt.isize=32, opt.nz=100, opt.nc=6, opt.ngf=64, opt.ngpu=1, opt.extralayers=0)
    # def __init__(self, isize, nz, nc, ndf, ngpu, n_extra_layers=0, add_final_conv=True):
    def __init__(self, iheight, iwidth, nz, nc, ndf, ngpu, n_extra_layers=0, add_final_conv=True):
        super(Q_net, self).__init__()
        self.ngpu = ngpu
        assert iwidth % 16 == 0, "isize has to be a multiple of 16"

        main = nn.Sequential()
        # input is nc x isize x isize
        main.add_module('initial_conv_{0}-{1}'.format(nc, ndf),
                        nn.Conv2d(nc, ndf, 4, 2, 1, bias=True))
        #main.add_module('initial.batchnorm', nn.BatchNorm2d(ndf))
        main.add_module('initial_relu_{0}'.format(ndf),
                        # nn.ReLU(inplace=True))
                        nn.LeakyReLU(0.2, inplace=True))
        # nn.PReLU(ndf))
        csize, cndf = iwidth / 2, ndf
        # csize, cndf = iheight , ndf
        self.nz = nz

        # Extra layers
        for t in range(n_extra_layers):
            main.add_module('extra-layers-{0}_{1}_conv'.format(t, cndf),
                            nn.Conv2d(cndf, cndf, 3, 1, 1, bias=False))
            main.add_module('extra-layers-{0}_{1}_batchnorm'.format(t, cndf), nn.BatchNorm2d(cndf))
            main.add_module('extra-layers-{0}_{1}_relu'.format(t, cndf),
                            # nn.ReLU(inplace=True))
                            nn.LeakyReLU(0.2, inplace=True))
            # nn.PReLU(cndf))

        # while csize > 4:
        # cndf = 64
        # csize=6
        # thus while will run once
        # as a results, # of channels 3--> 64 -->128
        while csize > 4:
            in_feat = cndf
            out_feat = cndf * 2
            main.add_module('pyramid_{0}-{1}_conv'.format(in_feat, out_feat),
                            nn.Conv2d(in_feat, out_feat, 4, 2, 1, bias=False))
            main.add_module('pyramid_{0}_batchnorm'.format(out_feat),
                            nn.BatchNorm2d(out_feat))
            main.add_module('pyramid_{0}_relu'.format(out_feat),
                            # nn.ReLU(inplace=True))
                            nn.LeakyReLU(0.2, inplace=True))
            # nn.PReLU(out_feat))
            cndf = cndf * 2
            csize = csize / 2

        # state size. K x 6 x 3 (NCHW)
        if add_final_conv:
            main.add_module('final_{0}-{1}_conv'.format(cndf, 1),
                            nn.Conv2d(cndf, self.nz, 4, 1, 0, bias=True))
        # by silee. add final linear layer
        # main.add_module('final.{0}-{1}.linear'.format(cndf*3*3, nz),
        #                nn.Linear(cndf*3*3, nz))

        self.main = main

    def forward(self, input):
        if isinstance(input.data, torch.cuda.FloatTensor) and self.ngpu > 1:
            output = nn.parallel.data_parallel(self.main, input, range(self.ngpu))
        else:
            output = self.main(input)

        # squeeze removes dimensions where its size =1, thus squeezing a tensor of size (2,1,1,5) becomes (2,5)
        # BCHW
        output = output.view(-1, self.nz).squeeze()
        return output


##
# (opt.isize=32, opt.nz=100, opt.nc=3, opt.ngf=64, opt.ngpu=1, opt.extralayers=0)
# ngf = number of final channels
# iwidth =24, iheight=12
class P_net(nn.Module):
    """
    DCGAN DECODER NETWORK
    """

    def __init__(self, iheight, iwidth, nz, nc, ngf, ngpu, n_extra_layers=0):
        super(P_net, self).__init__()
        self.ngpu = ngpu

        # from the encoder, the channel numbers are like below
        # as a results, # of channels 3--> 64 -->128
        assert iwidth % 16 == 0, "isize has to be a multiple of 16"

        self.nz = nz
        cngf, tisize = ngf // 2, 4

        # cngf=32, tisize = 3, iheight=12
        while tisize != iwidth:
            cngf = cngf * 2
            tisize = tisize * 2

        # cngf, tisize ==128, 12
        main = nn.Sequential()

        # main.add_module('initial.{0}-{1}.linear'.format(1, 3*3*cngf),
        #                 nn.Linear(self.nz, 3*3*cngf, bias=False))

        # 100->128
        main.add_module('initial_{0}-{1}_convt'.format(self.nz, cngf),
                        nn.ConvTranspose2d(self.nz, cngf, 4, 1, 0, bias=False))
        main.add_module('initial_{0}_batchnorm'.format(cngf),
                        nn.BatchNorm2d(cngf))
        main.add_module('initial_{0}_relu'.format(cngf),
                        nn.ReLU(inplace=True))
        # nn.LeakyReLU(0.2, inplace=True))
        # nn.PReLU(cngf))

        csize, _ = 4, cngf
        while csize < (iwidth // 2):
            main.add_module('pyramid_{0}-{1}_convt'.format(cngf, cngf // 2),
                            nn.ConvTranspose2d(cngf, cngf // 2, 4, 2, 1, bias=False))
            main.add_module('pyramid_{0}_batchnorm'.format(cngf // 2),
                            nn.BatchNorm2d(cngf // 2))
            main.add_module('pyramid_{0}_relu'.format(cngf // 2),
                            nn.ReLU(inplace=True))
            # nn.LeakyReLU(0.2, inplace=True))
            # nn.PReLU(cngf//2))
            cngf = cngf // 2
            csize = csize * 2

        # Extra layers
        for t in range(n_extra_layers):
            main.add_module('extra-layers-{0}_{1}_conv'.format(t, cngf),
                            nn.Conv2d(cngf, cngf, 3, 1, 1, bias=False))
            main.add_module('extra-layers-{0}_{1}_batchnorm'.format(t, cngf),
                            nn.BatchNorm2d(cngf))
            main.add_module('extra-layers-{0}_{1}_relu'.format(t, cngf),
                            nn.ReLU(inplace=True))
            # nn.LeakyReLU(0.2, inplace=True))
            # nn.PReLU(cngf))

        main.add_module('final_{0}-{1}_convt'.format(cngf, nc),
                        nn.ConvTranspose2d(cngf, nc, 4, 2, 1, bias=True))
        main.add_module('final_{0}_tanh'.format(nc),
                        nn.Tanh())
        self.main = main

    def forward(self, z):

        z = z.view(-1, self.nz, 1, 1)
        if isinstance(z.data, torch.cuda.FloatTensor) and self.ngpu > 1:
            output = nn.parallel.data_parallel(self.main, z, range(self.ngpu))
        else:
            output = self.main(z)
        return output


class D_net(nn.Module):
    def __init__(self, nz, N):
        super(D_net, self).__init__()
        self.lin1 = nn.Linear(nz, N)
        self.lin2 = nn.Linear(N, N)
        self.lin3 = nn.Linear(N, 1)

    def forward(self, x):
        x = F.dropout(self.lin1(x), p=0.5, training=self.training)
        x = F.relu(x)
        # x = F.leaky_relu(x, 0.2, inplace=True)
        x = F.dropout(self.lin2(x), p=0.5, training=self.training)
        # x = F.leaky_relu(x, 0.2, inplace=True)
        x = F.relu(x)

        return F.sigmoid(self.lin3(x))
