package com.example.jythontest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;


public class JythonTest {
	public static void main(String[] args) {
		File dir = new File("pycode");
		
		Runtime runtime = Runtime.getRuntime();
		
		try {
			ProcessBuilder builder = new ProcessBuilder("python","ptest.py","135","123");
			builder.directory(dir);
			
//			Map<String, String> env = builder.environment();
//			for(String key:env.keySet()){
//				System.out.println(key+":"+env.get(key));
//			}
			
			Process process = builder.start();
//			Process process = runtime.exec("python ptest.py 135 123", null, dir);
			process.waitFor();

			InputStream is = process.getInputStream();	//標準出力
			printInputStream(is);
			InputStream es = process.getErrorStream();	//標準エラー
			printInputStream(es);

			process.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void printInputStream(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		try {
			for (;;) {
				String line = br.readLine();
				if (line == null) break;
				System.out.println(line);
			}
		} finally {
			br.close();
		}
	}
}
