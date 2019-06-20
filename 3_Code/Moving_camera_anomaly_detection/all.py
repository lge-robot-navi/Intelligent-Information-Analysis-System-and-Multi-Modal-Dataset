#!/usr/bin/env pythonglobal_errors = np.load(opt.targetDir + 'errors.npy')


from __future__ import absolute_import
from __future__ import division
from __future__ import print_function
from cv2 import VideoWriter, VideoWriter_fourcc, imread, resize, VideoCapture, imwrite
from model import _netG
from PIL import Image
from PIL import ImageDraw
from random import randrange
from torch.autograd import Variable
import argparse
import matplotlib.image as matlabimg
import ntpath
import numpy as np
import os
import os.path
import re
import scipy.misc
import scipy.misc
import torch.nn as nn
import torch.utils.data
import torchvision.transforms as transforms
import utils

title = '20180618_112658'
#title = '20180509_144129'
# title = '20180509_144229'
# title = '20180509_144316'

video_frame_dir = 'new_test_vids/'+title;

patch = 128
#patch = 300

crop_size = 128
# crop_size = 150


parser = argparse.ArgumentParser()

# Preprocessing arguments #
parser.add_argument('--sourceDir', default=video_frame_dir, type=str, help='source directory')
parser.add_argument('--targetDir',  default='patches/', help='target directory')
parser.add_argument('--patchSize', type=int, default=128, help='patch size')
parser.add_argument('--maxPatch', type=int, default=patch, help='max # of patches')

# Testing arguments #
parser.add_argument('--dataset',  default='streetview', help='cifar10 | lsun | imagenet | folder | lfw ')
parser.add_argument('--test_image', required=False, help='path to dataset')
parser.add_argument('--workers', type=int, help='number of data loading workers', default=4)
parser.add_argument('--batchSize', type=int, default=64, help='input batch size')
parser.add_argument('--imageSize', type=int, default=128, help='the height / width of the input image to network')
parser.add_argument('--nz', type=int, default=100, help='size of the latent z vector')
parser.add_argument('--ngf', type=int, default=64)
parser.add_argument('--ndf', type=int, default=64)
parser.add_argument('--nc', type=int, default=3)
parser.add_argument('--niter', type=int, default=25, help='number of epochs to train for')
parser.add_argument('--lr', type=float, default=0.0002, help='learning rate, default=0.0002')
parser.add_argument('--beta1', type=float, default=0.5, help='beta1 for adam. default=0.5')
parser.add_argument('--cuda', action='store_true', help='enables cuda')
parser.add_argument('--ngpu', type=int, default=1, help='number of GPUs to use')
parser.add_argument('--netG', default='model/netG_streetview.pth', help="path to netG (to continue training)")
parser.add_argument('--netD', default='', help="path to netD (to continue training)")
parser.add_argument('--outf', default='patches/results', help='folder to output images and model checkpoints')
parser.add_argument('--manualSeed', type=int, help='manual seed')
parser.add_argument('--nBottleneck', type=int,default=4000,help='of dim for bottleneck of encoder')
parser.add_argument('--overlapPred',type=int,default=4,help='overlapping edges')
parser.add_argument('--nef',type=int,default=64,help='of encoder filters in first conv layer')
parser.add_argument('--wtl2',type=float,default=0.999,help='0 means do not use else use with this weight')
opt = parser.parse_args()

source_dir = opt.sourceDir
output_dir = opt.targetDir
max_patch=opt.maxPatch

rng = np.random.RandomState(0)
patch_size = (opt.patchSize,opt.patchSize)
global_counter = 0
global_frame = 0
global_points = np.empty((0,max_patch,3), int)


# Sorts Data in Alphanumeric order
def sorted_alphanumeric(data):
    convert = lambda text: int(text) if text.isdigit() else text.lower()
    alphanum_key = lambda key: [ convert(c) for c in re.split('([0-9]+)', key) ]
    return sorted(data, key=alphanum_key)

