package com.budgeta.pom;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

/**
 * 
 * @author Rabia Manna
 *
 */

public class BudgetaBoard extends AbstractPOM{

	@FindBy(className = "main-content")
	private WebElement wrapper;
	
	@FindBy(className = "add-root-budget")
	private WebElement addBudgetaBtn;
	
	
	
	public NewBudgetPopup addBudgeta(){
		WebdriverUtils.waitUntilClickable(driver, addBudgetaBtn);
		addBudgetaBtn.click();
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		return new NewBudgetPopup();
	}
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}
	
	

}
