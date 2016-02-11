package com.budgeta.pom;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebElementUtils;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class Actuals extends AbstractPOM{
	
	@FindBy(className = "forecast-view")
	private WebElement wrapper;
	
	@FindBy(css = "div.forecast-header div.scroll-columns div.column")
	private List<WebElement> dateHeader;
	
	@FindBy(className = "ember-list-item-view")
	private List<WebElement> rows;
	
	private By rowTitle = By.className("fixed-columns");
	
	private By rowValues = By.cssSelector("div.scrollable div.forecast-table input.ember-text-field");
	
	private By rowTotal = By.className("total-column");
	
	
	public Actuals(){
		WebdriverUtils.elementToHaveClass(wrapper, "active");
		WebdriverUtils.waitForBudgetaBusyBar(driver);
		WebElementUtils.hoverOverField(dateHeader.get(dateHeader.size()-1), driver, null);
		WebdriverUtils.sleep(200);
	}
	
	
	
	public String getDateByIndex(int index){
		return dateHeader.get(index).getText();
	}
	
	public int getHeaderSize(){
		return dateHeader.size();
	}
	
	public String getRowTitleByIndex(int index){
		return rows.get(index).findElement(rowTitle).getText();
	}
	

	
	public void clickOnLineByIndex(int index){
		rows.get(index).findElement(rowTitle).findElement(By.className("name")).click();
		WebdriverUtils.elementToHaveClass(driver.findElement(By.className("input-tab")), "active");
		WebdriverUtils.waitForBudgetaBusyBar(driver);
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		
	}
	
	public List<String> getAllValuesOfRow(int rowIndex){
		List<WebElement> values = rows.get(rowIndex).findElements(rowValues);
		List<String> res = new ArrayList<>();
		for(WebElement el : values){
			String value = el.getAttribute("placeholder");
			if(value.isEmpty())
				res.add("-");
			else
				res.add(value.replaceAll("[^0-9 .]","").trim());
		}
		return res;
	}
	
	
	
	public String getTotalOfRow(int rowIndex){
		String total = rows.get(rowIndex).findElement(rowTotal).getText();
		if(total.equals("-"))
			return total;
		else
			return total.replaceAll("[^0-9 .]","").trim();
	}
	public int getNumbreOfRows(){
		return rows.size();
	}
	
	public List<String> getAllDates(){
		List<String> res = new ArrayList<>();
		for(WebElement el : dateHeader){
			res.add(el.getText());
		}
		return res;
	}
	
	
	
	
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