# Generate random patches from the image
def generate_random_patches(input_image, size, number_of_patches, target_dir="."):
    global global_counter
    global global_points
    global global_frame

    W, H, L = input_image.shape
    W_, H_ = size

    points = np.empty((max_patch, 3), int)

    CropH, CropW = crop_size, crop_size
    CropX = int(round((W_ - CropH) / 2.))
    CropY = int(round((H_ - CropW) / 2.))

    for counter in range(number_of_patches):

        x = randrange(0, W - W_)
        y = randrange(0, H - H_)


        image = np.zeros((W_, H_, L)).astype(int)
        image = input_image[x: x + W_, y: y + H_]

        # crop_image = image.copy()
        # crop_image[CropX: CropX+CropW, CropY: CropY+CropH] =  (0,0,0)
        # scipy.misc.toimage(crop_image).save(crop_dir + "/" + str(global_counter) + '.png')

        points[counter] = global_counter, x, y

        scipy.misc.toimage(image).save(target_dir + "/" + str(global_counter) + '.png')

        global_counter+= 1
        # matlabimg.imsave(target_dir + "/" + str(counter) + '.png', image, format='png')

    # print(points.shape)
    global_points = np.append(global_points, points[np.newaxis, :, :], axis=0)
    #global_points = np.append(global_points, points, axis=0)
    global_frame+= 1

# Reconstruct the frame with the patches

def reconstruct_patches(points, recon_size, patch_size):
    W, H, L = recon_size
    W_, H_ = patch_size

    n_frames = points.shape[0]
    for frame in range(n_frames):
        image = np.zeros((W, H, L)).astype(int)

        for patches in range(points.shape[1]):
        # for patches in range(1):
            counter, x, y = points[frame][patches].astype(int)
            patch = scipy.misc.imread(patch_dir + "/" + str(counter) + '.png')
            image[x:x+W_, y:y+H_] = patch

        scipy.misc.toimage(image).save(recon_dir + "/reconstruct_" + str(frame) + '.png')

        image_box = Image.open(recon_dir + "/reconstruct_" + str(frame) + '.png')
        image_box = image_box.convert("RGBA")

        CropH, CropW = crop_size, crop_size
        CropX = int(round((W_ - CropH) / 2.))
        CropY = int(round((H_ - CropW) / 2.))

        for patches in range (points.shape[1]):
        # for patches in range(1):
            color_mask = Image.new('RGBA', image_box.size, (0, 0, 0, 0))
            draw = ImageDraw.Draw(color_mask)

            counter, x, y = points[frame][patches].astype(int)
            error = (global_errors[counter])

            if(error > threshold):
                max_alpha = 50
                alpha = max_alpha * (error * 10)

                draw.rectangle(((y+CropY, x+CropX), (y+CropY + CropH, x+CropX + CropW)), fill=(255, 0, 0, alpha))
            else:
                max_alpha = 15
                alpha = max_alpha * (1-error * 10)
                # print(alpha)

                draw.rectangle(((y + CropY, x + CropX), (y + CropY + CropH, x + CropX + CropW)), fill=(0, 255, 0, alpha))
            image_box = Image.alpha_composite(image_box, color_mask)

        if (frame % 10 == 0):
            print("Reconstructing Frame {0} of {1}. Completed {2}%".format(frame, n_frames, int((frame / n_frames) * 100)))

        # scipy.misc.toimage(image).save(directory + "/reconstruct_" + str(frame) + '.png')
        scipy.misc.toimage(image_box).save(recon_heat_dir + "/reconstruct_" + str(frame) + '_boxes' + '.png')


