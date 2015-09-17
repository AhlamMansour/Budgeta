package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class BudgetLineSetting extends AbstractPOM {

	
	private WebElement wrapper;
	
	private By lineSettingTriggerMenu = By.cssSelector("div.qtip-focus ul.narrow li");
	
	
	public BudgetLineSetting(WebElement _wrapper){
		wrapper = _wrapper;
	}
	
	
	public void clickDuplicate(){
		selectScenarioTrigger("Duplicate");
		
	}
	
	public void clickRename(){
		selectScenarioTrigger("Rename");
		
	}

	public void clickFlag(){
		selectScenarioTrigger("Flag");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("flagged")));
		
	}
	
	public void clickUnFlagBudgetLine(){
		selectScenarioTrigger("Unflag");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("flagged")));
	}
	
	public void clickShare(){
		selectScenarioTrigger("Share");
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));
	}
	
	public void clickDelete(){
		selectScenarioTrigger("Delete");
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));
	}
	
	
	private void selectScenarioTrigger(String option){
		openLineSettings();
		for(WebElement el : driver.findElements(lineSettingTriggerMenu)){
			if(el.getText().equals(option))
				el.click();
		}
	}
	
	private void openLineSettings(){
		wrapper.click();
		WebdriverUtils.waitForElementToBeFound(driver, By.className("qtip-focus"));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(lineSettingTriggerMenu));
	}

	

	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}
	
}
