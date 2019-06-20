#!/usr/bin/env python

#converts videos to imahes

import cv2
import os
import numpy as np
from cv2 import VideoWriter, VideoWriter_fourcc, imread, resize, VideoCapture, imwrite



print(cv2.__version__)

# out_dir = '/media/zaigham/SSD_1TB/etri_db/w_humans/adaptive_Histogram_equilized_frames_10fps'
# source_dir = '/media/zaigham/SSD_1TB/etri_db/w_humans/vids'
vid_1 = '/media/zaigham/SSD_1TB/gits/context_encoder_pytorch/Video_test/Codes and Trained Network/patches/final/rg/n5_P300_M300_T7.000000000000001_R736_B506_G181458.avi'
vid_2 = '/media/zaigham/SSD_1TB/gits/context_encoder_pytorch/Video_test/Codes and Trained Network/patches/final/hist_eq_full_frame_1mil /n5_P300_M300_T7.000000000000001_R668_B649_G181383.avi'
vid_3 = '/media/zaigham/SSD_1TB/gits/context_encoder_pytorch/Video_test/Codes and Trained Network/patches/final/adaptive thresholding /n5_P300_M300_T7.000000000000001_R798_B647_G181255.avi'
# histogram_eq = 0
# adaptive_hist_eq = 1
# videos = ['20180713_101224.mp4', '']

# file_list = os.listdir(source_dir)
count = 0
dilation_track_var = 0

vidcap_1 = cv2.VideoCapture(vid_1)
success, image_1 = vidcap_1.read()
cv2.putText(image_1,"No_hist", (1,1), cv2.FONT_HERSHEY_SIMPLEX, 10, (255,255,255))


vidcap_2 = cv2.VideoCapture(vid_2)
success, image_2 = vidcap_2.read()
cv2.putText(image_2,"hist_eq", (1,1), cv2.FONT_HERSHEY_SIMPLEX, 10, (255,255,255))

vidcap_3 = cv2.VideoCapture(vid_3)
success, image_3 = vidcap_3.read()
cv2.putText(image_3,"Adapt_hist_eq", (1,1), cv2.FONT_HERSHEY_SIMPLEX, 10, (255,255,255))

format = "XVID"
fourcc = VideoWriter_fourcc(*format)

# fourcc = cv2.VideoWriter_fourcc('mp4')  # Be sure to use lower case
# out = cv2.VideoWriter('stitched.mp4', fourcc, 30, (1920*2, 1080))
out_path = '/media/zaigham/SSD_1TB/gits/context_encoder_pytorch/Video_test/Codes and Trained Network/patches/final/'
size = image_1.shape[1], image_1.shape[0]+image_2.shape[0]+image_3.shape[0]

vid_writer = VideoWriter('/media/zaigham/SSD_1TB/gits/context_encoder_pytorch/Video_test/Codes and Trained Network/patches/final/'+'output.avi', fourcc, float(30), size, True)

while success:

        #for ch in range(3):
        #image2[:,:,ch] = cv2.equalizeHist(image[:, :, ch])
        image = np.concatenate((image_1, image_2), axis=0)
        image = np.concatenate((image, image_3), axis=0)




        #image_path = os.path.join(out_path, '/output')
        #frame = cv2.imread('')
        #image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)

        vid_writer.write(image)  # Write out frame to video

        # cv2.imshow('video', image_1)
        # imwrite ('/media/zaigham/SSD_1TB/gits/context_encoder_pytorch/Video_test/Codes and Trained Network/patches/final/1.jpg', image)



        success, image_1 = vidcap_1.read()
        success, image_2 = vidcap_2.read()
        success, image_3 = vidcap_3.read()
        print('Read a new frame: ', dilation_track_var, success)
        dilation_track_var += 1


print ('job finished')


# Release everything if job is finished
vid_writer.release()
