package com.gnorsilva.android.reschecker;

import static com.gnorsilva.android.reschecker.Utils.log;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ResourceChecker {
	
	private final List<Resource> resources;
	private final File manifest;
	private final File resFolder;
	private final File srcFolder;
	
	private ResourceChecker(List<Resource> resources, File manifest, File resFolder, File srcFolder){
		this.resources = resources;
		this.manifest = manifest;
		this.resFolder = resFolder;
		this.srcFolder = srcFolder;
	}
	
	private ResourceChecker(File manifest, File resFolder, File srcFolder){
		resources = null;
		this.manifest = manifest;
		this.resFolder = resFolder;
		this.srcFolder = srcFolder;
	}
	
	private boolean foundInManifest(Resource res) throws IOException {
		return Utils.findInFile(res.getXMLPattern(), manifest);
	}
	
	private boolean foundInResFolder(Resource res) throws IOException {
		return Utils.findInFolder(res.getXMLPattern(), resFolder);
	}

	private boolean foundInSrcFolder(Resource res) throws IOException {
		return Utils.findInFolder(res.getJavaPattern(), srcFolder);
	}
	
	private int findResources(){
		int resourcesNotUsed = 0;
		for ( Resource res : resources){
			if(!isResourceUsed(res)){
				resourcesNotUsed++;
			}
		}
		return resourcesNotUsed;
	}
	
	private boolean isResourceUsed(Resource res) {
		boolean isResourceFound = false;
		try{
			isResourceFound = foundInManifest(res) || foundInResFolder(res) || foundInSrcFolder(res);
			if(!isResourceFound){
				log(res.toString() + " is not being used");
			}
		}catch(IOException e){
			log("Error occurred while searching for resource " + res.toString());
			e.printStackTrace();
		}
		return isResourceFound;
	}
	
	public static int findUnusedResources(List<Resource> resources, File manifest, File resFolder, File srcFolder) {
		return new ResourceChecker(resources, manifest, resFolder, srcFolder).findResources();
	}
	
	public static boolean isResourceUsed(Resource resource, File manifest, File resFolder, File srcFolder) {
		return new ResourceChecker(manifest, resFolder, srcFolder).isResourceUsed(resource);
	}

}
