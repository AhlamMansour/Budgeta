package com.budgeta.pom;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class PreviewBoard extends AbstractPOM{

	@FindBy(className = "preview")
	private WebElement wrapper;
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
