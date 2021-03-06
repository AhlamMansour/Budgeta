package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class BudgetSettings extends SmallPopup{
	
	@FindBy(className = "flex")
	private List<WebElement> fields;
	
	private By dropdownOptions = By.cssSelector("ul.dropdown-menu li");
	private By dateRangeTo = By.cssSelector("div.month-picker.to");
	private By dateRangeFrom = By.cssSelector("div.month-picker.from");
	
	private WebElement currency;
	
	private WebElement Fiscalstart;
	
	@FindBy(className = "ember-checkbox")
	private List<WebElement> checkBoxes;
	
	private WebElement accountNumberCheckBox;
	
	private WebElement productFieldCheckBox;
	
	private WebElement geographyFieldCheckBox;
	
	public BudgetSettings() {
		for(WebElement el : fields){
			if(el.findElement(By.tagName("label")).getText().contains("Currency"))
				currency = el.findElement(By.className("dropdown"));
			if(el.findElement(By.tagName("label")).getText().contains("Fiscal"))
				Fiscalstart = el.findElement(By.className("dropdown"));
		}
		
		for(WebElement el : checkBoxes){
			if(el.findElement(By.xpath("..")).getText().contains("account ID"))
				accountNumberCheckBox = el;
			if(el.findElement(By.xpath("..")).getText().contains("product field"))
				productFieldCheckBox = el;
			if(el.findElement(By.xpath("..")).getText().contains("geography field"))
				geographyFieldCheckBox = el;
		}
	}
	
	public String getSelectedFiscal(){
		return getSelectedValue(Fiscalstart);
	}
	
	public String getSelectedCurrency(){
		return getSelectedValue(currency);
	}
	
	public String getDateRangeFrom(){
		return wrapper.findElement(dateRangeFrom).findElement(By.tagName("input")).getAttribute("value");
	}
	
	public String getDateRangeTo(){
		return wrapper.findElement(dateRangeTo).findElement(By.tagName("input")).getAttribute("value");
	}
	
	
	/***********************************************************************************/	
	private void openDropDown(WebElement dropdown){
		if(!dropdown.getAttribute("class").contains("open")){
			dropdown.click();
			WebdriverUtils.elementToHaveClass(dropdown, "open");
		}
	}
	
	private void setOptionInDropDown(WebElement dropdown, String option){
		openDropDown(dropdown);
		for(WebElement el : dropdown.findElements(dropdownOptions)){
			if(el.getText().equalsIgnoreCase(option)){
				el.click();
				WebdriverUtils.waitForElementToDisappear(driver, By.className("open"));
			}
		}
	}
	
	private void selectCheckBox(WebElement checkBox){
		if(checkBox.isSelected())
			return;
		checkBox.click();
	}
	
	private void removeCheckBox(WebElement checkBox){
		if(!checkBox.isSelected())
			return;
		checkBox.click();
	}
	
	private String getSelectedValue(WebElement dropdown){
		return dropdown.findElements(By.tagName("a")).get(0).getText();
	}
	
	public boolean isAccountNumberFieldSelected(){
		return isCheckBoxIsSelected(accountNumberCheckBox);
	}
	
	public boolean isProductFieldSelected(){
		return isCheckBoxIsSelected(productFieldCheckBox);
	}
	
	public boolean isGeographyFieldSelected(){
		return isCheckBoxIsSelected(geographyFieldCheckBox);
	}
	
	public boolean isCheckBoxIsSelected(WebElement checkBox){
		if(checkBox.isSelected())
			return true;
		else 
			return false;
	}
	
}
