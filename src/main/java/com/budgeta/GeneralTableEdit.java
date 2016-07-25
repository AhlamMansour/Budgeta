package com.budgeta;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class GeneralTableEdit extends AbstractPOM{
	
	@FindBy(className = "table-editor")
	private WebElement wrapper;
	
	
	@FindBy(className = "notes")
	private WebElement note;
	
	@FindBy(className = "flag")
	private WebElement flag;
	
	@FindBy(className = "duplicate")
	private WebElement duplicateLine;
	
	@FindBy(className = "delete_budget")
	private WebElement deleteLine;
	
	@FindBy(className = "table-edit-line")
	private WebElement tableLine;
	
	
	
	

	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
