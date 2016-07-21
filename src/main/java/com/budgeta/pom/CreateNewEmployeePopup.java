package com.budgeta.pom;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class CreateNewEmployeePopup extends AbstractPOM{
	
	@FindBy(className = "modal-content")
	private WebElement wrapper;
	
	
	
	

	
	
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
