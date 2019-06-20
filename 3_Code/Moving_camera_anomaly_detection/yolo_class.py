from __future__ import division

from models import *
from utils.utils import *
from utils.datasets import *

import os
import sys
import time
import datetime
import argparse

import torch
from torch.utils.data import DataLoader
from torchvision import datasets
from torch.autograd import Variable
from torchvision.transforms import ToTensor
from torchvision.transforms import Scale

import matplotlib.pyplot as plt
import matplotlib.patches as patches
from matplotlib.ticker import NullLocator
from PIL import Image



class YoloDetector():


    def __init__(self, use_cuda=True):
        self.config_path = 'config/yolov3.cfg'
        self.weights_path = 'weights/yolov3.weights'
        self.class_path = '/media/zaigham/SSD_1TB/gits/PyTorch-YOLOv3-master/data/coco.names'
        self.conf_thres = 0.8
        self.nms_thres = 0.4
        self.batch_size = 32
        self.n_cpu = 8
        self.img_size = 416
        self.use_cuda = use_cuda


    def yolo_detector(self, img):



        model = Darknet(self.config_path, img_size=self.img_size)
        model.load_weights(self.weights_path)
        if self.use_cuda:
            model.cuda()
            model.eval()

        #classes = load_classes(self.class_path)  # Extracts class labels from file

        loader = transforms.Compose([transforms.Scale([self.img_size, self.img_size])])
        image = loader(img)

        image = ToTensor()(image).unsqueeze(0)

        image = Variable(image)

        image=image.cuda()
        #image = image[None, :, :, :]

        #print ('dete')
        detections = model(image)
        detections = non_max_suppression(detections, 80, self.conf_thres, self.nms_thres)
        return detections

#
# #
# # #
# cuda = torch.cuda.is_available()
# Tensor = torch.cuda.FloatTensor if cuda else torch.FloatTensor
# #
# print (cuda)
# image = Image.open('/media/zaigham/SSD_1TB/gits/PyTorch-YOLOv3-master/one_image/giraffe.jpg')
# #
# # loader = transforms.Compose([transforms.Scale([1080, 1920]), transforms.ToTensor()])
# #
# # pil_image = Image.fromarray(np.uint8(image))
# # pil_image = loader(pil_image)
# #
# # pil_image = Variable(pil_image, requires_grad=True)
# #
# YD = YoloDetector(use_cuda=cuda)
# detections = YD.yolo_detector(img=image)
# print (len(detections[0]))
#
#

