package com.budgeta;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

/**
 * 
 * @author Rabia Manna
 *
 */

public class LoginPage extends AbstractPOM{
	
	@FindBy(className = "login-page")
	WebElement wrapper;
	
	@FindBy(id = "identification")
	WebElement email;
	
	@FindBy(id = "userpassword")
	WebElement password;
	
	@FindBy(id = "forgot-btn")
	WebElement forgotPasswordBtn;
	
	@FindBy(id = "remember-checkbox")
	WebElement rememberMeCheckBox;
	
	@FindBy(id = "login-btn")
	WebElement loginBtn;

	@FindBy(id = "signup-btn")
	WebElement signUpBtn;

	@FindBy(id = "terms-cond-btn")
	WebElement termsAndCondBtn;
	
	public LoginPage(){
		WebdriverUtils.waitForElementToBeFound(driver, By.className("login-page"));
		wait.until(ExpectedConditions.visibilityOf(loginBtn));
	}
	
	public void setEmail(String e_mail){
		email.sendKeys(e_mail);
	}
	
	public void setPassword(String pass){
		password.sendKeys(pass);
	}
	
	public void getEmail(){
		email.getAttribute("value");
	}
	
	public void getPassword(){
		password.getAttribute("value");
	}
	
	public boolean emailHasError(){
		return email.findElement(By.xpath("..")).getAttribute("class").contains("has-error");
	}
	
	public boolean passwordHasError(){
		return password.findElement(By.xpath("..")).getAttribute("class").contains("has-error");
	}
	
	public void clickLogin(){
		loginBtn.click();
		WebdriverUtils.waitForElementToDisappear(driver, By.className("login-page"));
		try{
			WebdriverUtils.waitForElementToBeFound(driver, By.className("tour-page"));
		}
		catch(Exception e){
			WebdriverUtils.waitForElementToBeFound(driver, By.className("tour-page"));
		}
	}

	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}
	

}