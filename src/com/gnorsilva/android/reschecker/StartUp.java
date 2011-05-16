package com.gnorsilva.android.reschecker;

import java.io.File;
import java.util.List;

public class StartUp {
	
	private static final String ARGS_PATH = "-p";
	
	public static void main(String [] args){
		if(args == null || args.length == 0){
			printHelp();
		}else {
			readArgs(args);
		}

	}

	private static void readArgs(String[] args) {
		for (int i = 0 ; i < args.length ; i++ ){
			String arg = args[i];
			if (arg.equals(ARGS_PATH)){
				if( ++i >= args.length){
					printHelp();
				}else{
					checkForResources(args[i]);
				}
			}else{
				printHelp();
			}
		}
	}

	private static void checkForResources(String projectPath) {
		log("Checking resources for project path " + projectPath);
		if ( !projectPath.endsWith(File.separator)){
			projectPath += File.separator;
		}
		List<Resource> resources = ResourceExtractor.getResources(projectPath);
		if(resources != null){
			ResourceChecker.findUnusedResources(resources, projectPath);
		}
	}
	
	public static void printHelp() {
		log("ResChecker finds out which resources are being used");
		log("Usage:");
		log("\t-p path of the project to be checked - project needs to have AndroidManifest.xml, and R.java inside the gen folder");
		log("\t-h prints this message");
		log("");
	}
	
	public static void log(String msg){
		System.out.println(msg);
	}

}
