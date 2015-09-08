package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class SignUpPage extends AbstractPOM {
	
	@FindBy(className = "login-page")
	private WebElement wrapper;
	
	@FindBy(id = "inviteCode")
	private WebElement InvitationCode;
	
	@FindBy(id = "firstName")
	private WebElement FirstNmae;
	
	@FindBy(id = "lastName")
	private WebElement LastName;
	
	@FindBy(id = "email")
	private WebElement email;
	
	@FindBy(id = "password")
	private WebElement password;
	
	@FindBy (id = "passwordVerify")
	private WebElement PasswordVerify;
	
	@FindBy (id = "signup-btn")
	private WebElement SignUpBtn;
	
	@FindBy (id = "terms-cond-btn")
	WebElement TermsAndConditionBtn;
	
	
	@FindBy(className = "error")
	private WebElement generalError;
	
	@FindBy(className = "login")
	private WebElement Login;
	
	
	private By fieldError = By.className("input-error");

	
	public SignUpPage()
	{
		WebdriverUtils.waitForElementToBeFound(driver, By.className("login-page"));
		wait.until(ExpectedConditions.visibilityOf(SignUpBtn)); 
	}
	
	
	public void setInvitationCode(String code){
		InvitationCode.sendKeys(code);
		
	}
	
	public void setFirstName(String firstname){
		FirstNmae.sendKeys(firstname);
	}
	
	public void setLastName(String lastname){
		LastName.sendKeys(lastname);
	}
	
	public void setEmail(String e_mail){
		email.sendKeys(e_mail);
		
	}
	
	public void setPassword(String pass){
		password.sendKeys(pass);
	}
	
	public void setPasswordVerify(String passVerify){
		PasswordVerify.sendKeys(passVerify);
	}
	
	
	public void getInvitationCode(){
		InvitationCode.getAttribute("value");
	}
	
	public void getFirstName(){
		FirstNmae.getAttribute("value");
	}
	
	public void getLastName()
	{
		LastName.getAttribute("value");
	}
	
	public void getEmail(){
		email.getAttribute("value");
	}
	
	public void getPassword(){
		password.getAttribute("value");
	}
	
	public void getPasswordVerify(){
		PasswordVerify.getAttribute("value");
	}
	
	public boolean InvitationCodeHasError(){
		return elementHasError(InvitationCode);
	}
	
	
	public boolean FirstNameHasError(){
		return elementHasError(FirstNmae);
	}
	
	public boolean LastNameHasError(){
		return elementHasError(LastName);
	}
	
	public boolean EmailHasError(){
		return elementHasError(email);
	}
	
	public boolean PasswordHasError(){
		return elementHasError(password);
	}
	
	public boolean PasswordVerifyHasError(){
		return elementHasError(PasswordVerify);
	}
	
	private boolean elementHasError(WebElement el){
		return el.findElement(By.xpath("..")).getAttribute("class").contains("has-error");
	}
	
	public void clickSignUp(){
		SignUpBtn.click();
		
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		//WebdriverUtils.waitForBudgetaLoadBar(driver);
		//WebdriverUtils.waitForBudgetaBusyBar(driver);
		/*
		try{
			WebdriverUtils.waitForElementToBeFound(driver, By.className("tour-page"));
		}
		catch(Exception e){
			WebdriverUtils.waitForElementToBeFound(driver, By.className("tour-page"));
		}
		*/
		
		
	}
	
	
	public String getEmailErrorMessage(){
		
		if(EmailHasError())
			return  email.findElement(By.xpath("..")).findElement(fieldError).getText();
		return "";
	}
	
	public String getInvitationCodeMessage(){
		
		if(EmailHasError())
			return  InvitationCode.findElement(By.xpath("..")).findElement(fieldError).getText();
		return "";
	}


	public String getFirstNameMessage(){
	
	if(EmailHasError())
		return  FirstNmae.findElement(By.xpath("..")).findElement(fieldError).getText();
	return "";
}

	public String getLastNameMessage(){
	
	if(EmailHasError())
		return  LastName.findElement(By.xpath("..")).findElement(fieldError).getText();
	return "";
}
	
	public String getPasswordMessage(){
		
		if(EmailHasError())
			return  password.findElement(By.xpath("..")).findElement(fieldError).getText();
		return "";
	}
	
public String getPassworVerifydMessage(){
		
		if(EmailHasError())
			return  PasswordVerify.findElement(By.xpath("..")).findElement(fieldError).getText();
		return "";
	}

	
	public String getError(){
		return generalError.getText();
	}
	
	public boolean isGeneralErrorAppear(){
		return WebdriverUtils.isDisplayed(generalError);
	}

	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
