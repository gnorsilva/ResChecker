package com.gnorsilva.android.reschecker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import com.gnorsilva.android.reschecker.Utils.Log;

public class ResourceChecker {

	public static void findUnusedResources(List<Resource> resources, String projectPath) {
		for ( Resource res : resources){
			isResourceUsed(res, projectPath);
		}
	}
	
	public static boolean isResourceUsed(Resource res, String projectPath) {
		boolean isResourceFound = false;
		try{
			isResourceFound = foundInManifest(res, projectPath) ||	foundInResFolder(res,projectPath) || foundInSrcFolder(res,projectPath);
			if(!isResourceFound){
				Log.v(res.toString() + " is not being used");
			}
		}catch(IOException e){
			Log.v("Error occurred while searching for resource " + res.toString());
			e.printStackTrace();
		}
		return isResourceFound;
	}

	private static boolean foundInManifest(Resource res, String projectPath) throws IOException {
		return findInFile(res.getXMLPattern(), new File(projectPath,"AndroidManifest.xml"));
	}
	
	private static boolean foundInResFolder(Resource res, String projectPath) throws IOException {
		return findInFolder(res.getXMLPattern(), new File(projectPath,"res"));
	}

	private static boolean foundInSrcFolder(Resource res, String projectPath) throws IOException {
		return findInFolder(res.getJavaPattern(), new File(projectPath,"src"));
	}

	protected static boolean findInFolder(Pattern pattern, File folder) throws IOException {
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

	protected static boolean findInFile(Pattern pattern, File file) throws IOException {
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