# Generate Video
def make_video(images, outimg=None, fps=5, size=None,
               is_color=True, format="XVID", outvid="image_video.avi"):
    """
    Create a video from a list of images.

    @param      outvid      output video
    @param      images      list of images to use in the video
    @param      fps         frame per second
    @param      size        size of each frame
    @param      is_color    color
    @param      format      see http://www.fourcc.org/codecs.php
    @return                 see http://opencv-python-tutroals.readthedocs.org/en/latest/py_tutorials/py_gui/py_video_display/py_video_display.html

    The function relies on http://opencv-python-tutroals.readthedocs.org/en/latest/.
    By default, the video will have the size of the first image.
    It will resize every image to this size before adding them to the video.
    """

    fourcc = VideoWriter_fourcc(*format)
    vid = None
    for image in images:
        if not os.path.exists(image):
            raise FileNotFoundError(image)
        img = imread(image)
        if vid is None:
            if size is None:
                size = img.shape[1], img.shape[0]
            vid = VideoWriter(outvid, fourcc, float(fps), size, is_color)
        if size[0] != img.shape[1] and size[1] != img.shape[0]:
            img = resize(img, size)
        vid.write(img)
    vid.release()
    return vid




# # MAIN CODE
#
#
# print("========================================================")
# print("================EXTRACTING FRAMES=======================")
# print("========================================================\n\n")
#
# vidcap = VideoCapture('video/'+title +'.mp4')
# success,image = vidcap.read()
# count = 0
# success = True
#
# if not os.path.exists(video_frame_dir):
#   os.makedirs(video_frame_dir)
#
# while success:
#   imwrite(video_frame_dir+str(count).zfill(5)+".jpg", image)     # save frame as JPEG file
#   success,image = vidcap.read()
#   print ('Read a new frame: ', success)
#   count += 1
#
#
# # generate_patches(images[0])
# try:
#     width, height = patch_size
#     patch_dir = opt.targetDir+'/patch'.format(width, height)
#     crop_dir = opt.targetDir + '/crop'.format(width, height)
#     recon_dir = opt.targetDir+'reconstruct'
#
#
#     os.makedirs(patch_dir)
#     os.makedirs(crop_dir)
#     os.makedirs(recon_dir)
# except OSError:
# 	pass
#
#
# images = []
# counter = 0
#
# filenames = sorted_alphanumeric(os.listdir(source_dir))
# total_files = len(filenames)
#
# print("========================================================")
# print("================GENERATING PATCHES======================")
# print("========================================================\n\n")
#
# # Loads the patches
# for filename in filenames:
#     path = os.path.join(source_dir, filename)
#
#     image_full = (matlabimg.imread(path))
#     generate_random_patches(image_full, patch_size, max_patch, patch_dir)
#
#     if (counter % 20 == 0):
#         print("Generating patches for Frame {0} of {1}. Completed {2}%".format(counter, total_files,int((counter/total_files) * 100)))
#     counter += 1
#
#
#
# #save point as file
# np.save(opt.targetDir + '/points', global_points)

netG = _netG(opt)
netG.load_state_dict(torch.load(opt.netG,map_location=lambda storage, location: storage)['state_dict'])
netG.eval()

transform = transforms.Compose([transforms.Resize(opt.imageSize),
                                transforms.ToTensor(),
                                transforms.Normalize((0.5, 0.5, 0.5), (0.5, 0.5, 0.5))])


path = "patches/patch/"
save_path = "patches/results/"

if not os.path.exists(path):
    os.makedirs(path)

if not os.path.exists(save_path):
    os.makedirs(save_path)

dirs = os.listdir(path)

count = len(dirs)

errors = np.empty((count,1))

im_num=1

dirs.sort()


print("========================================================")
print("================PERFORMING INPAINTING===================")
print("========================================================\n\n")

