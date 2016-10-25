package com.example.jythontest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RouteClassifier {
	
	private final File dir;
	
	public RouteClassifier(){
		this.dir = new File("pycode");
	}
	
	
	public String classify(String master,double[] location){
		
		try{
			ProcessBuilder builder = new ProcessBuilder("python","ptest.py",master,Double.toString(location[0]),Double.toString(location[1]));
			builder.directory(this.dir);
			
			Process process = builder.start();
			
			process.waitFor();

			InputStream is = process.getInputStream();	//標準出力
			List<String> stdOut = parseInputStream(is);
			InputStream es = process.getErrorStream();	//標準エラー
			List<String> stdErr = parseInputStream(es);

			process.waitFor();
			
			return stdOut.get(0);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			
		}
		
		return null;
	}
	
	public static List<String> parseInputStream(InputStream is) throws IOException {
		List<String> output = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		try {
			for (;;) {
				String line = br.readLine();
				if (line == null) break;
				output.add(line);
			}
		} finally {
			br.close();
		}
		return output;
	}
	
}
