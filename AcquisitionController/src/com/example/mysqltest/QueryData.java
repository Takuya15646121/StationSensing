package com.example.mysqltest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryData {
	
	private Connection con = null;
	
	public QueryData() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql_test1", "root", "1021");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void showBranches(String name){
		String sql = "select * from station_table where name="+name+";";
		try {
			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()){
				int stationId = resultSet.getInt("id");
				Statement joinStatement = con.createStatement();
				String joinSql = "select * from join_table where "
						+"prev_station="+Integer.toString(stationId)
						+" or to_station="+Integer.toString(stationId)+";";
				ResultSet joinResult = joinStatement.executeQuery(joinSql);
				while(joinResult.next()){
					int prevId = joinResult.getInt("prev_station");
					int toId = joinResult.getInt("to_station");
					int branchId = stationId==prevId ? toId:prevId;
					String branchSql = "select * from station_table where id="+Integer.toString(branchId)+";";
					Statement branchStatement = con.createStatement();
					ResultSet branchResult = branchStatement.executeQuery(branchSql);
					while(branchResult.next()){
						System.out.println(branchResult.getString("name"));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void closeConnection(){
		try {
			if(!con.isClosed()){
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args){
		/*
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql_test1", "root", "1021");
			Statement statement = con.createStatement();
			String sql = "select * from join_table";
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()){
				System.out.println(resultSet.getInt(3));
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		*/
		QueryData queryData = new QueryData();
		queryData.showBranches("'’†–ì'");
		queryData.closeConnection();
	}
}
