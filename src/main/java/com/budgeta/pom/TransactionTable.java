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
	
	@FindBy(css = "div.bottom-bar div.total")
	private WebElement total;
	
	@FindBy(css = "div.bottom-bar button")
	private List<WebElement> bottomButtons;
	
	@FindBy(css = "div.bottom-bar button.cancel")
	private WebElement cancelButtons;
	
	@FindBy(className = "svg-icon")
	private List<WebElement> icons;
	
	private By selectedDropdown = By.className("select2-chosen");
	
	@FindBy(className = "currency")
	private WebElement currency;
	
	@FindBy(className = "ember-text-field")
	private WebElement textField;
	
	private By dateRange = By.cssSelector("div.year-picker-from div.month-picker");
	
	
	
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
	
	public void undoChanges(){
		cancelButtons.click();
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}
	
	public void clickSave(){
		for (WebElement el : bottomButtons){
			if(el.getAttribute("type").equals("submit")){
				el.click();
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				break;
			}
		}
	}
	
	public DateRange openDate(){
		wrapper.findElement(dateRange).click();
		return new DateRange();
	} 
	
	public void setAmount(String amount){
		WebElement field = currency.findElement(By.className("ember-text-field"));
		field.clear();
		field.sendKeys(amount);
	}
	
	
	public String getTotalValue(){
//		String totalValue;
//		totalValue = total.findElement(By.tagName("span")).getText();
//		
//		return totalValue;
		String totalValue = total.findElement(By.tagName("span")).getText();
		if(total.equals("-"))
			return totalValue;
		else
			return totalValue.replaceAll("[^0-9 .]","").trim();
	}
	
	
	public String getAmountValue(){
//		String amountValue;
//		AddTransaction transaction = new AddTransaction();
//		transaction.clickTransactionTab();
//		
//		amountValue = currency.findElement(By.className("ember-text-field")).getAttribute("title");
//		return amountValue;
		String amountValue = currency.findElement(By.className("ember-text-field")).getAttribute("title");
		if(total.equals("-"))
			return amountValue;
		else
			return amountValue.replaceAll("[^0-9 .]","").trim();
	}
	
	public String getTransactionDate(){
		String Date;
		AddTransaction transaction = new AddTransaction();
		transaction.clickTransactionTab();
		
		Date = wrapper.findElement(dateRange).findElement(By.tagName("input")).getAttribute("value");
		
		return Date;
	}
	
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
