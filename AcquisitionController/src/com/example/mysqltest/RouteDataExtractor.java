package com.example.mysqltest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

public class RouteDataExtractor {
	private Connection con = null;
	private List<Integer> stationIdList;
	
	public RouteDataExtractor(){
		try {
			this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql_test1", "root", "1021");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.stationIdList = new ArrayList<Integer>();
	}
	
	public void initStationIdList(String startName,String endName){
		ResultSet startResult = getRailIdResult(startName);
		ResultSet endResult = getRailIdResult(endName);
		
		List<Integer> startIdList = getIntegerList(startResult, "rail_id");
		List<Integer> endIdList = getIntegerList(endResult, "rail_id");
		
		int matchedId = getMatchedRailId(startIdList, endIdList);
		List<Integer> joinList = getConnectionList(matchedId);
		int startId = getId(startName,matchedId);
		int endId = getId(endName,matchedId);
		
		if(startId < endId){
			for(int joinId:joinList){
				if(startId <= joinId && joinId <= endId) stationIdList.add(joinId);
			}
			return;
		}
		for(int joinId:joinList){
			if(endId <= joinId && joinId <=startId) stationIdList.add(joinId);
		}
		Integer[] idArray = stationIdList.toArray(new Integer[0]);
		ArrayUtils.reverse(idArray);
		stationIdList = Arrays.asList(idArray);
		return;
	}
	
	public List<Integer> getStationList(){
		return new ArrayList<Integer>(this.stationIdList);
	}
	
	public void showStationId(){
		List<String> nameList = getStationIdList();
		for(String name:nameList){
			System.out.println("----show------"+name+"-----branches-----");
			QueryData qd = new QueryData();
			qd.showBranches("'"+name+"'");
		}
		//ãtèáÇ…Ç»ÇÈÇÃÇ≈join_tableÇ©ÇÁÇÕí èÌÇÃèÛë‘Ç≈åüçıÇ≈Ç´Ç»Ç¢
	}
	
	
	public List<String> getStationIdList(){
		List<String> nameList = new ArrayList<String>();
		for(int id:stationIdList){
			String sql = "select name from station_table where id="+Integer.toString(id)+";";
			try {
				Statement statement = con.createStatement();
				ResultSet resultSet = statement.executeQuery(sql);
				if(resultSet.next()){
					String name = resultSet.getString("name");
					nameList.add(name);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return nameList;
	}
	
	
	private int getId(String name,int railId){
		try {
			Statement statement = con.createStatement();
			String sql = "select id from station_table where rail_id="+Integer.toString(railId)+" and name='"+name+"';";
			ResultSet resultSet = statement.executeQuery(sql);
			if(resultSet.next())return resultSet.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	private List<Integer> getConnectionList(int railId){
		ResultSet resultSet = null;
		try {
			Statement statement = con.createStatement();
			String sql = "select * from join_table where rail_id="+Integer.toString(railId)+";";
			resultSet = statement.executeQuery(sql);
			
			List<Integer> connectionList = new ArrayList<Integer>();
			while(resultSet.next()){
				if(connectionList.size()==0){
					connectionList.add(resultSet.getInt("prev_station"));
					
				}
				connectionList.add(resultSet.getInt("to_station"));
			}
			return connectionList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	private int getMatchedRailId(List<Integer> startList,List<Integer> endList){
		for(int startId:startList){
			for(int endId:endList){
				if(startId == endId)return startId;
			}
		}
		return -1;
	}
	
	private ResultSet getRailIdResult(String name){
		ResultSet resultSet = null;
		try {
			Statement statement = con.createStatement();
			String sql = "select rail_id from station_table where name='"+name+"';";
			resultSet = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}
	
	
	private List<Integer> getIntegerList(ResultSet rs,String columnName){
		List<Integer> idList = new ArrayList<Integer>();
		
		try {
			while(rs.next()){
				idList.add(rs.getInt(columnName));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return idList;
	}
	
	
	public static void main(String[] args){
		RouteDataExtractor rde = new RouteDataExtractor();
		rde.initStationIdList("ëÂã{", "írë‹");
		rde.showStationId();
	}
	
}
