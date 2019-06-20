from xml.dom import minidom

import cv2
import xml.etree.ElementTree as ET

#image_folder = '/media/zaigham/SSD_1TB/gits/context_encoder_pytorch/Video_test/Codes and Trained Network/!!11RESULTS_FOR_ANNUAL_REPORT_2018/Phone_video_abnormal_evaluation_excel_file/0.05/'

#file_name = image_folder + str(image) + '.jpg'
input_image = cv2.imread('0.jpg')
#height, width, layers = frame.shape
# cv2.rectangle(frame, (0,0), (1279,300),(255,255,255),1)



mydoc = ET.parse('output_xml.txt')
root = mydoc.getroot()

rt = root.attrib

#print(int(root.attrib['count']))

mot_list = []

for track in root:
        track_id = int(track.attrib['id'])
        for box in track:
            outside = int(box.attrib['outside'])
            occluded = int(box.attrib['occluded'])
            if outside == 0:
                    frame = int(box.attrib['frame'])
                    xtl = int(box.attrib['xtl'])
                    ytl = int(box.attrib['ytl'])
                    xbr = int(box.attrib['xbr'])
                    ybr = int(box.attrib['ybr'])

                    width = xbr - xtl
                    height = ybr - ytl

                    mot_list.append([frame, xtl, ytl, width, height])


#print (mot_list[0])
frame_number = 0
indices = [i for i, x in enumerate(mot_list) if x[0] == frame_number]
anomaly_annotated_boxes = []

num_of_objects = 0
for object in indices:
    # print (object)
    # m = mot_list[object][1]
    anomaly_annotated_boxes.append([mot_list[object][1], mot_list[object][2], mot_list[object][3], mot_list[object][4]])
    # anomaly_annotated_boxes[num_of_objects][0] = mot_list[object][1]
    # anomaly_annotated_boxes[num_of_objects][1] = mot_list[object][2]
    # anomaly_annotated_boxes[num_of_objects][2] = mot_list[object][3]
    # anomaly_annotated_boxes[num_of_objects][3] = mot_list[object][4]
    cv2.rectangle(input_image, (anomaly_annotated_boxes[num_of_objects][0], anomaly_annotated_boxes[num_of_objects][1]), (anomaly_annotated_boxes[num_of_objects][2]+anomaly_annotated_boxes[num_of_objects][0], anomaly_annotated_boxes[num_of_objects][3]+anomaly_annotated_boxes[num_of_objects][1]), (255, 255, 255), 1)
    num_of_objects +=1
    # cv2.imshow('djsa', input_image)
    # cv2.waitKey(0)
    # cv2.destroyAllWindows()

print(anomaly_annotated_boxes[1][0])
print(anomaly_annotated_boxes)
cv2.imshow('djsa', input_image)
cv2.waitKey(0)
cv2.destroyAllWindows()
#mydoc = minidom.parse('output_xml.txt')

#items = mydoc.getElementById('0')
items = mydoc.getElementsByTagName('box')

print(items[1].attributes['xtl'].value)


print ('sakjdhjsA')