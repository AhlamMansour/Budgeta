package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class SmallPopup extends AbstractPOM{

	@FindBy(className = "modal-content")
	protected WebElement wrapper;

	@FindBy(id = "cancel-btn")
	private WebElement cancelBtn;
	
	@FindBy(id = "confirm-btn")
	private WebElement confirmBtn;
	
	@FindBy(className = "modal-title")
	private WebElement title;
	
	private By nameField = By.className("ember-text-field");
	private By closeBtn = By.className("close");
	
	
	public String getTilte(){
		return title.getText();
	}
	
	public void setName(String name){
		wrapper.findElement(nameField).clear();
		wrapper.findElement(nameField).sendKeys(name);
	}
	
	public void clickCreate(){
		confirmBtn.click();
		WebdriverUtils.waitForElementToDisappear(driver, By.className("modal-content"));
	}
	
	public void clickCancel(){
		cancelBtn.click();
		WebdriverUtils.waitForElementToDisappear(driver, By.className("modal-content"));
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
