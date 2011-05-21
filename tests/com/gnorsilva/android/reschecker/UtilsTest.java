package com.gnorsilva.android.reschecker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;


public class UtilsTest {
	
	private Pattern appNameXML;
	private Pattern appNameJava;
	private Pattern iconXML;
	private Pattern iconJava;
	private Pattern mainXML;
	private Pattern mainJava;

	@Before
	public void setup(){
		appNameXML = Pattern.compile("@string/app_name");
		appNameJava = Pattern.compile("R.string.app_name");
		iconXML = Pattern.compile("@drawable/icon");
		iconJava = Pattern.compile("R.drawable.icon");
		mainXML = Pattern.compile("@layout/main");
		mainJava = Pattern.compile("R.layout.main"); 
	}
	
	@Test
	public void findResourceInManifest() throws IOException {
		File manifest = new File(TestConstants.ANDROID_TEST_PROJECT, "AndroidManifest.xml");
		assertTrue(manifest.isFile());
		assertTrue(Utils.findInFile(appNameXML, manifest));
		assertTrue(Utils.findInFile(iconXML, manifest));
		assertFalse(Utils.findInFile(mainXML, manifest));
	}

	@Test
	public void findResourceInJavaFile() throws IOException {
		String s = File.separator;
		File javaFile = new File(TestConstants.ANDROID_TEST_PROJECT, "src" + s + 
						"com" + s + "gnorsilva" + s + "android" + s + "reschecker" + s + "StartUp.java");
		assertTrue(javaFile.isFile());
		assertTrue(Utils.findInFile(mainJava, javaFile));
		assertFalse(Utils.findInFile(appNameJava, javaFile));
		assertFalse(Utils.findInFile(iconJava, javaFile));
	}

	@Test
	public void findResourceInResFolder() throws IOException {
		File resFolder = new File(TestConstants.ANDROID_TEST_PROJECT, "res");
		assertTrue(resFolder.isDirectory());
		assertTrue(Utils.findInFolder(appNameXML, resFolder));
		assertFalse(Utils.findInFolder(mainXML, resFolder));
		assertFalse(Utils.findInFolder(iconXML, resFolder));
	}

	@Test
	public void findResourceInSrcFolder() throws IOException {
		File srcFolder = new File(TestConstants.ANDROID_TEST_PROJECT, "src");
		assertTrue(srcFolder.isDirectory());
		assertTrue(Utils.findInFolder(mainJava, srcFolder));
		assertFalse(Utils.findInFolder(appNameJava, srcFolder));
		assertFalse(Utils.findInFolder(iconJava, srcFolder));
	}
	
}
