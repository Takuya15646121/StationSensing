# -*- coding: shift_jis -*-
"""
Created on Thu Nov 26 22:25:13 2015

@author: user
"""

import sys
import numpy as np
import pandas as pd
import pickle
from station_attribute import attribute

array = pd.read_csv('data/data.csv',encoding='sjis',header=None).as_matrix()

# for elem in array:
# 	print(elem[0],elem[1],elem[2])

args = sys.argv

fil_att = open('feature/'+args[1]+'.dump')
extractor = pickle.load(fil_att)

fil_cls = open('classifier/'+args[1]+'.pkl')
classifier = pickle.load(fil_cls)


longitude = float(args[2])
latitude = float(args[3])
print longitude,latitude

attributes = extractor.make_attribute(np.array([latitude,longitude]),np.array([134,38]))
print attributes