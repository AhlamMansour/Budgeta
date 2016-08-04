package com.budgeta;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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

	@FindBy(css = "div.department-column span.type-ahead-lazy-inactive-label")
	private List<WebElement> departmentLine;
	
	@FindBy(css = "div.geography-column span.type-ahead-lazy-inactive-label")
	private List<WebElement> geographyLine;

	@FindBy(className = "budgeta-checkbox")
	private List<WebElement> checkbox;

	@FindBy(className = "name-column")
	private List<WebElement> linesName;

	private final By lineName = By.className("name-column");
	private final By lines = By.className("table-edit-line");

	private By departments = By.className("department-filter-select");

	private By geographies = By.className("geo-filter-select");

	@FindBy(className = "geo-filter-select")
	private WebElement geography; 
	
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
		WebdriverUtils.click(el);
		// el.click();
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
			if (getLineName(el).equals(lineTitle)) {
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

	public boolean isLineExistByLevel(String name, int level) {
		boolean flag = false;
		for (WebElement el : tableLines) {
			if (el.findElement(lineName).findElement(By.tagName("input")).getAttribute("value").equals(name) && getLineLevel(name) == level)
				flag = true;
		}

		return flag;
	}

	public boolean isLineExistByIndex(String name, int index) {
		boolean flag = false;
		for (WebElement el : tableLines) {
			if (el.findElement(lineName).findElement(By.tagName("input")).getAttribute("value").equals(name)) {
				if (getIndexOfSlectedLine() == index)
					flag = true;
			}

		}

		return flag;
	}

	public boolean isLineExist(String name) {
		boolean flag = false;
		for (WebElement el : tableLines) {
			if (el.findElement(lineName).findElement(By.tagName("input")).getAttribute("value").equals(name)) {
				flag = true;
			}

		}

		return flag;
	}

	public List<Integer> getIndexOfLine(String name) {
		List<Integer> lineIndex = new ArrayList<>();
		int i = 0;
		for (WebElement el : tableLines) {
			if (el.findElement(lineName).findElement(By.tagName("input")).getAttribute("value").equals(name)) {
				lineIndex.add(i);
				i++;
			}
		}

		return lineIndex;
	}

	public List<Integer> getIndexOfLineBylevel(String name, int level) {
		List<Integer> lineIndex = new ArrayList<>();
		int i = 0;
		for (WebElement el : tableLines) {
			if (el.findElement(lineName).findElement(By.tagName("input")).getAttribute("value").equals(name) && getLineLevel(name) == level) {
				lineIndex.add(i);
				i++;
			}
		}

		return lineIndex;
	}

	public int numberOfSameLine(String name) {
		return getIndexOfLine(name).size();
	}

	public int numberOfSameLineInSameLevel(String name, int level) {
		return getIndexOfLineBylevel(name, level).size();
	}

	public void renameLine(String name, String text) {
		for (WebElement el : tableLines) {
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

	public void renameSubLines(String name, String text) {
		List<WebElement> subLine = getSublinesForLine(name);
		String sublineText = text;
		for (WebElement el : subLine) {
			WebElement elm = el.findElement(lineName).findElement(By.tagName("input"));
			Actions act = new Actions(driver);
			act.moveToElement(el).build().perform();
			el.findElement(lineName).click();
			elm.clear();
			sublineText = WebdriverUtils.getTimeStamp(text);
			elm.sendKeys(sublineText);
			WebdriverUtils.waitForBudgetaLoadBar(driver);
			elm.sendKeys(Keys.ENTER);
			text = text;
			continue;

		}
	}

	public List<String> getAllLinesName() {
		List<String> LineName = new ArrayList<>();

		for (WebElement el : tableLines) {
			WebElement elm = el.findElement(lineName).findElement(By.tagName("input"));
			LineName.add(elm.getAttribute("value"));
		}

		return LineName;
	}

	public List<String> getSubLinesName(String name) {
		List<WebElement> subLine = getSublinesForLine(name);
		List<String> subLineName = new ArrayList<>();

		for (WebElement el : subLine) {
			WebElement elm = el.findElement(lineName).findElement(By.tagName("input"));
			subLineName.add(elm.getAttribute("value"));
		}

		return subLineName;
	}

	private List<WebElement> getAllLines() {
		return driver.findElements(lines);
	}

	private List<WebElement> getSublinesForLine(String lineTitle) {
		List<WebElement> subLine = new ArrayList<WebElement>();
		List<WebElement> lines = getAllLines();
		int dataLevel = -1;
		boolean startInsert = false;
		for (WebElement el : lines) {
			if (getLineName(el).equals(lineTitle)) {
				dataLevel = Integer.parseInt(el.getAttribute("data-level"));
				startInsert = true;
				continue;
			}
			if (startInsert) {
				int currentLevel = Integer.parseInt(el.getAttribute("data-level"));
				if (currentLevel > dataLevel) {
					subLine.add(el);
				} else if (currentLevel <= dataLevel) {
					break;
				}
			}
		}
		return subLine;
	}

	public int getAllSublinesForLine(String name) {
		int subLine = 0;
		for (WebElement el : tableLines) {
			if (el.findElement(lineName).findElement(By.tagName("input")).getAttribute("value").equals(name)) {
				subLine = getSublinesForLine(name).size();
			}
		}

		return subLine;
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
		List<String> departmentsList = new ArrayList<>();
		for (WebElement el : geographyLine) {
			departmentsList.add(el.getAttribute("title"));
			

		}

		return departmentsList;

	}
	
	// /////////////////

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
	
	public String getSelectedGeographyFilterOption(){
		return geography.findElement(By.className("select2-chosen")).getText();
	}

	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
