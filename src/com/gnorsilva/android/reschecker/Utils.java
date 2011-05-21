package com.gnorsilva.android.reschecker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class Utils {
	public static void log(String msg){
		System.out.println(msg);
	}
	
	public static void log(){
		System.out.println();
	}
	
	public static boolean findInFolder(Pattern pattern, File folder) throws IOException {
		for (File file : folder.listFiles()) {
			if (file.isDirectory()) {
				if (findInFolder(pattern, file)) {
					return true;
				}
			} else if (file.isFile()) {
				if (findInFile(pattern, file)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean findInFile(Pattern pattern, File file) throws IOException {
		boolean resFound = false;
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		while( (line = reader.readLine()) != null ){
			if(pattern.matcher(line).find()){
				resFound = true;
				break;
			}
		}
		reader.close();
		return resFound;
	}
}