package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class AccountSettings extends AbstractPOM{
	
	@FindBy(className = "login-page")
	private WebElement wrapper;
	
	@FindBy(className = "user-input")
	private List<WebElement> userInput;
	
	@FindBy(className = "cancel")
	private WebElement cancelBtn;
	
	@FindBy(css = "div.center button")
	private List<WebElement> footerButtons;
	
	
	
	public AccountSettings(){
		WebdriverUtils.waitForBudgetaBusyBar(driver);
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		WebdriverUtils.waitForElementToBeFound(driver, By.className("login-page"));
	}
	
	public void setFirstName(String value){
		WebElement firstName = getFirstNameField();
		firstName.clear();
		firstName.sendKeys(value);
		WebdriverUtils.sleep(100);
	}
	
	public String getFirstName(){
		return getFirstNameField().getAttribute("value");
	}

	public String getLastName(){
		return getLastNameField().getAttribute("value");
	}
	
	public void setLastName(String value){
		WebElement lastName = getLastNameField();
		lastName.clear();
		lastName.sendKeys(value);
		WebdriverUtils.sleep(100);
	}
	
	public String getTitle(){
		return wrapper.findElement(By.className("page-header")).getText();
	}
	
	public BudgetaBoard clickCancel(){
		cancelBtn.click();
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		return new BudgetaBoard();
	}
	
	public BudgetaBoard clickSave(){
		getSaveButton().click();
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		WebdriverUtils.waitForBudgetaBusyBar(driver);
		

		
		return new BudgetaBoard();
	}
	
	private WebElement getSaveButton() {
		for(WebElement el : footerButtons){
			if(el.getAttribute("type").equals("submit"))
				return el;
		}
		return null;
	}

	private WebElement getFirstNameField(){
		for(WebElement el : userInput){
			if(el.findElement(By.tagName("label")).getText().trim().equals("First Name"))
				return el.findElement(By.tagName("input"));
		}
		return null;
	}
	
	private WebElement getLastNameField(){
		for(WebElement el : userInput){
			if(el.findElement(By.tagName("label")).getText().trim().equals("Last Name"))
				return el.findElement(By.tagName("input"));;
		}
		return null;
	}
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils .isDisplayed(wrapper);
	}
	
	

}
