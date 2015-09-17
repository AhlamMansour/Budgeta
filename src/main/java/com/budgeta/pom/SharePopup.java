package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class SharePopup extends AbstractPOM {
	
	@FindBy(className = "modal-content")
	private WebElement wrapper;
	
	
	@FindBy(id = "cancel-btn")
	private WebElement cancelBtn;

	@FindBy(id = "confirm-btn")
	private WebElement shareBtn;
	
	
	@FindBy( className = "modal-body")
	private WebElement body;
	
	private By email = By.className("ember-text-field");
	private By shareOption = By.className("dropdown");
	private By moreInfo = By.className("svg-icon");
	private By sendBtn = By.tagName("Send"); // it is OK??
	
	

	@FindBy(className = "modal-title")
	private WebElement title;

	private By closeBtn = By.className("close");
	
	private By moreInfoBtn = By.className("svg-icon");
	
	
	
	
	public String getTilte(){
		return title.getText();
	}
	
	public void clickShare(){
		shareBtn.click();
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
	
	
	public void clickSend(){
		wrapper.findElement(sendBtn).click();
		WebdriverUtils.waitForElementToDisappear(driver, By.className("modal-content"));
	}
	
	public void sendEmail(){
		wrapper.findElement(email).sendKeys("ember-text-field");
	}
	
	// how to set More info window
	
	
	

	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}
}
