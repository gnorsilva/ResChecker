package com.gnorsilva.android.reschecker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class ResourceCheckerTest {

	@Test
	public void findResourceInManifest() throws IOException {
		File manifest = new File(TestConstants.ANDROID_TEST_PROJECT, "AndroidManifest.xml");
		assertTrue(manifest.isFile());
		Resource res = new Resource("string","app_name");
		assertTrue(ResourceChecker.findInFile(res.getXMLPattern(), manifest));
		res = new Resource("drawable", "icon");
		assertTrue(ResourceChecker.findInFile(res.getXMLPattern(), manifest));
		res = new Resource("string","app");
		assertFalse(ResourceChecker.findInFile(res.getXMLPattern(), manifest));
	}

	@Test
	public void findResourceInJavaFile() throws IOException {
		String s = File.separator;
		File javaFile = new File(TestConstants.ANDROID_TEST_PROJECT, "src" + s + "com" + s + "gnorsilva" + s + "android" + s + "reschecker" + s
				+ "StartUp.java");
		assertTrue(javaFile.isFile());
		Resource res = new Resource("layout","main");
		assertTrue(ResourceChecker.findInFile(res.getJavaPattern(), javaFile));
		res = new Resource("string","app_name");
		assertFalse(ResourceChecker.findInFile(res.getJavaPattern(), javaFile));
	}

	@Test
	public void findResourceInResFolder() throws IOException {
		File resFolder = new File(TestConstants.ANDROID_TEST_PROJECT, "res");
		assertTrue(resFolder.isDirectory());
		Resource res = new Resource("string","app_name");
		assertTrue(ResourceChecker.findInFolder(res.getXMLPattern(), resFolder));
		res = new Resource("layout","main");
		assertFalse(ResourceChecker.findInFolder(res.getXMLPattern(), resFolder));
		res = new Resource("drawable","icon");
		assertFalse(ResourceChecker.findInFolder(res.getXMLPattern(), resFolder));
	}

	@Test
	public void findResourceInSrcFolder() throws IOException {
		File srcFolder = new File(TestConstants.ANDROID_TEST_PROJECT, "src");
		assertTrue(srcFolder.isDirectory());
		Resource res = new Resource("layout","main");
		assertTrue(ResourceChecker.findInFolder(res.getJavaPattern(), srcFolder));
		res = new Resource("string","app_name");
		assertFalse(ResourceChecker.findInFolder(res.getJavaPattern(), srcFolder));
	}

	@Test
	public void findUsedResources() {
		String path = TestConstants.ANDROID_TEST_PROJECT + File.separator;
		Resource res = new Resource("string", "app_name");
		assertTrue(ResourceChecker.isResourceUsed(res, path));
		res = new Resource("layout", "main");
		assertTrue(ResourceChecker.isResourceUsed(res, path));
		res = new Resource("drawable", "icon");
		assertTrue(ResourceChecker.isResourceUsed(res, path));
		res = new Resource("string", "app");
		assertFalse(ResourceChecker.isResourceUsed(res, path));
	}
	
	@Test
	public void unusedResources() {
		String path = TestConstants.ANDROID_TEST_PROJECT + File.separator;
		Resource res = new Resource("string", "app");
		assertFalse(ResourceChecker.isResourceUsed(res, path));
		res = new Resource("layout", "main_view");
		assertFalse(ResourceChecker.isResourceUsed(res, path));
		res = new Resource("drawable", "ic");
		assertFalse(ResourceChecker.isResourceUsed(res, path));
		res = new Resource("string", "appname");
		assertFalse(ResourceChecker.isResourceUsed(res, path));
	}

}
