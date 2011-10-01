package com.gnorsilva.android.reschecker;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.gnorsilva.android.reschecker.testutils.TestConstants;

public class ControllerTest implements TestConstants{

	@Test
	public void searchAndroidTestProject(){
		Controller app = new Controller(ANDROID_TEST_PROJECT);
		app.searchProject();
		assertEquals(0, app.getResourcesNotFound());
	}
}
