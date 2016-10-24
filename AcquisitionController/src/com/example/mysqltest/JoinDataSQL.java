package com.example.mysqltest;

import java.sql.SQLException;
import java.util.ArrayList;

public class JoinDataSQL extends MySQLTest{
	
	public JoinDataSQL() {
		super();
	}
	
	public JoinDataSQL(String url,String id,String pass){
		super(url,id,pass);
	}
	
	@Override
	protected void initMap() {
		super.putKeyValue(0, "rail_id");
		super.putKeyValue(1, "prev_station");
		super.putKeyValue(2, "to_station");
		super.putKeyValue(3, "time");
		super.putKeyValue(4, "accuracy_order");
		super.putKeyValue(5, "accuracy_reverse");
	}
	
	public static void main(String[] args) throws SQLException{		
		MySQLTest test = new JoinDataSQL();
		test.makeStationTable("res/join_201604.csv","join_table",new ArrayList<String>());
		test.close();
	}
	
	
}
