package com.budgeta.test.SignUp;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.LoginPage;
import com.budgeta.pom.SignUpPage;
import com.budgeta.pom.SignUpSuccessPage;
import com.budgeta.pom.TopBar;
import com.budgeta.pom.WelcomeScreen;
import com.budgeta.test.BudgetaTest;
import com.galilsoftware.AF.core.listeners.DataProviderParams;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestNGListener;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;


@Listeners({ MethodListener.class, TestNGListener.class })
public class SignUpTest extends BudgetaTest{
	
	SignUpPage signUpPage ;
	SignUpSuccessPage signUpSuccessPage;
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
		if(errorField.equals("InvalidPass"))
			Assert.assertFalse(signUpPage.passHasDigitsAndLetters(data.get("password")), "Password must contain at least one letter and one number and Password length must be at least 8 characters");
		
	
		if (data.get("ShouldPass").equals("TRUE")){
			signUpSuccessPage = new SignUpSuccessPage();
			Assert.assertTrue(signUpSuccessPage.isDisplayed(), "Expected To Sign Uo Seccess page to be dispaly");
			signUpSuccessPage.ConfirmSignUp();
			
			LoginPage login = new LoginPage();
			login.setEmail(email);
			login.setPassword(data.get("password"));
			login.clickLogin(true);
			
			WelcomeScreen welcomescreen =  new WelcomeScreen();
			Assert.assertTrue(welcomescreen.isDisplayed(), "Expected welcome screen to be displayed");
			welcomescreen.clickSkipTour();
			
			TopBar topBar = new TopBar();
			topBar.clickLogout();
		}
				
	}

}
