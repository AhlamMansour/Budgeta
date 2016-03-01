package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class ImportWinStep3 extends ImportWinStep2{
	
	@FindBy(className = "step-3")
    private WebElement wrapper;
	
	@FindBy(css = "ol.numbered-steps li a")
    private WebElement editselection;
	
	public void editSelectedRows(){
		editselection.click();
		WebdriverUtils.waitForElementToBeFound(driver, By.className("step-2"));
	}
	
	public void clickNext(){
		nextBtn.click();
		WebdriverUtils.waitForElementToBeFound(driver, By.className("step-4"));
	}

}
