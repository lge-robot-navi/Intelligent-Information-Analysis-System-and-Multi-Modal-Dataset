#!/usr/bin/env python

#Older version that it used of pytorh: 0.3.1
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
import cv2
import utils
import time
from yolo_class import YoloDetector
from yappi_functions import init_yappi, finish_yappi


parser = argparse.ArgumentParser()

# Preprocessing arguments #
parser.add_argument('--sourceDir', default="./patches/video/", type=str, help='source directory')
# parser.add_argument('--targetDir',  default='/media/zaigham/SSD_1TB/gits/context_encoder_pytorch/Video_test/Codes and Trained Network/patches/final/', help='target directory')
parser.add_argument('--targetDir',  default='./patches/final/', help='target directory')
parser.add_argument('--patchSize', type=int, default=128, help='patch size')
parser.add_argument('--maxPatch', type=int, default=300, help='max # of patches')

# Testing arguments #
parser.add_argument('--dataset',  default='streetview', help='cifar10 | lsun | imagenet | folder | lfw ')
parser.add_argument('--test_image', required=False, help='path to dataset')
parser.add_argument('--workers', type=int, help='number of data loading workers', default=14)
parser.add_argument('--batchSize', type=int, default=1024, help='input batch size')
parser.add_argument('--imageSize', type=int, default=128, help='the height / width of the input image to network')
parser.add_argument('--nz', type=int, default=100, help='size of the latent z vector')
parser.add_argument('--ngf', type=int, default=64)
parser.add_argument('--ndf', type=int, default=64)
parser.add_argument('--nc', type=int, default=3)
parser.add_argument('--niter', type=int, default=25, help='number of epochs to train for')
parser.add_argument('--lr', type=float, default=0.0002, help='learning rate, default=0.0002')
parser.add_argument('--beta1', type=float, default=0.5, help='beta1 for adam. default=0.5')
parser.add_argument('--cuda', action='store_true', help='enables cuda')
parser.add_argument('--ngpu', type=int, default=2, help='number of GPUs to use')
parser.add_argument('--netG', default='model/netG_streetview.pth', help="path to netG (to continue training)")
parser.add_argument('--netD', default='', help="path to netD (to continue training)")
parser.add_argument('--outf', default='patches/results', help='folder to output images and model checkpoints')
parser.add_argument('--manualSeed', type=int, help='manual seed')
parser.add_argument('--nBottleneck', type=int,default=4000,help='of dim for bottleneck of encoder')
parser.add_argument('--overlapPred',type=int,default=4,help='overlapping edges')
parser.add_argument('--nef',type=int,default=64,help='of encoder filters in first conv layer')
parser.add_argument('--wtl2',type=float,default=0.999,help='0 means do not use else use with this weight')

# # YOLO arguments
# # parser.add_argument('--image_folder', type=str, default='data/samples', help='path to dataset')
# parser.add_argument('--config_path', type=str, default='config/yolov3.cfg', help='path to model config file')
# parser.add_argument('--weights_path', type=str, default='weights/yolov3.weights', help='path to weights file')
# parser.add_argument('--class_path', type=str, default='/media/zaigham/SSD_1TB/gits/PyTorch-YOLOv3-master/data/coco.names', help='path to class label file')
# parser.add_argument('--conf_thres', type=float, default=0.8, help='object confidence threshold')
# parser.add_argument('--nms_thres', type=float, default=0.4, help='iou thresshold for non-maximum suppression')
# # parser.add_argument('--batch_size', type=int, default=32, help='size of the batches')
# parser.add_argument('--n_cpu', type=int, default=8, help='number of cpu threads to use during batch generation')
# parser.add_argument('--img_size', type=int, default=416, help='size of each image dimension')
# parser.add_argument('--use_cuda', type=bool, default=True, help='whether to use cuda if available')
# #YOLO until here

opt = parser.parse_args()
print (opt)
source_dir = opt.sourceDir
output_dir = opt.targetDir
max_patch=opt.maxPatch

rng = np.random.RandomState(0)
patch_size = (opt.patchSize,opt.patchSize)

skip_frame = 5          #skip rate, process only after every  N frames
print_green = 0         #toggle for printing green boxes (normal patches) in output video
print_blue = 0          #toggle for printing blue boxes (non-overlapping red patches which are regarded as normal) in output video
print_yolo_box = 0      #toggle for printing yolo output boxes in output video
hist_eq = 0             #toggle for performing histogram equalization
adptv_hist_eq = 1       #toggle for performing adaptive histogram equalization

