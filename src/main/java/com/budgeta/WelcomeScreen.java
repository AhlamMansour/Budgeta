package com.budgeta;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;


/**
 * 
 * @author Rabia Manna
 *
 */

public class WelcomeScreen extends AbstractPOM{

	@FindBy(className = "tour-page")
	private WebElement wrapper;
	
	@FindBy(className = "next-step")
	private WebElement seeHowItWorksBtn;
	
	@FindBy(className = "skip-tour")
	private WebElement skipTheTourBtn;
	
	
	public BudgetaBoard clickSkipTour(){
		skipTheTourBtn.click();
		WebdriverUtils.waitForElementToDisappear(driver, By.className("skip-tour"));
		return new BudgetaBoard();
	}
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);	
	}
	

}
