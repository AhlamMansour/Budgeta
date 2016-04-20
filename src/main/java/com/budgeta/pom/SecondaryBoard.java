package com.budgeta.pom;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	String Department = "departmen";
	String importBudgetName = "Example 3 for share";
	String selectRevenueLineType = "Perpetual Licenses";
	String selectCostOfRevenueLineType = "Expense";
	String selectOperatingExpensesLineType = "Expense";
	String selectOtherIncomeAndExpensesLineType = "Expense";
	ArrayList<String> revenuesLines = new ArrayList<>(Arrays.asList("Leads Revenue from Paid traffic", "Ads Revenue", "Closed Policies Revenue"));
	ArrayList<String> costOfRevenueLines = new ArrayList<>(Arrays.asList("Data Licensing Fee", "Data Maintenance Fee", "Data Feed Cost", "Insurance Licensing"));
	ArrayList<String> operatingExpensesLines = new ArrayList<>(Arrays.asList("CEO", "Director of Engineering", "Director of Product", "Director of Sales",
			"Data Scientist", "Front end Engineer", "Consultant", "Marketing Consultant", "Marketing VP", "Insurance Costs And Employee Taxes",
			"Content Writers", "(Content Writer)", "Licensing Consultant", "PR", "Development Costs "));
	ArrayList<String> otherIncomeAndExpensesLines = new ArrayList<>(Arrays.asList("Servers and Laptops", "Liability insurance", "Legal fees", "Rent",
			"Other G&A"));
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
		BuildCompanyBudgetPopup buildPopup = new BuildCompanyBudgetPopup();
		if (buildPopup.isDisplayed()) {
			while (!buildPopup.getConfirmButtontext().equals("Finish")) {
				if (buildPopup.getTilte().equals("Build your company budget")) {
					buildBudget();
				}
				if (buildPopup.getTilte().equals("Revenues")) {
					addRevenues();
				}

				if (buildPopup.getTilte().equals("Cost of Revenues")) {
					addCostOfRevenues();
				}

				if (buildPopup.getTilte().equals("Professional Services")) {
					addProfessionalServices();
				}

				if (buildPopup.getTilte().equals(Department)) {
					addDepartment();
				}

				if (buildPopup.getTilte().equals("Salary & wages")) {
					addSalaryAndWages();
				}

				if (buildPopup.getTilte().equals(Department + " / Professional services")) {
					addDepartmentAndProfessionalServices();
				}

				if (buildPopup.getTilte().equals("Professional Services - " + Department)) {
					addProfissionalServicesAndDepartment();
				}

				if (buildPopup.getTilte().equals("Past balances and transactions")) {
					addTransactionBalance();
				}

				if (buildPopup.getTilte().equals("Operational Expenses")) {
					addOperationalExpenses();
				}

				if (buildPopup.getTilte().equals("Operational Expenses / Salary & wages")) {
					addOperationalExpensesAndSalary();
				}

				if (buildPopup.getTilte().equals("Operational Expenses / Professional services")) {
					addOperationalExpensesAndProfessionalServices();
				}

				if (buildPopup.getTilte().contains("Operational Expenses / Past balances and transactions")) {
					addExpensesAndPastBlances();
				}

				if (buildPopup.getTilte().equals("Other income and expenses")) {
					addOtherIncomeAndExpenses();
				}

				if (buildPopup.getTilte().equals("Other income")) {
					addOtherIncome();
				}

			}

			if (buildPopup.getTilte().equals("All Done") || buildPopup.getConfirmButtontext().equals("Finish")) {
				buildPopup.clickConfirm();
				lineElm.findElement(addLinesBtn).click();
			}

		} else
			WebdriverUtils.waitForElementToBeFound(driver, By.className("tree-edit"));

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
				// /WebdriverUtils.waitForBudgetaBusyBar(driver);
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				WebdriverUtils.sleep(1500);
			}
		}
	}

	public void buildBudget() {
		BuildCompanyBudgetPopup buildPopup = new BuildCompanyBudgetPopup();
		buildPopup.slectOption("Yes", "No Grouping");
		try{
			//WebdriverUtils.waitForBudgetaBusyBar(driver);
			WebdriverUtils.waitForBudgetaLoadBar(driver);
			WebdriverUtils.waitForBudgetaLoadBar(driver);
		}
		catch(Exception e){}
		buildPopup = new BuildCompanyBudgetPopup();
		buildPopup.clickNext();
	}

	public void addRevenues() {
		BuildCompanyBudgetPopup buildPopup = new BuildCompanyBudgetPopup();
		// buildPopup.clickCreateBudget("Create");
		buildPopup.clickCreateBudget();
		buildPopup.clickAdd();
		buildPopup = new BuildCompanyBudgetPopup();
		buildPopup.setName("revenue");
		buildPopup.clickNext();
	}

	public Map<String, List<String>> importRevenueLines() {
		BuildCompanyBudgetPopup buildPopup = new BuildCompanyBudgetPopup();
		buildPopup.clickImportBudget();
		UploadImportFile upload = new UploadImportFile();
		upload.clickUpload(new File("").getAbsolutePath() + "/browserDownloads/" + importBudgetName + ".xlsx");
		ImportWinStep1 importSetup = new ImportWinStep1();
		importSetup.clickOnNext();

		ImportWinStep2 importData = new ImportWinStep2();
		// importData.selectAllRows();
		// importData.selectRandomLines();
		// importData.selectType(selectLineType);
		importData.selectSpecificLines(revenuesLines);
		importData.selectTypeForSelectedLines(selectRevenueLineType);
		importData.clickNext();

		ImportWinStep3 importcolumns = new ImportWinStep3();
		importcolumns.selectDateColumns();
		importcolumns.selectTotalColumn();
		importcolumns.changeDate();
		importcolumns.clickNext();
		// importcolumns.clickOnMessage();

		ImportWinStep4 importBudget = new ImportWinStep4();
		Map<String, List<String>> valuesBeforeImport = importBudget.getAllValues();

		importBudget.clickImport();
		return valuesBeforeImport;
	}

	public Map<String, List<String>> importCostOfRevenueLines() {
		BuildCompanyBudgetPopup buildPopup = new BuildCompanyBudgetPopup();
		buildPopup.clickImportBudget();
		UploadImportFile upload = new UploadImportFile();
		upload.clickUpload(new File("").getAbsolutePath() + "/browserDownloads/" + importBudgetName + ".xlsx");
		ImportWinStep1 importSetup = new ImportWinStep1();
		importSetup.clickOnNext();

		ImportWinStep2 importData = new ImportWinStep2();
		importData.selectSpecificLines(costOfRevenueLines);
		importData.selectTypeForSelectedLines(selectCostOfRevenueLineType);
		importData.clickNext();

		ImportWinStep3 importcolumns = new ImportWinStep3();
		importcolumns.selectDateColumns();
		importcolumns.selectTotalColumn();
		importcolumns.changeDate();
		importcolumns.clickNext();
		// importcolumns.clickOnMessage();

		ImportWinStep4 importBudget = new ImportWinStep4();
		Map<String, List<String>> valuesBeforeImport = importBudget.getAllValues();

		importBudget.clickImport();
		return valuesBeforeImport;

	}

	public Map<String, List<String>> importOperatingExpensesLines() {
		BuildCompanyBudgetPopup buildPopup = new BuildCompanyBudgetPopup();
		buildPopup.clickImportBudget();
		UploadImportFile upload = new UploadImportFile();
		upload.clickUpload(new File("").getAbsolutePath() + "/browserDownloads/" + importBudgetName + ".xlsx");
		ImportWinStep1 importSetup = new ImportWinStep1();
		importSetup.clickOnNext();

		ImportWinStep2 importData = new ImportWinStep2();
		importData.selectSpecificLines(operatingExpensesLines);
		importData.selectTypeForSelectedLines(selectOperatingExpensesLineType);
		importData.clickNext();

		ImportWinStep3 importcolumns = new ImportWinStep3();
		importcolumns.selectDateColumns();
		importcolumns.selectTotalColumn();
		importcolumns.changeDate();
		importcolumns.clickNext();
		// importcolumns.clickOnMessage();

		ImportWinStep4 importBudget = new ImportWinStep4();
		Map<String, List<String>> valuesBeforeImport = importBudget.getAllValues();

		importBudget.clickImport();
		return valuesBeforeImport;

	}

	public Map<String, List<String>> importOtherIncomeAndExpensesLines() {
		BuildCompanyBudgetPopup buildPopup = new BuildCompanyBudgetPopup();
		buildPopup.clickImportBudget();
		UploadImportFile upload = new UploadImportFile();
		upload.clickUpload(new File("").getAbsolutePath() + "/browserDownloads/" + importBudgetName + ".xlsx");
		ImportWinStep1 importSetup = new ImportWinStep1();
		importSetup.clickOnNext();

		ImportWinStep2 importData = new ImportWinStep2();
		importData.selectSpecificLines(otherIncomeAndExpensesLines);
		importData.selectTypeForSelectedLines(selectOtherIncomeAndExpensesLineType);
		importData.clickNext();

		ImportWinStep3 importcolumns = new ImportWinStep3();
		importcolumns.selectDateColumns();
		importcolumns.selectTotalColumn();
		importcolumns.changeDate();
		importcolumns.clickNext();
		// importcolumns.clickOnMessage();

		ImportWinStep4 importBudget = new ImportWinStep4();
		Map<String, List<String>> valuesBeforeImport = importBudget.getAllValues();

		importBudget.clickImport();
		return valuesBeforeImport;

	}

	public void addCostOfRevenues() {
		BuildCompanyBudgetPopup buildPopup = new BuildCompanyBudgetPopup();
		// buildPopup.clickCreateBudget("Create");
		buildPopup.clickCreateBudget();
		//buildPopup.slectOption("Yes", "");
		buildPopup.selectOption();
		buildPopup.clickAdd();
		buildPopup = new BuildCompanyBudgetPopup();
		buildPopup.setName("expense");
		buildPopup.clickMore();
		WebdriverUtils.sleep(1000);
		buildPopup.clickNext();
	}

	public void addProfessionalServices() {
		BuildCompanyBudgetPopup buildPopup = new BuildCompanyBudgetPopup();
		buildPopup.selectOption();
		buildPopup.clickAdd();
		buildPopup = new BuildCompanyBudgetPopup();
		buildPopup.setName(Department);
		buildPopup.clickNext();
	}

	public void addDepartment() {
		BuildCompanyBudgetPopup buildPopup = new BuildCompanyBudgetPopup();
		buildPopup.selectOption();
		// buildPopup.clickCreateBudget("Create");
		buildPopup.clickCreateBudget();
		buildPopup.selectAllcheckBoxes();

		buildPopup.clickAdd();
		buildPopup = new BuildCompanyBudgetPopup();
		buildPopup.setName("Expenses");
		buildPopup.clickMore();
		WebdriverUtils.sleep(1000);
		buildPopup.clickNext();

	}

	public void addSalaryAndWages() {
		BuildCompanyBudgetPopup buildPopup = new BuildCompanyBudgetPopup();
		// buildPopup.clickCreateBudget("Create");
		buildPopup.clickCreateBudget();
		buildPopup.clickAdd();
		buildPopup = new BuildCompanyBudgetPopup();
		buildPopup.setName("employee1");
		buildPopup.clickNext();

	}

	public void addDepartmentAndProfessionalServices() {
		BuildCompanyBudgetPopup buildPopup = new BuildCompanyBudgetPopup();
		// buildPopup.clickCreateBudget("Create");
		buildPopup.clickCreateBudget();
		buildPopup.clickAdd();
		buildPopup = new BuildCompanyBudgetPopup();
		buildPopup.setName("Contractor");
		buildPopup.clickNext();

	}

	public void addDepartmentAndSalaryWages() {
		BuildCompanyBudgetPopup buildPopup = new BuildCompanyBudgetPopup();
		WebdriverUtils.sleep(5000);
		// buildPopup.clickCreateBudget("Create");
		buildPopup.clickCreateBudget();
		buildPopup.clickAdd();
		buildPopup = new BuildCompanyBudgetPopup();
		buildPopup.setName("Empoloyee");
		buildPopup.clickNext();

	}

	public void addTransactionBalance() {
		BuildCompanyBudgetPopup buildPopup = new BuildCompanyBudgetPopup();
		// buildPopup.setLineNumber("1");
		buildPopup.clickCreateBudget();
		buildPopup.clickAdd();
		buildPopup = new BuildCompanyBudgetPopup();
		buildPopup.setName("Balance");
		buildPopup.clickNext();

	}

	public void addOperationalExpenses() {
		BuildCompanyBudgetPopup buildPopup = new BuildCompanyBudgetPopup();
		// buildPopup.clickCreateBudget("Create");
		buildPopup.clickCreateBudget();
	//	buildPopup.slectOption("Yes", "No Grouping");
		buildPopup.selectOption();
		buildPopup.selectAllcheckBoxes();
		buildPopup.clickAdd();
		buildPopup = new BuildCompanyBudgetPopup();
		buildPopup.setName("Expenses");
		buildPopup.clickMore();
		WebdriverUtils.sleep(1000);
		buildPopup.clickNext();

	}

	public void addOperationalExpensesAndSalary() {
		BuildCompanyBudgetPopup buildPopup = new BuildCompanyBudgetPopup();
		// buildPopup.clickCreateBudget("Create");
		buildPopup.clickCreateBudget();
		buildPopup.clickAdd();
		buildPopup = new BuildCompanyBudgetPopup();
		buildPopup.setName("employee");
		buildPopup.clickNext();
	}

	public void addOperationalExpensesAndProfessionalServices() {
		BuildCompanyBudgetPopup buildPopup = new BuildCompanyBudgetPopup();
		// buildPopup.clickCreateBudget("Create");
		buildPopup.clickCreateBudget();
		buildPopup.clickAdd();
		buildPopup = new BuildCompanyBudgetPopup();
		buildPopup.setName("contractor");
		buildPopup.clickNext();
	}

	public void addExpensesAndPastBlances() {
		BuildCompanyBudgetPopup buildPopup = new BuildCompanyBudgetPopup();
		// buildPopup.setLineNumber("1");
		buildPopup.clickCreateBudget();
		buildPopup.clickAdd();
		buildPopup = new BuildCompanyBudgetPopup();
		buildPopup.setName("Balance1");
		buildPopup.clickNext();
	}

	public void addOtherIncomeAndExpenses() {
		BuildCompanyBudgetPopup buildPopup = new BuildCompanyBudgetPopup();
		// buildPopup.clickCreateBudget("Create");
		buildPopup.clickCreateBudget();
		buildPopup.selectAllcheckBoxes();
		buildPopup.clickAdd();
		buildPopup = new BuildCompanyBudgetPopup();
		buildPopup.setName("expense");
		buildPopup.clickMore();
		WebdriverUtils.sleep(1000);
		buildPopup.clickNext();
	}

	public void addOtherExpenses() {
		BuildCompanyBudgetPopup buildPopup = new BuildCompanyBudgetPopup();
		// buildPopup.clickCreateBudget("Create");
		buildPopup.clickCreateBudget();
		buildPopup.clickAdd();
		buildPopup = new BuildCompanyBudgetPopup();
		buildPopup.setName("expense");
		buildPopup.clickNext();
	}

	public void addProfissionalServicesAndDepartment() {
		BuildCompanyBudgetPopup buildPopup = new BuildCompanyBudgetPopup();
		//buildPopup.slectOption("Yes", "No Grouping");
		buildPopup.selectOption();
		// buildPopup.clickCreateBudget("Create");
		buildPopup.clickCreateBudget();
		buildPopup.selectAllcheckBoxes();
		buildPopup.clickNext();
	}

	public void addOtherIncome() {
		BuildCompanyBudgetPopup buildPopup = new BuildCompanyBudgetPopup();
		// buildPopup.clickCreateBudget("Create");
		buildPopup.clickCreateBudget();
		buildPopup.clickAdd();
		buildPopup = new BuildCompanyBudgetPopup();
		buildPopup.setName("income");
		buildPopup.clickNext();
	}

	public void addAllBudgetLines() {
		if (!wrapper.getAttribute("class").contains("tree-edit")) {
			selectedBudget.findElement(addLinesBtn).click();
			BuildCompanyBudgetPopup buildPopup = new BuildCompanyBudgetPopup();
			if (buildPopup.isDisplayed()) {
				while (!buildPopup.getConfirmButtontext().equals("Finish")) {
					if (buildPopup.getTilte().contains("Build your")) {
						buildBudget();
					}
					if (buildPopup.getTilte().equalsIgnoreCase("Revenues")) {
						addRevenues();
					}

					if (buildPopup.getTilte().equalsIgnoreCase("Cost of Revenues")) {
						addCostOfRevenues();
					}

					if (buildPopup.getTilte().equalsIgnoreCase("Professional Services")) {
						addProfessionalServices();
						// addDepartmentAndProfessionalServices();
					}

					if (buildPopup.getTilte().equalsIgnoreCase("Department")) {
						addDepartment();
					}

					if (buildPopup.getTilte().equalsIgnoreCase("Salary & wages")) {
						addSalaryAndWages();
					}

					if (buildPopup.getTilte().equalsIgnoreCase(Department + " / Professional services")) {
						addDepartmentAndProfessionalServices();
					}

					if (buildPopup.getTilte().equalsIgnoreCase(Department + " - Salary & wages")) {
						addDepartmentAndSalaryWages();
					}

					if (buildPopup.getTilte().equalsIgnoreCase("Professional Services - " + Department)) {
						addProfissionalServicesAndDepartment();
					}

					if (buildPopup.getTilte().equalsIgnoreCase("Past balances and transactions")) {
						addTransactionBalance();
					}

					if (buildPopup.getTilte().equalsIgnoreCase("Operational Expenses")) {
						addOperationalExpenses();
					}

					if (buildPopup.getTilte().equalsIgnoreCase("Operational Expenses / Salary & wages")) {
						addOperationalExpensesAndSalary();
					}

					if (buildPopup.getTilte().equalsIgnoreCase("Operational Expenses - Salary & wages")) {
						addOperationalExpensesAndSalary();
					}

					if (buildPopup.getTilte().equalsIgnoreCase("Operational Expenses / Professional services")) {
						addOperationalExpensesAndProfessionalServices();
					}

					if (buildPopup.getTilte().contains("Operational Expenses / Past balances and transactions")) {
						addExpensesAndPastBlances();
					}

					if (buildPopup.getTilte().equalsIgnoreCase("Operational Expenses - Professional services")) {
						addOperationalExpensesAndProfessionalServices();
					}

					if (buildPopup.getTilte().contains("Operational Expenses - Past balances and transactions")) {
						addExpensesAndPastBlances();
					}

					if (buildPopup.getTilte().equalsIgnoreCase("Other income and expenses")) {
						addOtherIncomeAndExpenses();
					}

					if (buildPopup.getTilte().equalsIgnoreCase("Other income")) {
						addOtherIncome();
					}

					if (buildPopup.getTilte().equalsIgnoreCase("Other expenses")) {
						addOtherExpenses();
					}

				}

				if (buildPopup.getTilte().equalsIgnoreCase("All Done") || buildPopup.getConfirmButtontext().equalsIgnoreCase("Finish")) {
					buildPopup.clickConfirm();
					selectedBudget.findElement(addLinesBtn).click();
				}

			} else
				WebdriverUtils.waitForElementToBeFound(driver, By.className("tree-edit"));

		}
	}

	public Map<String, Map<String, List<String>>> importAllBudgetLines() {
		Map<String, Map<String, List<String>>> allMaps = new HashMap<String, Map<String, List<String>>>();
		if (!wrapper.getAttribute("class").contains("tree-edit")) {
			selectedBudget.findElement(addLinesBtn).click();
			BuildCompanyBudgetPopup buildPopup = new BuildCompanyBudgetPopup();
			if (buildPopup.isDisplayed()) {
				while (!buildPopup.getConfirmButtontext().equals("Finish")) {
					if (buildPopup.getTilte().contains("Build your")) {
						buildBudget();
					}
					if (buildPopup.getTilte().equalsIgnoreCase("Revenues")) {
						allMaps.put("Revenues", importRevenueLines());
					}

					if (buildPopup.getTilte().equalsIgnoreCase("Cost of Revenues")) {
						allMaps.put("Cost of Revenues", importCostOfRevenueLines());
						// importCostOfRevenueLines();
					}

					if (buildPopup.getTilte().equalsIgnoreCase("Professional Services")) {
						addProfessionalServices();
						// addDepartmentAndProfessionalServices();
					}

					if (buildPopup.getTilte().equalsIgnoreCase(Department)) {
						addDepartment();
					}

					if (buildPopup.getTilte().equalsIgnoreCase("Salary & wages")) {
						addSalaryAndWages();
					}

					if (buildPopup.getTilte().equalsIgnoreCase(Department + " / Professional services")) {
						addDepartmentAndProfessionalServices();
					}

					if (buildPopup.getTilte().equalsIgnoreCase(Department + " - Salary & wages")) {
						addDepartmentAndSalaryWages();
					}

					if (buildPopup.getTilte().equalsIgnoreCase("Professional Services - " + Department)) {
						addProfissionalServicesAndDepartment();
					}

					if (buildPopup.getTilte().equalsIgnoreCase("Past balances and transactions")) {
						addTransactionBalance();
					}

					if (buildPopup.getTilte().equalsIgnoreCase("Operational Expenses")) {
						allMaps.put("Operational Expenses", importOperatingExpensesLines());
						// importOperatingExpensesLines();
					}

					if (buildPopup.getTilte().equalsIgnoreCase("Operational Expenses / Salary & wages")) {
						addOperationalExpensesAndSalary();
					}

					if (buildPopup.getTilte().equalsIgnoreCase("Operational Expenses - Salary & wages")) {
						addOperationalExpensesAndSalary();
					}

					if (buildPopup.getTilte().equalsIgnoreCase("Operational Expenses / Professional services")) {
						addOperationalExpensesAndProfessionalServices();
					}

					if (buildPopup.getTilte().contains("Operational Expenses / Past balances and transactions")) {
						addExpensesAndPastBlances();
					}

					if (buildPopup.getTilte().equalsIgnoreCase("Operational Expenses - Professional services")) {
						addOperationalExpensesAndProfessionalServices();
					}

					if (buildPopup.getTilte().contains("Operational Expenses - Past balances and transactions")) {
						addExpensesAndPastBlances();
					}

					if (buildPopup.getTilte().equalsIgnoreCase("Other income and expenses")) {
						allMaps.put("Other income and expenses", importOtherIncomeAndExpensesLines());
						// importOtherIncomeAndExpensesLines();
					}

					if (buildPopup.getTilte().equalsIgnoreCase("Other income")) {
						addOtherIncome();
					}

					if (buildPopup.getTilte().equalsIgnoreCase("Other expenses")) {
						addOtherExpenses();
					}

				}

				if (buildPopup.getTilte().equalsIgnoreCase("All Done") || buildPopup.getConfirmButtontext().equalsIgnoreCase("Finish")) {
					buildPopup.clickConfirm();
				}

			} else
				WebdriverUtils.waitForElementToBeFound(driver, By.className("tree-edit"));

		}
		return allMaps;
	}

	public void addAllSubBudgetLines() {
		BuildCompanyBudgetPopup buildPopup = new BuildCompanyBudgetPopup();
		if (buildPopup.isDisplayed()) {
			while (!buildPopup.getConfirmButtontext().equals("Finish")) {
				if (buildPopup.getTilte().contains("Build your")) {
					buildBudget();
				}
				if (buildPopup.getTilte().equalsIgnoreCase("Revenues")) {
					addRevenues();
				}

				if (buildPopup.getTilte().equalsIgnoreCase("Cost of Revenues")) {
					addCostOfRevenues();
				}

				if (buildPopup.getTilte().equalsIgnoreCase("Professional Services")) {
					addProfessionalServices();
					// addDepartmentAndProfessionalServices();
				}

				if (buildPopup.getTilte().equalsIgnoreCase(Department)) {
					addDepartment();
				}

				if (buildPopup.getTilte().equalsIgnoreCase("Salary & wages")) {
					addSalaryAndWages();
				}

				if (buildPopup.getTilte().equalsIgnoreCase(Department + " / Professional services")) {
					addDepartmentAndProfessionalServices();
				}

				if (buildPopup.getTilte().equalsIgnoreCase(Department + " - Salary & wages")) {
					addDepartmentAndSalaryWages();
				}

				if (buildPopup.getTilte().equalsIgnoreCase("Professional Services - " + Department)) {
					addProfissionalServicesAndDepartment();
				}

				if (buildPopup.getTilte().equalsIgnoreCase("Past balances and transactions")) {
					addTransactionBalance();
				}

				if (buildPopup.getTilte().equalsIgnoreCase("Operational Expenses")) {
					addOperationalExpenses();
				}

				if (buildPopup.getTilte().equalsIgnoreCase("Operational Expenses / Salary & wages")) {
					addOperationalExpensesAndSalary();
				}

				if (buildPopup.getTilte().equalsIgnoreCase("Operational Expenses - Salary & wages")) {
					addOperationalExpensesAndSalary();
				}

				if (buildPopup.getTilte().equalsIgnoreCase("Operational Expenses / Professional services")) {
					addOperationalExpensesAndProfessionalServices();
				}

				if (buildPopup.getTilte().contains("Operational Expenses / Past balances and transactions")) {
					addExpensesAndPastBlances();
				}

				if (buildPopup.getTilte().equalsIgnoreCase("Operational Expenses - Professional services")) {
					addOperationalExpensesAndProfessionalServices();
				}

				if (buildPopup.getTilte().contains("Operational Expenses - Past balances and transactions")) {
					addExpensesAndPastBlances();
				}

				if (buildPopup.getTilte().equalsIgnoreCase("Other income and expenses")) {
					addOtherIncomeAndExpenses();
				}

				if (buildPopup.getTilte().equalsIgnoreCase("Other income")) {
					addOtherIncome();
				}

				if (buildPopup.getTilte().equalsIgnoreCase("Other expenses")) {
					addOtherExpenses();
				}

			}

			if (buildPopup.getTilte().equalsIgnoreCase("All Done") || buildPopup.getConfirmButtontext().equals("Finish")) {
				buildPopup.clickConfirm();
			}

		} else
			WebdriverUtils.waitForElementToBeFound(driver, By.className("tree-edit"));

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
					// WebdriverUtils.waitForBudgetaBusyBar(driver);
					WebdriverUtils.waitForBudgetaLoadBar(driver);
					return;
				}
			}
		}
	}

	public MenuTrigger getBudgetMenuTrigger() {
		WebElementUtils.hoverOverField(selectedBudget, driver, null);
		selectedBudget.click();
		WebdriverUtils.sleep(200);
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
				// WebdriverUtils.waitForBudgetaLoadBar(driver);
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
			// if
			// (el.findElement(By.className("budget-name-text-display")).getText().contains(subLineTitle))
			// {
			if (el.findElement(By.className("budget-name")).getText().contains(subLineTitle)) {
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
		// WebdriverUtils.waitForBudgetaBusyBar(driver);
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}

	public void clickOnSubLine(String lineName, String subLineName) {
		clickClose();
		List<WebElement> sublines = getSubLinesForLine(lineName);
		// List<WebElement> sublines = getSubLinesForSubLine(lineName,
		// subLineName);
		for (WebElement el : sublines) {
			WebElementUtils.hoverOverField(el, driver, null);
			if (el.getAttribute("class").contains("collapsed")) {
				el.findElement(By.cssSelector(".svg-icon")).click();
				WebdriverUtils.elementToHaveClass(el, "expanded");
				WebdriverUtils.sleep(200);
			}

			if (getLineName(el).equals(subLineName)) {
				el.findElement(budgetName).click();
				// WebdriverUtils.waitForBudgetaBusyBar(driver);
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
//			if (getLineName(el).contains(sub_subLine)) {
//				el.findElement(budgetName).click();
//				// WebdriverUtils.waitForBudgetaBusyBar(driver);
//				WebdriverUtils.waitForBudgetaLoadBar(driver);
//				return;
//			}
			if (getLineName(el).contains(sub_subLine)) {
				WebElementUtils.hoverOverField(el, driver, null);
				if(el.findElement(lineType).getText().contains("Employee")){
					el.findElement(budgetName).click();
					// WebdriverUtils.waitForBudgetaBusyBar(driver);
					WebdriverUtils.waitForBudgetaLoadBar(driver);
					return;
				}
				
			}
		}
	}
	
	public void clickOnIncomSubLine(String lineName, String subLineName, String sub_subLine) {
		if (WebdriverUtils.isDisplayed(closeBtn))
			clickClose();
		List<WebElement> sublines = getSubLinesForSubLine(lineName, subLineName);
		for (WebElement el : sublines) {
			if (getLineName(el).contains(sub_subLine)) {
				el.findElement(budgetName).click();
				// WebdriverUtils.waitForBudgetaBusyBar(driver);
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				return;
			}
			
				
			
		}
	}

	public void clickOnSubLine(String lineName, String subLineName, String sub_subLine, String nextLevelLine) {
		clickClose();
		List<WebElement> sublines = getSubLinesFourthLevel(lineName, subLineName, sub_subLine);
		for (WebElement el : sublines) {
			if (el.getAttribute("class").contains("collapsed")) {
				el.findElement(By.cssSelector(".svg-icon")).click();
				WebdriverUtils.elementToHaveClass(el, "expanded");
				WebdriverUtils.sleep(200);
			}
			if (getLineName(el).contains(nextLevelLine)) {
				el.findElement(budgetName).click();
				WebdriverUtils.elementToHaveClass(el, "active");
				// WebdriverUtils.waitForBudgetaBusyBar(driver);
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				return;
			}
		}
	}

	public BudgetSettings openBudgetSettings() {
		budgetSettings.click();
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
		// WebdriverUtils.waitForBudgetaBusyBar(driver);
		return new BudgetSettings();
	}

	public void addSubLineForLine(String _lineName, String subLineName) {
		List<WebElement> sublines = getSubLinesForLine(_lineName);
		for (WebElement el : sublines) {
			try {
				if (el.findElement(lineName).getText().equals(subLineName)) {
					el.findElement(addLineBtn).click();
					WebdriverUtils.sleep(300);
					// WebdriverUtils.waitForBudgetaBusyBar(driver);
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
					// WebdriverUtils.waitForBudgetaBusyBar(driver);
					WebdriverUtils.waitForBudgetaLoadBar(driver);
					return;
				}
			} catch (Exception e) {
			}
		}
	}

	public void openAddChild(String lineTitle, int level) {
		clickClose();
		// List<WebElement> allLinesInLevel =
		// driver.findElements(By.cssSelector("ol.tree.nav")).get(0).findElement(By.className("selected-root"))
		// .findElement(By.tagName("ol")).findElements(line);
		List<WebElement> allLinesInLevel = driver.findElements(line);
		for (WebElement el : allLinesInLevel) {
			if (getLineName(el).equals(lineTitle) && el.getAttribute("data-level").equals(level + "")) {
				WebElementUtils.hoverOverField(el, driver, null);
				WebdriverUtils.sleep(300);
				WebElementUtils.hoverOverField(el.findElement(addLinesBtn), driver, null);
				el.findElement(addLinesBtn).click();
				WebdriverUtils.sleep(600);
				// WebdriverUtils.waitForBudgetaBusyBar(driver);
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

	public void addNewline() {
		clickAddLineButton();
		clickAddSubLineButton();
		List<WebElement> lines = getSubLines();
		for (WebElement el : lines) {
			if ((el.getAttribute("data-level").equals("2") || el.getAttribute("data-level").equals("3")) && el.getAttribute("class").contains("new-line")) {

				try {
					if (el.findElement(addLineBtn).getAttribute("class").contains("enable") && WebdriverUtils.isVisible(el.findElement(addLineBtn))) {
						el.findElement(addLineBtn).click();
						return;
					}

				} catch (Exception e) {
					continue;
				}
			}
		}

	}

	public void clickAddLineButton() {
		List<WebElement> lines = getLines();
		for (WebElement el : lines) {
			if (WebdriverUtils.isDisplayed(el)) {
				WebElementUtils.hoverOverField(el, driver, null);
				try {
					if (el.findElement(By.className("add-child-budget")).isDisplayed()) {
						el.findElement(By.className("add-child-budget")).click();

					}
				} catch (NoSuchElementException e) {
					continue;
				}

			}

		}
	}

	public void clickAddSubLineButton() {
		List<WebElement> lines = getSubLines();
		for (WebElement el : lines) {
			if (WebdriverUtils.isDisplayed(el)) {
				WebElementUtils.hoverOverField(el, driver, null);
				try {
					if (el.findElement(By.className("add-child-budget")).isDisplayed()) {
						el.findElement(By.className("add-child-budget")).click();

					}
				} catch (NoSuchElementException e) {
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
		try {
			if (el.getAttribute("class").contains("new-line"))
				return el.findElement(lineName).getText();
			return el.findElement(budgetName).getText();
		} catch (Exception e) {
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
			// for (WebElement el :
			// driver.findElement(By.cssSelector("ol.tree")).findElement(By.className("selected-root")).findElement(By.tagName("ol"))
			// .findElements(line)) {
			for (WebElement el : driver.findElements(line)) {
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
				if (el.getAttribute("data-level").equals("2") || el.getAttribute("data-level").equals("3"))
					list.add(el);
			}
		} catch (Exception e) {
		}
		return list;
	}

	public int getNumberOfLines() {
		clickClose();
		List<WebElement> list = new ArrayList<WebElement>();
		try {
			for (WebElement el : driver.findElement(By.cssSelector("ol.tree")).findElement(By.className("selected-root")).findElement(By.tagName("ol"))
					.findElements(line)) {
				if ((el.getAttribute("data-level").equals("1") || el.getAttribute("data-level").equals("2") || el.getAttribute("data-level").equals("3") || el
						.getAttribute("data-level").equals("4")) && WebdriverUtils.isDisplayed(el))
					if (el.getAttribute("class").contains("collapsed")) {
						collapsedTree.click();
					}
				list.add(el);
			}
		} catch (Exception e) {
		}
		return list.size();
	}

	private List<WebElement> getAllLines() {
		// return
		// driver.findElements(By.cssSelector("ol.tree.nav")).get(1).findElement(By.className("selected-root")).findElement(By.tagName("ol"))
		// .findElements(line);
		for (WebElement el : driver.findElements(line)) {
			if (el.getAttribute("class").contains("collapsed")) {
				el.findElement(By.cssSelector(".svg-icon")).click();
				WebdriverUtils.elementToHaveClass(el, "expanded");
				WebdriverUtils.sleep(200);
			}
		}
		return driver.findElements(line);
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
		List<WebElement> subLines = new ArrayList<WebElement>();
		List<WebElement> lines = getAllLines();
		int dataLevel = -1;
		boolean startInsert = false;
		for (WebElement el : lines) {
			System.out.println(getLineName(el));
			if (getLineName(el).startsWith(lineTitle)) {// if(getLineName(el).replaceAll("\\d","").trim().equals(name))
				dataLevel = Integer.parseInt(el.getAttribute("data-level"));
				startInsert = true;
				continue;
			}
			if (startInsert) {
				int currentLevel = Integer.parseInt(el.getAttribute("data-level"));
				if (currentLevel == (dataLevel + 1)) {
					subLines.add(el);
				} else if (currentLevel <= dataLevel) {
					break;
				}
			}
		}
		return subLines;
		// WebElement lineElm = getLineByName(lineTitle);
		// if (lineElm.getAttribute("class").contains("collapsed")) {
		// lineElm.findElement(By.cssSelector(".svg-icon")).click();
		// WebdriverUtils.elementToHaveClass(lineElm, "expanded");
		// WebdriverUtils.sleep(200);
		// }
		// return lineElm.findElements(line);

	}

	private List<WebElement> getSubLinesForSubLine(String lineTitle, String subLineTitle) {
		// List<WebElement> sublines = getSubLinesForLine(lineTitle);
		// for (WebElement el : sublines) {
		// if (getLineName(el).equals(subLineTitle)) {
		// if (el.getAttribute("class").contains("collapsed")) {
		// el.findElement(By.cssSelector(".svg-icon.collapse-tree ")).click();
		// WebdriverUtils.elementToHaveClass(el, "expanded");
		// WebdriverUtils.sleep(200);
		// }
		// return el.findElements(line);
		// }
		// }
		// return null;
		List<WebElement> sub_subLines = new ArrayList<WebElement>();
//		List<WebElement> subLines = getSubLinesForLine(lineTitle);
		List<WebElement> lines = getAllLines();
		int dataLevel = -1;
		boolean startInsert = false;
		boolean lineFound = false;
		for (WebElement el : lines) {
			System.out.println(getLineName(el));
			if (getLineName(el).equals(lineTitle)){
				lineFound = true;
			}
			if(lineFound){
				System.out.println(getLineName(el));
				if (getLineName(el).equals(subLineTitle)) {// if(getLineName(el).replaceAll("\\d","").trim().equals(name))
						dataLevel = Integer.parseInt(el.getAttribute("data-level"));
						startInsert = true;
						continue;
					}
//				if (getLineName(el).contains(subLineTitle)) {// if(getLineName(el).replaceAll("\\d","").trim().equals(name))
//					if(el.findElement(lineType).getText().contains("Employee")){
//						dataLevel = Integer.parseInt(el.getAttribute("data-level"));
//						startInsert = true;
//						continue;
//					}
//					
//				}
			}
			if (startInsert) {
				int currentLevel = Integer.parseInt(el.getAttribute("data-level"));
				if (currentLevel == (dataLevel + 1)) {
					sub_subLines.add(el);
				} else if (currentLevel <= dataLevel) {
					break;
				}
			}
		}
		return sub_subLines;
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
		// WebElementUtils.hoverOverField(lineName, driver, null);
		// lineName.click();

		// wait.until(WebdriverUtils.elementToHaveAttribute(dropdown.findElement(By.xpath("./..")).findElement(By.xpath("./..")),
		// "select2-dropdown-open"));
		// for (WebElement elm :
		// driver.findElements(By.cssSelector("ul.select2-results li")))
		// if (elm.getText().equalsIgnoreCase(textToSelect)) {
		// elm.click();
		// break;
		// }
		// WebdriverUtils.sleep(1000);
	}

	public boolean isBudgetSettingIconDispaly() {

		try {
			return settingBudgetIcon.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	public boolean isBudgetLineSettingIconDispaly(String name) {
		WebElement line = getLineByName(name);
		WebElementUtils.hoverOverField(line, driver, null);
		Actions act = new Actions(driver);
		act.moveToElement(line).build().perform();
		try {
			return line.findElement(lineSetting).isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}
}
