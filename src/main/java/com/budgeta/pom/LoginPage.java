package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;


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
	
	@FindBy(className = "error")
	private WebElement generalError;
	
	private By fieldError = By.className("input-error");
	
	
	public LoginPage(){
		WebdriverUtils.waitForElementToBeFound(driver, By.className("login-page"));
		wait.until(ExpectedConditions.visibilityOf(loginBtn));
	}
	
	public void setEmail(String e_mail){
		email.clear();
		email.sendKeys(e_mail);
	}
	
	public void setPassword(String pass){
		password.clear();
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
	
	public void clickLogin(boolean doLogin){
		loginBtn.click();
		if(!doLogin){
			WebdriverUtils.sleep(5000);
			return;
		}
		WebdriverUtils.waitForElementToDisappear(driver, By.className("login-page"));
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		WebdriverUtils.waitForBudgetaBusyBar(driver);
		try{
			WebdriverUtils.waitForElementToBeFound(driver, By.className("tour-page"));
		}
		catch(Exception e){
			WebdriverUtils.waitForElementToBeFound(driver, By.className("tour-page"));
		}
	}
	
	public String getEmailErrorMessage(){
		if(emailHasError())
			return  email.findElement(By.xpath("..")).findElement(fieldError).getText();
		return "";
	}
	
	public String getPasswordErrorMessage(){
		if(emailHasError())
			return  password.findElement(By.xpath("..")).findElement(fieldError).getText();
		return "";
	}
	
	public String getErrorMessage(){
		return generalError.getText();
	}
	
	public boolean isGeneralErrorAppear(){
		return !getErrorMessage().isEmpty();
	}
	
	public SignUpPage clickSignUp(){
		signUpBtn.click();
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		return new SignUpPage();
	}

	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}
	

}