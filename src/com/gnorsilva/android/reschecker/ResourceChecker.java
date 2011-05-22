package com.gnorsilva.android.reschecker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

public class ResourceChecker {
	
	private static final String JAVA = ".java";
	private static final String XML = ".xml";
	private final Set<Resource> resources;
	private final int initialSize;
	private int resourcesFound;
	
	public ResourceChecker(Set<Resource> resources){
		this.resources = resources;
		initialSize = resources.size();
	}
	
	public void findUnusedResources(File ... files) throws IOException{
		for(File file : files){
			if(file.isDirectory()){
				findInFolder(file);
			}else if(file.isFile()){
				findInFile(file);
			}
		}
	}

	protected void findInFolder(File folder) throws IOException {
		for (File file : folder.listFiles()) {
			if (file.isDirectory()) {
				findInFolder(file);
			} else if (file.isFile()) {
				if(file.getName().endsWith(JAVA) || file.getName().endsWith(XML)){
					findInFile(file);
				}
			}
		}
	}

	protected void findInFile(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		boolean isXml = file.getName().endsWith(XML) ? true : false;
		String line;
		while( (line = reader.readLine()) != null ){
			matchLine(isXml, line);
		}
		reader.close();
	}
	
	private void matchLine(boolean isXml, String line) {
		for (Iterator<Resource> i = resources.iterator(); i.hasNext();) {
		    Resource res = i.next();
			Pattern pattern = isXml ? res.getXMLPattern() : res.getJavaPattern();
			if(pattern.matcher(line).find()){
				i.remove();
				updateResourceLog();
			}
		}
	}

	private void updateResourceLog() {
		System.out.print("Resources used: " + (++resourcesFound) + "/" + initialSize + " \r");
	}
	
}
