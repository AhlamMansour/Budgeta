package main.java.budgeta.test;

import web.infra.GalilDriver;

public class BudgetaLoginInOut {

	public void loginToBudgeta(String url,String user,String pass) throws Exception{
		GalilDriver.getWebDriver().get(url);
		
	}
	
	public void logoutFromBudgeta(String url,String user,String pass) throws Exception{
		GalilDriver.getWebDriver().get(url);
		
	}
}
