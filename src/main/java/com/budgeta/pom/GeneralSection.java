package com.budgeta.pom;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;

public class GeneralSection extends AbstractPOM{

	@FindBy(id = "section-General")
	private WebElement wrapper;
	
	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
