package com.gnorsilva.android.reschecker;

import java.io.File;
import java.io.IOException;

public class Resource {
	
	private final String xmlName;
	private final String javaName;
	private final File manifest;
	private final File resFolder;
	private final File srcFolder;
	
	public Resource(String type, String name, File manifest, File resFolder, File srcFolder) {
		xmlName = new StringBuilder('"').append('@').append(type).append('/').append(name).append('"').toString();
		javaName = new StringBuilder(type).append('.').append(name).toString();
		this.manifest = manifest;
		this.resFolder = resFolder;
		this.srcFolder = srcFolder;
	}

	public boolean isUsed() throws IOException{
		return isUsedInManifest() || isUsedInResources() || isUsedInJavaCode();
	}
	
	private boolean isUsedInManifest() throws IOException {
		return findInFile(xmlName, manifest);
	}
	
	private boolean isUsedInResources() throws IOException {
		return findInFolder(xmlName, resFolder);
	}

	private boolean isUsedInJavaCode() throws IOException {
		return findInFolder(javaName, srcFolder);
	}

	private static boolean findInFolder(String resource, File folder) throws IOException {
		for (File file : folder.listFiles()) {
			if (file.isDirectory()) {
				if (findInFolder(resource, file)) {
					return true;
				}
			} else if (file.isFile()) {
				if (findInFile(resource, file)) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean findInFile(String resource, File file) throws IOException {
		String contents = Utils.readFile(file);
		return contents.contains(resource);
	}

}