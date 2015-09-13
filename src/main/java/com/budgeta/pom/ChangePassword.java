package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class ChangePassword extends AbstractPOM{
	
	@FindBy(className = "login-page")
	private WebElement wrapper;
	
	@FindBy(className = "cancel")
	private WebElement cancelBtn;
	
	@FindBy(css = "div.center button")
	private List<WebElement> footerButtons;
	
	@FindBy(className = "ember-text-field")
	private List<WebElement> textFields;
	
	
	public ChangePassword(){
		WebdriverUtils.waitForBudgetaBusyBar(driver);
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		WebdriverUtils.waitForElementToBeFound(driver, By.className("login-page"));
	}
	
	public String getTitle(){
		return wrapper.findElement(By.tagName("h1")).getText();
	}
	
	public void setCurrentPassword(String pass){
		WebElement field = getCurrentPasswordField();
		field.clear();
		field.sendKeys(pass);
	}
	
	public void setNewPassword(String pass){
		WebElement field = getNewPasswordField();
		field.clear();
		field.sendKeys(pass);
	}
	
	public void setVerifyPassword(String pass){
		WebElement field = getVerifyPasswordField();
		field.clear();
		field.sendKeys(pass);
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
	
	private WebElement getCurrentPasswordField(){
		return getField("Current Password");
	}
	
	private WebElement getNewPasswordField(){
		return getField("New Password");
	}
	
	private WebElement getVerifyPasswordField(){
		return getField("Verify Password");
	}
	
	private WebElement getField(String field){
		for(WebElement el : textFields){
			if(el.getAttribute("placeholder").equals(field))
				return el;
		}
		return null;
	}
	
	private WebElement getSaveButton() {
		for(WebElement el : footerButtons){
			if(el.getAttribute("type").equals("submit"))
				return el;
		}
		return null;
	}
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils .isDisplayed(wrapper);
	}
}
