package com.example.mysqltest;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class MySQLTest {
	
	private Connection con = null;
	private Map<Integer,String> masterColumnMap;
	
	public MySQLTest(){
		this("jdbc:mysql://localhost:3306/mysql_test1", "root", "1021");
	}
	
	public MySQLTest(String url,String id,String pass){
		initConnection(url, id, pass);
		masterColumnMap = new HashMap<Integer, String>();
		initMap();
	}
	
	private void initConnection(String url,String id,String pass){
		try {
			con = DriverManager.getConnection(url,id,pass);
			System.out.println("接続できました");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected void initMap(){
		masterColumnMap.put(0, "id");
		masterColumnMap.put(2, "name");
		masterColumnMap.put(5, "rail_id");
		masterColumnMap.put(9, "longitude");
		masterColumnMap.put(10, "latitude");
	}
	
	//TODO:int配列とString配列を引数としてマップを初期化するメソッドを追加する．
	
	protected void putKeyValue(Integer key,String column){
		masterColumnMap.put(key, column);
	}
	
	public boolean makeStationTable(String fileName,String tableName,List<String> strList){
		//"res/station_master_201604.csv"
		try {
			List<String> lines = FileUtils.readLines(new File(fileName), "utf-8");
			
			for(int i=1;i<lines.size();i++){
				Statement statement = con.createStatement();
				String sql = null;
				String[] columns = lines.get(i).split(",");
				for(int key:masterColumnMap.keySet()){
					if(sql == null){
						sql = "insert into "+tableName+ " values(";
					} else {
						sql +=",";						
					}		
					String value = masterColumnMap.get(key);
					//if(value.equals("name")){
					if(strList.contains(value)){
						sql = sql + "'"+columns[key]+"'";
						continue;
					}
					sql += columns[key];
				}
				sql += ");";
				statement.executeUpdate(sql);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return false;
	}
	
	public void close(){
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	

	
	
}
