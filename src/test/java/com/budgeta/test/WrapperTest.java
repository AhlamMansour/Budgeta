package com.budgeta.test;

import org.testng.annotations.BeforeClass;

public class WrapperTest extends BudgetaTest{

	
	@BeforeClass
	public synchronized void logOntoSite() {
		doLogin();
	}
	
	
}
