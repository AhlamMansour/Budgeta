package com.budgeta.test;

import org.testng.Assert;
import org.testng.annotations.Test;

public class tryTest extends WrapperTest {

	@Test
	public void try1() {
		System.out.println("success");
		Assert.assertFalse(true);
	}
}
