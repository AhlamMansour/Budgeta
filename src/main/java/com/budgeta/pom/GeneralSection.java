package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;

public class GeneralSection extends AbstractPOM{

	@FindBy(id = "section-General")
	private WebElement wrapper;
	
	@FindBy(css = "div.month-picker.from input")
	private WebElement dateRange_from;
	
	@FindBy(css = "div.month-picker.to input")
	private WebElement dateRange_to;
	
	@FindBy(id = "attribute-currency")
	private WebElement currency;
		
	@FindBy(className = "data-row")
	List<WebElement> accountNumbers;
	
	@FindBy(id = "attribute-region")
	private WebElement geography;
	
	@FindBy(id = "attribute-product")
	private WebElement product;	
	
	
	private By dropdown = By.className("select2-container");
	
	
	public DropDown getCurrencyDropDown(){
		return new DropDown(currency.findElement(dropdown));
	}
	
	public String getDateRangeFrom(){
		return dateRange_from.getAttribute("value");
	}
	
	public String getDateRangeTo(){
		return dateRange_to.getAttribute("value");
	}
	
	public DateRange openDateRangeFrom(){
		dateRange_from.click();
		return new DateRange("from");
	}
	
	public DateRange openDateRangeTo(){
		dateRange_to.click();
		return new DateRange("to");
	}
	
	public void setAccountNumberInRowByIndex(int indexOfRow, int value){
		WebElement row = accountNumbers.get(indexOfRow - 1).findElement(By.tagName("input"));
		row.clear();
		row.sendKeys(value+"");
	}
	
	public String getAccountNumberInRowByIndex(int indexOfRow){
		return accountNumbers.get(indexOfRow - 1).findElement(By.tagName("input")).getAttribute("value");
	}
	
	public void setGeography(String value){
		WebElement field = geography.findElements(By.tagName("input")).get(1);
		field.clear();
		field.sendKeys(value);
	}
	
	public String getGeography(){
		return geography.findElements(By.tagName("input")).get(1).getAttribute("value");
	}
	
	public void setProduct(String value){
		WebElement field = product.findElements(By.tagName("input")).get(1);
		field.clear();
		field.sendKeys(value);
	}
	
	public String getProduct(){
		return product.findElements(By.tagName("input")).get(1).getAttribute("value");
	}
	
	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
