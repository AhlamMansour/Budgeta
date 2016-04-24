package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class TransactionTable extends AbstractPOM{
	
	@FindBy(className = "trx-table")
	private WebElement wrapper;
	
	@FindBy(className = "trx-table-header")
	private WebElement headerWrapper;
	
	@FindBy(className = "flag")
	private WebElement flag;
	
	@FindBy(className = "svg-icon")
	private List<WebElement> icons;
	
	
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
