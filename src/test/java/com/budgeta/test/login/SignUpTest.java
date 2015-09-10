package com.budgeta.test.login;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.budgeta.pom.LoginPage;
import com.budgeta.pom.SignUpPage;
import com.budgeta.test.BudgetaTest;
import com.galilsoftware.AF.core.listeners.DataProviderParams;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;



public class SignUpTest extends BudgetaTest{
	
	SignUpPage signUpPage ;
	String existEmail = "";
	
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
		String email = data.get("email");
		
		if(email.contains("@")){
			String prefix = email.substring(0, email.indexOf("@"));
			String suffix = email.substring(email.indexOf("@"));
			email = prefix + WebdriverUtils.getTimeStamp("_") + suffix;
		}
		
		if(data.get("ExistEmail").equals("FALSE")){
			existEmail = email;
		}
		else{
			email = existEmail;
		}
		
		signUpPage.setEmail(email);
		signUpPage.setPassword(data.get("password"));
		signUpPage.setPasswordVerify(data.get("PasswordVerify"));
		
		signUpPage.clickSignUp();
		
		String errorField = data.get("ErrorField");
		if(errorField.equals("InvitationCode"))
			Assert.assertTrue(signUpPage.InvitationCodeHasError(),"Value is required");
		if(errorField.equals("email"))
			Assert.assertTrue(signUpPage.EmailHasError(),"Email address is invalid");
		if(errorField.equals("SignUpErrorPass"))
			Assert.assertTrue(signUpPage.isGeneralErrorAppear(),"Passwords don't match");
		if(errorField.equals("SignUpErrorUser"))
			Assert.assertTrue(signUpPage.isGeneralErrorAppear(),"An account with this email address already exists");
		
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
