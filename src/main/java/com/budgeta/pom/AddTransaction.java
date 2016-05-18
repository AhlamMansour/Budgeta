package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
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

	private By subReportType = By.className("select2-choice");
	
	private SideDropDown dropdown;
	private SideDropDown subReporterDropDown;
	
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
				break;
			}
		}	
	}
	
	public void selectSubReportType(String option){
		dropdown = new SideDropDown(wrapper.findElement(By.className("sheets-dropdown-select")));
		String str = dropdown.getSelectedValue();
		if(!str.equals(option)){
			getSubReporterDropDown().selectValue(option);
			WebdriverUtils.sleep(200);
		}
		
	}
	
	
private SideDropDown getSubReporterDropDown(){
		
		if(subReporterDropDown == null){
//			for(WebElement el : wrapper.findElements(subReportType)){
//				if(el.getText().trim().equals("Budget"))
			subReporterDropDown = new SideDropDown(
					driver.findElements(subReportType).get(0).findElement(By.xpath("..")));
//		}
		}
		return subReporterDropDown;
	}



	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
