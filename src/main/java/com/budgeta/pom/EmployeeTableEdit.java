package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

	private SideDropDown HeadcountDropDown;
	private SideDropDown DepartmentsDropDown;
	private SideDropDown GeographyDropDown;

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

	public int getIndexOfSlectedLine() {
		int i = 0;
		for (WebElement el : employeeEditLine) {
			if (WebdriverUtils.hasClass("selected", el))
				return i;
			i++;
		}
		return i;
	}

	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}
}
