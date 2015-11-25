package com.budgeta.pom;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebElementUtils;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class View extends AbstractPOM{

	@FindBy(className = "forecast-view")
	private WebElement wrapper;
	
	
	private SideDropDown reporterDropDown; 
	private SideDropDown subReporterDropDown;
	private SideDropDown subActualReporterDropDown; 
	
	
	@FindBy(css = "div.forecast-header div.scroll-columns div.column")
	private List<WebElement> dateHeader;
	
	@FindBy(className = "ember-list-item-view")
	private List<WebElement> rows;
	
	@FindBy(css="div.column-wrapper div.header div.column")
	private List<WebElement> columns;
	

	private By columnTitle = By.className("sub-header-text-wrapper");	
	private By rowTitle = By.className("fixed-columns");
	private By rowValues = By.cssSelector("div.scroll-columns div.column span span");
	private By rowTotal = By.className("total-column");
	
	
//	private By reportType = By.cssSelector("div.subnav div.report-view-wrapper div.reportType");
	private By subReportType = By.cssSelector("div.dropdown a.add-border");
	
	
	
	
	
	public View(){
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
			String value = el.getText();
			if(value.equals("-"))
				res.add(value);
			else
				res.add(value.replaceAll("[^0-9 .]","").trim());
		}
		return res;
	}
	
	public List<String> getAllValuesOfRowByTitle(int rowIndex, String title){
		List<String> rowValues = getAllValuesOfRow(rowIndex);
		int titleIndex = getIndexOfTitle(title);
		List<String> res = new ArrayList<>();
		for(int i=titleIndex ; i< rowValues.size(); i+=getNumberOfSubColumns()){
			res.add(rowValues.get(i));
		}
		return res;
	}
	
	private int getIndexOfTitle(String title){
		int i = 0;
		List<WebElement> elms = columns.get(0).findElements(columnTitle);
		WebElementUtils.hoverOverField(elms.get(0), driver, null);
		WebdriverUtils.sleep(200);
		for(WebElement el : elms){
			if(el.getText().equalsIgnoreCase(title))
				return i;
			i++;
		}
		return -1;
	}
	
	private int getNumberOfSubColumns(){
		return columns.get(0).findElements(columnTitle).size();
		
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
	
	public void selectReportType(String option){
		getReporterDropDown().selectValue(option);
	}
	
	public void selectSubReportType(String option){
		getSubReporterDropDown().selectValue(option);
		WebdriverUtils.sleep(200);
	}
	
	public void selectSubActualReportType(String option){
		getSubActualReporterDropDown().selectValue(option);
		
	}
	
	
	
	private SideDropDown getReporterDropDown(){
		if(reporterDropDown == null){
			reporterDropDown = new SideDropDown(wrapper.findElement(By.cssSelector("div.subnav div.report-view-wrapper div.reportType")));
		}
		return reporterDropDown;
	}

	
	private SideDropDown getSubReporterDropDown(){
		
		if(subReporterDropDown == null){
//			for(WebElement el : wrapper.findElements(subReportType)){
//				if(el.getText().trim().equals("Budget"))
					subReporterDropDown = new SideDropDown(wrapper.findElements(subReportType).get(1).findElement(By.xpath("..")));
//		}
		}
		return subReporterDropDown;
	}
	
	
	private SideDropDown getSubActualReporterDropDown(){
		if(subActualReporterDropDown == null){
			for(WebElement el : wrapper.findElements(subReportType)){
				if(el.getText().trim().equals("Budget vs. actuals"))
					subActualReporterDropDown = new SideDropDown(el.findElement(By.xpath("..")));
		}
			
		}
	
		return subActualReporterDropDown;
	}
	
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
