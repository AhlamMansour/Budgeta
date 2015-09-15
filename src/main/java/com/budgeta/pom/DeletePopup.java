package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class DeletePopup extends AbstractPOM {

	@FindBy(className = "modal-content")
	private WebElement wrapper;

	@FindBy(id = "cancel-btn")
	private WebElement cancelBtn;

	@FindBy(id = "confirm-btn")
	private WebElement deleteBtn;

	@FindBy(className = "modal-title")
	private WebElement title;

	private By closeBtn = By.className("close");
	
	
	public String getTilte(){
		return title.getText();
	}
	
	public void clickDelete(){
		deleteBtn.click();
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
