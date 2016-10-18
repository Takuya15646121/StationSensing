package com.example.controller;

public interface AcquisitionController {
	/**
	 * 
	 * @param result false...stop or other, true...running
	 * @return
	 */
	public boolean canAcquisition(boolean result,long recentTime);
	
	public void updateStation(String prevStation,String newStation);
	
	public void updateStation(int prevId,int newId);
	
	public void updateStation();
}
