package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class GeneralSection extends AbstractPOM{

	@FindBy(id = "section-General")
	private WebElement wrapper;
	
	@FindBy(id = "attribute-currency")
	private WebElement currency;
		
	@FindBy(className = "wide")
	List<WebElement> accountNumbers;
	
	@FindBy(id = "attribute-region")
	private WebElement geography;
	
	@FindBy(id = "attribute-product")
	private WebElement product;
	
	@FindBy(id = "attribute-notes")
	private WebElement notes;
	
	@FindBy(className = "error")
	List<WebElement> errors;
	
	@FindBy(className = "attribute-allocated")
	private WebElement department;
	
	private By dateRange_from = By.cssSelector("div.month-picker.from input");
	private By dateRange_to = By.cssSelector("div.month-picker.to input");
	private By dropdown = By.className("select2-container");

	
	public void selectCurrency(String option){
		DropDown curr = new DropDown(currency.findElement(dropdown));
		curr.selsectOption(option);
	}
	
	public String getSelectedCurrency(){
		DropDown curr = new DropDown(currency.findElement(dropdown));
		return curr.getSelectedValue();
	}
	
	public String getDateRangeFrom(){
		return wrapper.findElement(dateRange_from).getAttribute("value");
	}
	
	public String getDateRangeTo(){
		if(wrapper.findElement(dateRange_to).getAttribute("value").isEmpty()){
			return wrapper.findElement(dateRange_to).getAttribute("placeholder");
		}
		return wrapper.findElement(dateRange_to).getAttribute("value");
	}
	
	public DateRange openDateRangeFrom(){
		wrapper.findElement(dateRange_from).click();
		return new DateRange("from");
	}
	
	public DateRange openDateRangeTo(){
		wrapper.findElement(dateRange_to).click();
		return new DateRange("to");
	}
	
	public void setAccountNumberInRowByIndex(int indexOfRow, String value){
		WebElement row = getAccountNumber().findElements(By.tagName("input")).get(indexOfRow - 1);
		row.clear();
		row.sendKeys(value);
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
	
	public void setNotes(String note){
		WebElement field = notes.findElement(By.className("ember-text-area"));
		field.clear();
		field.sendKeys(note);
	}
	
	public boolean isGeneralHasError(){
		for(WebElement el : errors){
			if(!el.getText().isEmpty())
				return true;
		}
		return false;
	}
	
	public void setDepartment(String value){
		DropDown dr = new DropDown(department.findElement(dropdown));
		dr.sendKeysToDropDown(value);
	}
	
	public String getDepartment(){
		DropDown dr = new DropDown(department.findElement(dropdown));
		return dr.getSelectedValue();
	}
	
	
	private WebElement getAccountNumber(){
		for(WebElement el : accountNumbers){
			if(el.getText().equals("Account Number"))
				return el.findElement(By.xpath(".."));
		}
		return null;
	}
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
