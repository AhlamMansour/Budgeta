package com.budgeta.test.login;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.budgeta.pom.LoginPage;
import com.budgeta.pom.SignUpPage;
import com.budgeta.pom.SignUpSuccessPage;
import com.budgeta.test.BudgetaTest;
import com.galilsoftware.AF.core.listeners.DataProviderParams;
import com.galilsoftware.AF.core.listeners.TestFirst;



public class SignUpTest extends BudgetaTest{
	
	SignUpPage signUpPage ;
	
	@Test(dataProvider = "ExcelFileLoader", enabled = true)
	@DataProviderParams(sheet = "SignUp" , area = "CreateUser")
	public void createUsertTest(Hashtable<String, String> data) {
	
		driver.get(baseURL);
		LoginPage loginPage = new LoginPage();
		signUpPage = loginPage.clickSignUp();
		String EmailError = signUpPage.getEmailErrorMessage();
		System.out.println(EmailError);
		Assert.assertTrue(signUpPage.isDisplayed(),"expected to sign up page be displayed");
		
		signUpPage.setInvitationCode(data.get("InvitationCode"));
		
		//Assert.assertTrue(signUpPage.InvitationCodeHasError(), "Request a beta invitation.");
		signUpPage.setFirstName(data.get("FirstName"));
		signUpPage.setLastName(data.get("LastName"));
		signUpPage.setEmail(data.get("email"));
		signUpPage.setPassword(data.get("password"));
		signUpPage.setPasswordVerify(data.get("PasswordVerify"));
		
		signUpPage.clickSignUp();
		
		
		if(data.get("ErrorField").equals("InvitationCode"))
			Assert.assertEquals(signUpPage.InvitationCodeHasError(), "Value is required");
		
		
		
		//Assert.assertTrue(signUpPage.isGeneralErrorAppear(), "");
		//Assert.assertEquals(signUpPage.getError(), "");
		
		//Assert.assertTrue(signUpPage.InvitationCodeHasError(), "Request a beta invitation.");
		
		
	}


	
	/*
	
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
		
		signUpPage.setFirstName("trybudgeta");
		signUpPage.setLastName("last");
		signUpPage.setEmail("ahlam1@hotmail.com");
		signUpPage.setInvitationCode("trybudgeta");
		signUpPage.setPassword("123");
		signUpPage.setPasswordVerify("123");
		
		
		
		
		signUpPage.clickSignUp();
		Assert.assertTrue(signUpPage.isGeneralErrorAppear(), "");
		Assert.assertEquals(signUpPage.getError(), "");
		
	
		//Assert.assertTrue(signUpPage.getEmailErrorMessage(),"expected to sign up page be displayed");	
		//Assert.assertTrue(signUpPage.getEmailErrorMessage(), "same user");
	}
	

	*/
	
}
