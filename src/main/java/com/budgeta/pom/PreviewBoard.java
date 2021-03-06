package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebElementUtils;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class PreviewBoard extends AbstractPOM{

	@FindBy(className = "preview")
	private WebElement wrapper;

	@FindBy(className = "forecast-footer")
	private WebElement forecast;
	
	@FindBy(css = "div.forecast-footer div.total-column")
	private WebElement totalValue;

	@FindBy(css = "div.forecast-footer div.scroll-columns div.forecast-row")
	private List<WebElement> valuesWrapper;
	
	@FindBy(css = "div.forecast-header div.scroll-columns div.column")
	private List<WebElement> dateHeader;
	
	private By column = By.className("column");
	
	
	public PreviewBoard(){
		List<WebElement> header = wrapper.findElements(By.cssSelector("div.forecast-header div.scroll-columns div.column"));
		
	//	WebElementUtils.hoverOverField(header.get(header.size()-1), driver, null);
		for(int i = 0 ; i < header.size()-1 ; i++){
			WebElementUtils.hoverOverField(header.get(i), driver, null);
			Actions act = new Actions(driver);
			act.moveToElement(header.get(i)).build().perform();
		}
	}
	
	public int getValuesSize(){
		return getAllValues().size();
	}
	
/*
	public String getValueByIndex(int index){
		String res = getAllValues().get(index).getText();
		if(res.equals("-"))
			return res;
		if(res.contains("."))
			return res.replaceAll("[^0-9 .]","").trim();
		return res.replaceAll("[^0-9]","").trim();
	}
	
	*/
	
	public String getValueByIndex(int index){
		String res = getAllValues().get(index).getText();
		if(res.equals("-"))
			return res;
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		return res.replaceAll("[^0-9]","").trim();
		
	}
	
	
	/*
	public String getTotalValue(){
		String res = totalValue.getText();
		if(res.equals("-"))
			return res;
		if(res.contains("."))
			return res.replaceAll("[^0-9 .]","").trim();
		return res.replaceAll("[^0-9]","").trim();
	}
	*/
	
	public String getTotalValue(){
		String res = totalValue.getText();
		if(res.equals("-"))
			return res;
		return res.replaceAll("[^0-9]","").trim();
	}

	public int getIndexOfHeaderDate(String date){
		int i = 0;
		for(WebElement el : dateHeader){
			if(el.getText().equalsIgnoreCase(date))
				return i;
			i++;
		}
		return -1;
	}
	
	private List<WebElement> getAllValues(){
		return returnVisibleElement(valuesWrapper).findElements(column);
	}
	
	private WebElement returnVisibleElement(List<WebElement> elms){
		for(WebElement el : elms){
			if(el.isDisplayed())
				return el;
		}
		return null;
	}
	
	public String getFirstDate(){
		return dateHeader.get(0).findElement(By.className("text-header")).getText();
	}
	
	public String getLastDate(){
		int i = dateHeader.size();
		return dateHeader.get(i-1).findElement(By.className("text-header")).getText();
		
	}
	
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
