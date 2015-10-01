package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class SharePopup extends SmallPopup {
	
//	private By moreInfo = By.cssSelector("div.modal-header span.help-iq");
	private By sendBtn = By.cssSelector("form.invite-more button");
	
	
	
	@FindBy(className = "input-error")
	private WebElement fieldError;
//	private By moreInfoBtn = By.cssSelector("div.modal-body span.help-iq");
	
	
	
	
	
	
	
	public void clickSend(){
		wrapper.findElement(sendBtn).click();
		WebdriverUtils.waitForBudgetaBusyBar(driver);
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}
	
	public void sendEmail(String _email){
		setName(_email);
	}
	
	
	
	public boolean isShareErrorAppear(){
		try{
			return !fieldError.getText().equals("");
		}
		catch(Exception e){
			return false;
		}
	}
	
	
	
	

}
