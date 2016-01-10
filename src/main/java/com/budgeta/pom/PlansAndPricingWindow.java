package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class PlansAndPricingWindow extends AbstractPOM {

	
	
	@FindBy(className = "modal-content")
	private WebElement wrapper;
	
	@FindBy(className = "box-btn")
	private WebElement boxBtn;
	
	private By DropdownUser = By.className("select2-chosen");
	
	
	
	public void openEditUsers(){
		
	}
	
	
	public void clickOnUpgrade(){
		
	}
	
	
	public void clickOnDowngrade(){
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
		
	}
	
	
}
