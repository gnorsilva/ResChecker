package com.gnorsilva.android.reschecker;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;


public class UtilsTest {

	@Test
	public void readNormalFileToString() throws IOException{
		File hello = new File("tests" + File.separator + "test_assets" + File.separator + "hello");
		String contents = Utils.readFile(hello);
		assertTrue(contents.contains("Hello World!"));
	}
	
	@Test
	public void readJavaFileToString() throws IOException{
		File java = new File("tests" + File.separator + "test_assets" + File.separator + "HelloWorld.java");
		String contents = Utils.readFile(java);
		assertTrue(contents.contains("public class HelloWorld{}"));
	}
	
	@Test
	public void readXMLFileToString() throws IOException{
		File xml = new File("tests" + File.separator + "test_assets" + File.separator + "strings.xml");
		String contents = Utils.readFile(xml);
		assertTrue(contents.contains("<?xml version=\"1.0\" encoding=\"utf-8\"?>"));
	}
}
