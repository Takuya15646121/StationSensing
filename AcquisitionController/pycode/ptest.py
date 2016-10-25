# -*- coding: shift_jis -*-
"""
Created on Thu Nov 26 22:25:13 2015

@author: user
"""

import sys
import numpy as np
import pandas as pd
import pickle

array = pd.read_csv('data/data.csv',encoding='sjis',header=None).as_matrix()

# for elem in array:
# 	print(elem[0],elem[1],elem[2])

fil = open('classifier/uni.pkl')
classifier = pickle.load(fil)

args = sys.argv

print args[1]