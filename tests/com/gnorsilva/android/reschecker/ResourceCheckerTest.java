package com.gnorsilva.android.reschecker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class ResourceCheckerTest {

	private File manifest;
	private File resFolder;
	private File srcFolder;
	
	@Before
	public void setup(){
		manifest = new File(TestConstants.ANDROID_TEST_PROJECT, "AndroidManifest.xml");
		assertTrue(manifest.isFile());
		resFolder = new File(TestConstants.ANDROID_TEST_PROJECT, "res");
		assertTrue(resFolder.isDirectory());
		srcFolder = new File(TestConstants.ANDROID_TEST_PROJECT, "src");
		assertTrue(srcFolder.isDirectory());
	}
	
	private boolean isUsed(Resource resource){
		return ResourceChecker.isResourceUsed(resource, manifest, resFolder, srcFolder);
	}
	
	@Test
	public void findUsedResources() {
		Resource resource = new Resource("string", "app_name");
		assertTrue(isUsed(resource));
		resource = new Resource("layout", "main");
		assertTrue(isUsed(resource));
		resource = new Resource("drawable", "icon");
		assertTrue(isUsed(resource));
	}
	
	@Test
	public void unusedResources() {
		Resource resource = new Resource("string", "app");
		assertFalse(isUsed(resource));
		resource = new Resource("layout", "main_view");
		assertFalse(isUsed(resource));
		resource = new Resource("drawable", "ic");
		assertFalse(isUsed(resource));
		resource = new Resource("string", "appname");
		assertFalse(isUsed(resource));
	}

}
