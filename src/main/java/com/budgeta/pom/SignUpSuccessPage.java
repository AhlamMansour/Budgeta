package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class SignUpSuccessPage extends AbstractPOM {
	
	
	@FindBy(className = "modal-content")
	private WebElement Success;
	
	@FindBy(id = "confirm-btn")
	private WebElement ConfirmBtn;
	
	
	public  SignUpSuccessPage(){
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
		wait.until(ExpectedConditions.visibilityOf(ConfirmBtn)); 
	}

	public void ConfirmSignUp(){
		ConfirmBtn.click();
		WebdriverUtils.waitForElementToBeFound(driver, By.className("main-content login-page"));
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

	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(Success);
	}
	

}
