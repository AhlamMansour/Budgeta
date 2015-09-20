package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebElementUtils;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class MenuTrigger extends AbstractPOM {

	
	private WebElement wrapper;
	
	private By lineSettingTriggerMenu = By.cssSelector("div.qtip-focus ul.narrow li");
	
	
	public MenuTrigger(WebElement _wrapper){
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
	
	public SharePopup clickShare(){
		selectScenarioTrigger("Share");
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));
		return new SharePopup();
	}
	
	public DeletePopup clickDelete(){
		selectScenarioTrigger("Delete");
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));
		return new DeletePopup();
	}
	
	
	private void selectScenarioTrigger(String option){
		openLineSettings();
		for(WebElement el : driver.findElements(lineSettingTriggerMenu)){
			if(el.getText().equals(option))
				el.click();
		}
	}
	
	private void openLineSettings(){
		WebElementUtils.hoverOverField(wrapper, driver, null);
		wait.until(ExpectedConditions.visibilityOf(wrapper));
		WebElementUtils.clickElementEvent(driver,wrapper);
		try{
			WebdriverUtils.waitForElementToBeFound(driver, By.className("qtip-focus"));
			wait.until(ExpectedConditions.visibilityOfElementLocated(lineSettingTriggerMenu));
		}
		catch(Exception e){
			WebElementUtils.clickElementEvent(driver,wrapper);
			WebdriverUtils.waitForElementToBeFound(driver, By.className("qtip-focus"));
			wait.until(ExpectedConditions.visibilityOfElementLocated(lineSettingTriggerMenu));
		}
	}

	

	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}
	
}
