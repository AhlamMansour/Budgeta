package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class UploadImportFile extends AbstractPOM{

	
	@FindBy(className = "import")
    private WebElement wrapper;
	
	@FindBy(className = "import-select-file")
    private WebElement upload;
	
	public void clickUpload(String path){
		upload.findElement(By.tagName("input")).sendKeys(path);
		WebdriverUtils.sleep(2000);
		WebdriverUtils.waitForBudgetaBusyBar(driver);
		WebdriverUtils.waitForElementToBeFound(driver, By.className("step-1"));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
    public boolean isDisplayed() {
	return WebdriverUtils.isDisplayed(wrapper);
    }
}
