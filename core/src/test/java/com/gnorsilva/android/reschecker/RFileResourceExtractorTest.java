package com.gnorsilva.android.reschecker;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.Set;

import org.junit.After;
import org.junit.Test;

import com.gnorsilva.android.reschecker.testutils.TestConstants;

public class RFileResourceExtractorTest implements TestConstants {

	@Test
	public void getResources() {
		Set<Resource> resources = new RFileResourceExtractor(ANDROID_TEST_PROJECT).getResources();
		Resource app_name = new Resource("string", "app_name");
		assertTrue(resources.contains(app_name));
		Resource icon = new Resource("drawable", "icon");
		assertTrue(resources.contains(icon));
		Resource mainLayout = new Resource("layout", "main");
		assertTrue(resources.contains(mainLayout));
	}

	@Test
	public void wrongProjectPaths() {
		Set<Resource> resources = new RFileResourceExtractor(".").getResources();
		assertNull(resources);
	}

	@After
	public void ensureFilesAreCleanedUp() {
		String path = ANDROID_TEST_PROJECT + "_gen_com_gnorsilva_android_reschecker";
		File rFolder = new File(path.replace('_', File.separatorChar));
		assertTrue(rFolder.isDirectory());
		for (File file : rFolder.listFiles()) {
			String name = file.getName();
			assertFalse(name.endsWith(".class"));
		}
	}

}
