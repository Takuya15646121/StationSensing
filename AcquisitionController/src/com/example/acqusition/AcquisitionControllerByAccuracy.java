package com.example.acqusition;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.controller.AcquisitionController;

/**
 * Œo˜H”»’è•”•ª‚ÌƒIƒtƒ‰ƒCƒ“•]‰¿Œ‹‰Ê‚ÉŠî‚Ã‚¢‚Ä‘ªˆÊŠÔŠu‚ðŒˆ’è‚·‚é‚½‚ß‚ÌƒNƒ‰ƒX
 * @author user 
 *
 */
public class AcquisitionControllerByAccuracy extends AcquisitionControllerImpl{

	
	private List<Double> accuracyList;
	private double tempAccuracy;
	
	public AcquisitionControllerByAccuracy(List<Integer> idList, int splitNum) {
		super(idList, splitNum);
		if(idList.size() < 2)throw new IllegalArgumentException("‰w”‚ª2ˆÈ‰º‚Å‚·");
		initAccuracyList(idList.get(0) < idList.get(1),idList);
	}
	
	
	
	private void initAccuracyList(final boolean isOrdered,List<Integer> stationIdList){
		this.accuracyList = new ArrayList<Double>();
		
		String columnName = isOrdered ? "accuracy_order":"accuracy_reverse";
				
		for(int i=0;i<stationIdList.size()-1;i++){
			int[] orderedId = super.getOrderedStationId(stationIdList.get(i), stationIdList.get(i + 1));
			String sql = "select * from join_table where prev_station="
					+ Integer.toString(orderedId[0])
					+ " and to_station="
					+ Integer.toString(orderedId[1]) + ";";
		
			double accuracy = getAccuracy(super.getQueryResult(sql), columnName);
			
			this.accuracyList.add(accuracy);
		}
	}
	
	protected double getAccuracy(final ResultSet rs,final String columnName){
		try{
			while(rs.next()){
				return rs.getDouble(columnName);
			}
		} catch(SQLException e){
			e.printStackTrace();
		}
		return -1;
		
		
	}
	
	@Override
	public boolean canAcquisition(boolean result, long recentTime) {
		if(!result){
			//ˆ—
			return false;
		}
		
		
		return true;
	}

	@Override
	public void updateStation(String prevStation, String newStation) {
		
	}

	@Override
	public void updateStation(int prevId, int newId) {
		
	}

	@Override
	public void updateStation() {
		super.updateStation();
		this.tempAccuracy = this.accuracyList.remove(0);
	}

}
