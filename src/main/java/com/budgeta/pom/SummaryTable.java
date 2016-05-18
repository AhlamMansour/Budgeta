package com.budgeta.pom;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebElementUtils;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class SummaryTable extends AbstractPOM{
	
	@FindBy(className = "forecast-wrapper")
	private WebElement wrapper;
	
	@FindBy(css = "div.scroll-columns div.forecast-row div.column div.text-header")
	private List<WebElement> dateHeader;
	
	@FindBy(className = "ember-list-item-view")
	private List<WebElement> rows;
	
	@FindBy(css="div.column-wrapper div.header div.column")
	private List<WebElement> columns;
	
	@FindBy(css="div.total-column div.sub-header-text-wrapper")
	private List<WebElement> totalColumns;
	
	@FindBy(css="div.scroll-columns div.sub-header div.sub-header-text-wrapper span")
	private List<WebElement> rowData;
	
	private By columnTitle = By.className("sub-header-text-wrapper");	
	private By rowTitle = By.className("fixed-columns");
	private By rowValues = By.cssSelector("div.scroll-columns div.forecast-row span");
	private By rowTotal = By.className("total-column");
	
	
	
	
	public SummaryTable(){
		WebdriverUtils.elementToHaveClass(wrapper, "active");
		WebdriverUtils.waitForBudgetaBusyBar(driver);
		WebElementUtils.hoverOverField(dateHeader.get(dateHeader.size()-1), driver, null);
		WebdriverUtils.sleep(200);
	}
	
	public String getDateByIndex(int index){
		return dateHeader.get(index).getText();
	}
	
//	public List <String> getAllDates(){
//		List<String> res = new ArrayList<>();
//		for (WebElement el : dateHeader){
//			res.add(el.findElement(By.className("text-header")).getText());
//		}
//		return res;
//	}
	
	
	
	public int getHeaderSize(){
		return dateHeader.size();
	}
	
	public String getRowTitleByIndex(int index){
		return rows.get(index).findElement(rowTitle).getText();
	}
	

	
	public void clickOnLineByIndex(int index){
		rows.get(index).findElement(rowTitle).findElement(By.className("name")).click();
		WebdriverUtils.elementToHaveClass(driver.findElement(By.cssSelector(".navigator-header-text.inputs")),
				"active");
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
		int titleIndex = getIndexOfActualsTitle(title);
		List<String> res = new ArrayList<>();
		if(titleIndex > -1){
			for(int i=titleIndex ; i< rowValues.size(); i+=getNumberOfSubColumns()){
				res.add(rowValues.get(i));
			}
		}
		
		return res;
	}
	
	private int getIndexOfTitle(String title){
		int i = 0;
//		List<WebElement> elms = columns.get(0).findElements(columnTitle);
		List<WebElement> elms = columns.get(0).findElements(By.className("differrence-header"));
		WebElementUtils.hoverOverField(elms.get(0), driver, null);
		WebdriverUtils.sleep(200);
		for(WebElement el : elms){
			if(el.isDisplayed()){
				if(el.getText().equalsIgnoreCase(title))
					return i;
			}
			i++;
		}
		return -1;
	}
	
	
	private int getIndexOfActualsTitle(String title){
		int i = 0;
//		List<WebElement> elms = columns.get(0).findElements(columnTitle);
		List<WebElement> elms = totalColumns.get(0).findElements(By.tagName("span"));
		WebElementUtils.hoverOverField(elms.get(0), driver, null);
		WebdriverUtils.sleep(200);
		for(WebElement el : elms){
			if(el.isDisplayed()){
				if(el.getText().equalsIgnoreCase(title))
					return i;
			}
			i++;
		}
		return -1;
	}
	
	private int getNumberOfSubColumns(){
		return columns.get(0).findElements(columnTitle).size();
		
	}	
	
	public String getTotalOfRow(int rowIndex){
		String total = rows.get(rowIndex).findElement(rowTotal).findElement(By.className("[object Object]")).getText();
		if(total.equals("-"))
			return total;
		else
			return total.replaceAll("[^0-9 .]","").trim();
	}
	
	
	public int getNumbreOfRows(){
		return rows.size();
	}
	
	
	
	public String getActualsTotalOfRow(int rowIndex, String title){
		List<WebElement> elms = totalColumns.get(0).findElements(By.tagName("span"));
		for(WebElement el : elms){
			if (el.getText().equals(title)){
				String total = rows.get(rowIndex).findElement(rowTotal).getText();
				total = total.substring(0, total.indexOf("\n"));
				if(total.equals("-"))
					return total;
				else
					return total.replaceAll("[^0-9 .]","").trim();
				
			}
		}
		
		return null;
	}
	
	
	public String getActualsAmountOfRow(int rowIndex, String title){
		//List<WebElement> elms = rowData.get(0).findElements(By.tagName("span"));
		WebElementUtils.hoverOverField(columns.get(0), driver, null);
		WebdriverUtils.sleep(200);
		for(WebElement el : rowData){
			if (el.getText().equals(title)){
				String actuals = rows.get(rowIndex).findElement(By.className("column-has-actual")).getText();
				actuals = actuals.substring(0, actuals.indexOf("\n"));
				if(actuals.equals("-"))
					return actuals;
				else
					return actuals.replaceAll("[^0-9 .]","").trim();
				
			}
		}
		
		return null;
	}
	
	
	public List<String> getAllDates(){
		List<String> res = new ArrayList<>();
		for(WebElement el : dateHeader){
			res.add(el.getText());
		}
		return res;
	}
	
//	public void selectReportType(String option){
//		getReporterDropDown().selectValue(option);
//	}
//	
//	public void selectSubReportType(String option){
//		getSubReporterDropDown().selectValue(option);
//		WebdriverUtils.sleep(200);
//	}
//	
//	public void selectSubActualReportType(String option){
//		getSubActualReporterDropDown().selectValue(option);
//		
//	}
	
	
	
//	private SideDropDown getReporterDropDown(){
//		if(reporterDropDown == null){
//			reporterDropDown = new SideDropDown(wrapper.findElement(By.cssSelector("div.subnav div.report-view-wrapper div.reportType")));
//		}
//		return reporterDropDown;
//	}
//
//	
//	private SideDropDown getSubReporterDropDown(){
//		
//		if(subReporterDropDown == null){
////			for(WebElement el : wrapper.findElements(subReportType)){
////				if(el.getText().trim().equals("Budget"))
//			subReporterDropDown = new SideDropDown(
//					driver.findElements(subReportType).get(0).findElement(By.xpath("..")));
////		}
//		}
//		return subReporterDropDown;
//	}
//	
//	
//	private SideDropDown getSubActualReporterDropDown(){
//		if(subActualReporterDropDown == null){
//			for (WebElement el : driver.findElements(subReportType)) {
//				if(el.getText().trim().equals("Budget vs. actuals"))
//					subActualReporterDropDown = new SideDropDown(el.findElement(By.xpath("..")));
//		}
//			
//		}
//	
//		return subActualReporterDropDown;
//	}
//	
//	
	
//	public void selectCurrencyType(String option){
//		getCurrencyTypeDropDown().selectValue(option);
//		
//	}
//	
//	private SideDropDown getCurrencyTypeDropDown(){
//		if(currencyTypeDropDown == null){
//			for(WebElement el : fillterViewWrapper.findElements(fillterView)){
//				el.click();
//				WebdriverUtils.elementToHaveClass(el, "open");
//				if(el.getText().contains("By currency")){
//					el.click();
//					currencyTypeDropDown = new SideDropDown(el);
//					break;
//				}
//				el.click();
//				WebdriverUtils.sleep(200);
//		}
//			
//		}
//	
//		return currencyTypeDropDown;
//	}
	
//	public String getDateRangeFrom(){
//		return wrapper.findElement(dateRangeFrom).findElement(By.tagName("input")).getAttribute("placeholder");
//	}
//	
//	public String getDateRangeTo(){
//		return wrapper.findElement(dateRangeTo).findElement(By.tagName("input")).getAttribute("placeholder");
//	}
//	
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
