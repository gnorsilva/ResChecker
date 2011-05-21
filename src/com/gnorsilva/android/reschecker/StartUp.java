package com.gnorsilva.android.reschecker;

import static com.gnorsilva.android.reschecker.Utils.log;

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
		File manifest = new File(projectPath, "AndroidManifest.xml");
		File resFolder = new File(projectPath, "res");
		File srcFolder = new File(projectPath, "src");
		
		if( manifest.isFile() && resFolder.isDirectory() && srcFolder.isDirectory() ){
			log("Checking resources for project path " + projectPath);
			
			if ( !projectPath.endsWith(File.separator)){
				projectPath += File.separator;
			}
			
			List<Resource> resources = ResourceExtractor.getResources(projectPath);
			int resourcesNotUsed = 0;
			if(resources != null){
				resourcesNotUsed = ResourceChecker.findUnusedResources(resources, manifest, resFolder, srcFolder);
				
				if(resourcesNotUsed == 0){
					log("Finished checking for resources. All seem to be used!");
				}else{
					log("Finished checking for resources. Total unused: " + resourcesNotUsed);
					log("Please remember to backup your project before you remove any resource");
				}
			}
		}else{
			if( !manifest.isFile() ){
				log("Could not load AndroidManifest.xml");
			}
			if( !resFolder.isDirectory() ){
				log("Could not load the resources folder");
			}
			if( !srcFolder.isDirectory() ){
				log("Could not load the source folder");
			}
		}
	}
	
	public static void printHelp() {
		log();
		log("ResChecker finds out which resources are being used");
		log();
		log("Usage:");
		log("\t-p path of the project to be checked - project needs to have AndroidManifest.xml, and R.java inside the gen folder");
		log("\t-h prints this message");
		log();
	}

}
