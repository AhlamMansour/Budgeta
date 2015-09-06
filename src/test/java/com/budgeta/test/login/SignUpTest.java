package com.budgeta.test.login;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.budgeta.pom.LoginPage;
import com.budgeta.pom.SignUpPage;
import com.budgeta.pom.SignUpSuccessPage;
import com.budgeta.test.BudgetaTest;
import com.galilsoftware.AF.core.listeners.TestFirst;

public class SignUpTest extends BudgetaTest{

	SignUpPage signUpPage ;
	SignUpSuccessPage signUpSuccessPage;
	
	@TestFirst
	@Test(enabled = true)
	public void insertToSignUpPage(){
		
		driver.get(baseURL);
		LoginPage loginPage = new LoginPage();
		signUpPage = loginPage.clickSignUp();
		
		Assert.assertTrue(signUpPage.isDisplayed(),"expected to sign up page be displayed");
	}
	
	@Test(enabled = true)
	public void signUpTest(){
		
		signUpPage.setFirstName("firstName");
		signUpPage.setLastName("last");
		signUpPage.setEmail("ahlam@hotmail.com");
		signUpPage.setInvitationCode("trybudgeta");
		signUpPage.setPassword("123");
		signUpPage.setPasswordVerify("123");
		
		signUpPage.clickSignUp();
	
		System.out.println("");
	}
	
	@Test(enabled = true)
	public void ConfirmSignUp(){
		signUpSuccessPage.ConfirmSignUp();
		//Assert.assertTrue(signUpSuccessPage.isDisplayed(),"Error");
		
	}
	
	
}
