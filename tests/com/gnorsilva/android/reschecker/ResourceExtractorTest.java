package com.gnorsilva.android.reschecker;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

import org.junit.Test;

import com.gnorsilva.android.reschecker.ResourceExtractor.PackageNotFoundException;

public class ResourceExtractorTest implements TestConstants{
	
	@Test
	public void extractPackageName() throws ValidityException, ParsingException, IOException, PackageNotFoundException{
		String manifestPath = ANDROID_TEST_PROJECT + File.separator;
		String packageName = ResourceExtractor.getPackageName(manifestPath);
		assertEquals("com.gnorsilva.android.reschecker",packageName);
	}
	
	@Test
	public void testPackageNameExceptions() throws ValidityException, ParsingException, IOException, PackageNotFoundException{
		String manifestPath = ANDROID_TEST_PROJECT + File.separator + "src";
		String packageName;
		try{
			packageName = ResourceExtractor.getPackageName(manifestPath);
			fail("Should not have found AndroidManifest.xml");
		}catch(FileNotFoundException e){
			assertTrue(true);
		}

		manifestPath = "test_assets" + File.separator;
		try{
			packageName = ResourceExtractor.getPackageName(manifestPath);
			System.out.println(packageName);
			fail("Should not have found the \"package\" xml attribute");
		}catch(PackageNotFoundException e){
			assertTrue(true);
		}
	}
	
	@Test
	public void loadRAsAClass() throws MalformedURLException, ClassNotFoundException {
		String manifestPath = ANDROID_TEST_PROJECT + File.separator;
		String packageName = "com.gnorsilva.android.reschecker";
		Class<?> rClass = ResourceExtractor.loadRClass(manifestPath, packageName);
		assertNotNull(rClass);
		assertEquals("com.gnorsilva.android.reschecker.R", rClass.getName());
	}
	
	@Test
	public void getResourcesFromR_Java() {
		Set<Resource> resources = ResourceExtractor.getResources(ANDROID_TEST_PROJECT + File.separator);
		Resource app_name = new Resource("string", "app_name");
		assertTrue(resources.contains(app_name));
		Resource icon = new Resource("drawable", "icon");
		assertTrue(resources.contains(icon));
		Resource mainLayout = new Resource("layout", "main");
		assertTrue(resources.contains(mainLayout));
	}
	
}
