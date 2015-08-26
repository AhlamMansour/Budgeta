package main.java.budgeta.component;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
	
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
	

}