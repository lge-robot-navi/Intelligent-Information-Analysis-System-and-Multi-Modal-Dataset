#!/usr/bin/env python


from __future__ import print_function
import argparse
import os
import random
import torch
import torch.nn as nn
import torch.nn.parallel
import torch.backends.cudnn as cudnn
import torch.optim as optim
import torch.utils.data
import torchvision.datasets as dset
import torchvision.transforms as transforms
import torchvision.utils as vutils
from torch.autograd import Variable
import numpy as np
from model import _netG

import utils


from PIL import Image
import os.path
import sys


transform = transforms.Compose([transforms.Resize(300),
                                transforms.ToTensor(),
                                transforms.Normalize((0.5, 0.5, 0.5), (0.5, 0.5, 0.5))])


path = "/home/zaigham/context_encoder_pytorch/iccas_experiments/results_image2_128/reconstructed/"
#path = "/home/zaigham/etri_corridor_db/db_reduced_cropped/"
save_path = "/home/zaigham/context_encoder_pytorch/iccas_experiments/results_image2_300/"

dirs = os.listdir(path)
im_num=1
for item in dirs:
    print (item)
    fullpath = os.path.join(path, item)
    image = utils.load_image(fullpath)
    # image = utils.load_image(fullpath, opt.imageSize)
    #print(image.size())
    image = transform(image)


    name3 = (str(save_path) + item + '.png')
    utils.save_image(name3, image)
    #vutils.save_image(recon_image.data[0], str(save_path) + str(l2 * 10000) + str(im_num) + '_reconstructed_im' + str(l2) + '.png', normalize = True)