for item in dirs:


    name = int(os.path.splitext(ntpath.basename(item))[0])

    fullpath = os.path.join(path, item)

    if (im_num % 100 == 0):
       print("Processing Patch {0} of {1}. Completed {2}%".format(im_num, count, int((im_num / count) * 100)))

    image = utils.load_image(fullpath)
    image = transform(image)

    image = image.repeat(1, 1, 1, 1)

    input_real = torch.FloatTensor(1, 3, opt.imageSize, opt.imageSize)
    input_cropped = torch.FloatTensor(1, 3, opt.imageSize, opt.imageSize)
    real_center = torch.FloatTensor(1, 3, int(opt.imageSize/2), int(opt.imageSize/2))

    criterionMSE = nn.MSELoss()


    input_real = Variable(input_real)
    input_cropped = Variable(input_cropped)
    real_center = Variable(real_center)


    input_real.data.resize_(image.size()).copy_(image)

    input_cropped.data.resize_(image.size()).copy_(image)
    real_center_cpu = image[:,:,int(opt.imageSize/4):int(opt.imageSize/4)+int(opt.imageSize/2),int(opt.imageSize/4):int(opt.imageSize/4)+int(opt.imageSize/2)]
    real_center.data.resize_(real_center_cpu.size()).copy_(real_center_cpu)

    input_cropped.data[:,0,int(opt.imageSize/4)+int(opt.overlapPred):opt.imageSize//4+opt.imageSize//2-opt.overlapPred,opt.imageSize//4+opt.overlapPred:opt.imageSize//4+opt.imageSize//2-opt.overlapPred] = 2*117.0/255.0 - 1.0
    input_cropped.data[:,1,opt.imageSize//4+opt.overlapPred:opt.imageSize//4+opt.imageSize//2-opt.overlapPred,opt.imageSize//4+opt.overlapPred:opt.imageSize//4+opt.imageSize//2-opt.overlapPred] = 2*104.0/255.0 - 1.0
    input_cropped.data[:,2,opt.imageSize//4+opt.overlapPred:opt.imageSize//4+opt.imageSize//2-opt.overlapPred,opt.imageSize//4+opt.overlapPred:opt.imageSize//4+opt.imageSize//2-opt.overlapPred] = 2*123.0/255.0 - 1.0

    fake = netG(input_cropped)

    errG = criterionMSE(fake,real_center)

    recon_image = input_cropped.clone()
    recon_image.data[:,:,opt.imageSize//4:opt.imageSize//4+opt.imageSize//2,opt.imageSize//4:opt.imageSize//4+opt.imageSize//2] = fake.data

    t = real_center - fake
    l2 = np.mean(np.square(t.data.numpy()))


    name3 = str(save_path) + str(name) + '.png'
    utils.save_image(name3, recon_image.data[0])


    errors[name] = l2
    im_num += 1


np.save('patches/errors', errors)

patch_size = (opt.patchSize,opt.patchSize)
global_points = np.load(opt.targetDir + 'points.npy')
global_errors = np.load(opt.targetDir + 'errors.npy')

try:
    width, height = patch_size
    patch_dir = opt.targetDir + '/patch'.format(width, height)
    recon_dir = opt.targetDir+'reconstruct'
    recon_heat_dir = opt.targetDir + 'reconstruct_heat'

    if not os.path.exists(patch_dir):
        os.makedirs(patch_dir)

    if not os.path.exists(recon_dir):
        os.makedirs(recon_dir)

    if not os.path.exists(recon_heat_dir):
        os.makedirs(recon_heat_dir)

except OSError:
	pass


print("========================================================")
print("================RECONSTRUCTING FRAMES===================")
print("========================================================\n\n")


threshold = 0.06


reconstruct_patches(global_points, [720,1280,3], patch_size);



print("========================================================")
print("================SAVING FRAMES AS VIDEO==================")
print("========================================================\n\n")


out_dir = "patches/reconstruct_heat"
output = sorted_alphanumeric(os.listdir(out_dir))
list_images = []

for out in output:
    path = os.path.join(out_dir, out)
    list_images.append(path)

make_video(images=list_images, fps=30, outvid=output_dir+title+"_results_heat.avi")

out_dir = "patches/reconstruct"
output = sorted_alphanumeric(os.listdir(out_dir))
list_images = []
for out in output:
    path = os.path.join(out_dir, out)
    list_images.append(path)

make_video(images=list_images, fps=30, outvid=output_dir+title+"_results_reconstruct.avi")

print("\n\n========================================================")
print("================TASK COMPLETED!=========================")
print("========================================================")

