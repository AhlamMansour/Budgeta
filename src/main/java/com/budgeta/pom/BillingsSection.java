package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class BillingsSection extends AbstractPOM{
	
	
	@FindBy(id = "section-Billings")
	private WebElement wrapper;
	
	@FindBy(id = "attribute-period")
	private WebElement repeat;
	
	@FindBy(id = "attribute-amountType")
	private WebElement spread;
	
	@FindBy(id = "attribute-occursOn")
	private WebElement onDate;
	
	@FindBy(id = "attribute-at")
	private WebElement atDate;
	
	@FindBy(id = "attribute-amount")
	private WebElement amount;
	
	@FindBy(name = "paymentAfter")
	private WebElement paymentAfter;
	
	
	private By dateRange = By.cssSelector("div.month-picker input");
	private By textField = By.className("ember-text-field");
	private By dropdown = By.className("select2-container");
	
	
	DropDown repeatDropDown;
	DropDown spreadDropDown;
	DropDown amountDropDown;
	DropDown atDateDropDown;
	
	public BillingsSection(){
		initializeRepeatDropdown();
	}
	
	
	public void selectRepeat(String option){
		repeatDropDown.selsectOption(option);
		if(option.equals("Once")){
			return;
		}
		else{
			wait.until(ExpectedConditions.visibilityOf(spread));
			initializeSpreadDropdown();
		}
	}
	
	public void selectSpred(String option){
		spreadDropDown.selsectOption(option);
	}
	
	public void selectOnDate(String month, String year){
		onDate.findElement(dateRange).click();
		DateRange range = new DateRange();
		range.setYear(year);
		range.setMonth(month);
		
	}
	
	public String getSelectedOndDate(){
		return onDate.findElement(dateRange).getAttribute("value");
	}
	
	public void setAmount(String value){
		WebElement field = amount.findElement(textField);
		field.clear();
		field.sendKeys(value);
	}
	
	public void setCurrency(String curr){
		initializeAmaountDropdown();
		amountDropDown.selsectOption(curr);
	}
	
	public void setPaYmentAfter(String value){
		paymentAfter.clear();
		paymentAfter.sendKeys(value);
	}
	
	public void selectAtDate(String option){
		initializeAtDateDropdown();
		atDateDropDown.selsectOption(option);
	}
	/*************************************************************************/
	private void initializeRepeatDropdown(){
		repeatDropDown = new DropDown(repeat.findElement(dropdown));
	}
	
	private void initializeSpreadDropdown(){
		spreadDropDown = new DropDown(spread.findElement(dropdown));
	}

	private void initializeAmaountDropdown(){
		amountDropDown = new DropDown(amount.findElement(dropdown));
	}
	
	private void initializeAtDateDropdown(){
		atDateDropDown = new DropDown(atDate.findElement(dropdown));
	}

	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}
}
