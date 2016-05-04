package com.budgeta.pom;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebElementUtils;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class BudgetNavigator extends AbstractPOM {

	@FindBy(className = "budget-navigator-wrapper")
	private WebElement wrapper;

	@FindBy(css = "div.navigator-headers div.dashboard")
	private WebElement dashBoard;

	@FindBy(css = "div.navigator-headers div.inputs")
	private WebElement inputs;

	@FindBy(css = "div.navigator-headers div.sheets")
	private WebElement Sheets;

	@FindBy(css = "div.qtip-focus div.no-padding div.ember-view ul.budgeta-dropdown-list")
	private WebElement budgetsListWrapper;

	@FindBy(id = "selected-root-menu")
	private WebElement showBudgetsBtn;

	@FindBy(css = "div.qtip-content div.no-padding div.ember-view ul.budgeta-dropdown-list li")
	private List<WebElement> budgetsList;

	private By budgetSettingTriggerMenu = By
			.cssSelector("div.qtip-focus ul.budgeta-dropdown-list li");

	@FindBy(id = "more-budget-actions")
	private WebElement moreBtn;

	@FindBy(className = "share")
	private WebElement shareBtn;

	@FindBy(css = "div.budget-title a.active")
	private WebElement selectedBudget;

	@FindBy(id = "more-budget-actions")
	private WebElement budgetSetting;

	private final By budgetTitle = By.className("budget-title");

	@FindBy(css = "div.budget-title input.ember-text-field")
	private WebElement editBudgetName;

	@FindBy(css = "div.qtip-focus div.add-option")
	private WebElement addNewBudget;

	@FindBy(css = "div.qtip-focus div.search-wrapper input")
	private WebElement searchBudget;

	public BudgetNavigator() {
		// WebdriverUtils.waitForBudgetaBusyBar(driver);
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}

	public void openDashboardTab() {
		clickOnTab(dashBoard);
	}

	public void openInputTab() {
	try{
		clickOnTab(inputs);
		TopHeaderBar topBar = new TopHeaderBar();
		topBar.openBaseTab();
		
	}catch(NoSuchElementException e){
		selectRandomBudgeta();
		//driver.findElement(By.xpath("//body")).sendKeys(Keys.F5);
		driver.navigate().refresh();
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		clickOnTab(inputs);
		TopHeaderBar topBar = new TopHeaderBar();
		topBar.openBaseTab();
	}
		

	}

	public void openSheetTab() {
		clickOnTab(Sheets);
	}

	public String getOpenTab() {
		return wrapper.findElement(
				By.cssSelector(".navigator-header-text.active")).getText();
	}

	public void openSharePopup() {
		shareBtn.click();
		WebdriverUtils.waitForElementToBeFound(driver,
				By.className("modal-content"));

	}

	private void clickOnTab(WebElement el) {
		if (el.getAttribute("class").contains("active"))
			return;
		el.click();
		WebdriverUtils.elementToHaveClass(el, "active");
		//WebdriverUtils.waitForBudgetaBusyBar(driver);
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}

	public void openBudgetsList() {
		if (!WebdriverUtils.isDisplayed(budgetsListWrapper)) {
			showBudgetsBtn.click();
			wait.until(ExpectedConditions.visibilityOf(budgetsListWrapper));
			WebdriverUtils.sleep(100);
			WebElementUtils.hoverOverField(budgetsListWrapper, driver, null);
		}
	}

	public void closeBudgetsList() {
		if (WebdriverUtils.isDisplayed(budgetsListWrapper)) {
			showBudgetsBtn.click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By
					.cssSelector("div.qtip-focus div.no-padding div.ember-view ul.budgeta-dropdown-list")));
			WebdriverUtils.sleep(100);
			
		}
	}

	public void openMoreBudgetList() {
		moreBtn.click();
		try {
			WebdriverUtils.waitForElementToBeFound(driver,
					By.className("qtip-focus"));
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(budgetSettingTriggerMenu));
		} catch (Exception e) {
			WebElementUtils.clickElementEvent(driver, wrapper);
			WebdriverUtils.waitForElementToBeFound(driver,
					By.className("qtip-focus"));
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(budgetSettingTriggerMenu));
		}
	}

	// public void selectBudgetSettings(String option){
	// MenuTrigger navigatior = new MenuTrigger(moreBtn);
	// navigatior.selectBudgetMenuTrigger(option);
	// }

	public MenuTrigger getMenuTrigger() {
		return new MenuTrigger(moreBtn);
	}

	public boolean isBudgetExist(String budgetaName) {
		openBudgetsList();
		boolean found = false;
		for (WebElement budget : budgetsList) {
			if (budget.getText().equals(budgetaName)) {
				found = true;
				break;
			}
		}
		WebElementUtils.hoverOverField(inputs, driver, null);
		WebdriverUtils.sleep(300);
		return found;
	}

	public boolean isScenarioExist(String budgetaName) {
		boolean found = false;
		for (WebElement budget : budgetsList) {
			if (budget.getText().equals(budgetaName)) {
				found = true;
				break;
			}
		}
		WebElementUtils.hoverOverField(inputs, driver, null);
		WebdriverUtils.sleep(300);
		return found;
	}

	public boolean isShareIconExist(String budgetName) {
		openBudgetsList();
		for (WebElement budget : budgetsList) {
			if (budget.getText().equals(budgetName)) {
				budget.findElement(
						By.cssSelector("div.actions-toggle span.budget-name  div.svg-icon"))
						.isDisplayed();
				return true;

			}
		}

		return false;
	}

	public void selectBudgeta(String budgetaName) {
		openBudgetsList();
		for (WebElement budget : budgetsList) {
			if (budget.getText().equals(budgetaName)) {
				budget.click();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By
						.className("budgeta-dropdown-list")));
				WebdriverUtils.waitForElementToBeFound(driver,
						By.className("level-1"));
				break;
			}
		}
		WebdriverUtils
				.waitForElementToBeFound(driver, By.id("section-General"));
	}

	public void selectRandomBudgeta() {
		openBudgetsList();
		int random = WebElementUtils.getRandomNumberByRange(0,
				getNumbreOfExistBudgets() - 1), i = 0;
		while (budgetsList.get(random).findElement(By.xpath(".."))
				.getAttribute("class").contains("hidden")
				&& i < 5) {
			random = WebElementUtils.getRandomNumberByRange(0,
					getNumbreOfExistBudgets() - 1);
			i++;
		}
		WebElementUtils.hoverOverField(budgetsList.get(random), driver, null);
		budgetsList.get(random).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By
				.className("budget-list")));
		WebdriverUtils.waitForElementToBeFound(driver, By.className("level-1"));
		openInputTab();
		try {
			driver.findElement(By.className("highlight")).isDisplayed();
			selectRandomBudgeta();
		} catch (NoSuchElementException e) {

		}
	}

	public void selectRandomBudgetWithPrefix(String prefix) {
		openBudgetsList();
		List<WebElement> budgetsStartWithPrefix = new ArrayList<>();
		for (WebElement el : budgetsList) {
			if (el.getText().startsWith(prefix)) {
				budgetsStartWithPrefix.add(el);
			}
		}
		int random = WebElementUtils.getRandomNumberByRange(0,
				budgetsStartWithPrefix.size() - 1);
		WebElementUtils.hoverOverField(budgetsStartWithPrefix.get(random),
				driver, null);
		budgetsStartWithPrefix.get(random).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By
				.className("budget-list")));
		WebdriverUtils.waitForElementToBeFound(driver, By.className("level-1"));
	}

	public int getNumberOfBudgetsWithName(String name) {
		int sum = 0;
		openBudgetsList();
		for (WebElement el : budgetsList) {
			if (el.getText().equals(name)) {
				sum++;
			}
		}
		showBudgetsBtn.click();
		return sum;
	}

	public int getNumberOfBudget(String budgetaName) {
		openBudgetsList();
		searchBudget(budgetaName);
		int num = 0;
		for (WebElement budget : budgetsList) {
			System.out.println(budget.getText().trim());
			if (budget.getText().equals(budgetaName)) {
				num++;
			}
		}
		clearSearch();
		return num;
	}

	public int getNumbreOfExistBudgets() {
		openBudgetsList();
		return budgetsList.size();
	}

	public String getSelectedBudgetName() {
		// return getLineName(selectedBudget);
		return selectedBudget.getText();
	}

	private String getLineName(WebElement el) {
		return el.findElement(budgetTitle).getText();

	}

	public void setBudgetTitle(String value) {
		editBudgetName.clear();
		editBudgetName.sendKeys(value);
		editBudgetName.sendKeys(Keys.ENTER);

	}

	public NewBudgetPopup addNewBudget() {
		openBudgetsList();
		addNewBudget.click();
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		return new NewBudgetPopup();
	}

	public LimitPopup budgetLimit() {
		openBudgetsList();
		addNewBudget.click();
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		return new LimitPopup();
	}

	public void searchBudget(String budget) {
		WebdriverUtils.waitForElementToBeFound(driver,
				By.cssSelector("div.qtip-focus div.search-wrapper input"));
		// wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.qtip-focus div.search-wrapper input")));
		searchBudget.clear();
		searchBudget.sendKeys(budget);
	}

	public void clearSearch() {
		searchBudget.clear();
	}

	public boolean isInputTabDispaly() {

		try {
			return inputs.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	public boolean isSheetsTabDispaly() {

		try {
			return Sheets.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	public boolean isDashboardTabDispaly() {

		try {
			return dashBoard.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
