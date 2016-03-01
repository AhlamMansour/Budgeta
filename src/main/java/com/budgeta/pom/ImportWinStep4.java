package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class ImportWinStep4 extends ImportWinStep3{
	
	@FindBy(className = "step-4")
    private WebElement wrapper;
	
	public void clickImport(){
		nextBtn.click();
		WebdriverUtils.waitForElementToDisappear(driver, By.className("step-4"));
	}

}
