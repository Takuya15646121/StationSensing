# -*- coding: shift_jis -*-
"""
Created on Wed Nov 25 13:11:46 2015

@author: user
"""
import numpy as np
import pandas as pd
import os
import pickle

def fild_all(directory):
    for root,dirs,files in os.walk(directory):
        yield root
        for file in files:
            yield os.path.join(root,file)

class attribute:
    def __init__(self):
        self.m_vec = None
        self.b_vecs = None
        self.attnames = []

    def init_table(self,master,branches):
        self.m_vec = np.array([master[1],master[2]])
        self.b_vecs = []
        
        self.attnames.append('lat')        
        self.attnames.append('lon')        
        
        for b in branches:
            self.b_vecs.append(np.array([b[1],b[2]]))
           
            self.attnames.append('dot'+b[0].encode(enc))
            self.attnames.append('norm ratio'+b[0].encode(enc))
            
        return map(str,self.attnames)
            
    def make_attribute(self,x,node_vec):
        result = []
        rel_vec = x-node_vec
        result.append(rel_vec[0])
        result.append(rel_vec[1])
        for b in self.b_vecs:
            vec_b = b - self.m_vec
            vec_u = x - node_vec
            norm_dot = np.dot(vec_b,vec_u)/(np.linalg.norm(vec_b)*np.linalg.norm(vec_u))
            result.append(norm_dot)
            result.append(np.linalg.norm(vec_u)/np.linalg.norm(vec_b))
        return result
        


station_name = '八王子'
file_path = 'File\station\\'+station_name
rail_path = 'railway'
master_file = 'table_master.csv'
branch_file = 'table_branches.csv'
enc = 'shift_jis'

if __name__=='__main__':
    
    
    master_path = file_path+'\\'+master_file
    branch_path = file_path+'\\'+branch_file
    
    master_vec = pd.read_csv(master_path,header=None,encoding=enc).as_matrix()
    branch_mat = pd.read_csv(branch_path,header=None,encoding=enc).as_matrix()
    
    node_vec = master_vec[0,1:3]    
    
    att = attribute()
    attnames = att.init_table(master_vec[0],branch_mat)
    
    all_files = fild_all(file_path+'\\'+rail_path)
    all_files = [x for x in all_files if os.path.isfile(x)==True]
    
    correct_label = []
    att_label = []
    for f in all_files:
        print f.decode(enc)
        x_matrix = pd.read_csv(f,encoding=enc).as_matrix()
        for x in x_matrix:
            correct_label.append(os.path.basename(f).rstrip('.csv'))
            att_label.append(att.make_attribute(np.array([x[1],x[2]]),node_vec))
    
    np.savetxt(file_path+'\label.csv',correct_label,fmt='%s',header='label',delimiter=',')
    np.savetxt(file_path+'\data.csv',att_label,fmt='%s',header=','.join(attnames),delimiter=',')
    
    att_result = open('featureexecutor\\'+station_name+'.dump','w')
    pickle.dump(att,att_result)
    
