package com.budgeta.pom;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebElementUtils;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class SecondaryBoard extends AbstractPOM {

	MenuTrigger trigger;
	
	private SecondaryBoard secondaryBoard;
	protected BudgetaBoard board;

	@FindBy(className = "secondary")
	private WebElement wrapper;

	@FindBy(className = "add-root-budget")
	private WebElement addBudgetaBtn;

	@FindBy(id = "selected-root-menu")
	private WebElement budgetOptionsMenuBtn;

	@FindBy(css = "div.popup-menu div.drop-down ul.narrow li")
	private List<WebElement> budgetDropDownOptions;

	@FindBy(className = "budget-list")
	private WebElement budgetsLineWrapper;

	@FindBy(className = "search-wrapper")
	private WebElement searchBudget;

	// @FindBy(css = "div.subnav-main-icons div.scenarios")
	@FindBy(id = "sidebar-scenarios")
	private WebElement scenarios;

	@FindBy(id = "sidebar-versions")
	private WebElement versions;

	@FindBy(css = "div.root-budget div.budget-menu")
	private WebElement settingBudgetIcon;

	@FindBy(name = "name")
	private WebElement editLineName;

	@FindBy(css = "div.tree-edit-bar div.right")
	private WebElement closeBtn;

	@FindBy(className = "scenario-added")
	private List<WebElement> scenarioLine;

	@FindBy(id = "budget-settings")
	private WebElement budgetSettings;

	@FindBy(css = "li.selected-root li.active")
	private WebElement selectedLine;

	@FindBy(className = "scenario-changed")
	private List<WebElement> versionChanges;

	@FindBy(id = "sidebar-reports")
	private WebElement reportsBtn;

	@FindBy(css = "aside.secondary h2 a.active")
	private WebElement budgetTitle;

	@FindBy(className = "root-budget")
	private WebElement selectedBudget;

	@FindBy(css = "div.root-budget a.add-child-budget")
	private WebElement addBudgetLines;
	
	@FindBy(className = "collapse-tree")
	private WebElement collapsedTree;
	
	
	// private final By newLine = By.className("new-line");
	// private final By selectBudget =
	// By.cssSelector("aside.secondary h2 input");

	private final By line = By.cssSelector("li.budget-list-item");

	private final By addLinesBtn = By.className("add-child-budget");

	private final By budgetName = By.className("budget-name-text-display");
	private final By addLineBtn = By.cssSelector(".add.add-line");

	private final By lineName = By.className("inline-edit");

	private final By lineSetting = By.className("budget-menu");

	private final By nameField = By.className("ember-text-field");
	
	

	// private final By shareIcon =
	// By.cssSelector("div.actions-toggle span.budget-name  div.svg-icon");

	private By lineType = By.className("type");

	public Scenarios openScenarios() {
		if (driver.findElement(By.id("sidebar-scenarios")).getAttribute("title").equals("Scenarios")) {
			scenarios.findElement(By.xpath("..")).click();
			WebdriverUtils.elementToHaveClass(scenarios, "expanded");
		}
		return new Scenarios();
	}

	public Versions openVersions() {
		if (driver.findElement(By.className("version-subnav")).getAttribute("class").contains("collapsed")) {
			versions.findElement(By.xpath("..")).click();
			WebdriverUtils.elementToHaveClass(driver.findElement(By.className("version-subnav")), "expanded");
		}
		return new Versions();
	}

	public NewBudgetPopup addBudgeta() {
		WebdriverUtils.waitUntilClickable(driver, addBudgetaBtn);
		addBudgetaBtn.click();
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		return new NewBudgetPopup();

	}

	public void addSubLine(String lineTitle) {
		if (WebdriverUtils.isDisplayed(closeBtn))
			clickClose();
		WebElement lineElm = getLineByName(lineTitle);
		WebElementUtils.hoverOverField(lineElm, driver, null);
		WebdriverUtils.sleep(300);
		WebElementUtils.hoverOverField(lineElm.findElement(addLinesBtn), driver, null);
		lineElm.findElement(addLinesBtn).click();
		WebdriverUtils.sleep(500);
	}

	public void addLine() {
		if (!wrapper.getAttribute("class").contains("tree-edit")) {
			selectedBudget.findElement(addLinesBtn).click();
			WebdriverUtils.waitForElementToBeFound(driver, By.className("tree-edit"));
		}

	}

	public void addAllLines() {
		if (!wrapper.getAttribute("class").contains("tree-edit")) {
			selectedBudget.findElement(addLinesBtn).click();
			WebdriverUtils.waitForElementToBeFound(driver, By.className("tree-edit"));
		}
		WebElement line = null;
		while (getNextLineToAdd() != null) {
			line = getNextLineToAdd();
			WebElement add = line.findElement(addLineBtn);
			if (add.getAttribute("class").contains("enable") && WebdriverUtils.isVisible(add)) {
				add.findElement(By.className("add-budget-line")).click();
				WebdriverUtils.waitForBudgetaBusyBar(driver);
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				WebdriverUtils.sleep(1500);
			}
		}
	}

	public void addLine(String lineTitle) {
		if (!wrapper.getAttribute("class").contains("tree-edit")) {
			selectedBudget.findElement(addLinesBtn).click();
			WebdriverUtils.waitForElementToBeFound(driver, By.className("tree-edit"));
		}
		List<WebElement> lines = getLines();
		String name = "";
		for (WebElement el : lines) {
			name = getLineName(el).replaceAll("\\d", "").trim();
			if (name.equals(lineTitle) && el.getAttribute("class").contains("new-line")) {
				WebElement add = el.findElement(addLineBtn);
				if (add.getAttribute("class").contains("enable")) {
					add.click();
					WebdriverUtils.sleep(300);
					WebdriverUtils.waitForBudgetaBusyBar(driver);
					WebdriverUtils.waitForBudgetaLoadBar(driver);
					return;
				}
			}
		}
	}

	public MenuTrigger getBudgetMenuTrigger() {
		WebElementUtils.hoverOverField(selectedBudget, driver, null);
		WebdriverUtils.sleep(100);
		return new MenuTrigger(settingBudgetIcon);
	}

	public void openBudgetDropDownOptionsMenu() {
		if (isBudgetDropDownOptionsOpen()) {
			budgetOptionsMenuBtn.click();
			WebdriverUtils.waitForElementToBeFound(driver, By.className("qtip-focus"));
			wait.until(ExpectedConditions.visibilityOfAllElements(budgetDropDownOptions));
		}
	}

	public synchronized void selectBudgetOption(String option) {
		openBudgetDropDownOptionsMenu();
		for (WebElement el : budgetDropDownOptions) {
			if (el.getText().equalsIgnoreCase(option)) {
				WebElementUtils.hoverOverField(el, driver, null);
				el.click();
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				WebdriverUtils.waitForBudgetaBusyBar(driver);
			}
		}
	}

	public MenuTrigger getLineSettings(String name) {
		WebElement line = getLineByName(name);
		WebElementUtils.hoverOverField(line, driver, null);
		Actions act = new Actions(driver);
		act.moveToElement(line).build().perform();
		return new MenuTrigger(line.findElement(lineSetting));
	}

	public MenuTrigger getSubLinSettings(String lineTitle, String subLineTitle) {
		List<WebElement> subLines = getSubLinesForLine(lineTitle);
		for (WebElement el : subLines) {
			// if
			// (el.findElement(budgetName).getText().replaceAll(el.findElement(By.cssSelector("span.type")).getText(),
			// "").trim().equals(subLineTitle))
			// if
			// (el.findElement(By.className("budget-name-text")).getText().contains(subLineTitle)){
			if (el.findElement(By.className("budget-name-text-display")).getText().contains(subLineTitle)) {
				WebElementUtils.hoverOverField(el, driver, null);
				Actions act = new Actions(driver);
				act.moveToElement(el).build().perform();
				return new MenuTrigger(el.findElement(lineSetting));
			}
		}
		return null;
	}

	public void clickClose() {
		if (WebdriverUtils.isDisplayed(closeBtn)) {
			closeBtn.click();
			WebdriverUtils.waitForElementToDisappear(driver, By.cssSelector("div.tree-edit-bar div.right"));
		}
	}

	public boolean isLineExist(String lineTitle) {
		clickClose();
		List<WebElement> lines = getLines();
		for (WebElement el : lines) {
			if (el.findElement(budgetName).getText().trim().equals(lineTitle))
				return true;
		}
		return false;
	}

	public boolean isSubLineExist(String lineTitle, String subLineTitle) {
		List<WebElement> subLines = getSubLinesForLine(lineTitle);
		for (WebElement el : subLines) {
			try {
				// if
				// (el.findElement(budgetName).getText().replaceAll(el.findElement(By.className("type")).getText(),
				// "").trim().equals(subLineTitle))
				if (el.findElement(By.className("budget-name-text")).getText().contains(subLineTitle))
					return true;
			} catch (Exception e) {
			}
		}
		return false;
	}

	public boolean isLineFlag(String lineTitle) {
		clickClose();
		WebElement line = getLineByName(lineTitle);
		try {
			line.findElement(By.className("flagged")).isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean isBudgetFlag(String budget_Name) {
		// String name = selectedBudget.findElement(budgetName).getText();
		try {
			selectedBudget.findElement(By.xpath("..")).findElement(By.className("flagged")).isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean isLineShared(String lineTitle) {
		clickClose();
		WebElement line = getLineByName(lineTitle);
		try {
			line.findElement(By.cssSelector("div.actions-toggle span.budget-name  div.svg-icon")).isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void RenameLine(String newName) {
		List<WebElement> lines = getLines();
		for (WebElement el : lines) {
			if (el.getAttribute("class").contains("active")) {
				el.findElement(nameField).clear();
				el.findElement(nameField).sendKeys(newName);
				el.findElement(nameField).sendKeys(Keys.ENTER);
				WebdriverUtils.sleep(500);
			}
		}
	}

	public void RenameBudget(String newName) {
		selectedBudget.findElement(nameField).clear();
		selectedBudget.findElement(nameField).sendKeys(newName);
		selectedBudget.findElement(nameField).sendKeys(Keys.ENTER);
		WebdriverUtils.sleep(500);
	}

	public boolean isScenarioLineDisplayed(String name) {
		for (WebElement el : scenarioLine) {
			// if
			// (el.findElement(budgetName).getText().replaceAll(el.findElement(By.className("type")).getText(),
			// "").trim().equals(name))
			if (el.findElement(By.className("budget-name-text")).getText().contains(name)) {
				return WebdriverUtils.isDisplayed(el);
			}
		}
		return false;
	}

	public int getNumberOfSubLinesForLine(String lineTitle, String subLineTitle) {
		List<WebElement> subLines = getSubLinesForLine(lineTitle);
		int num = 0;
		for (WebElement el : subLines) {
			if (el.findElement(budgetName).getText().equals(subLineTitle))
				num++;
		}
		return num;
	}

	public int getNumberOfLines(String lineTitle) {
		List<WebElement> Lines = getLines();
		int num = 0;
		for (WebElement el : Lines) {
			try {
				if (el.findElement(budgetName).getText().contains(lineTitle))
					num++;
			} catch (Exception e) {
				continue;
			}

		}
		return num;
	}

	public void clickOnLine(String name) {
		clickClose();
		getLineByName(name).findElement(budgetName).click();
		WebdriverUtils.waitForBudgetaBusyBar(driver);
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}

	public void clickOnSubLine(String lineName, String subLineName) {
		clickClose();
		List<WebElement> sublines = getSubLinesForLine(lineName);
		for (WebElement el : sublines) {
			if (getLineName(el).equals(subLineName)) {
				el.findElement(budgetName).click();
				WebdriverUtils.waitForBudgetaBusyBar(driver);
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				return;
			}
		}
	}

	public void clickOnSubLine(String lineName, String subLineName, String sub_subLine) {
		if (WebdriverUtils.isDisplayed(closeBtn))
			clickClose();
		List<WebElement> sublines = getSubLinesForSubLine(lineName, subLineName);
		for (WebElement el : sublines) {
			if (getLineName(el).contains(sub_subLine)) {
				el.findElement(budgetName).click();
				WebdriverUtils.waitForBudgetaBusyBar(driver);
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				return;
			}
		}
	}

	public void clickOnSubLine(String lineName, String subLineName, String sub_subLine, String nextLevelLine) {
		clickClose();
		List<WebElement> sublines = getSubLinesFourthLevel(lineName, subLineName, sub_subLine);
		for (WebElement el : sublines) {
			if (getLineName(el).contains(nextLevelLine)) {
				el.findElement(budgetName).click();
				WebdriverUtils.elementToHaveClass(el, "active");
				WebdriverUtils.waitForBudgetaBusyBar(driver);
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				return;
			}
		}
	}

	public BudgetSettings openBudgetSettings() {
		budgetSettings.click();
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
		WebdriverUtils.waitForBudgetaBusyBar(driver);
		return new BudgetSettings();
	}

	public void addSubLineForLine(String _lineName, String subLineName) {
		List<WebElement> sublines = getSubLinesForLine(_lineName);
		for (WebElement el : sublines) {
			try {
				if (el.findElement(lineName).getText().equals(subLineName)) {
					el.findElement(addLineBtn).click();
					WebdriverUtils.sleep(300);
					WebdriverUtils.waitForBudgetaBusyBar(driver);
					WebdriverUtils.waitForBudgetaLoadBar(driver);
					return;
				}
			} catch (Exception e) {
			}
		}
	}

	public void addSubLineForSubLine(String lineTitle, String subLineTitle, String subLineToAdd) {
		List<WebElement> subLines = getSubLinesForSubLine(lineTitle, subLineTitle);
		for (WebElement el : subLines) {
			try {
				if (getLineName(el).equals(subLineToAdd)) {
					WebElementUtils.hoverOverField(el, driver, null);
					WebdriverUtils.sleep(300);
					WebElementUtils.hoverOverField(el.findElement(addLineBtn), driver, null);
					el.findElement(addLineBtn).click();
					WebdriverUtils.sleep(600);
					WebdriverUtils.waitForBudgetaBusyBar(driver);
					WebdriverUtils.waitForBudgetaLoadBar(driver);
					return;
				}
			} catch (Exception e) {
			}
		}
	}

	public void openAddChild(String lineTitle, int level) {
		clickClose();
		List<WebElement> allLinesInLevel = driver.findElements(By.cssSelector("ol.tree.nav")).get(0).findElement(By.className("selected-root"))
				.findElement(By.tagName("ol")).findElements(line);
		for (WebElement el : allLinesInLevel) {
			if (getLineName(el).equals(lineTitle) && el.getAttribute("data-level").equals(level + "")) {
				WebElementUtils.hoverOverField(el, driver, null);
				WebdriverUtils.sleep(300);
				WebElementUtils.hoverOverField(el.findElement(addLinesBtn), driver, null);
				el.findElement(addLinesBtn).click();
				WebdriverUtils.sleep(600);
				WebdriverUtils.waitForBudgetaBusyBar(driver);
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				return;
			}
		}
	}

	public String getSelectedLine() {
		return getLineName(selectedLine);
	}

	public String getSelectedBudgetName() {
		return getLineName(selectedBudget);
	}

	public int getNumberOfVersionChanges() {
		return versionChanges.size();
	}

	public ReportsPopup clickReports() {
		reportsBtn.click();
		return new ReportsPopup();
	}

	public void selectDropDownInLine(String dropdown, String textToSelect) {
		addBudgetLines.click();
		final By secondaryBarDDNLocator = By.cssSelector("ol.tree.nav");
		WebdriverUtils.sleep(1100);
		List<WebElement> list = driver.findElements(secondaryBarDDNLocator);
		for (WebElement listMember : list) {
			if (WebdriverUtils.isDisplayed(listMember) && listMember.findElement(By.className("dropdown-value-text")).getText().equalsIgnoreCase(dropdown))
				selectDropDown(listMember.findElement(By.className("dropdown-value-text")), textToSelect);
		}
	}

	public String getBudgetTitle() {
		return budgetTitle.getText().trim();
	}

	public void setBudgetTitle(String value) {
		editLineName.clear();
		editLineName.sendKeys(value);
		editLineName.sendKeys(Keys.ENTER);

	}

	public boolean isShareIconExist(String budget_Name) {
		String name = selectedBudget.findElement(budgetName).getText();
		if (name.equals(budget_Name)) {
			selectedBudget.findElement(By.cssSelector("div.root-budget span.budget-name svg.icon")).isDisplayed();
			return true;

		}

		return false;
	}

	public boolean checkIfLineTypeIsModel(String lineTitle) {
		List<WebElement> lines = getAllLines();
		for (WebElement el : lines) {
			if (getLineName(el).startsWith(lineTitle)) {
				WebElementUtils.hoverOverField(el, driver, null);
				if (el.findElement(lineType).getText().contains("Model"))
					return true;
				return false;
			}
		}
		return false;
	}
	
	
	public void addNewline(){
		clickAddLineButton();
		clickAddSubLineButton();
		List<WebElement> lines = getSubLines();
		for (WebElement el : lines) {
			if ((el.getAttribute("data-level").equals("2") || el.getAttribute("data-level").equals("3")) && el.getAttribute("class").contains("new-line")) {
				
				try {
					if (el.findElement(addLineBtn).getAttribute("class").contains("enable") && WebdriverUtils.isVisible(el.findElement(addLineBtn))){
						el.findElement(addLineBtn).click();
						return;
					}
						
					
				} catch (Exception e) {
					continue;
				}
			}
		}
		
		
		
	}
	
	public void clickAddLineButton(){
		List<WebElement> lines = getLines();
		for (WebElement el : lines){
			if (WebdriverUtils.isDisplayed(el)){
				WebElementUtils.hoverOverField(el, driver, null);
				try{
					if(el.findElement(By.className("add-child-budget")).isDisplayed()){
						el.findElement(By.className("add-child-budget")).click();
						
					}
				}catch (NoSuchElementException e){
					continue;
				}
				
				
			}
			
		}
	}
	public void clickAddSubLineButton(){
		List<WebElement> lines = getSubLines();
		for (WebElement el : lines){
			if (WebdriverUtils.isDisplayed(el)){
				WebElementUtils.hoverOverField(el, driver, null);
				try{
					if(el.findElement(By.className("add-child-budget")).isDisplayed()){
						el.findElement(By.className("add-child-budget")).click();
						
					}
				}catch (NoSuchElementException e){
					continue;
				}
				
				
			}
			
		}
	}

	/*************************************************************************************************************/
	/*************************************************************************************************************/

	private WebElement getLineByName(String name) {
		List<WebElement> lines = getLines();
		for (WebElement el : lines) {
			System.out.println(getLineName(el));
			if (getLineName(el).startsWith(name))// if(getLineName(el).replaceAll("\\d","").trim().equals(name))
				return el;
		}
		return null;
	}

	private String getLineName(WebElement el) {
		try{
			if (el.getAttribute("class").contains("new-line"))
				return el.findElement(lineName).getText();
			return el.findElement(budgetName).getText();
		}
		catch(Exception e){
			return "";
		}
		// String accountId = "";
		// try {
		// accountId =
		// el.findElement(budgetName).findElement(By.className("account-id")).getText();
		// } catch (Exception e) {
		// }
		// int startIndex = accountId.length();
		// if (el.findElement(budgetName).getText().indexOf("\n") == -1)
		// return
		// el.findElement(budgetName).getText().substring(startIndex).trim();
		// return el.findElement(budgetName).getText().substring(startIndex,
		// el.findElement(budgetName).getText().indexOf("\n")).trim();
		//
	}

	private boolean isBudgetDropDownOptionsOpen() {
		try {
			return driver.findElement(By.className("qtip-focus")).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	private List<WebElement> getLines() {
		List<WebElement> list = new ArrayList<WebElement>();
		try {
			for (WebElement el : driver.findElement(By.cssSelector("ol.tree")).findElement(By.className("selected-root")).findElement(By.tagName("ol"))
					.findElements(line)) {
				if (el.getAttribute("data-level").equals("1"))
					list.add(el);
			}
		} catch (Exception e) {
		}
		return list;
	}
	
	private List<WebElement> getSubLines() {
		List<WebElement> list = new ArrayList<WebElement>();
		try {
			for (WebElement el : driver.findElement(By.cssSelector("ol.tree")).findElement(By.className("selected-root")).findElement(By.tagName("ol"))
					.findElements(line)) {
				if (el.getAttribute("data-level").equals("2") ||el.getAttribute("data-level").equals("3"))
					list.add(el);
			}
		} catch (Exception e) {
		}
		return list;
	}
	
	public Integer getNumberOfLines() {
		clickClose();
		List<WebElement> list = new ArrayList<WebElement>();
		try {
			for (WebElement el : driver.findElement(By.cssSelector("ol.tree")).findElement(By.className("selected-root")).findElement(By.tagName("ol"))
					.findElements(line)) {
				if ((el.getAttribute("data-level").equals("1") || el.getAttribute("data-level").equals("2") || el.getAttribute("data-level").equals("3") || el.getAttribute("data-level").equals("4")) && WebdriverUtils.isDisplayed(el))
					if(el.getAttribute("class").contains("collapsed")){
						collapsedTree.click();	
					}
					list.add(el);
			}
		} catch (Exception e) {
		}
		return list.size();
	}

	private List<WebElement> getAllLines() {
		return driver.findElements(By.cssSelector("ol.tree.nav")).get(1).findElement(By.className("selected-root")).findElement(By.tagName("ol"))
				.findElements(line);
	}

	private WebElement getNextLineToAdd() {
		List<WebElement> lines = getLines();
		for (WebElement el : lines) {
			if (el.getAttribute("data-level").equals("1") && el.getAttribute("class").contains("new-line")) {
				if (el.findElement(lineName).getText().contains("Model") && isLineExist("Model"))
					return null;
				try {
					if (el.findElement(addLineBtn).getAttribute("class").contains("enable") && WebdriverUtils.isVisible(el.findElement(addLineBtn)))
						return el;
				} catch (Exception e) {
					continue;
				}
			}
		}
		return null;
	}
	
	
	
	

	private List<WebElement> getSubLinesForLine(String lineTitle) {
		WebElement lineElm = getLineByName(lineTitle);
		if (lineElm.getAttribute("class").contains("collapsed")) {
			lineElm.findElement(By.cssSelector(".svg-icon")).click();
			WebdriverUtils.elementToHaveClass(lineElm, "expanded");
			WebdriverUtils.sleep(200);
		}
		return lineElm.findElements(line);
	}

	private List<WebElement> getSubLinesForSubLine(String lineTitle, String subLineTitle) {
		List<WebElement> sublines = getSubLinesForLine(lineTitle);
		for (WebElement el : sublines) {
			if (getLineName(el).equals(subLineTitle)) {
				if (el.getAttribute("class").contains("collapsed")) {
					el.findElement(By.cssSelector(".svg-icon.collapse-tree ")).click();
					WebdriverUtils.elementToHaveClass(el, "expanded");
					WebdriverUtils.sleep(200);
				}
				return el.findElements(line);
			}
		}
		return null;
	}

	private List<WebElement> getSubLinesFourthLevel(String lineTitle, String subLineTitle, String sub_subLine) {
		List<WebElement> sublines = getSubLinesForSubLine(lineTitle, subLineTitle);
		for (WebElement el : sublines) {
			if (getLineName(el).equals(sub_subLine)) {
				if (el.getAttribute("class").contains("collapsed")) {
					el.findElement(By.tagName("i")).click();
					WebdriverUtils.elementToHaveClass(el, "expanded");
					WebdriverUtils.sleep(200);
				}
				return el.findElements(line);
			}
		}
		return null;
	}

	private void selectDropDown(WebElement dropdown, String textToSelect) {
		trigger.selectBudgetMenuTrigger(textToSelect);
		//WebElementUtils.hoverOverField(lineName, driver, null);
	//	lineName.click();
		
		
//		wait.until(WebdriverUtils.elementToHaveAttribute(dropdown.findElement(By.xpath("./..")).findElement(By.xpath("./..")), "select2-dropdown-open"));
//		for (WebElement elm : driver.findElements(By.cssSelector("ul.select2-results li")))
//			if (elm.getText().equalsIgnoreCase(textToSelect)) {
//				elm.click();
//				break;
//			}
//		WebdriverUtils.sleep(1000);
	}

	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}
}
