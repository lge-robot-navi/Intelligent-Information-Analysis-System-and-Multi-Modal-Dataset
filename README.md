# X:MAS Extremely Large-Scale Multi-Modal Sensor Dataset for Outdoor Surveillance in Real Environments

# Organizations and participants
* ![](https://www.lge.co.kr/lgekor/asset/company/images/about/ci_img03.jpg)
* DongKi Noh, Advanced Robotics Lab. LG Electronics Inc. (E-mail: dongki.noh@lge.com)
* Seungmin Baek, Advanced Robotics Lab. LG Electronics Inc. (E-mail: seungmin2.baek@lge.com)
* Hyoung-Rock Kim, Advanced Robotics Lab. LG Electronics Inc. (E-mail: hyoungrock.kim@lge.com )
* Teayoung Uhm, Korea Institute of Robotics and Technology Convergence (KIRO). (E-mail: uty@kiro.re.kr)
* Hochul Shin, Electronics and Telecommunications Research Institute (ETRI). (E-mail: creatrix@etri.re.kr) 


# Goals
### Number of simultaneous monitoring agents
* Total number of mobile/fixed monitoring agents that can be controlled and scheduled per second (10 or more)

# Assignment
* Information analysis system design/implementation
* Multi-modal monitoring data management (MAMS - Multi-Agent Monitoring System)
* Outdoor abnormal situation DB purification/tagging

# Citation

* BibTeX X-MAS (RA-L)

  @article{noh2023x,
  title={X-MAS: Extremely Large-Scale Multi-Modal Sensor Dataset for Outdoor Surveillance in Real Environments},
  author={Noh, DongKi and Sung, Changki and Uhm, Teayoung and Lee, WooJu and Lim, Hyungtae and Choi, Jaeseok and Lee, Kyuewang and Hong, Dasol and Um, Daeho and Chung, Inseop and others},
  journal={IEEE Robotics and Automation Letters},
  volume={8},
  number={2},
  pages={1093--1100},
  year={2023},
  publisher={IEEE}
}

# License

This datasets are released under GNU LGPL (Lesser General Public License).

GNU Lesser General Public License Version 2.1, February 1999

# Information on our Dataset 
The dataset was collected using a cloud-based surveillance
system at Pohang and Gwangju in South Korea between
April 2017 and August 2021. The system was composed of
mobile robots, fixed sensor modules, and a cloud system for
outdoor surveillance.
* How to Download Dataset

If you send the purpose, name (including affiliation), and email
address to the author (uty@kiro.re.kr), the download website and password will be delivered. 



* Statistics on the dataset 

The X-MAS dataset contains annotated 2,624 sequences
and 556,499 pairs and is analyzed by place, agent, category, time, weather, and situation, as shown in Fig. 5. The number
of action classification sequences is 2,388 and the number
of detection/tracking sequences is 236.
However, total dataset including non-annotated dataset is as below:

<img width="70%" src="https://user-images.githubusercontent.com/51143120/202905372-ed457edc-7e8c-43bb-be76-d12eb346c7f5.png"/>

* Environments

A robot patrols around the building, and fixed cameras
are installed at the borders of the building and significant
points to collect the multi-modal sensor dataset for
surveillance tasks. Therefore, we selected two big sites for
operating our surveillance robots. The
robot moved along the pavement around the buildings under
various weather conditions including rainy and foggy days.
Walker, cyclists, and cars pass by the mobile robots and fixed
multi-modal sensor module. While the mobile robot patrols,
humans, cyclists, and cars are observed.

(The disaster robotics center in Pohang)

<p align="center">
<img width="70%" src="https://user-images.githubusercontent.com/51143120/201525234-75ffbe8b-3ecc-48f3-a932-2bf89804a9eb.png"/>
</p>
(The Nano industrial complex in Gwangju)

<p align="center">
<img width="70%" src="https://user-images.githubusercontent.com/51143120/201525301-803dde23-df82-48d4-95eb-5a886c7a3e4a.png"/>
</p>
* Multi-Modal Sensor Module

Our sensor module includes five different sensors. For the detailed information on the sensor
configuration, calibration, and synchronization, please refer
to our previous work. (T. Uhm, J. Park, J. Lee, G. Bae, G. Ki, and Y. Choi, “Design of
multimodal sensor module for outdoor robot surveillance system,”
Electronics, vol. 11, no. 14, p. 2214, 2022.)
![SensorConfiguration](https://user-images.githubusercontent.com/51143120/201525432-29c7a41b-cd18-447e-b1ce-a07e2fcc5f80.png)

* Detail Descriptions on our Dataset
<p align="center">
<img width="70%" src="https://user-images.githubusercontent.com/51143120/202033192-71e75529-5fdb-4523-8320-50443b359604.jpg"/>
<img width="70%" src="https://user-images.githubusercontent.com/51143120/202032420-c23d6336-7994-4236-84a6-c66adc5bf693.jpg"/>
</p>



* Information on Actors 

The numerous prominent researchers of LG Electronics, KIRO, SNU, KAIST, and ETRI for their hard work in developing, evaluating, and constructing datasets over the past
five years. We also used professional actors to provide various scenarios and realistic actions for our dataset. We hired two female and two male actors. The age of the actors is 20∼30. All actors are Korean. The average height of female actors is about 160 cm. The average height of male actors is about 175 cm.

* Information on Action Recognition DB related to surveillance tasks
 ![ActionDB](https://user-images.githubusercontent.com/51143120/201524932-06204fc5-18ad-4cdb-b66f-42946c76fc71.jpg)
 
# Sensor data Sychronization 
<p align="center">
<img width="70%" src="https://user-images.githubusercontent.com/51143120/202033649-369ea5a9-7c3f-4b75-b1e9-c9b30e0a1b08.png"/> 
</p> 
# USE CASES OF DEVELOPING SURVEILLANCE ALGORITHMS USING THE DATASET

* Note that we open our source code used in our surveillance system via
GitHub (https://github.com/kyuewang17/SNU_USR_dev). In the following sub-sections, the study presents various approaches for each module and results.

* Various use-cases have been introduced by our recent paper. 
  
  Title: X-MAS: Extremely Large-Scale Multi-Modal Sensor Dataset for Outdoor Surveillance in Real Environments (https://arxiv.org/abs/2212.14574)

  * BibTeX X-MAS (RA-L)

  @article{noh2023x,
  title={X-MAS: Extremely Large-Scale Multi-Modal Sensor Dataset for Outdoor Surveillance in Real Environments},
  author={Noh, DongKi and Sung, Changki and Uhm, Teayoung and Lee, WooJu and Lim, Hyungtae and Choi, Jaeseok and Lee, Kyuewang and Hong, Dasol and Um, Daeho and Chung, Inseop and others},
  journal={IEEE Robotics and Automation Letters},
  volume={8},
  number={2},
  pages={1093--1100},
  year={2023},
  publisher={IEEE}
}

* Real-time Tracking in the nighttime with RGB and nightvision images

In our work, we used a high-performance night vision camera so that it can collect high-quality grayscale images. We have analyzed the performance of the foreground/background segmentation and detection with the night vision camera qualitatively. As can be seen in the below figure, the night vision showed better performance than the thermal camera.
<p align="center">
<img width="70%" src="https://user-images.githubusercontent.com/51143120/201526405-5413ed80-9a44-4efb-acd2-a04918fdfdba.jpg"/>
<img width="70%" src="https://user-images.githubusercontent.com/51143120/201578420-86dd32cb-4cf6-47c2-9f2a-ce22bf437b10.jpg"/>
</p>
