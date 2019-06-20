import xml.etree.ElementTree as ET
import os
from shutil import copyfile

import argparse

parser = argparse.ArgumentParser(description='Vatic\'s XML format to MOT\'s ')
parser.add_argument('--include_occluded', default=True, type=bool, help='whether to include the occluded object to labels or not (default: include)')
parser.add_argument('--person_class_only', default=False, type=bool, help='whether to include person class only or not (default: not person class only)')

args = parser.parse_args()

label_root = '/media/astrid/DATA/Dataset/InternAnnotations'
label_filenames = ['intern1/56-1',
                   'intern1/identifier56-02',
                   'intern1/identifier56-03',
                   'intern1/identifier56-04',
                   'intern1/identifier76-01',
                   'intern1/identifier76-02',
                   'intern2/identifier01',
                   'intern2/identifier5502',
                   'intern2/identifier5503',
                   'intern2/identifier5506',
                   'intern2/identifier5701',
                   'intern2/identifier5702',
                   'intern2/identifier7800',
                   'intern2/indetifier5504']

images_root = label_root
images_folders = ['intern1/56-01',
                  'intern1/56-02',
                  'intern1/56-03',
                  'intern1/56-04',
                  'intern1/76-01',
                  'intern1/76-02',
                  'intern2/video1',
                  'intern2/video2',
                  'intern2/video3',
                  'intern2/video6',
                  'intern2/video5701',
                  'intern2/video5702',
                  'intern2/video7800',
                  'intern2/video4']

mot_label_root = '/media/astrid/DATA/Dataset/InternAnnotations/MOT_labels'
mot_label_filenames = ['56-01',
                       '56-02',
                       '56-03',
                       '56-04',
                       '76-01',
                       '76-02',
                       '55-01',
                       '55-02',
                       '55-03',
                       '55-06',
                       '57-01',
                       '57-02',
                       '78-00',
                       '55-04']

mot_images_root = '/media/astrid/DATA/Dataset/InternAnnotations/MOT_images'
mot_images_folders = mot_label_filenames

def take_frame(elem):
    return elem[0]

for label_filename, mot_label_filename in zip(label_filenames, mot_label_filenames):
    tree = ET.parse(os.path.join(label_root, label_filename + '.xml'))
    root = tree.getroot()
    object_count = int(root.attrib['count'])

    mot_list = []
    for track in root:
        track_id = int(track.attrib['id'])
        track_class = track.attrib['label']
        if track_class == 'person' or not args.person_class_only:
            for box in track:
                outside = int(box.attrib['outside'])
                occluded = int(box.attrib['occluded'])
                if outside == 0:
                    if occluded == 0 or args.include_occluded:
                        frame = int(box.attrib['frame'])
                        xtl = int(box.attrib['xtl'])
                        ytl = int(box.attrib['ytl'])
                        xbr = int(box.attrib['xbr'])
                        ybr = int(box.attrib['ybr'])

                        width = xbr - xtl
                        height = ybr - ytl

                        mot_list.append([frame+1, track_id+1, xtl, ytl, width, height, -1, -1, -1, -1])


    mot_list.sort(key=take_frame)

    # write to MOT
    F = open(os.path.join(mot_label_root, mot_label_filename + '.txt'), 'w')
    for mot_obj in mot_list:
        one_line = ""
        for idx, mot_obj_elem in enumerate(mot_obj):
            one_line += str(mot_obj_elem)
            if idx < (len(mot_obj) - 1):
                one_line += ','
            else:
                one_line += '\n'
        F.write(one_line)
    F.close()

    pass

############################################ images
for images_folder, mot_images_folder in zip(images_folders, mot_images_folders):

    # create folder
    if not os.path.exists(os.path.join(mot_images_root, mot_images_folder)):
        os.mkdir(os.path.join(mot_images_root, mot_images_folder))

    for _dir in os.listdir(os.path.join(images_root, images_folder, '0')):
        for src_img in os.listdir(os.path.join(images_root, images_folder, '0', _dir)):
            src_img_base = int(src_img[:-4])
            extension = src_img[-4:]
            copyfile(os.path.join(images_root, images_folder, '0', _dir, src_img), os.path.join(mot_images_root, mot_images_folder, str(src_img_base+1).zfill(6) + extension))










