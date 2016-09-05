package com.budgeta.pom;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebElementUtils;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class EmployeeTableEdit extends AbstractPOM {

	@FindBy(className = "view-table-edit")
	private WebElement wrapper;

	@FindBy(className = "top-bar-buttons-line")
	private WebElement topBatButtonWrapper;

	@FindBy(className = "table-mod-btn")
	private List<WebElement> tableBtn;

	@FindBy(className = "add-emp-btn")
	private WebElement addEmployeeBtn;

	@FindBy(className = "svg-icon")
	private List<WebElement> actionIcons;

	@FindBy(className = "employee-edit-line")
	private List<WebElement> employeeEditLine;

	@FindBy(className = "budgeta-checkbox")
	private List<WebElement> checkbox;

	@FindBy(className = "name-column")
	private List<WebElement> linesName;

	private final By lineName = By.className("name-column");
	private By departments = By.className("department-filter-select");

	@FindBy(className = "department-filter-select")
	private WebElement department;
	
	private By geographies = By.className("geo-filter-select");
	
	private By types = By.className("emp-filter-select");

	@FindBy(className = "emp-filter-select")
	private WebElement type;
	
	@FindBy(className = "geo-filter-select")
	private WebElement geography;

	@FindBy(css = "div.department-column span.type-ahead-lazy-inactive-label")
	private List<WebElement> departmentLine;
	
	@FindBy(css = "div.geography-column span.type-ahead-lazy-inactive-label")
	private List<WebElement> geographyLine;
	
	@FindBy(css = "div.type-column span.line-type")
	private List<WebElement> typeLine;
	
	@FindBy(css = "div.status-column div.text-status")
	private List<WebElement> statusLine;
	
	private final By lines = By.className("employee-edit-line");
	
	private SideDropDown HeadcountDropDown;
	private SideDropDown DepartmentsDropDown;
	private SideDropDown GeographyDropDown;
	private SideDropDown TypeDropDown;

	public void clickOnEmployeeButton() {
		for (WebElement el : tableBtn) {
			if (el.getText().equals("Employees")) {
				el.click();
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				WebdriverUtils.waitForElementToBeFound(driver, By.className("add-emp-btn"));
				WebdriverUtils.elementToHaveClass(el, "active");
				break;
			}

		}
	}

	

	public void clickOnAddEmployeeButton() {

		addEmployeeBtn.click();
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));

	}

	public void ClickOnDuplicate(String name) {
		for (WebElement el : actionIcons) {
			if (el.getAttribute("title").equals("Duplicate")) {
				el.click();
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				break;
			}
		}

	}
	
	
	public void DuplicateTableLine(int Index) {
		List <WebElement> icons = employeeEditLine.get(Index).findElements(By.className("svg-icon"));
		for (WebElement el : icons) {
			if (el.getAttribute("title").equals("Duplicate")) {
				el.click();
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				break;
			}
		}
	}

	public void ClickOnNotes(int Index) {
		
		List <WebElement> icons = employeeEditLine.get(Index).findElements(By.className("svg-icon"));
		for (WebElement el : icons) {
			if (el.getAttribute("title").equals("Notes")) {
				el.click();
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
				
				break;
			}
		}

	}

	public void ClickOnFlag(int Index) {
		List <WebElement> icons = employeeEditLine.get(Index).findElements(By.className("svg-icon"));
		for (WebElement el : icons) {
			if (el.getAttribute("title").equals("Flag") && !WebdriverUtils.hasClass("flagged", el) ) {
				el.click();
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				WebdriverUtils.elementToHaveClass(el, "flagged");
				break;
			}
		}
		
	}
	
	

	public void ClickOnDlete(int Index) {
		List <WebElement> icons = employeeEditLine.get(Index).findElements(By.className("svg-icon"));
		for (WebElement el : icons) {
			if (el.getAttribute("title").equals("Delete")) {
				el.click();
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
				break;
			}
		}
	}

	public void duplicateLineBylineName(String name) {

		for (WebElement el : employeeEditLine) {
			if (el.findElement(lineName).findElement(By.tagName("input")).getAttribute("value").equals(name)) {
				ClickOnDuplicate(name);
				break;

			}
		}

	}

	public void duplicateLine(String name, int Index) {

		for (WebElement el : employeeEditLine) {
			if (el.findElement(lineName).findElement(By.tagName("input")).getAttribute("value").equals(name)) {
				DuplicateTableLine(Index);
				break;

			}
		}

	}

	public void addNotestoLineBylineName(String name, int index) {

		for (WebElement el : employeeEditLine) {
			if (el.findElement(lineName).findElement(By.tagName("input")).getAttribute("value").equals(name)) {
				ClickOnNotes(index);
				break;
			}
		}

		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
	}

	public void flagLineBylineName(String name, int index) {

		for (WebElement el : employeeEditLine) {
			if (el.findElement(lineName).findElement(By.tagName("input")).getAttribute("value").equals(name)) {
				ClickOnFlag(index);
				break;
			}
		}

	}
	
	public boolean isLineFlag(String name, int index){
		boolean flag = false;
		for (WebElement el : employeeEditLine) {
			if (el.findElement(lineName).findElement(By.tagName("input")).getAttribute("value").equals(name)) {
				if (isFlagLine(index))
					flag = true;	
			}
		}
		return flag;
	}

	
	private boolean isFlagLine(int Index) {
		boolean flag = false;
		List <WebElement> icons = employeeEditLine.get(Index).findElements(By.className("svg-icon"));
		for (WebElement el : icons) {
			if (el.getAttribute("title").equals("Flag")) {
				if (WebdriverUtils.hasClass("flagged", el))
				{
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
	
	public void deleteLineBylineName(String name, int index) {

		for (WebElement el : employeeEditLine) {
			if (el.findElement(lineName).findElement(By.tagName("input")).getAttribute("value").equals(name)) {
				ClickOnDlete(index);
				break;
			}
		}

	}

	public int getNumberOflines() {
		return employeeEditLine.size();
	}

	public String getLineNameByIndex(int index) {
		return linesName.get(index + 1).findElement(By.tagName("input")).getAttribute("value");
	}

	public void selectRandomLine() {
		int random = WebElementUtils.getRandomNumberByRange(1, getNumberOflines() - 1);

		checkbox.get(random).click();
		WebdriverUtils.elementToHaveClass(employeeEditLine.get(random), "selected");

	}
	
	public void unSelectLineByIndex(int index) {
		checkbox.get(index).click();
		WebdriverUtils.waitForBudgetaLoadBar(driver);

	}
	
	public int getNumberOfSelectedLine()
	{
		int i = 0;
		for (WebElement el : employeeEditLine) {
			if (WebdriverUtils.hasClass("selected", el))
				i++;
		}
		return i;
	}

	public int getNumberOfSpecificLine(String name)
	{
		int i = 0;
		for (WebElement el : employeeEditLine) {
			if (el.findElement(lineName).findElement(By.tagName("input")).getAttribute("value").contains(name))
				i++;
		}
		return i;
	}
	
	public int getIndexOfSlectedLine() {
		int i = 0;
		for (WebElement el : employeeEditLine) {
			if (WebdriverUtils.hasClass("selected", el))
				return i;
			i++;
		}
		return i;
	}
	
	///////////////////////////////////////
	
	private List<WebElement> getAllLines() {
		return driver.findElements(lines);
	}
	
	public int allLines() {
		return getAllLines().size();
	}

	public List<String> getAllDepartmentForAllLines() {
		List<String> departmentsList = new ArrayList<>();
		for (WebElement el : departmentLine) {
			departmentsList.add(el.getAttribute("title"));
			

		}

		return departmentsList;

	}

	public List<String> getAllGeographyForAllLines() {
		List<String> geographyList = new ArrayList<>();
		for (WebElement el : geographyLine) {
			geographyList.add(el.getAttribute("title"));
			

		}

		return geographyList;

	}
	
	public List<String> getAllTypeForAllLines() {
		List<String> typeList = new ArrayList<>();
		for (WebElement el : typeLine) {
			typeList.add(el.getAttribute("title"));
			

		}

		return typeList;

	}
	
	public List<String> getAllStatusForAllLines() {
		List<String> statusList = new ArrayList<>();
		for (WebElement el : statusLine) {
			statusList.add(el.getAttribute("title"));
			

		}

		return statusList;

	}
	
	public void selectSubReportType(String option) {
		getDepartmentDropDown().selectValue(option);
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}

	public void selectRandomDepartment() {
		getDepartmentDropDown().selectRandomValue();
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}

	private SideDropDown getDepartmentDropDown() {

		if (DepartmentsDropDown == null) {
			// for(WebElement el : wrapper.findElements(subReportType)){
			// if(el.getText().trim().equals("Budget"))
			DepartmentsDropDown = new SideDropDown(driver.findElement(departments));
			// }
		}
		return DepartmentsDropDown;
	}

	public void selectRandomGeography() {
		getGeographyDropDown().selectRandomValue();
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}

	private SideDropDown getGeographyDropDown() {

		if (GeographyDropDown == null) {
			// for(WebElement el : wrapper.findElements(subReportType)){
			// if(el.getText().trim().equals("Budget"))
			GeographyDropDown = new SideDropDown(driver.findElement(geographies));
			// }
		}
		return GeographyDropDown;
	}
	
	public void selectRandomType() {
		getTypeDropDown().selectRandomValue();
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}
	
	public void selectHeadcount(String option) {
		getTypeDropDown().selectValue(option);
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}
	
	public void selectDepartment(String option) {
		getDepartmentDropDown().selectValue(option);
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}

	public void selectGeopgraphy(String option) {
		getGeographyDropDown().selectValue(option);
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}
	
	private SideDropDown getTypeDropDown() {

		if (TypeDropDown == null) {
			// for(WebElement el : wrapper.findElements(subReportType)){
			// if(el.getText().trim().equals("Budget"))
			TypeDropDown = new SideDropDown(driver.findElement(types));
			// }
		}
		return TypeDropDown;
	}
	
	public String getSelectedGeographyFilterOption(){
		return geography.findElement(By.className("select2-chosen")).getText();
	}
	
	public String getSelectedTypeFilterOption(){
		return type.findElement(By.className("select2-chosen")).getText();
	} 
	public String getSelectedDepartmentFilterOption(){
		return department.findElement(By.className("select2-chosen")).getText();
	}
	
	
	public void renameLine(String name, String text) {
		for (WebElement el : employeeEditLine) {
			WebElement elm = el.findElement(lineName).findElement(By.tagName("input"));
			if (elm.getAttribute("value").equals(name)) {
				Actions act = new Actions(driver);
				act.moveToElement(el).build().perform();
				el.findElement(lineName).click();
				elm.clear();
				elm.sendKeys(text);
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				elm.sendKeys(Keys.ENTER);
				// el.findElement(By.className("account-id-column")).click();
				// act.moveToElement(el).build().perform();
				// driver.findElements(By.className("column-text")).get(0).click();
				break;
			}

		}

	}
	
	public boolean isLineExistByIndex(String name, int index) {
		boolean flag = false;
		for (WebElement el : employeeEditLine) {
			if (el.findElement(lineName).findElement(By.tagName("input")).getAttribute("value").equals(name)) {
				if (getIndexOfSlectedLine() == index)
					flag = true;
			}

		}

		return flag;
	}
	
	
	public boolean isLineExist(String name) {
		boolean flag = false;
		for (WebElement el : employeeEditLine) {
			if (el.findElement(lineName).findElement(By.tagName("input")).getAttribute("value").equals(name)) {
					flag = true;
			}

		}

		return flag;
	}
	
	
	public boolean isLineRemovedByIndex(String name, int index) {
		boolean flag = false;
		for (WebElement el : employeeEditLine) {
			if (el.findElement(lineName).findElement(By.tagName("input")).getAttribute("value").equals(name)) {
				if (WebdriverUtils.hasClass("scenario-line-removed", el))
					flag = true;
			}

		}

		return flag;
	}
	

	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}
}
