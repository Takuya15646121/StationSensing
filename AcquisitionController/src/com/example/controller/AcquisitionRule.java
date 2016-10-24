package com.example.controller;

public enum AcquisitionRule {
	
	RULE()
	;
	
	public long calcAcquisitionTime(final long betweenTime,double accuracy){
		if(accuracy > 0.9){
			return betweenTime/10;
		}
		if(accuracy <= 0.9 && accuracy > 0.5){
			return betweenTime/5;
		}
		if(accuracy <= 0.5){
			return betweenTime/2;
		}
		return betweenTime;
	}
}
