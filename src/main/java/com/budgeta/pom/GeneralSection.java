package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class GeneralSection extends AbstractPOM{

	@FindBy(id = "section-General")
	private WebElement wrapper;
	
	@FindBy(css = "div.month-picker.from input")
	private WebElement dateRange_from;
	
	@FindBy(css = "div.month-picker.to input")
	private WebElement dateRange_to;
	
	@FindBy(id = "attribute-currency")
	private WebElement currency;
	
	@FindBy(css = "ul.select2-results li")
	List<WebElement> dropdownOptions;
	
	@FindBy(className = "data-row")
	List<WebElement> accountNumbers;
	
	@FindBy(id = "attribute-region")
	private WebElement geography;
	
	@FindBy(id = "attribute-product")
	private WebElement product;	
	
	
	private By dropdown = By.className("select2-container");
	
	private By selectedDropdown = By.className("select2-chosen");
	
	public String getSelectedCurrency(){
		return getSelectedDoprdown(currency.findElement(dropdown));
	}
	
	public void selectCurrency(String curr){
			selsectDropdown(currency.findElement(dropdown), curr);
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
	
	private String getSelectedDoprdown(WebElement dropdown){
		return dropdown.findElement(selectedDropdown).getText();
	}
	
	private void selsectDropdown(WebElement dropdown, String option){
		dropdown.click();
		WebdriverUtils.elementToHaveClass(dropdown, "select2-dropdown-open");
		wait.until(ExpectedConditions.visibilityOf(dropdownOptions.get(0)));
		for(WebElement el : dropdownOptions){
			if(el.getText().equals(option)){
				el.click();
				WebdriverUtils.waitForElementToDisappear(driver ,By.className("select2-dropdown-open"));
				return;
			}
		}
	}
	
	
	
	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