#
# #
# # parser = argparse.ArgumentParser()
# # parser.add_argument('--image_folder', type=str, default='data/samples', help='path to dataset')
# # parser.add_argument('--config_path', type=str, default='config/yolov3.cfg', help='path to model config file')
# # parser.add_argument('--weights_path', type=str, default='weights/yolov3.weights', help='path to weights file')
# # parser.add_argument('--class_path', type=str, default='/media/zaigham/SSD_1TB/gits/PyTorch-YOLOv3-master/data/coco.names', help='path to class label file')
# # parser.add_argument('--conf_thres', type=float, default=0.8, help='object confidence threshold')
# # parser.add_argument('--nms_thres', type=float, default=0.4, help='iou thresshold for non-maximum suppression')
# # parser.add_argument('--batch_size', type=int, default=32, help='size of the batches')
# # parser.add_argument('--n_cpu', type=int, default=8, help='number of cpu threads to use during batch generation')
# # parser.add_argument('--img_size', type=int, default=416, help='size of each image dimension')
# # parser.add_argument('--use_cuda', type=bool, default=True, help='whether to use cuda if available')
# opt = parser.parse_args()
# print(opt)
#
# cuda = torch.cuda.is_available() and opt.use_cuda
#
# os.makedirs('output', exist_ok=True)
#
# # Set up model
# model = Darknet(opt.config_path, img_size=opt.img_size)
# model.load_weights(opt.weights_path)
#
# if cuda:
#     model.cuda()
#
# model.eval() # Set in evaluation mode
#
# dataloader = DataLoader(ImageFolder(opt.image_folder, img_size=opt.img_size),
#                         batch_size=opt.batch_size, shuffle=False, num_workers=opt.n_cpu)
#
#
# classes = load_classes(opt.class_path) # Extracts class labels from file
#
# Tensor = torch.cuda.FloatTensor if cuda else torch.FloatTensor
#
# imgs = []           # Stores image paths
# img_detections = [] # Stores detections for each image index
#
# print ('\nPerforming object detection:')
# prev_time = time.time()
#
#
# loader = transforms.Compose([transforms.Scale([opt.img_size,opt.img_size]), transforms.ToTensor()])
#
# def image_loader(image_name):
#     """load image, returns cuda tensor"""
#     image = Image.open(image_name)
#     image = loader(image).float()
#     image = Variable(image, requires_grad=True)
#     # image = image.unsqueeze(0)  #this is for VGG, may not be needed for ResNet
#     return image.cuda()  #assumes that you're using GPU
#
# image2 = image_loader('/media/zaigham/SSD_1TB/gits/PyTorch-YOLOv3-master/one_image/giraffe.jpg')
#
# image=image2[None, :, :, :]
#
#
#
#
# # for batch_i, (img_paths, input_imgs) in enumerate(dataloader):
#     # Configure input
#     #input_imgs = Variable(input_imgs.type(Tensor))
#
#     # # Get detections
#     # with torch.no_grad():
#     #     detections = model(input_imgs)
#     #     detections = non_max_suppression(detections, 80, opt.conf_thres, opt.nms_thres)
#     # Get detections
# with torch.no_grad():
#     detections = model(image)
#     detections = non_max_suppression(detections, 80, opt.conf_thres, opt.nms_thres)
#
#
# # Log progress
# # current_time = time.time()
# # inference_time = datetime.timedelta(seconds=current_time - prev_time)
# # prev_time = current_time
# # print ('\t+ Batch %d, Inference Time: %s' % (batch_i, inference_time))
# img_paths = []
# # Save image and detections
# imgs.extend(img_paths)
# img_detections.extend(detections)
#
# # Bounding-box colors
# cmap = plt.get_cmap('tab20b')
# colors = [cmap(i) for i in np.linspace(0, 1, 20)]
#
# print ('\nSaving images:')
# # Iterate through images and save plot of detections
#
# # print ("(%d) Image: '%s'" % (img_i, path))
#
# for img_i, (path, detections) in enumerate(zip(imgs, img_detections)):
#
#
#     # Create plot
#     img = np.array(Image.open('/media/zaigham/SSD_1TB/gits/PyTorch-YOLOv3-master/one_image/giraffe.jpg'))
#     plt.figure()
#     fig, ax = plt.subplots(1)
#     ax.imshow(img)
#
#     # The amount of padding that was added
#     pad_x = max(img.shape[0] - img.shape[1], 0) * (opt.img_size / max(img.shape))
#     pad_y = max(img.shape[1] - img.shape[0], 0) * (opt.img_size / max(img.shape))
#     # Image height and width after padding is removed
#     unpad_h = opt.img_size - pad_y
#     unpad_w = opt.img_size - pad_x
#
#     # Draw bounding boxes and labels of detections
#     if detections is not None:
#         d=detections[0]
#         unique_labels = detections[:, -1].cpu().unique()
#         n_cls_preds = len(unique_labels)
#         bbox_colors = random.sample(colors, n_cls_preds)
#         for x1, y1, x2, y2, conf, cls_conf, cls_pred in detections:
#
#             print ('\t+ Label: %s, Conf: %.5f' % (classes[int(cls_pred)], cls_conf.item()))
#
#             # Rescale coordinates to original dimensions
#             box_h = ((y2 - y1) / unpad_h) * img.shape[0]
#             box_w = ((x2 - x1) / unpad_w) * img.shape[1]
#             y1 = ((y1 - pad_y // 2) / unpad_h) * img.shape[0]
#             x1 = ((x1 - pad_x // 2) / unpad_w) * img.shape[1]
#
#             color = bbox_colors[int(np.where(unique_labels == int(cls_pred))[0])]
#             # Create a Rectangle patch
#             bbox = patches.Rectangle((x1, y1), box_w, box_h, linewidth=2,
#                                     edgecolor=color,
#                                     facecolor='none')
#             # Add the bbox to the plot
#             ax.add_patch(bbox)
#             # Add label
#             plt.text(x1, y1, s=classes[int(cls_pred)], color='white', verticalalignment='top',
#                     bbox={'color': color, 'pad': 0})
#
#     # Save generated image with detections
#     plt.axis('off')
#     plt.gca().xaxis.set_major_locator(NullLocator())
#     plt.gca().yaxis.set_major_locator(NullLocator())
#     plt.savefig('output/%d.png' % (img_i), bbox_inches='tight', pad_inches=0.0)
#
#     plt.close()
