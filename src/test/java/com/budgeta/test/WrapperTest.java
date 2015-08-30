package com.budgeta.test;

import org.testng.annotations.BeforeClass;

/**
 * 
 * @author Rabia Manna
 *
 */

public class WrapperTest extends BudgetaTest{

	
	@BeforeClass
	public synchronized void logOntoSite() {
		doLogin();
	}
	
	
}
