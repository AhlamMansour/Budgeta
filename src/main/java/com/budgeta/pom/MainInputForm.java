package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class MainInputForm extends AbstractPOM{
	

	@FindBy(id = "main-input-form")
	private WebElement wrapper;
	
	public boolean isViewOnlyMode(){
		try {
			return wrapper.findElement(By.className("highlight")).isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}
}
