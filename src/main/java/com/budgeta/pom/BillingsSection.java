package com.budgeta.pom;

import java.util.List;

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
	
	@FindBy(name = "growth")
	private WebElement growth;
	
	@FindBy(name = "supportPercentage")
	private WebElement supportPercentage;
	
	@FindBy(name = "subscriptionPeriod")
	private WebElement supportPeriod;
	
	@FindBy(name = "churn")
	private WebElement churn;
	
	@FindBy(className = "error")
	List<WebElement> errors;
	
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
		DateRange range = new DateRange("From");
		range.setHireYear(year);
		range.setHireMonth(month);
		
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
	
	public void setPaymentAfter(String value){
		setTextField(paymentAfter, value);
	}
	
	public void selectAtDate(String option){
		initializeAtDateDropdown();
		atDateDropDown.selsectOption(option);
	}
	
	public void setGrowth(String value){
		setTextField(growth, value);
	}
	
	public void setSupportPercentage(String value){
		setTextField(supportPercentage, value);
		if(!value.isEmpty()){
			wait.until(ExpectedConditions.visibilityOf(supportPeriod));
			WebdriverUtils.sleep(200);
		}
	}
	
	public void setSupportPeriod(String value){
		setTextField(supportPeriod, value);
	}
	
	public void setChurn(String value){
		setTextField(churn, value);
	}
	
	public boolean isBillingsHasError(){
		WebdriverUtils.waitForBudgetaBusyBar(driver);
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		for(WebElement el : errors){
			if(!el.getText().isEmpty())
				return true;
		}
		return false;
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
	
	private void setTextField(WebElement field, String text){
		field.clear();
		field.sendKeys(text);
	}

	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}
}
