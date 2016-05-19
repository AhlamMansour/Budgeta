package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class SharePopup extends SmallPopup {
	
//	private By moreInfo = By.cssSelector("div.modal-header span.help-iq");
	private By sendBtn = By.cssSelector("form.invite-more button");
	
	
	
	@FindBy(className = "input-error")
	private WebElement fieldError;
	
	@FindBy(css = "div.table-wrapper ul.table li")
    private List<WebElement> table ;
//	private By moreInfoBtn = By.cssSelector("div.modal-body span.help-iq");
	
	@FindBy(css = "div.modal-content div.permission-select")
	private WebElement permissionSelect;
	
	@FindBy(css = "div.budgeta-checkbox label")
	private List<WebElement> excludeSheet;
	
    private By sharePermissions = By.cssSelector("div.select2-drop-active ul.select2-results li");
	

	
	
	
	public void clickSend(){
		wrapper.findElement(sendBtn).click();
		WebdriverUtils.waitForBudgetaBusyBar(driver);
	//	WebdriverUtils.waitForBudgetaLoadBar(driver);
	}
	
	public void sendEmail(String _email){
		setName(_email);
	}
	
	public void selectSharePermissios(String option){
		permissionSelect.click();
		for(WebElement el : driver.findElements(sharePermissions)){
			if(el.getText().equals(option)){
				el.click();
				return;
			}
		}
	}
	
	public void selectExcludeSheet(String option){
		
		for (WebElement el : excludeSheet){
			if(el.getText().equals(option))
			{
				el.click();
				break;
			}
				
			
		}
		
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
