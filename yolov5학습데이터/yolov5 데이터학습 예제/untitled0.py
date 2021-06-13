# -*- coding: utf-8 -*-
"""Untitled0.ipynb

Automatically generated by Colaboratory.

Original file is located at
    https://colab.research.google.com/drive/1mGayJh9QSQQkknJnKzMTwivvP8_rBHVi
"""

# Commented out IPython magic to ensure Python compatibility.
# %cd /content

!git clone https://github.com/ultralytics/yolov5.git

cd yolov5/

!pip install -r requirements.txt

# Commented out IPython magic to ensure Python compatibility.
# %cd /
 
from glob import glob
 
img_list=glob('/content/dataset/export/images/*.jpg')
 
print(len(img_list))

from sklearn.model_selection import train_test_split
 
train_img_list,val_img_list = train_test_split(img_list,test_size=0.1,random_state=2000)
print(len(train_img_list),len(val_img_list))
 
#나눈 데이터의 경로를 train.txt 와 val.txt에 각각 쓰기
with open('/content/dataset/train.txt','w') as f:
    f.write('\n'.join(train_img_list)+'\n')
 
with open('/content/dataset/val.txt','w') as f:
    f.write('\n'.join(val_img_list)+'\n')

import yaml
 
#yaml파일 불러오기
with open('/content/dataset/data.yaml','r') as f:
    data =yaml.load(f)
 
print(data)
 
#데이터 수정
data['train']='/content/dataset/train.txt'
data['val']='/content/dataset/val.txt'
#data['nc']=1
#data['names']=['air','bird','strawberry']
 
#yaml파일 저장
with open('/content/dataset/data.yaml','w') as f:
    yaml.dump(data,f)
 
print(data)

# Commented out IPython magic to ensure Python compatibility.
# %cd /content/yolov5
 
!python train.py --img 416 --batch 3 --epochs 500 --data /content/dataset/data.yaml --cfg ./models/yolov5s.yaml --weights yolov5s.pt --name yolov5s_results

# Commented out IPython magic to ensure Python compatibility.
# %load_ext tensorboard
# %tensorboard --logdir /content/yolov5/runs/

from IPython.display import Image
import os
 
val_img_path='ratest2.jpg'
 
!python detect.py --weights /content/yolov5/runs/train/yolov5s_results/weights/best.pt --img 416 --conf 0.5 --source "{val_img_path}"

pwd