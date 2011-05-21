package com.gnorsilva.android.reschecker;

import static com.gnorsilva.android.reschecker.Utils.log;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class Controller {
	
	private final File manifest;
	private final File resFolder;
	private final File srcFolder;
	private final String projectPath;
	private Set<Resource> resources;

	public Controller(String projectPath){
		this.projectPath = projectPath;
		manifest = new File(projectPath, "AndroidManifest.xml");
		resFolder = new File(projectPath, "res");
		srcFolder = new File(projectPath, "src");
	}
	
	public void searchProject(){
		if(filesAreSetup()){
			log("Loading resources for " + projectPath);
			resources = ResourceExtractor.getResources(projectPath);
			try {
				checkResources();
			} catch (IOException e) {
				log("Error!");
				e.printStackTrace();
			}
		}
	}

	private boolean filesAreSetup() {
		boolean result = false;
		if( manifest.isFile() && resFolder.isDirectory() && srcFolder.isDirectory() ){
			result = true;
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
		return result;
	}

	private void checkResources() throws IOException {
		if(resources != null){
			log("Total resources found: " + resources.size());
			log();
			try {
				new ResourceChecker(resources).findUnusedResources(manifest,srcFolder,resFolder);
				finishUpCurrentSearch();
			} catch (IOException e) {
				log("Error checking resources for project" + projectPath );
				e.printStackTrace();
			}
		}else{
			log("Error loading resources for project" + projectPath );
		}
	}
	
	private void finishUpCurrentSearch() {
		log("\n\n");
		if(resources.size() == 0){
			log("Finished checking resources. All seem to be in use!");
		}else{
			log("Finished checking resources. " + resources.size() + " unused. ");
			log();
			for(Resource res : resources){
				log(res.toString());
			}
			log();
			log("Please remember to backup your project before you remove any resource");
		}
	}
	
	public int getResourcesNotFound(){
		return resources.size();
	}
	
}
