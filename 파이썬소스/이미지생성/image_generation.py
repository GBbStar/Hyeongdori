import numpy as np
import os
from os import listdir
from os.path import isfile, join
from PIL import Image

from tensorflow.keras.preprocessing.image import ImageDataGenerator, array_to_img, img_to_array, load_img

data_datagen = ImageDataGenerator(rescale=1. / 255,
                                  rotation_range=45,
                                  shear_range=0.5,
                                  horizontal_flip=True,
                                  vertical_flip=True,
                                  fill_mode='nearest')

filename_in_dir = []

for root, dirs, files in os.walk('images'):
    for fname in files:
        full_fname = os.path.join(root, fname)
        filename_in_dir.append(full_fname)

for file_image in filename_in_dir:
    img = load_img(file_image)
    x = img_to_array(img)
    x = x.reshape((1,) + x.shape)

    i = 0

    for batch in data_datagen.flow(x, save_to_dir='gen_images',
                                   save_prefix='gen', save_format='jpg'):
        i += 1
        if i > 6:
            break
