package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebElementUtils;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class DateRange extends AbstractPOM{

	@FindBy(className = "qtip-focus")
	private WebElement wrapper;
	
	private By prevYear = By.className("left-arrow");
	
	private By nextYear = By.className("right-arrow");
	
	private By year = By.className("year");
	
	private By months = By.cssSelector("div.month-picker ul li");
	
	
	public DateRange() {
		WebdriverUtils.waitForElementToBeFound(driver, By.className("qtip-focus"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("qtip-focus")));
		WebElementUtils.hoverOverField(driver.findElement(By.className("qtip-focus")), driver, null);
	}
	
	
	public void setYear(String _year){
		int wantedYear = Integer.parseInt(_year);
		int selectedYear = Integer.parseInt(wrapper.findElement(year).getText());
		if(wantedYear == selectedYear)
			return;
		while(wantedYear < selectedYear){
			wrapper.findElement(prevYear).click();
			WebdriverUtils.waitForBudgetaBusyBar(driver);
			WebdriverUtils.waitForBudgetaLoadBar(driver);
			selectedYear = Integer.parseInt(wrapper.findElement(year).getText());
		}
		while(wantedYear > selectedYear){
			wrapper.findElement(nextYear).click();
			WebdriverUtils.waitForBudgetaBusyBar(driver);
			WebdriverUtils.waitForBudgetaLoadBar(driver);
			selectedYear = Integer.parseInt(wrapper.findElement(year).getText());
		}
		
	}
	
	public void setMonth(String wantedMonth){
		for(WebElement el : wrapper.findElements(months)){
			if(el.getText().equalsIgnoreCase(wantedMonth)){
				el.click();
				WebdriverUtils.waitForInvisibilityOfElement(driver, wrapper, 10);
				return;
			}
		}
	}
	
	public void setMonthByIndex(int index){
		wrapper.findElements(months).get(index-1).click();
		WebdriverUtils.waitForInvisibilityOfElement(driver, wrapper, 10);
	}
	
	public String getDateRange(){
		return wrapper.findElement(By.className("ember-text-field")).getAttribute("value");
	}
	
/*****************************************************************************************/
	
	
	
	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
