package com.example.mysqltest;

import java.util.Arrays;

public class LineDataSQL extends MySQLTest{

	public LineDataSQL() {
		super();
	}
	
	public LineDataSQL(String url,String id,String pass){
		super(url,id,pass);
	}
	
	@Override
	protected void initMap() {
		super.putKeyValue(0, "rail_id");
		super.putKeyValue(2, "rail_name");
	}
	
	
	public static void main(String[] args) {
		MySQLTest test = new LineDataSQL();
		String[] strColumn = new String[]{"rail_name"};
		test.makeStationTable("res/line_name201604.csv","line_table",Arrays.asList(strColumn));
		test.close();
	}
}
