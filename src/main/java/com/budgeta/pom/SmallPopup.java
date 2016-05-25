package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.web.util.WebUtils;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class SmallPopup extends AbstractPOM{

	@FindBy(className = "modal-content")
	protected List<WebElement> wrappers;

	@FindBy(id = "cancel-btn")
	WebElement cancelBtn;
	
	@FindBy(id = "confirm-btn")
	private WebElement confirmBtn;
	
//	@FindBy(className = "modal-title")
//	private WebElement title;
	
	private By title = By.className("modal-title");
	private By nameField = By.className("ember-text-field");
	private By closeBtn = By.className("close");
	

	protected WebElement wrapper;
	
	public SmallPopup(){
		for(WebElement el : wrappers){
			if(WebdriverUtils.isDisplayed(el))
				wrapper = el;
		}
	}
	
	
	
	
	
	
	public String getTilte(){
		//return driver.findElement(title).getText();
		String title="";
		List<WebElement> popups = driver.findElements(By.className("modal-title"));
		int popUpsCount = popups.size(); 
		try{
			if (popUpsCount == 1){
				title = driver.findElements(By.className("modal-title")).get(0).getText();
			}

			if (popUpsCount > 1){
				title = driver.findElements(By.className("modal-title")).get(popUpsCount - 1).getText();
			}
			
		}catch(StaleElementReferenceException se){
			WebdriverUtils.sleep(1000);
			title = driver.findElement(By.className("modal-title")).getText();
		}
		return title;
	}
	
	public String getConfirmButtontext(){
		String confirmText;
		
		try{
			confirmText = driver.findElement(By.id("confirm-btn")).getText();
		}catch(StaleElementReferenceException se){
			WebdriverUtils.sleep(1000);
			confirmText = driver.findElement(By.id("confirm-btn")).getText();
		}
		
		return confirmText;
	}
	
	public void setName(String name){
		wrapper.findElement(nameField).clear();
		wrapper.findElement(nameField).sendKeys(name);
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}
	
	public void clickConfirm(){
		confirmBtn.click();
		WebdriverUtils.waitForElementToDisappear(driver, By.className("modal-content"));
		WebdriverUtils.sleep(3000);
		WebdriverUtils.waitForBudgetaBusyBar(driver);
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}
	
	public void clickConfirm(boolean doWait){
		confirmBtn.click();
		WebdriverUtils.sleep(1000);
		if(doWait){
			WebdriverUtils.waitForElementToDisappear(driver, By.className("modal-content"));
			WebdriverUtils.sleep(1000);
			WebdriverUtils.waitForBudgetaBusyBar(driver);
			WebdriverUtils.waitForBudgetaLoadBar(driver);
		}
		WebdriverUtils.waitForBudgetaBusyBar(driver);
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}
	
	public void clickCancel(){
		cancelBtn.click();
		WebdriverUtils.waitForElementToDisappear(driver, By.className("modal-content"));
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}
	
	public void clickClose(){
		wrapper.findElement(closeBtn).click();
		WebdriverUtils.waitForElementToDisappear(driver, By.className("modal-content"));
	}
	
	
	

	
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}
	
}
