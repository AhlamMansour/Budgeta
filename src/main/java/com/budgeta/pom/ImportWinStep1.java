package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class ImportWinStep1 extends AbstractPOM{
	
	@FindBy(className = "step-1")
    private WebElement wrapper;
	
	@FindBy(css = "ul.quick-links li")
    private List<WebElement> quickLinks;
	
	@FindBy(css = "button.right")
    WebElement nextBtn;
	
	@FindBy(css = "button.inactive")
    private WebElement cancelBtn;
	
	
	public void clickOnNext(){
		nextBtn.click();
		WebdriverUtils.waitForElementToBeFound(driver, By.className("step-2"));
	}
	
	public void clickOnCancel(){
		cancelBtn.click();
		WebdriverUtils.waitForElementToDisappear(driver, By.className("step-1"));
	}
	
	
	
	@Override
    public boolean isDisplayed() {
	return WebdriverUtils.isDisplayed(wrapper);
    }

}
