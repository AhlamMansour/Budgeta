package com.budgeta.test;

import java.util.Hashtable;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.budgeta.pom.BudgetaBoard;
import com.budgeta.pom.LoginPage;
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
	
	protected BudgetaBoard board;
	protected String username;
	protected String password;
	protected LoginPage loginPage;
	
	protected String newPassword = "aaa1234";
	static Hashtable<String, String> months = new Hashtable<String, String>();
	
	
	@BeforeClass
	@Parameters()
	public void init() throws Exception {
		username = SelTestProps.get("common.username");
		password = SelTestProps.get("common.password");
		
		months.put("Jan", "01");
		months.put("Feb", "02");
		months.put("Mar", "03");
		months.put("Apr", "04");
		months.put("May", "05");
		months.put("Jun", "06");
		months.put("Jul", "07");
		months.put("Aug", "08");
		months.put("Sep", "09");
		months.put("Oct", "10");
		months.put("Nov", "11");
		months.put("Dec", "12");
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
		loginPage.clickLogin(true);
		board = new BudgetaBoard();
	}
	
	public static String getDateByNumbersFormat(String month, String year){
		return months.get(month)+"/"+year;
	}
	
	public static Hashtable<String, String> getMonths(){
		return months;
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
