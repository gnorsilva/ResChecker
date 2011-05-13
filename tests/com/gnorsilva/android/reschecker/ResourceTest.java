package com.gnorsilva.android.reschecker;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class ResourceTest {

	private File androidTestProject = new File("AndroidTestProject");
	private File manifest = new File(androidTestProject, "AndroidManifest.xml");
	private File srcFolder = new File(androidTestProject, "src");
	private File resFolder = new File(androidTestProject, "res");

	@Before
	public void before(){
		assertTrue(manifest.isFile());
		assertTrue(srcFolder.isDirectory());
		assertTrue(resFolder.isDirectory());
	}
	
	@Test
	public void resourcesUsed() throws IOException{
		assertTrue(new Resource("string","app_name",manifest,resFolder,srcFolder).isUsed());
		assertTrue(new Resource("layout","main",manifest,resFolder,srcFolder).isUsed());
		assertTrue(new Resource("drawable","icon",manifest,resFolder,srcFolder).isUsed());
	}

}
