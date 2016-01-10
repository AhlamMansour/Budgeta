package com.budgeta;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.budgeta.pom.SmallPopup;
import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class AddNewUserPopup extends SmallPopup{
	
	@FindBy(className = "modal-content")
	private WebElement wrapper;
	
	private By addUserBtn = By.cssSelector("form.create-new-user-form button");
	
	private final By noty_message = By.className("noty_text");
	
	
	public void clickAddUser(){
		wrapper.findElement(addUserBtn).click();
		WebdriverUtils.waitForBudgetaBusyBar(driver);
		WebdriverUtils.waitForElementToDisappear(driver, noty_message);
	}
	
	
	
	public void sendEmail(String _email){
		setName(_email);
	}
	
	
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
