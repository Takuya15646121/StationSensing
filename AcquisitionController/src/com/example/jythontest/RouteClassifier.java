package com.example.jythontest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.example.mysqltest.QueryUtil;

public class RouteClassifier extends PycodeExecuter{
	
	
	public RouteClassifier(String dir,String pyFile){
		super(dir, pyFile);
	}
	
	
	public String classify(String master,double[] location){
		
		String[] args = new String[] { master, Double.toString(location[0]),
				Double.toString(location[1]) };

		Process process = super.executePyProcess(args);
		if (process == null) {
			return null;
		}
		try {
			process.waitFor();
			InputStream is = process.getInputStream(); // �W���o��
			List<String> stdOut = super.parseInputStream(is);
			InputStream es = process.getErrorStream(); // �W���G���[
			List<String> stdErr = super.parseInputStream(es);

			process.waitFor();

			return stdOut.get(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;			
		
	}
	

	
	public static void main(String[] args){
		RouteClassifier rc = new RouteClassifier("pycode","ptest.py");
		String stationName = "�r��";
		
		//�ʒu���Ⴄ�H���n�n�̈Ⴂ�H
		rc.classify(stationName, QueryUtil.getInstance().getLocation(stationName));
	}
	
}
