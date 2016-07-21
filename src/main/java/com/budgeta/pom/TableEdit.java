package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebElementUtils;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class TableEdit extends AbstractPOM {

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

	@FindBy(className = "checkbox")
	private List<WebElement> checkbox;

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

	public void clickOnGeneralFields() {
		for (WebElement el : tableBtn) {
			if (el.getText().equals("General Fields")) {
				el.click();
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				WebdriverUtils.elementToHaveClass(el, "active");
				break;
			}

		}

	}

	public void clickOnAddEmployeeButton() {

		addEmployeeBtn.click();
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));

	}

	public void ClickOnDuplicate() {

		for (WebElement el : actionIcons) {
			if (el.getAttribute("title").equals("Duplicate")) {
				el.click();
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				break;
			}
		}
	}

	public void ClickOnNotes() {

		for (WebElement el : actionIcons) {
			if (el.getAttribute("title").equals("Notes")) {
				el.click();
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				break;
			}
		}
	}

	public void ClickOnFlag() {

		for (WebElement el : actionIcons) {
			if (el.getAttribute("title").equals("Flag")) {
				el.click();
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				WebdriverUtils.elementToHaveClass(el, "flagged");
				break;
			}
		}
	}

	public void ClickOnDlete() {

		for (WebElement el : actionIcons) {
			if (el.getAttribute("title").equals("Delete")) {
				el.click();
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				break;
			}
		}
	}

	public void duplicateLineBylineName(String name) {

		for (WebElement el : employeeEditLine) {
			if (el.findElement(lineName).getText().equals(name)) {
				ClickOnDuplicate();
				WebdriverUtils.waitForBudgetaLoadBar(driver);
			}
		}

	}

	public void addNotestoLineBylineName(String name) {

		for (WebElement el : employeeEditLine) {
			if (el.findElement(lineName).getText().equals(name)) {
				ClickOnNotes();
				WebdriverUtils.waitForBudgetaLoadBar(driver);
			}
		}

	}

	public void flagLineBylineName(String name) {

		for (WebElement el : employeeEditLine) {
			if (el.findElement(lineName).getText().equals(name)) {
				ClickOnFlag();
				WebdriverUtils.waitForBudgetaLoadBar(driver);
			}
		}

	}

	public void deleteLineBylineName(String name) {

		for (WebElement el : employeeEditLine) {
			if (el.findElement(lineName).getText().equals(name)) {
				ClickOnDlete();
				WebdriverUtils.waitForBudgetaLoadBar(driver);
			}
		}

	}

	public int getNumberOflines() {
		return employeeEditLine.size();
	}

	public String getLineNameByIndex(int index) {
		return employeeEditLine.get(index).getText();
	}

	public void selectRandomLine() {
		int random = WebElementUtils.getRandomNumberByRange(1, getNumberOflines() - 1);

		checkbox.get(random).click();
		WebdriverUtils.elementToHaveClass(employeeEditLine.get(random), "selected");

	}

	public int getIndexOfSlectedLine() {
		int i = 0;
		for (WebElement el : employeeEditLine) {
			if (el.isSelected())
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
