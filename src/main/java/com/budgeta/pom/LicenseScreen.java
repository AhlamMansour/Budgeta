package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.FindElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class LicenseScreen extends AbstractPOM {
	
	@FindBy(className = "my-license-page")
	private WebElement wrapper;
	
	@FindBy(className = "add-user-btn")
	private WebElement addUser;
	
	
	@FindBy(css = "form.my-license-form button")
    private List<WebElement> buttons ;
	
	@FindBy(className = "remove-btn")
	private WebElement removeUser;
	
	@FindBy(className = "is-admin-label")
	private WebElement makeAdmin;
	
	@FindBy(css = "div.user-input input.ember-text-field")
	private WebElement yourPlanName;
	

	
	
	public void clickUpdate(){
		for(WebElement el : wrapper.findElements(By.cssSelector("form.my-license-form button"))){
			if(el.getText().equals("Update")){
				el.click();
				return;
			}
		}
	}
	
	
	public void clickSave(){
		for(WebElement el : wrapper.findElements(By.cssSelector("form.my-license-form button"))){
			if(el.getText().equals("Save")){
				el.click();
				return;
			}
		}
	}
	
	public void clickCancele(){
		for(WebElement el : wrapper.findElements(By.cssSelector("form.my-license-form button"))){
			if(el.getText().equals("Cancel")){
				el.click();
				return;
			}
		}
	}
	
	public void addUser(){
		addUser.click();
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
		
	}
	
	public void removeUser(){
		removeUser.click();
	}
	
	public void makeAdminUser(){
		makeAdmin.click();
	}
	
	public String yourPlanName()
	{
		return yourPlanName.getText();
	}
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
		
	}

}
