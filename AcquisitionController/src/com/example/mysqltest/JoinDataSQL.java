package com.example.mysqltest;

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
	}
	
	
	
	
}
