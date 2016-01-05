package com.budgeta.test.login;

import org.openqa.selenium.Point;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.BudgetaBoard;
import com.budgeta.pom.LoginPage;
import com.budgeta.test.BudgetaTest;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestNGListener;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

@Listeners({ MethodListener.class, TestNGListener.class })
public class LoginTest extends BudgetaTest{
	
	
	@BeforeMethod
	private void initTest() {	
		//((JavascriptExecutor)driver).executeScript("");
		
		driver.manage().window().maximize();
		
		
	}
	
	
	@Test(enabled = true)
	public void secureLoginTest(){
		driver.get(baseURL);
		LoginPage loginPage = new LoginPage();
		
		Assert.assertTrue(loginPage.isDisplayed(), "expected login page to be displayed");
		
		//empty fields
		loginPage.clickLogin(false);
		Assert.assertTrue(loginPage.emailHasError(), "expected email error message to be displayed");
		Assert.assertTrue(loginPage.passwordHasError(), "expected password error message to be displayed");
		
		
		//error email
		String prefix = username.split("@")[0];
		String suffix = username.split("@")[1];
		loginPage.setEmail(WebdriverUtils.getTimeStamp(prefix)+"@"+suffix);
		loginPage.setPassword(password);
		loginPage.clickLogin(false);
		Assert.assertTrue(loginPage.isGeneralErrorAppear(), "expected error message to be displayed");
		
		//error password
		loginPage.setEmail(username);
		loginPage.setPassword(WebdriverUtils.getTimeStamp(password));
		loginPage.clickLogin(false);
		Assert.assertTrue(loginPage.isGeneralErrorAppear(), "expected error message to be displayed");
		
		//correct values
		loginPage.setEmail(username);
		loginPage.setPassword(password);
		loginPage.clickLogin(true);
		BudgetaBoard board = new BudgetaBoard();
		Assert.assertTrue(board.isDisplayed(), "expected budgeta board to be displayed");
		
	}
}
