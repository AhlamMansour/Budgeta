package com.galilsoftware.AF.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;

import com.galilsoftware.AF.core.utilities.WebdriverUtils;


/**
 * Abstract class that automatically initializes the POM's and a wait object as well and gets the driver automatically.
 * @author Amir Najjar
 *
 */
public abstract class AbstractPOM { 
	
	protected WebDriver driver = (WebDriver) TestParamsThreadPool.get(TestParamsThreadPool.KEY_WEB_DRIVER);
	
	protected Wait<WebDriver> wait = WebdriverUtils.initializePOM(driver, this);
	
	protected WebElement wrapper;
	protected WebElement Success;
	
	public abstract boolean isDisplayed();
	
}
