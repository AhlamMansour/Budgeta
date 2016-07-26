package com.budgeta;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.budgeta.pom.SideDropDown;
import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebElementUtils;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class GeneralTableEdit extends AbstractPOM {

	@FindBy(className = "view-table-edit")
	private WebElement wrapper;

	@FindBy(className = "table-edit-line")
	private List<WebElement> tableLines;

	@FindBy(className = "notes")
	private WebElement note;

	@FindBy(className = "flag")
	private WebElement flag;

	@FindBy(className = "duplicate")
	private WebElement duplicateLine;

	@FindBy(className = "delete_budget")
	private WebElement deleteLine;

	@FindBy(className = "table-mod-btn")
	private List<WebElement> tableBtn;

	@FindBy(className = "budgeta-checkbox")
	private List<WebElement> checkbox;

	@FindBy(className = "name-column")
	private List<WebElement> linesName;

	private final By lineName = By.className("name-column");
	private final By lines = By.className("table-edit-line");

	private SideDropDown HeadcountDropDown;
	private SideDropDown DepartmentsDropDown;
	private SideDropDown GeographyDropDown;

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

	public void clickOnNotes() {
		note.click();
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
	}

	public void clickOnDuplicate() {
		duplicateLine.click();
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}

	public void clickOnFlag() {
		flag.click();
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		WebdriverUtils.elementToHaveClass(flag, "flagged");

	}

	public void clickOnDelete() {
		deleteLine.click();
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
	}

	public void ClickOnNotes(int Index) {

		WebElement el = tableLines.get(Index).findElement(By.className("notes"));
		el.click();
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
	}

	public void ClickOnFlag(int Index) {
		WebElement el = tableLines.get(Index).findElement(By.className("flag"));

		if (!WebdriverUtils.hasClass("flagged", el)) {
			el.click();
			WebdriverUtils.waitForBudgetaLoadBar(driver);
			WebdriverUtils.elementToHaveClass(el, "flagged");
		}

	}

	public void addNotestoLineBylineName(String name, int index) {

		for (WebElement el : tableLines) {
			if (el.findElement(lineName).findElement(By.tagName("input")).getAttribute("value").equals(name)) {
				ClickOnNotes(index);
				break;
			}
		}

		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
	}

	public void flagLineBylineName(String name, int index) {

		for (WebElement el : tableLines) {
			if (el.findElement(lineName).findElement(By.tagName("input")).getAttribute("value").equals(name)) {
				ClickOnFlag(index);
				break;
			}
		}

	}

	public boolean isLineFlag(String name, int index) {
		boolean flag = false;
		for (WebElement el : tableLines) {
			if (el.findElement(lineName).findElement(By.tagName("input")).getAttribute("value").equals(name)) {
				if (isFlagLine(index))
					flag = true;
			}
		}
		return flag;
	}

	private boolean isFlagLine(int Index) {
		boolean flag = false;
		WebElement el = tableLines.get(Index).findElement(By.className("flag"));
		if (WebdriverUtils.hasClass("flagged", el))
			flag = true;

		return flag;
	}

	public void ClickOnDelete(int Index) {
		WebElement el = tableLines.get(Index).findElement(By.className("delete_budget"));
		el.click();
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));

	}

	public void deleteLineBylineName(String name, int index) {

		for (WebElement el : tableLines) {
			if (el.findElement(lineName).findElement(By.tagName("input")).getAttribute("value").equals(name)) {
				ClickOnDelete(index);
				break;
			}
		}

	}

	public int getNumberOflines() {
		return tableLines.size();
	}

	public void selectRandomLine() {
		int random = WebElementUtils.getRandomNumberByRange(1, getNumberOflines() - 1);

		checkbox.get(random).click();
		WebdriverUtils.elementToHaveClass(tableLines.get(random), "selected");

	}

	public String getLineNameByIndex(int index) {
		return linesName.get(index + 1).findElement(By.tagName("input")).getAttribute("value");
	}

	public void unSelectLineByIndex(int index) {
		checkbox.get(index).click();
		WebdriverUtils.waitForBudgetaLoadBar(driver);

	}

	public int getNumberOfSelectedLine() {
		int i = 0;
		for (WebElement el : tableLines) {
			if (WebdriverUtils.hasClass("selected", el))
				i++;
		}
		return i;
	}

	public int getIndexOfSlectedLine() {
		int i = 0;
		for (WebElement el : tableLines) {
			if (WebdriverUtils.hasClass("selected", el))
				return i;
			i++;
		}
		return i;
	}
	
	public int getLineLevel(String lineTitle) {
		List<WebElement> allLinesInLevel = driver.findElements(lines);
		int level = -1;
		for (WebElement el : allLinesInLevel) {
			if (getLineName(el).equals(lineTitle)){
				WebElementUtils.hoverOverField(el, driver, null);
				WebdriverUtils.sleep(300);
				level = Integer.parseInt(el.getAttribute("data-level")); 
			}
		}
		return level;
	}
	
	private String getLineName(WebElement el) {
		try {
			if (el.getAttribute("class").contains("new-line"))
				return el.findElement(lineName).getText();
			return el.findElement(lineName).findElement(By.tagName("input")).getAttribute("value");
		} catch (Exception e) {
			return "";
		}
	}

	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
