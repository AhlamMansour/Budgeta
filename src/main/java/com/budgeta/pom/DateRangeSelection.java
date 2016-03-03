package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class DateRangeSelection extends AbstractPOM{
	
	@FindBy(className = "noty_type_information")
	protected WebElement wrapper;
	
	@FindBy(className = "btn-primary")
	protected WebElement confirmBtn;
	
	@FindBy(className = "btn-cancel")
	protected WebElement cancelBtn;
	
	
	public void confirmSelectionMonth(){
		confirmBtn.click();
		WebdriverUtils.sleep(1000);
	}
	
	public void cancelSelection(){
		cancelBtn.click();
		WebdriverUtils.sleep(1000);
	}

	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}
	

}
