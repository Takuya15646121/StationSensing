package com.example.jythontest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PycodeExecuter {

	private final File dir;
	private final String pyFile;
	
	public PycodeExecuter(String dir,String pyFile){
		this.dir = new File(dir);
		this.pyFile = pyFile;
	}
	
	
	protected Process executePyProcess(String[] args){
		List<String> cmd = new ArrayList<String>();
		cmd.add("python");
		cmd.add(this.pyFile);
		for(String arg:args){
			cmd.add(arg);
		}
		ProcessBuilder builder = new ProcessBuilder(cmd);
		builder.directory(this.dir);
		
		try {
			Process process = builder.start();
			return process;
		} catch (IOException e) {
			e.printStackTrace();
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
				System.out.println(line);
				output.add(line);
			}
		} finally {
			br.close();
		}
		return output;
	}
	
}
