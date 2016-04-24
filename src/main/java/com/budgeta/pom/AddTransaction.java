package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class AddTransaction extends AbstractPOM {

	@FindBy(className = "top-bar-buttons")
	private WebElement wrapper;

	@FindBy(className = "view-tab-option")
	private WebElement viewTabOption;

	@FindBy(className = "svg-icon")
	private List<WebElement> icons;

	public void clickSummaryTab() {

		if (viewTabOption.getText().equalsIgnoreCase("Summary")) {
			viewTabOption.click();
		}

	}

	public void clickTransactionTab() {

		if (viewTabOption.getText().equalsIgnoreCase("Transactions")) {
			viewTabOption.click();
		}

	}
	
	public void clickImportData(){
		
		for(WebElement el : icons){
			if(el.getText().equalsIgnoreCase("Import data")){
				el.click();
			}
		}
		
	}
	
	public void clickAddTransaction(){
		for(WebElement el : icons){
			if(el.getText().equalsIgnoreCase("Add transaction")){
				el.click();
			}
		}	
	}
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
