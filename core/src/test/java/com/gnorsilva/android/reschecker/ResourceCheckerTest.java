package com.gnorsilva.android.reschecker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.gnorsilva.android.reschecker.testutils.TestConstants;


public class ResourceCheckerTest implements TestConstants{
	
	private Set<Resource> resources;
	private ResourceChecker resChecker;
	
	@Before
	public void setupResources(){
		resources = new HashSet<Resource>();
		resources.add(new Resource("string","app_name"));
		resources.add(new Resource("drawable","icon"));
		resources.add(new Resource("layout","main"));
		resChecker = new ResourceChecker(resources);
	}

	@Test
	public void findResourcesInManifest() throws IOException{
		File manifest = new File(ANDROID_TEST_PROJECT, "AndroidManifest.xml");
		assertTrue(manifest.isFile());
		
		resChecker.findInFile(manifest);
		assertEquals(1, resources.size());
		assertTrue(resources.contains(new Resource("layout","main")));
	}
	
	@Test
	public void findResourcesInJavaFile() throws IOException{
		String s = File.separator;
		File javaFile = new File(ANDROID_TEST_PROJECT, "src" + s + 
						"com" + s + "gnorsilva" + s + "android" + s + "reschecker" + s + "StartUp.java");
		assertTrue(javaFile.isFile());
		
		resChecker.findInFile(javaFile);
		assertEquals(2, resources.size());
		assertTrue(resources.contains(new Resource("string","app_name")));
		assertTrue(resources.contains(new Resource("drawable","icon")));
	}
	
	@Test
	public void findResourcesInResFolder() throws IOException {
		File resFolder = new File(ANDROID_TEST_PROJECT, "res");
		assertTrue(resFolder.isDirectory());
		
		resChecker.findInFolder(resFolder);
		assertEquals(2, resources.size());
		assertTrue(resources.contains(new Resource("layout","main")));
		assertTrue(resources.contains(new Resource("drawable","icon")));
	}

	@Test
	public void findResourcesInSrcFolder() throws IOException {
		File srcFolder = new File(ANDROID_TEST_PROJECT, "src");
		assertTrue(srcFolder.isDirectory());
		
		resChecker.findInFolder(srcFolder);
		assertEquals(2, resources.size());
		assertTrue(resources.contains(new Resource("string","app_name")));
		assertTrue(resources.contains(new Resource("drawable","icon")));
	}
	
	@Test
	public void findResourcesInProject() throws IOException {
		File manifest = new File(ANDROID_TEST_PROJECT, "AndroidManifest.xml");
		assertTrue(manifest.isFile());
		File srcFolder = new File(ANDROID_TEST_PROJECT, "src");
		assertTrue(srcFolder.isDirectory());
		File resFolder = new File(ANDROID_TEST_PROJECT, "res");
		assertTrue(resFolder.isDirectory());
		
		resChecker.findUnusedResources(manifest,srcFolder,resFolder);
		assertEquals(0, resources.size());
	}
	
}
