package com.budgeta.pom;

import org.openqa.selenium.By;

import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class SharePopup extends SmallPopup {
	
//	private By moreInfo = By.cssSelector("div.modal-header span.help-iq");
	private By sendBtn = By.cssSelector("form.invite-more button");
	
//	private By moreInfoBtn = By.cssSelector("div.modal-body span.help-iq");
	
	
	
	public void clickSend(){
		wrapper.findElement(sendBtn).click();
		WebdriverUtils.waitForElementToDisappear(driver, sendBtn);
	}
	
	public void sendEmail(String _email){
		setName(_email);
	}
	

}
