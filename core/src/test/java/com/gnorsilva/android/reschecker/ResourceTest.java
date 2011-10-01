package com.gnorsilva.android.reschecker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ResourceTest {

	@Test
	public void two_equal_resources(){
		Resource res1 = new Resource("string", "hello");
		Resource res2 = new Resource("string", "hello");
		assertEquals(res1, res2);
	}
	
	private boolean findInJava(String resType, String resName, String line){
		return new Resource(resType,resName).getJavaPattern().matcher(line).find();
	}
	
	private boolean findInXML(String resType, String resName, String line){
		return new Resource(resType,resName).getXMLPattern().matcher(line).find();
	}
	
	@Test
	public void javaPatternMatch(){
		assertTrue(findInJava("layout","main","setContentView( R.layout.main );"));
	}
	
	@Test
	public void javaPatternShouldNotMatch(){
		assertFalse(findInJava("layout","main","setContentView( R.layout.main_layout );"));
		assertFalse(findInJava("layout","main","setContentView( R.layout.mainlayout );"));
	}
	
	@Test
	public void xmlPatternMatch(){
		assertTrue(findInXML("string","app_name","android:text=\"@string/app_name\" />"));
		assertTrue(findInXML("style","Fill","<style name=\"custom_style\" parent=\"Fill\" />"));
		assertTrue(findInXML("style","Fill","<style name=\"Fill.custom_style\" />" ));
	}
	
	@Test
	public void xmlPatternShouldNotMatch(){
		assertFalse(findInXML("string","app_name","android:text=\"@string/app\""));
		assertFalse(findInXML("string","app_name","android:text=\"@string/app_nameother\""));
		assertFalse(findInXML("style","Fill","<style name=\"custom_style\" parent=\"Fill_width\" />"));
		assertFalse(findInXML("style","Fill","<style name=\"custom_style\" parent=\"FillWidth\" />"));
	}
	
}

