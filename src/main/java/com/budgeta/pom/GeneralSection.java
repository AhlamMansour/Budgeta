package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class GeneralSection extends AbstractPOM{

	@FindBy(className = "section-General")
	private WebElement wrapper;
	
	@FindBy(css = ".inline-edit")
	private WebElement title;

	@FindBy(id = "attribute-currency")
	private WebElement currency;
		
	@FindBy(css = ".budgeta-type-value label")
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
	
	@FindBy(className = "cancel")
	private WebElement cancelBtn;

	@FindBy(className = "ember-text-field")
	private WebElement currencyField;
	
	@FindBy(css = "div.budgeta-type-value span.select2-chosen")
	private WebElement currentCurrency;
	
	@FindBy(className = "budgeta-type-value")
	List<WebElement> typeValue;
	
	@FindBy(css = "div.month-picker input")
	List<WebElement> onDate;
	
	private By dateRange_from = By.cssSelector("div.month-picker.from input");
	private By hireDateRange_from = By.cssSelector("div.year-picker-from div.from input");
	private By dateRange_to = By.cssSelector("div.month-picker.to input");
	private By hireDateRange_to = By.cssSelector("div.year-picker-to div.to input");
	private By dropdown = By.className("select2-container");
	//private By onDate = By.cssSelector("div.month-picker input");
	

	public TestModal openBugetaErrorModal() {
		title.click();
		Actions action = new Actions(driver);
		String selectAll = Keys.chord(Keys.CONTROL, Keys.ALT, "t");
		action.sendKeys(selectAll).perform();
		return new TestModal();
		
	}
	
	public void selectCurrency(String option){
		DropDown curr = new DropDown(currency.findElement(dropdown));
		curr.selsectOption(option);
	}
	
	public String getSelectedCurrency(){
//		DropDown curr = new DropDown(currency.findElement(dropdown));
//		return curr.getSelectedValue();
		//return currencyField.findElement(By.tagName("placeholder")).getText();
//		for (WebElement el : typeValue){
//			if (el.findElement(By.tagName("input")).getAttribute("name").equals("currency")){
//				return el.findElement(By.tagName("input")).getAttribute("placeholder");
//			}
//			
//		}
		
		return currentCurrency.getText();
	}
	
	
	
	
	public String getDateRangeFrom(){
		if(wrapper.findElement(dateRange_from).getAttribute("value").isEmpty()){
			return wrapper.findElements(dateRange_from).get(1).getAttribute("placeholder");
			
		}
		return wrapper.findElements(dateRange_from).get(1).getAttribute("value");
	}
	
	
	public String getHireDateRangeFrom(){
		if(wrapper.findElement(hireDateRange_from).getAttribute("value").isEmpty()){
			return wrapper.findElement(hireDateRange_from).getAttribute("placeholder");
			
		}
		return wrapper.findElement(hireDateRange_from).getAttribute("value");
	}
	
	
	public String getDateRangeTo(){
		if(wrapper.findElement(dateRange_to).getAttribute("value").isEmpty()){
			return wrapper.findElements(dateRange_to).get(1).getAttribute("placeholder");
		}
		return wrapper.findElements(dateRange_to).get(1).getAttribute("value");
	}
	
	public String getHireDateRangeTo(){
		if(wrapper.findElement(hireDateRange_to).getAttribute("value").isEmpty()){
			return wrapper.findElement(hireDateRange_to).getAttribute("placeholder");
		}
		return wrapper.findElement(hireDateRange_to).getAttribute("value");
	}
	
	public String getGeneralDateRangeFrom(){
		if(wrapper.findElement(dateRange_from).getAttribute("value").isEmpty()){
			return wrapper.findElement(dateRange_from).getAttribute("placeholder");
			
		}
		return wrapper.findElement(dateRange_from).getAttribute("value");
	}
	
	public String getGeneralDateRangeTo(){
		if(wrapper.findElement(dateRange_to).getAttribute("value").isEmpty()){
			return wrapper.findElement(dateRange_to).getAttribute("placeholder");
		}
		return wrapper.findElement(dateRange_to).getAttribute("value");
	}
	
	public String getDateRange(){
		return onDate.get(0).getAttribute("value");
	}
	
	public DateRange openDateRangeFrom(){
		hoverToNote();
		wrapper.findElement(dateRange_from).click();
		return new DateRange("From");
	}
	
	public DateRange openDateRangeTo(){
		hoverToNote();
		wrapper.findElement(dateRange_to).click();
		return new DateRange("To");
	}
	
	
	public DateRange openHireDateFrom(){
		wrapper.findElement(dateRange_from).click();
		return new DateRange();
	}
	
	public DateRange openHireDateTo(){
		wrapper.findElement(dateRange_to).click();
		return new DateRange();
	}  
	
	

	public void hoverToNote() {
		Actions act = new Actions(driver);
		act.moveToElement(notes).build().perform();
	}
	
	public String getNoteText(){
		return notes.findElement(By.tagName("textarea")).getAttribute("value");
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
		if (value != null)
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
			if (el.getText().equals("Actuals account"))
				return el.findElement(By.xpath(".."));
		}
		return null;
	}
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
