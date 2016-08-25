package com.budgeta.pom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebElementUtils;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class Sheets extends AbstractPOM {

	@FindBy(className = "forecast-view")
	private WebElement wrapper;

	private By dateRangeTo = By.cssSelector("div.month-picker.to");
	private By dateRangeFrom = By.cssSelector("div.month-picker.from");

	private SideDropDown reporterDropDown;
	private SideDropDown subReporterDropDown;
	private SideDropDown HeadcountReporterDropDown;
	private SideDropDown employeestReporterDropDown;
	private SideDropDown subActualReporterDropDown;
	private SideDropDown currencyTypeDropDown;

	@FindBy(css = ".select2-choice")
	private List<WebElement> choiceList;

	@FindBy(css = "div.forecast-header div.scroll-columns div.column")
	private List<WebElement> dateHeader;

	@FindBy(className = "ember-list-item-view")
	private List<WebElement> rows;

	@FindBy(className = "forecast-footer")
	private WebElement totalrow;

	@FindBy(css = "div.column-wrapper div.header div.column")
	private List<WebElement> columns;

	@FindBy(className = "filter-view-wrapper")
	private WebElement fillterViewWrapper;

	private By columnTitle = By.className("sub-header-text-wrapper");
	private By rowTitle = By.className("fixed-columns");
	// private By rowValues =
	// By.cssSelector("div.scroll-columns div.column span span");
	private By rowValues = By.cssSelector("div.scroll-columns div.forecast-row span");

	private By costValues = By.className("cost-value");

	private By headcountValue = By.className("headcount-value");

	@FindBy(css = "div.scroll-columns div.forecast-row span")
	private List<WebElement> rowValue;

	private By rowTotal = By.className("total-column");

	// private By reportType =
	// By.cssSelector("div.subnav div.report-view-wrapper div.reportType");
	private By subReportType = By.className("select2-choice");

	private By fillterView = By.className("dropdown");

	private By newRows = By.className("ember-list-item-view");

	public Sheets() {
		WebdriverUtils.elementToHaveClass(wrapper, "active");
		WebdriverUtils.waitForBudgetaBusyBar(driver);
		ScrollTableToTheBottomJS(driver);
		WebdriverUtils.sleep(400);
		ScrollTableToTheTopJS(driver);
		WebdriverUtils.sleep(400);
	}

	public static void ScrollTableToTheBottomJS(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("var elm = document.getElementsByClassName('ember-list-view')[0]; elm.scrollTop +=elm.scrollHeight;");
	}

	public static void ScrollTableToJS(WebDriver driver, String pexils) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("var elm = document.getElementsByClassName('ember-list-view')[0]; elm.scrollTop += " + pexils + ";");
	}

	public static void ScrollTableToTheTopJS(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("var elm = document.getElementsByClassName('ember-list-view')[0]; elm.scrollTop -=elm.scrollHeight;");
	}

	public String getDateByIndex(int index) {
		return dateHeader.get(index).getText();
	}

	public int getHeaderSize() {
		return dateHeader.size();
	}

	public String getRowTitleByIndex(int index) {
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		return rows.get(index).findElement(rowTitle).getText();

	}

	public void clickOnLineByIndex(int index) {
		WebElement el = rows.get(index).findElement(rowTitle).findElement(By.className("name"));
		WebElementUtils.hoverOverField(el, driver, null);
		// WebElementUtils.clickElementEvent(driver, el);
		el.click();
		WebdriverUtils.elementToHaveClass(driver.findElement(By.cssSelector(".navigator-header-text.inputs")), "active");
		WebdriverUtils.waitForBudgetaBusyBar(driver);
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		WebdriverUtils.waitForBudgetaLoadBar(driver);

	}

	public void clickOnLine(int index, String title) {
		WebElement el = rows.get(index).findElement(rowTitle).findElement(By.className("name"));
		WebElementUtils.hoverOverField(el, driver, null);
		// WebElementUtils.clickElementEvent(driver, el);
		if (el.getText().equals(title)) {
			WebElementUtils.hoverOverField(el, driver, null);
			el.click();

			WebdriverUtils.elementToHaveClass(driver.findElement(By.cssSelector(".navigator-header-text.inputs")), "active");
		} else {
			ScrollTableToJS(driver, "50");
			el = rows.get(index).findElement(rowTitle).findElement(By.className("name"));
			WebElementUtils.hoverOverField(el, driver, null);
			if (el.getText().equals(title)) {
				el.click();

				WebdriverUtils.elementToHaveClass(driver.findElement(By.cssSelector(".navigator-header-text.inputs")), "active");

			}

		}

		WebdriverUtils.elementToHaveClass(driver.findElement(By.cssSelector(".navigator-header-text.inputs")), "active");
		WebdriverUtils.waitForBudgetaBusyBar(driver);
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		WebdriverUtils.waitForBudgetaLoadBar(driver);

	}

	public List<String> getAllValuesOfRow(int rowIndex) {
		List<WebElement> values = rows.get(rowIndex).findElements(rowValues);
		List<String> res = new ArrayList<>();
		for (WebElement el : values) {
			String value = el.getText();
			if (value.equals("-"))
				res.add(value);
			else
				res.add(value.replaceAll("[^0-9 .]", "").trim());
		}
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		return res;
	}

	public List<String> getAllValuesOfRows(int rowIndex) {
		List<WebElement> values = rows.get(rowIndex).findElements(rowValues);
		List<String> res = new ArrayList<>();
		for (WebElement el : values) {
			String value = el.getText();
			if (value.equals("-"))
				res.add("0");
			else
				res.add(value.replaceAll("[^0-9 .]", "").trim());
		}
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		return res;
	}

	public List<String> getAllValuesOfTotalRow() {
		List<WebElement> values = totalrow.findElements(rowValues);
		List<String> res = new ArrayList<>();
		for (WebElement el : values) {
			String value = el.getText();
			if (value.equals("-"))
				res.add("0");
			else
				res.add(value.replaceAll("[^0-9 .]", "").trim());
		}
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		return res;
	}

	public List<String> getAllValuesOfRowByTitle(String title) {
		// List<WebElement> values = rows.get(rowIndex).findElements(rowValues);
		List<String> res = new ArrayList<>();
		for (WebElement el : rows) {
			if (el.isDisplayed()) {
				if (el.findElement(rowTitle).getText().equals(title)) {
					int indexOftitle = getIndexOfRowName(title);
					res = getAllValuesOfRows(indexOftitle);
					break;

				}
			}

		}
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		return res;
	}

	public List<String> getAllValuesOfTotalRow(String title) {
		// List<WebElement> values = rows.get(rowIndex).findElements(rowValues);
		List<String> res = new ArrayList<>();

		if (totalrow.isDisplayed()) {
			if (totalrow.findElement(rowTitle).getText().equals(title)) {
				int indexOftitle = getIndexOfRowName(title);
				res = getAllValuesOfTotalRow();

			}
		}

		WebdriverUtils.waitForBudgetaLoadBar(driver);
		return res;
	}

	public List<String> getAllValuesOfRowByTitle(int rowIndex, String title) {
		List<String> rowValues = getAllValuesOfRow(rowIndex);
		int titleIndex = getIndexOfTitle(title);
		List<String> res = new ArrayList<>();
		if (titleIndex > -1) {
			for (int i = titleIndex; i < rowValues.size(); i += getNumberOfSubColumns()) {
				res.add(rowValues.get(i));
			}
		}

		return res;
	}

	private int getIndexOfTitle(String title) {
		int i = 0;
		// List<WebElement> elms = columns.get(0).findElements(columnTitle);
		List<WebElement> elms = columns.get(0).findElements(By.className("differrence-header"));
		WebElementUtils.hoverOverField(elms.get(0), driver, null);
		WebdriverUtils.sleep(200);
		for (WebElement el : elms) {
			if (el.isDisplayed()) {
				if (el.getText().equalsIgnoreCase(title))
					return i;
			}
			i++;
		}
		return -1;
	}

	public void clickOnLineBtTitle(String title) {

		// // List<WebElement> elms = columns.get(0).findElements(columnTitle);
		// List<WebElement> elms =
		// columns.get(0).findElements(By.className("differrence-header"));
		// WebElementUtils.hoverOverField(elms.get(0), driver, null);
		WebdriverUtils.sleep(200);
		for (WebElement el : rows) {
			if (el.isDisplayed()) {
				el.click();
				if (el.findElement(rowTitle).getText().equals(title)) {
					// WebElementUtils.hoverOverField(el, driver, null);
					el.findElement(rowTitle).click();
					// WebElementUtils.clickElementEvent(driver, el);
					break;
				}

			}

		}

	}

	public int getIndexOfRowName(String title) {
		int i = 0;
		// // List<WebElement> elms = columns.get(0).findElements(columnTitle);
		// List<WebElement> elms =
		// columns.get(0).findElements(By.className("differrence-header"));
		// WebElementUtils.hoverOverField(elms.get(0), driver, null);
		WebdriverUtils.sleep(200);
		for (WebElement el : rows) {
			if (el.isDisplayed()) {
				if (el.findElement(rowTitle).getText().equals(title))
					return i;
			}
			i++;
		}
		return -1;
	}

	private int getNumberOfSubColumns() {
		return columns.get(0).findElements(columnTitle).size();

	}

	public String getTotalOfRow(int rowIndex) {
		String total = rows.get(rowIndex).findElement(rowTotal).getText();
		if (total.equals("-"))
			return total;
		else
			return total.replaceAll("[^0-9 .]", "").trim();
	}

	public int getNumbreOfRows() {
		return rows.size();
	}

	public List<String> getAllDates() {
		List<String> res = new ArrayList<>();
		for (WebElement el : dateHeader) {
			res.add(el.getText());
		}
		return res;
	}

	public void selectReportType(String option) {
		getReporterDropDown().selectValue(option);
	}

	public void selectSubReportType(String option) {
		getSubReporterDropDown().selectValue(option);
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}

	public void selectHeadcount(String option) {
		getHeadCountReporterDropDown().selectValue(option);
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}

	public void selectEmployees(String option) {
		getEmployeesReporterDropDown().selectValue(option);
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}

	public void selectRepeatReportType(String option) {
		getRepeatReporterDropDown().selectValue(option);
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}

	public void selectSubActualReportType(String option) {
		getSubActualReporterDropDown().selectValue(option);

	}

	private SideDropDown getReporterDropDown() {
		if (reporterDropDown == null) {
			reporterDropDown = new SideDropDown(wrapper.findElement(By.cssSelector("div.subnav div.report-view-wrapper div.reportType")));
		}
		return reporterDropDown;
	}

	private SideDropDown getSubReporterDropDown() {

		if (subReporterDropDown == null) {
			// for(WebElement el : wrapper.findElements(subReportType)){
			// if(el.getText().trim().equals("Budget"))
			subReporterDropDown = new SideDropDown(driver.findElements(subReportType).get(0).findElement(By.xpath("..")));
			// }
		}
		return subReporterDropDown;
	}

	private SideDropDown getRepeatReporterDropDown() {

		if (subReporterDropDown == null) {
			// for(WebElement el : wrapper.findElements(subReportType)){
			// if(el.getText().trim().equals("Budget"))
			subReporterDropDown = new SideDropDown(driver.findElements(subReportType).get(1).findElement(By.xpath("..")));
			// }
		}
		return subReporterDropDown;
	}

	private SideDropDown getHeadCountReporterDropDown() {

		if (HeadcountReporterDropDown == null) {
			// for(WebElement el : wrapper.findElements(subReportType)){
			// if(el.getText().trim().equals("Budget"))
			HeadcountReporterDropDown = new SideDropDown(driver.findElements(subReportType).get(1).findElement(By.xpath("..")));
			// }
		}
		return HeadcountReporterDropDown;
	}

	private SideDropDown getEmployeesReporterDropDown() {

		if (employeestReporterDropDown == null) {
			// for(WebElement el : wrapper.findElements(subReportType)){
			// if(el.getText().trim().equals("Budget"))
			employeestReporterDropDown = new SideDropDown(driver.findElements(subReportType).get(2).findElement(By.xpath("..")));
			// }
		}
		return employeestReporterDropDown;
	}

	private SideDropDown getSubActualReporterDropDown() {
		if (subActualReporterDropDown == null) {
			for (WebElement el : driver.findElements(subReportType)) {
				if (el.getText().trim().equals("Budget vs. actuals"))
					subActualReporterDropDown = new SideDropDown(el.findElement(By.xpath("..")));
			}

		}

		return subActualReporterDropDown;
	}

	public void selectCurrencyType(String option) {
		getCurrencyTypeDropDown().selectValue(option);

	}

	private SideDropDown getCurrencyTypeDropDown() {
		if (currencyTypeDropDown == null) {
			for (WebElement el : fillterViewWrapper.findElements(fillterView)) {
				el.click();
				WebdriverUtils.elementToHaveClass(el, "open");
				if (el.getText().contains("By currency")) {
					el.click();
					currencyTypeDropDown = new SideDropDown(el);
					break;
				}
				el.click();
				WebdriverUtils.sleep(200);
			}

		}

		return currencyTypeDropDown;
	}

	public String getDateRangeFrom() {
		return wrapper.findElement(dateRangeFrom).findElement(By.tagName("input")).getAttribute("placeholder");
	}

	public String getDateRangeTo() {
		return wrapper.findElement(dateRangeTo).findElement(By.tagName("input")).getAttribute("placeholder");
	}

	private List<WebElement> openAllRows(List<WebElement> els, int level) {
		if (els == null || els.size() == 0)
			return null;
		for (WebElement el : els) {
			WebElement elm = el.findElement(By.className("fixed-columns"));
			if (WebdriverUtils.hasClass("collapsed", elm)) {
				try{
					el.findElement(By.cssSelector("div.ember-list-item-view div.column-content div.svg-icon")).isDisplayed();
				}catch (NoSuchElementException e){
					continue;
				}
				int newLevel = Integer.parseInt(elm.getAttribute("data-level"));
				if (newLevel - level == 1 || newLevel - level == 0 || newLevel == 1) {
					el.findElement(By.cssSelector("div.ember-list-item-view div.column-content div.svg-icon")).click();
					openAllRows(driver.findElements(newRows), newLevel);
				}
			}
		}
		return null;
	}

	public Map<String, List<String>> HeadcountCost() {
		Map<String, List<String>> allCost = new HashMap<String, List<String>>();

		for (WebElement el : rows) {
			WebElement elm = el.findElement(By.className("fixed-columns"));
			try{
				if (WebdriverUtils.hasClass("collapsed", elm)&& el.findElement(By.cssSelector("div.ember-list-item-view div.column-content div.svg-icon")).isDisplayed()){
//					if (WebdriverUtils.hasClass("collapsed", elm)){
					el.findElement(By.cssSelector("div.ember-list-item-view div.column-content div.svg-icon")).click();
					openAllRows(driver.findElements(newRows), Integer.parseInt(elm.getAttribute("data-level")));

				}
			}catch (NoSuchElementException e){
				continue;
			}
					
		}

		for (WebElement elm : rows) {
			String lineName = elm.findElement(By.cssSelector("div.ember-list-item-view div.column-content div.name-text")).getText();
			List<WebElement> values = elm.findElements(costValues);
			List<String> res = new ArrayList<>();
			for (WebElement el : values) {
				String value = el.getText();
				if (value.equals("-"))
					res.add(value);
				else
					res.add(value.replaceAll("[^0-9 .]", "").trim());
			}
			WebdriverUtils.waitForBudgetaLoadBar(driver);

			allCost.put(lineName, res);
			System.out.println(allCost);
		}

		return allCost;
	}

	public Map<String, List<String>> allHeadcount() {
		Map<String, List<String>> allHeadcount = new HashMap<String, List<String>>();

		for (WebElement el : rows) {
			WebElement elm = el.findElement(By.className("fixed-columns"));
			if (WebdriverUtils.hasClass("collapsed", elm)) {
				try{
					el.findElement(By.cssSelector("div.ember-list-item-view div.column-content div.svg-icon")).isDisplayed();
				}catch (NoSuchElementException e){
					continue;
				}
				el.findElement(By.cssSelector("div.ember-list-item-view div.column-content div.svg-icon")).click();
				openAllRows(driver.findElements(newRows), Integer.parseInt(elm.getAttribute("data-level")));

			}
		}

		for (WebElement elm : rows) {
			String lineName = elm.findElement(By.cssSelector("div.ember-list-item-view div.column-content div.name-text")).getText();
			List<WebElement> values = elm.findElements(headcountValue);
			List<String> res = new ArrayList<>();
			for (WebElement el : values) {
				String value = el.getText();
				if (value.equals("-"))
					res.add(value);
				else
					res.add(value.replaceAll("[^0-9 .]", "").trim());
			}
			WebdriverUtils.waitForBudgetaLoadBar(driver);

			allHeadcount.put(lineName, res);
			System.out.println(allHeadcount);
		}

		return allHeadcount;
	}

	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
