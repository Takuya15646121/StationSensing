package com.example.moving;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import weka.classifiers.Classifier;

public class SensingListener {
	
	private List<String> accLines = new ArrayList<String>();
	private List<String> magLines = new ArrayList<String>();
	
	public SensingListener(File accFile,File magFile){
		try {
			this.accLines = FileUtils.readLines(accFile);
			this.magLines = FileUtils.readLines(magFile);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int stepOver(MovingClassifier classifier,WindowWrapper wrapper){
		
		boolean accFlg = false;
		boolean magFlg = false;
		//time,accuracy,x,y,z,label
		
		do{
			String[] accElem = this.accLines.get(0).split(",");
			String[] magElem = this.magLines.get(0).split(",");
			long accTime = Long.parseLong(accElem[0]);
			long magTime = Long.parseLong(magElem[0]);
			
			if(accTime < magTime){
				
				this.accLines.remove(0);
				double x = Double.parseDouble(accElem[2]);
				double y = Double.parseDouble(accElem[3]);
				double z = Double.parseDouble(accElem[4]);
				boolean flg = wrapper.isAvailableAccWindow(x, y, z);
				if(!accFlg && flg){
					wrapper.setWindowAccQueue(classifier.getBase());
					accFlg = flg;
				}
				
			} else{
				
				this.magLines.remove(0);
				double x = Double.parseDouble(magElem[2]);
				double y = Double.parseDouble(magElem[3]);
				double z = Double.parseDouble(magElem[4]);
				boolean flg = wrapper.isAvailableMagWindowMag(x, y, z);
				if(!magFlg && flg){
					wrapper.setMagWindowQueue(classifier.getBase());
					magFlg = flg;
				}
			}
		
		}while(accFlg && magFlg);
		int result = classifier.classify();
		
		//�e�X�g���[�h�ł̓��x���̃C���f�b�N�X��n���悤�ɂ���
		return result;
	}
	
	
}
