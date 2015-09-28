package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class SuccessPage extends SmallPopup{
	
	
	@FindBy(className = "main-view")
	private WebElement mainWrapper;
		
	public SuccessPage(){
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
	}
	
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
