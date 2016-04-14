package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class LimitPopup extends SmallPopup {

	public void clickCancel(){
		List<WebElement> cancelButton = driver.findElements(By.id("cancel-btn"));
		int cancelButtonCount = cancelButton.size();
		
		if(cancelButtonCount == 1){
			driver.findElements(By.id("cancel-btn")).get(0).click();
			WebdriverUtils.waitForBudgetaLoadBar(driver);
		}
		
		if(cancelButtonCount > 1){
			driver.findElements(By.id("cancel-btn")).get(cancelButtonCount - 1).click();
			WebdriverUtils.waitForBudgetaLoadBar(driver);
		}
	}

}
