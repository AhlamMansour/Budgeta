package com.budgeta.pom;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class TopBarButtons extends AbstractPOM{
	
	@FindBy(className = "top-bar-buttons-line")
	private WebElement wrapper;
	
	@FindBy(className = "div.top-bar-buttons-line div.svg-icon")
	private WebElement Icons;
	
	
	
	public boolean isImportAndTransactionIconDispaly() {

		try {
			return Icons.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}
}
