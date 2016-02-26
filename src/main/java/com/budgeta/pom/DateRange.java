package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebElementUtils;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class DateRange extends AbstractPOM{

	@FindBy(className = "qtip-focus")
	private WebElement wrappers;
	
	@FindBy(css = "div.qtip-content div.top")
	private WebElement topYearsWrappers;
	
	private By prevYear = By.className("left-arrow");
	
	private By nextYear = By.className("right-arrow");
	
	private By year = By.className("year");
	
	private By months = By.cssSelector("ul li");
	
	private WebElement wrapper;
	
//	@FindBy(className = "month-picker-control")
//	private List<WebElement> controler;
	private By controler = By.className("month-picker-control");
	
	public DateRange() {
		WebdriverUtils.waitForElementToBeFound(driver, By.className("qtip-focus"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("qtip-focus")));
		WebElementUtils.hoverOverField(driver.findElement(By.className("qtip-focus")), driver, null);
	}
	
	public DateRange(String cal) {
		WebdriverUtils.waitForElementToBeFound(driver, By.className("qtip-focus"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("qtip-focus")));
		WebElementUtils.hoverOverField(driver.findElement(By.className("qtip-focus")), driver, null);
		initializeWrapper(cal);
	}
	
	private void initializeWrapper(String cal){
		if(cal.equalsIgnoreCase("From"))
			wrapper = wrappers.findElements(controler).get(0);
		if(cal.equalsIgnoreCase("To"))
			wrapper = wrappers.findElements(controler).get(0);
	}
	
	public void setYear(String _year){
		int wantedYear = Integer.parseInt(_year);
		int selectedYear = Integer.parseInt(wrapper.findElement(year).getText());
		if(wantedYear == selectedYear)
			return;
		while(wantedYear < selectedYear){
			
			wrapper.findElement(prevYear).click();
		//	WebdriverUtils.waitForBudgetaBusyBar(driver);
			WebdriverUtils.waitForBudgetaLoadBar(driver);
			selectedYear = Integer.parseInt(wrapper.findElement(year).getText());
		}
		while(wantedYear > selectedYear){
			
			wrapper.findElement(nextYear).click();
			//WebdriverUtils.waitForBudgetaBusyBar(driver);
			WebdriverUtils.waitForBudgetaLoadBar(driver);
			selectedYear = Integer.parseInt(wrapper.findElement(year).getText());
		}
		
	}
	

	public void setHireYear(String _year){
		int wantedYear = Integer.parseInt(_year);
		int selectedYear = Integer.parseInt(wrappers.findElement(year).getText());
		if(wantedYear == selectedYear)
			return;
		while(wantedYear < selectedYear){
			
			wrappers.findElement(prevYear).click();
			//WebdriverUtils.waitForBudgetaBusyBar(driver);
			WebdriverUtils.waitForBudgetaLoadBar(driver);
			selectedYear = Integer.parseInt(wrappers.findElement(year).getText());
		}
		while(wantedYear > selectedYear){
			
			wrappers.findElement(nextYear).click();
			//WebdriverUtils.waitForBudgetaBusyBar(driver);
			WebdriverUtils.waitForBudgetaLoadBar(driver);
			selectedYear = Integer.parseInt(wrappers.findElement(year).getText());
		}
		
	}
	
	public void setMonth(String wantedMonth){
		for(WebElement el : wrapper.findElements(months)){
			if(el.getText().equalsIgnoreCase(wantedMonth)){
				el.click();
				//Actions action = new Actions(driver);
				//action.moveByOffset(0, 0).build().perform();
				//WebElementUtils.hoverOverField(wrappers.findElement(By.xpath("../..")), driver, null);
				//WebdriverUtils.waitForInvisibilityOfElement(driver, wrappers, 10);
				return;
			}
		}
	}
	
	
	public void closeDatePopup(){
		
				Actions action = new Actions(driver);
				action.moveByOffset(0, 0).build().perform();
				WebElementUtils.hoverOverField(wrappers.findElement(By.xpath("../..")), driver, null);
				WebdriverUtils.waitForInvisibilityOfElement(driver, wrappers, 10);
				
			
	} 
	
	
	public void setHireMonth(String wantedMonth){
		for(WebElement el : wrappers.findElements(months)){
			if(el.getText().equalsIgnoreCase(wantedMonth)){
				el.click();
				WebdriverUtils.waitForInvisibilityOfElement(driver, wrappers, 10);
				return;
			}
		}
	}
	
	public void setMonthByIndex(int index){
		wrappers.findElements(months).get(index-1).click();
		WebdriverUtils.waitForInvisibilityOfElement(driver, wrappers, 10);
	}
	
	public String getDateRange(){
		return wrappers.findElement(By.className("ember-text-field")).getAttribute("value");
	}
	
	
	public int getFromYear(){
		int fromYear = Integer.parseInt(topYearsWrappers.findElement(By.cssSelector("div.left-top-inner div.year")).getText());
		return fromYear;
	}
	
	
	public int getToYear(){
		int toYear = Integer.parseInt(topYearsWrappers.findElement(By.cssSelector("div.right-top-inner div.year")).getText());
		return toYear;
	}
	
/*****************************************************************************************/
	
	
	
	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
