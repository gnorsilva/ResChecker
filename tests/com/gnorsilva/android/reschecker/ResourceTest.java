package com.gnorsilva.android.reschecker;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ResourceTest {

	@Test
	public void testEqualResources(){
		Resource res1 = new Resource("string", "hello");
		Resource res2 = new Resource("string", "hello");
		assertEquals(res1, res2);
	}
	

}
