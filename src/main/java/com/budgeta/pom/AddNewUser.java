package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class AddNewUser extends SmallPopup{
	
	
	@FindBy(css = "div.close-button-wrapper div.svg-icon")
	private WebElement closeBtn;
	
	@FindBy(css = "form.create-new-user-form button")
	private WebElement addUserBtn;
	
	
	
	public void clickAddUser(){
		addUserBtn.click();
		WebdriverUtils.waitForElementToDisappear(driver, By.className("modal-content"));
		//WebdriverUtils.waitForBudgetaBusyBar(driver);
	}
	
	public void clickClosePopup(){
		closeBtn.click();
		WebdriverUtils.waitForElementToDisappear(driver, By.className("modal-content"));
		WebdriverUtils.waitForBudgetaBusyBar(driver);
	}
	
	
}
