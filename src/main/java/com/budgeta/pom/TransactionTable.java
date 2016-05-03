package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
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
	
	private By selectedDropdown = By.className("select2-chosen");
	
	@FindBy(className = "currency")
	private WebElement currency;
	
	@FindBy(className = "ember-text-field")
	private WebElement textField;
	
	
	
	public void flagLine(){
		flag.click();
		WebdriverUtils.waitForElementToBeFound(driver, By.className("flagged"));
		
	}
	
	public void duplaicateTransactionLine(){
		for (WebElement el : icons){
			if(el.getAttribute("title").equalsIgnoreCase("Duplicate")){
				el.click();
				break;
			}
				
		}
	}
	
	public void reverseTransactionLine(){
		for (WebElement el : icons){
			if(el.getAttribute("title").equalsIgnoreCase("Reverse")){
				el.click();
				break;
			}
				
		}
	}
	
	public void deleteTransactionLine(){
		for (WebElement el : icons){
			if(el.getAttribute("title").equalsIgnoreCase("Delete")){
				el.click();
				break;
			}
				
		}
	}
	
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
