package com.budgeta.test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.budgeta.LoginPage;
import com.galilsoftware.AF.core.SelTestProps;
import com.galilsoftware.AF.core.logging.SelTestLog;
import com.galilsoftware.AF.core.testConfig.TearDown;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

/**
 * 
 * @author Rabia Manna
 *
 */
public class BudgetaTest extends TearDown{
	
	protected String username;
	protected String password;
	protected LoginPage loginPage;
	
	
	@BeforeClass
	@Parameters()
	public void init() throws Exception {
		username = SelTestProps.get("common.username");
		password = SelTestProps.get("common.password");
		
	}
	
	protected void doLogin(){
		try{
			driver.get(baseURL);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed navigating to: "+baseURL+" retrying ...");
			WebdriverUtils.sleep(1200);
			driver.get(baseURL);
		}
		loginPage = new LoginPage();
		loginPage.isDisplayed();
		loginPage.setEmail(username);
		loginPage.setPassword(password);
		loginPage.clickLogin();
	}
	
	
	@BeforeMethod
	public void clearFocus() {
		try {
			driver.switchTo().defaultContent();
			// refresh
			// driver.get(driver.getCurrentUrl());
		} catch (Exception e) {
			SelTestLog.warn("Could not switch to default content, failed in beforeMethod", e);
		}
	}
	
	

}
