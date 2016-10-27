package com.example.mysqltest;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryUtil {

	private Connection con;
	private static final QueryUtil singleton = new QueryUtil();
	
	private QueryUtil(){
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql_test1", "root", "1021");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static QueryUtil getInstance(){
		return singleton;
	}
	
	public double[] getLocation(String stationName){
		String sql = "select * from station_table where name='"+stationName+"';";
		double[] location = new double[2];
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				location[0] = rs.getDouble("latitude");
				location[1] = rs.getDouble("longitude");
			}
			
			return location;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return location;
		
	}
	
	
	public static void main(String[] args){
		QueryUtil util = QueryUtil.getInstance();
		double[] location = util.getLocation("’r‘Ü");
		System.out.println(Double.toString(location[0])+" "+Double.toString(location[1]));
		
	}
}
