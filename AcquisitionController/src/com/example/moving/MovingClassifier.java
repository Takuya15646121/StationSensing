package com.example.moving;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tuat.daily.phonepos.feature.base.BaseFeature;
import tuat.daily.phonepos.feature.base.IBaseFeature;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffLoader;

public class MovingClassifier {
	
	private Classifier classfier;
	private IBaseFeature base;
	
	public MovingClassifier(File modelPath,File arffPath){
		try {
			this.classfier = (Classifier)SerializationHelper.read(modelPath.getAbsolutePath());
			String[] featureArray = getFeatures(arffPath);
			this.base = new BaseFeature(featureArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public IBaseFeature getBase(){
		return this.base;
	}
	
	
	private String[] getFeatures(File arffPath){
		
		List<String> featureList = new ArrayList<String>();
		ArffLoader loader = new ArffLoader();
		try {
			loader.setFile(arffPath);
			Instances instances = loader.getDataSet();
			for(int i=0;i<instances.numAttributes()-1;i++){
				featureList.add(instances.attribute(i).name());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return featureList.toArray(new String[0]);
	}
	
	
	public int classify(){
		Instance instance = base.makeInstance();
		try {
			double type = this.classfier.classifyInstance(instance);
			return (int)type;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return -1;
	}
	
}
