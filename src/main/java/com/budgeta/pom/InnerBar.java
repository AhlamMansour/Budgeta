package com.budgeta.pom;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;

public class InnerBar extends AbstractPOM{
	
	@FindBy(className = "inner-bar")
	private WebElement wrapper;

	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
