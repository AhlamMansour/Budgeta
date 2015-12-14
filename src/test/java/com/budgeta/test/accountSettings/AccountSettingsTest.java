package com.budgeta.test.accountSettings;


import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.AccountSettings;
import com.budgeta.pom.BudgetaBoard;
import com.budgeta.pom.ChangePassword;
import com.budgeta.pom.LoginPage;
import com.budgeta.pom.SignUpPage;
import com.budgeta.pom.TopBar;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestNGListener;

@Listeners({ MethodListener.class, TestNGListener.class })
public class AccountSettingsTest extends WrapperTest{
	
	
	
	@Test(enabled = true)
	public void changeAccountSettingsTest(){
		
		TopBar topBar = new TopBar();
		topBar.clickAccountSettings();

		
		AccountSettings account = new AccountSettings();
		
		Assert.assertTrue(account.isDisplayed(), "expected account settings page to be displayed");
		Assert.assertEquals(account.getTitle(), "YOUR ACCOUNT");
			
		account.setFirstName("Dema");
		account.setLastName("Ma");
		account.clickSave();
		
		Assert.assertEquals(topBar.getUserName(), "Dema Ma");
		
		topBar.clickAccountSettings();
		account = new AccountSettings();
		
		Assert.assertEquals(account.getFirstName(), "Dema");
		Assert.assertEquals(account.getLastName(), "Ma");
		
		account.setFirstName("ahlam");
		account.setLastName("mansour");
		
		account.clickSave();	
	}
	
	@Test(enabled = true)
	public void changePasswordTest(){
		TopBar topBar = new TopBar();
		topBar.clickChangePassword();
		
		ChangePassword changePassword = new ChangePassword();
		
		Assert.assertTrue(changePassword.isDisplayed(), "expected change password page to be displayed");
		Assert.assertEquals(changePassword.getTitle(), "CHANGE PASSWORD");
		
		/*
		//error current password
		changePassword.setCurrentPassword(WebdriverUtils.getTimeStamp(""));
		changePassword.setNewPassword(newPassword);
		changePassword.setVerifyPassword(newPassword);
		changePassword.clickSave(false);
		Assert.assertTrue(changePassword.isErrorDisplayed(), "expected to error");
		
		//error verify password
		changePassword.setCurrentPassword(password);
		changePassword.setNewPassword(newPassword);
		changePassword.setVerifyPassword(newPassword+WebdriverUtils.getTimeStamp(""));
		changePassword.clickSave(false);
		Assert.assertTrue(changePassword.isErrorDisplayed(), "expected to error");
		*/
		
		 
		//success change password
		changePassword.setCurrentPassword(password);
		changePassword.setNewPassword(newPassword);
		changePassword.setVerifyPassword(newPassword);
		
		if(changePassword.passHasDigitsAndLetters(newPassword) == false){
			changePassword.clickSave(false);
			Assert.assertFalse(changePassword.passHasDigitsAndLetters(newPassword), "Password must contain at least one letter and one number and Password length must be at least 8 characters");
		}else{
			changePassword.clickSave(true);
			topBar.clickLogout();
			
			LoginPage login = new LoginPage();
			login.setEmail(username);
			login.setPassword(password);
			login.clickLogin(false);
			
			Assert.assertTrue(login.isGeneralErrorAppear(), "expected to get error with the old password");
			
			login.setPassword(newPassword);
			login.clickLogin(true);
			
			BudgetaBoard board = new BudgetaBoard();
			Assert.assertTrue(board.isDisplayed(), "expected to login and budgeta board to be displayed");
			
			topBar = new TopBar();
			topBar.clickChangePassword();
			
			changePassword.setCurrentPassword(newPassword);
			changePassword.setNewPassword(password);
			changePassword.setVerifyPassword(password);
			changePassword.clickSave(true);
			
		}
			
		
		
		
		
	}
}