titles = ("1", "2")   #title of the video file to be tested, add one or more as ("title_1", "title_2") and so on
#patches = {100}         #Total patches to be extracted from frames
max_patches = {100}

#error threshold, if L2 error exceeds value, boxes will appear red.
threshold = 0.07
thresholds = {0.06}     #A thrshold list, if multiple threshold output is desired  {0.06, 0.07, 0.08} and so on

# values of frame height and width (to make sure input and output has the same frame dimensions)
frame_H = 1080          #video frame height
frame_W = 1920          #video frame width

crop_size = 150         # the height/width of the square shape crop patch


#patch size
patch_size = (opt.patchSize,opt.patchSize)

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


# yolo_wrapper(a)


# MAIN CODE

#YOLO related Code
def intersection(a,b):
    x = max(a[0], b[1])
    y = max(a[1], b[0])
    w = min(a[0]+a[2], b[1]+b[3]) - x
    h = min(a[1]+a[3], b[0]+b[2]) - y
    if w < 0 or h < 0:
        return 0  # or (0,0,0,0)
    else:
        return 1



global_frame_counter = 0



# init_yappi()


start_time = time.time()


for title in titles:
    video_frame_dir = opt.sourceDir + title;
    #video_frame_dir2 = '/media/zaigham/Data2/gits/context_encoder_pytorch/Video_test/Codes and Trained Network/patches/video/'
    print ('dir: ', video_frame_dir)



    # for patch in patches:

    for max_patch in max_patches:

            print("========================================================")
            print("================EXTRACTING FRAMES=======================")
            print("========================================================\n\n")

            print("Filename: {0}".format(title))
            print("Patch Size: {0}".format(patch_size))
            print("Max Patch: {0}\n".format(max_patch))

            try:
                width, height = patch_size
                patch_dir = opt.targetDir+'/patch'.format(width, height)
                crop_dir = opt.targetDir + '/crop'.format(width, height)
                recon_dir = opt.targetDir+'reconstruct'


                os.makedirs(patch_dir)
                os.makedirs(crop_dir)
                os.makedirs(recon_dir)
            except OSError:
                pass



            # Create a video capture instance
            vidcap = VideoCapture('patches/video/'+title +'.mp4')

            # Load single frame from the video
            success,image = vidcap.read()



            if hist_eq is 1:
                img_yuv = cv2.cvtColor(image, cv2.COLOR_BGR2YUV)

                # equalize the histogram of the Y channel
                img_yuv[:, :, 0] = cv2.equalizeHist(img_yuv[:, :, 0])

                # convert the YUV image back to RGB format
                image = cv2.cvtColor(img_yuv, cv2.COLOR_YUV2BGR)


            if adptv_hist_eq is 1:
                img_yuv = cv2.cvtColor(image, cv2.COLOR_BGR2YUV)

                # equalize the histogram of the Y channel
                clahe = cv2.createCLAHE(clipLimit=2.0, tileGridSize=(8, 8))
                img_yuv[:, :, 0] = clahe.apply(img_yuv[:, :, 0])
                image = cv2.cvtColor(img_yuv, cv2.COLOR_YUV2BGR)
                #cv2.imwrite(out_dir + "/frame%d_adap_hist_eq.jpg" % count, image2)  # save frame as JPEG file


            if success:
                print('video loaded successfully')

            # Frame counter
            frame_count = 0

            # Generator network instance
            netG = _netG(opt)
            netG.load_state_dict(torch.load(opt.netG,map_location=lambda storage, location: storage)['state_dict'])
            netG.eval()

            transform = transforms.Compose([transforms.Resize(opt.imageSize),
                                            transforms.ToTensor(),
                                            transforms.Normalize((0.5, 0.5, 0.5), (0.5, 0.5, 0.5))])

            # Create Instance for video writer
            format="XVID"
            fourcc = VideoWriter_fourcc(*format)


            # Create folder to save reconstructed frames
            out_dir = "patches/reconstruct_heat"
            #if not os.path.exists(video_frame_dir):
              #original_umask = os.umask(0)
              #os.makedirs(video_frame_dir, mode=0o777)

            outvids = []
            vids = []
            all_color_masks = []
            red_counts = []

            # statistics
            nGreens = []
            nBlues = []
            nReds = []

            # Color layer to be combined with the original frame image
            for _tresh in range(len(thresholds)):
                all_color_masks.append(Image.new('RGBA', (frame_H, frame_W), (0, 0, 0, 0)))
                red_counts.append(np.zeros([frame_H, frame_W]))
                vids.append(None)
                outvids.append("")

                nReds.append(0)
                nBlues.append(0)
                nGreens.append(0)

            # Continue loop while frames are loaded
            while success:



              #Resize and convert color of the image
              image = resize(image, (frame_W, frame_H))

              image_for_yolo = image


              image = cv2.cvtColor(image, cv2.COLOR_RGB2BGR)

              #frame image dimensions
              W, H, L = image.shape
              W_, H_ = patch_size

              #YOLO_wrapper(image)


              #crop sizes
              CropH, CropW = crop_size, crop_size
              CropX = int(round((W_ - CropH) / 2.))
              CropY = int(round((H_ - CropW) / 2.))

              if (frame_count % skip_frame == 0 or frame_count == 0):

                  # print('Processing frame {0}...'.format(frame_count))

                  #if video writer is not yet initialized, intialize
                  #YOLO CODE
                  cuda = torch.cuda.is_available()
                  Tensor = torch.cuda.FloatTensor if cuda else torch.FloatTensor
                  pil_image = Image.fromarray(np.uint8(image_for_yolo))
                  YD = YoloDetector(use_cuda=cuda)
                  detections = YD.yolo_detector(img=pil_image)
                  #YOLOCODE END

                  reconstructs = []
                  error_lists = []

                  for _tresh in range(len(thresholds)):
                      if vids[_tresh] is None:
                          size = image.shape[1], image.shape[0]
                          fps = 30
                          outvids[_tresh] = output_dir + title + "_results_reconstruct{0}.avi".format(_tresh)
                          vids[_tresh] = VideoWriter(outvids[_tresh], fourcc, float(fps), size, True)

                      #reconstructed image
                      reconstructs.append(np.zeros((W, H, L)).astype(int))

                      # list of errors
                      error_lists.append(np.empty([0, 3]))

                      #initialize color mask again
                      all_color_masks[_tresh] = Image.new('RGBA', (H, W), (0, 0, 0, 0))
                      red_counts[_tresh].fill(0)


                  #process for each patches to be extracted (size: max_patch)
                  for counter in range(max_patch):

                        x = 0
                        y = 0
                        overlap = 0

                        patch_props = [0,0,0,0]
                        #get random X and Y axis locations
                        patch_props[0] = randrange(0, W - W_)
                        patch_props[1] = randrange(0, H - H_)
                        patch_props[2] = patch_size[0]
                        patch_props[3] = patch_size[0]
                        #print (len(detections))
                        #print (detections[0] == None)
                        if detections[0] is not None:
                            for num_of_boxes in range(len(detections[0])):

                                if (detections[0][num_of_boxes][6].data).cpu().numpy() == 0.0:
                                    x1 = (detections[0][num_of_boxes][0].data).cpu().numpy()
                                    y1 = (detections[0][num_of_boxes][1].data).cpu().numpy()
                                    x2 = (detections[0][num_of_boxes][2].data).cpu().numpy()
                                    y2 = (detections[0][num_of_boxes][3].data).cpu().numpy()
                                    class_pred = (detections[0][num_of_boxes][6].data).cpu().numpy()
                                    # print('class Predict: ', class_pred)

                                    #print(int(detections[0][num_of_boxes][0]), int(detections[0][num_of_boxes][1]), int(detections[0][num_of_boxes][2]), int(detections[0][num_of_boxes][3]))
                                    #yolo_box_height = int(3.8 *(int(detections[0][num_of_boxes][3]) - int(detections[0][num_of_boxes][1])))  #y2-y1
                                    #yolo_box_width = int(3.8*(int(detections[0][num_of_boxes][2]) - int(detections[0][num_of_boxes][0]))) #x2-x1)
                                    #print (yolo_box_height, yolo_box_width)
                                    #yolo_block = [int(detections[0][num_of_boxes][0]), int(detections[0][num_of_boxes][1]), int(detections[0][num_of_boxes][2]), int(detections[0][num_of_boxes][3])]

                                    pad_x = max(image.shape[0] - image.shape[1], 0) * (416 / max(image.shape))
                                    pad_y = max(image.shape[1] - image.shape[0], 0) * (416 / max(image.shape))
                                    # Image height and width after padding is removed
                                    unpad_h = 416 - pad_y
                                    unpad_w = 416 - pad_x
                                    # x1 = int(detections[0][num_of_boxes][0])
                                    # y1 = int(detections[0][num_of_boxes][1])
                                    # x2 = int(detections[0][num_of_boxes][2])
                                    # y2 = int(detections[0][num_of_boxes][3])

                                    #print(image.shape[0])
                                    #print (image.shape[1])
                                    box_h = ((y2 - y1) / unpad_h) * image.shape[0]
                                    box_w = ((x2 - x1) / unpad_w) * image.shape[1]
                                    y1 = ((y1 - pad_y // 2) / unpad_h) * image.shape[0]
                                    x1 = ((x1 - pad_x // 2) / unpad_w) * image.shape[1]

                                    #print (box_h, box_w, y1,x1)
                                    if print_yolo_box == 1:
                                        cv2.rectangle(image, (int(x1),int(y1)), (int(x1+box_w), int(y1+box_h)), (0,0,255),2)


                                    yolo_block = [x1, y1, box_w, box_h]    #x-width axis, y-height axis, width, height
                                    if intersection(patch_props, yolo_block):
                                        overlap += 1
                                    # if overlap:
                                    #     print ('overlap', overlap)
                                    #print ('overlap: ', overlap)
                        else:
                            overlap = 0
                        if overlap == 0:
                            x = patch_props[0]
                            y = patch_props[1]
                        else:
                            overlap = 0
                            #print ('patch number for continue statement: ', counter)
                            continue


                        color_mask = (Image.new('RGBA', (H, W), (0, 0, 0, 0)))
                        draw = (ImageDraw.Draw(color_mask))

                        #get patch image
                        crop_image = np.zeros((W_, H_, L)).astype(int)
                        crop_image = image[x: x + W_, y: y + H_]

                        for _tresh in range(len(thresholds)):
                            reconstructs[_tresh][x:x + W_, y:y + H_] = crop_image

                        # Proceed with In-Painting
                        tensorImage = Image.fromarray(crop_image)
                        tensorImage = transform(tensorImage)
                        tensorImage = tensorImage.repeat(1, 1, 1, 1)

                        input_real = torch.FloatTensor(1, 3, opt.imageSize, opt.imageSize)
                        input_cropped = torch.FloatTensor(1, 3, opt.imageSize, opt.imageSize)
                        real_center = torch.FloatTensor(1, 3, int(opt.imageSize / 2), int(opt.imageSize / 2))

                        criterionMSE = nn.MSELoss()

                        input_real = Variable(input_real)
                        input_cropped = Variable(input_cropped)
                        real_center = Variable(real_center)

                        input_real.data.resize_(tensorImage.size()).copy_(tensorImage)

                        input_cropped.data.resize_(tensorImage.size()).copy_(tensorImage)
                        real_center_cpu = tensorImage[:, :, int(opt.imageSize / 4):int(opt.imageSize / 4) + int(opt.imageSize / 2),
                                        int(opt.imageSize / 4):int(opt.imageSize / 4) + int(opt.imageSize / 2)]
                        real_center.data.resize_(real_center_cpu.size()).copy_(real_center_cpu)

                        input_cropped.data[:, 0,
                        int(opt.imageSize / 4) + int(opt.overlapPred):opt.imageSize // 4 + opt.imageSize // 2 - opt.overlapPred,
                        opt.imageSize // 4 + opt.overlapPred:opt.imageSize // 4 + opt.imageSize // 2 - opt.overlapPred] = 2 * 117.0 / 255.0 - 1.0
                        input_cropped.data[:, 1,
                        opt.imageSize // 4 + opt.overlapPred:opt.imageSize // 4 + opt.imageSize // 2 - opt.overlapPred,
                        opt.imageSize // 4 + opt.overlapPred:opt.imageSize // 4 + opt.imageSize // 2 - opt.overlapPred] = 2 * 104.0 / 255.0 - 1.0
                        input_cropped.data[:, 2,
                        opt.imageSize // 4 + opt.overlapPred:opt.imageSize // 4 + opt.imageSize // 2 - opt.overlapPred,
                        opt.imageSize // 4 + opt.overlapPred:opt.imageSize // 4 + opt.imageSize // 2 - opt.overlapPred] = 2 * 123.0 / 255.0 - 1.0

                        fake = netG(input_cropped)

                        errG = criterionMSE(fake, real_center)

                        recon_image = input_cropped.clone()
                        recon_image.data[:, :, opt.imageSize // 4:opt.imageSize // 4 + opt.imageSize // 2,
                        opt.imageSize // 4:opt.imageSize // 4 + opt.imageSize // 2] = fake.data

                        t = real_center - fake

                        #compute for error
                        error = np.mean(np.square(t.data.numpy()))


                        _tresh = 0
                        for threshold in thresholds:

                            #compute for the color and intensity based on the error
                            if (error > threshold):

                                #update error count


                                red_counts[_tresh][x: x + W_, y: y + H_] += 1
                                value = np.array([x, y, error])

                                error_lists[_tresh] = np.vstack([error_lists[_tresh], value])

                                # max_alpha = 50
                                # alpha = max_alpha * (error * 10)
                                #
                                # draw.rectangle(((int(y + CropY), int(x + CropX)), (int(y + CropY + CropH), int(x + CropX + CropW))), fill=(255, 0, 0, int(alpha)))
                            else:
                                max_alpha = 15
                                alpha = max_alpha * (1 - error * 10)
                                # print(alpha)
                                if print_green == 1:
                                    draw.rectangle(((int(y + CropY), int(x + CropX)), (int(y + CropY + CropH), int(x + CropX + CropW))), fill=(0, 255, 0, int(alpha)))
                                nGreens[_tresh] += 1

                            #combine all colored boxes
                            all_color_masks[_tresh] = Image.alpha_composite(all_color_masks[_tresh], color_mask)
                            _tresh += 1


                  max_alpha = 50


                  for _tresh in range(len(thresholds)):
                      # look for errors with no overlaps
                      for error in range(len(error_lists[_tresh])):
                          color_mask = (Image.new('RGBA', (H, W), (0, 0, 0, 0)))
                          draw = (ImageDraw.Draw(color_mask))

                          x = int(error_lists[_tresh][error][0])
                          y = int(error_lists[_tresh][error][1])
                          L2 = error_lists[_tresh][error][2]

                          alpha = max_alpha * (L2 * 10)


                          #if error does not have any neighbors, show as error, else, remove

                          if (np.any(red_counts[_tresh][x: x + W_, y: y + H_] > 1)):
                              # print("error")
                              nReds[_tresh] += 1
                              draw.rectangle(((int(y + CropY), int(x + CropX)), (int(y + CropY + CropH), int(x + CropX + CropW))),fill=(255, 0, 0, int(alpha)))
                          else:
                              # print("remove")
                              nBlues[_tresh] += 1
                              if print_blue == 1:
                                draw.rectangle(((int(y + CropY), int(x + CropX)), (int(y + CropY + CropH), int(x + CropX + CropW))),fill=(0, 0, 255, int(alpha)))

                          all_color_masks[_tresh] = Image.alpha_composite(all_color_masks[_tresh], color_mask)

                  # reconpath = recon_dir + "/reconstruct_" + str(frame_count) + '.jpg'
                  # imwrite(reconpath, reconstruct)
                  #Composite = Image.open(reconpath)

              for _tresh in range(len(thresholds)):
                  #Combine frame image with colored boxes
                  Composite = Image.fromarray(image)
                  Composite.convert("RGB")
                  background = Image.new('RGBA', Composite.size, (255, 255, 255))
                  background.paste(Composite)
                  background = Image.alpha_composite(background, all_color_masks[_tresh])

                  # scipy.misc.toimage(background).save(recon_heat_dir + "/reconstruct_" + str(frame_count) + '_boxes' + '.png')
                  # img = imread(recon_heat_dir + "/reconstruct_" + str(frame_count) + '_boxes' + '.png')

                  # img_back = background.convert("RGB")
                  img_back = np.asarray(background)
                  img_back = img_back[:, :, :3]
                  img_back = cv2.cvtColor(img_back, cv2.COLOR_BGR2RGB)

                  #Save frames to video
                  vids[_tresh].write(img_back)

              #Load next image
              success,image = vidcap.read()

              # if(frame_count == 2):
              #     success = False

              frame_count += 1
              global_frame_counter +=1
              #print('frame: ', frame_count)
              print('frame: ', global_frame_counter)


            print("========================================================")
            print("================PROCESS COMPLETED=======================")
            print("========================================================\n\n")

            _tresh = 0
            for threshold in thresholds:
                print("Threshold : {0}".format(threshold))
                print("Number of Anomalies (Red) : {0}".format(nReds[_tresh]))
                print("Number of Isolated/Removed Anomalies (Blue) : {0}".format(nBlues[_tresh]))
                print("Number of Cleared Patches (Green) : {0}\n".format(nGreens[_tresh]))

                # Release video writer
                vids[_tresh].release()
                rename = output_dir + "{0}_P{1}_M{2}_T{3}_R{4}_B{5}_G{6}.avi".format(title, patch_size, max_patch,
                                                                                     threshold * 100, nReds[_tresh], nBlues[_tresh],
                                                                                     nGreens[_tresh])
                os.rename(outvids[_tresh], rename)
                _tresh+=1




print ('Time Consumed: ', time.time() - start_time)
print('total frames processed: ', global_frame_counter)

# finish_yappi()
# stats = yappi.get_func_stats()
# stats.save('callgrind.out', type = 'callgrind')
# yappi.get_func_stats().print_all()