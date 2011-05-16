package com.gnorsilva.android.reschecker;

import org.junit.Test;

public class StartUpTest {
	
	@Test
	public void args(){
		//TODO add asserts - how to assert that printHelp() is being called without adding extra code? read the command line?
		StartUp.main(null);
		StartUp.main(new String []{});
		StartUp.main(new String []{"-h"});
		StartUp.main(new String []{"fslkdflkasdj"});
		StartUp.main(new String []{"-p"});
		StartUp.main(new String []{"-p",TestConstants.ANDROID_TEST_PROJECT});
		StartUp.main(new String []{"-p",TestConstants.ANDROID_TEST_PROJECT,"-p",TestConstants.ANDROID_TEST_PROJECT});
		StartUp.main(new String []{"-p",TestConstants.ANDROID_TEST_PROJECT,"-p",TestConstants.ANDROID_TEST_PROJECT,"-p"});
	}

}
