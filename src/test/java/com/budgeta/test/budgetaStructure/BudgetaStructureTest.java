package com.budgeta.test.budgetaStructure;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.BillingsSection;
import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.BudgetSettings;
import com.budgeta.pom.BudgetaBoard;
import com.budgeta.pom.CommentsSection;
import com.budgeta.pom.DateRange;
import com.budgeta.pom.EmployeeAssumptions;
import com.budgeta.pom.EmplyeeSection;
import com.budgeta.pom.GeneralSection;
import com.budgeta.pom.MainSection;
import com.budgeta.pom.PreviewBoard;
import com.budgeta.pom.RevenuesAddSubLine;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.pom.TopHeaderBar;
import com.budgeta.test.BudgetaTest;
import com.budgeta.test.BudgetaUtils;
import com.budgeta.test.WrapperTest;
import com.budgeta.test.common.BudgetaCommon;
import com.galilsoftware.AF.core.listeners.DataProviderParams;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestFirst;
import com.galilsoftware.AF.core.listeners.TestNGListener;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

@Listeners({ MethodListener.class, TestNGListener.class })
public class BudgetaStructureTest extends WrapperTest {

	SecondaryBoard secondaryBoard;
	String revenuesSubLine = WebdriverUtils.getTimeStamp("revenues_");
	String revenues = "Revenues";
	String cost_of_revenues = "Cost of Revenues";
	String cost_of_revenues_subLine = "Professional Services";
	String OperationalExpenses = "Operational Expenses";
	String OperationalExpensesSubline = "Salary & wages";
	String salary_and_wages = "Salary & wages";
	String employee = "employee_";

	String OtherIncomeAndExpensesLine = "Other income and expenses";
	String OtherIncomeAndExpensesSubLine = "Other income";
	String OtherIncomeAndExpensesSub_SubLine = "Simple_";
	private TopHeaderBar topHeaderBar;

//	@BeforeMethod
//	private void initTest() {	
//		//((JavascriptExecutor)driver).executeScript("");
//		
//		driver.manage().window().maximize();
//	}

	@TestFirst
	@Test(enabled = true)
	public void setBudgetTest() {
		BudgetaCommon create = new BudgetaCommon();
		create.createBudget();
		secondaryBoard = board.getSecondaryBoard();
		//BudgetNavigator navigator = new BudgetNavigator();
		//navigator.selectRandomBudgeta();
		//navigator.selectRandomBudgetWithPrefix("aaaa");
		//navigator.openInputTab();
		
		secondaryBoard.addAllBudgetLines();
		secondaryBoard = new SecondaryBoard();
		secondaryBoard.addSubLine("Revenues");
		RevenuesAddSubLine subLine = new RevenuesAddSubLine();
		subLine.setName(revenuesSubLine);
		subLine.selectDropDown("Perpetual License");
		subLine.clickAdd();

		secondaryBoard = new SecondaryBoard();
		secondaryBoard.addSubLine(cost_of_revenues);
		secondaryBoard = new SecondaryBoard();
		secondaryBoard.addSubLineForLine(cost_of_revenues, cost_of_revenues_subLine);
		secondaryBoard = new SecondaryBoard();

		//secondaryBoard.addSubLineForSubLine(cost_of_revenues, cost_of_revenues_subLine, salary_and_wages);
		secondaryBoard.addSubLine(OperationalExpenses);
		secondaryBoard = new SecondaryBoard();
		secondaryBoard.openAddChild(OperationalExpensesSubline, 2);
		subLine = new RevenuesAddSubLine();
		employee = WebdriverUtils.getTimeStamp(employee);
		subLine.setName(employee);
		subLine.clickAdd();
		secondaryBoard.selectDropDownInLine("Select Grouping", "No Grouping");

		secondaryBoard = new SecondaryBoard();
		secondaryBoard.addSubLine(OtherIncomeAndExpensesLine);
		secondaryBoard = new SecondaryBoard();
		secondaryBoard.addSubLineForLine(OtherIncomeAndExpensesLine, OtherIncomeAndExpensesSubLine);
		secondaryBoard = new SecondaryBoard();
		// secondaryBoard.addSubLinrForSubLine(OtherIncomeAndExpensesLine,
		// OtherIncomeAndExpensesSubLine, OtherIncomeAndExpensesSub_SubLine);
		secondaryBoard.addSubLineForSubLine(OtherIncomeAndExpensesLine, OtherIncomeAndExpensesSubLine, OtherIncomeAndExpensesSub_SubLine);
		secondaryBoard = new SecondaryBoard();
		secondaryBoard.openAddChild(OtherIncomeAndExpensesSubLine, 2);
		subLine = new RevenuesAddSubLine();
		OtherIncomeAndExpensesSub_SubLine = WebdriverUtils.getTimeStamp(OtherIncomeAndExpensesSub_SubLine);
		subLine.setName(OtherIncomeAndExpensesSub_SubLine);
		subLine.clickAdd();

	}

	@Test(dataProvider = "ExcelFileLoader", enabled = true, priority = 1)
	@DataProviderParams(sheet = "BudgetaForm", area = "GeneralRevenues")
	public void GeneralRevenuesTest(Hashtable<String, String> data) {
		board = new BudgetaBoard();
		secondaryBoard = board.getSecondaryBoard();

		secondaryBoard.clickOnLine(revenues);
		GeneralSection general = new GeneralSection();

		Assert.assertTrue(general.isDisplayed(), "expected general section to be displayed");

		DateRange from = general.openDateRangeFrom();
		from.setYear(data.get("DateRange_from_year"));
		from.setMonth(data.get("DateRange_from_month"));

		//DateRange to = general.openDateRangeTo();
		from.setYear(data.get("DateRange_to_year"));
		from.setMonth(data.get("DateRange_to_month"));

		//general.selectCurrency(data.get("Currency"));

		general.setAccountNumberInRowByIndex(1, data.get("AccountNumber"));
		
		if (!data.get("Product").isEmpty())
		{
			TopHeaderBar topheader = new TopHeaderBar();
			topheader.openBudgetSettings();
			BudgetSettings settings = new BudgetSettings();
			boolean selectedProduct = settings.isProductFieldSelected();
			
			settings.clickCancel();
			
			if (selectedProduct)
			{
				general.setProduct(data.get("Product"));
			}
			
		}
		
		general.setNotes(data.get("Notes"));

		if (data.get("ShouldPass").equals("FALSE"))
			Assert.assertTrue(general.isGeneralHasError(), "expected to error in general section");
		else {
			board.clickSaveChanges();
			CommentsSection comments = new CommentsSection();
			Assert.assertTrue(comments.isDisplayed(), "expected comments section to be displayed");
			comments.setComments(data.get("Comments"));

			board = new BudgetaBoard();
			board.clickSaveChanges();

			general = new GeneralSection();
			Assert.assertEquals(general.getGeneralDateRangeFrom(),
					BudgetaTest.getDateByNumbersFormat(data.get("DateRange_from_month"), data.get("DateRange_from_year")));
			Assert.assertEquals(general.getGeneralDateRangeTo(), BudgetaTest.getDateByNumbersFormat(data.get("DateRange_to_month"), data.get("DateRange_to_year")));
			//Assert.assertEquals(general.getSelectedCurrency(), data.get("Currency"));
		}
	}

	@Test(dataProvider = "ExcelFileLoader", enabled = true, priority = 2)
	@DataProviderParams(sheet = "BudgetaForm", area = "Revenues Form_Details")
	public void revenuesFormDetailsTest(Hashtable<String, String> data) {
		board = new BudgetaBoard();
		secondaryBoard = board.getSecondaryBoard();
		secondaryBoard.clickOnLine("Revenues");
		GeneralSection general = new GeneralSection();
		String monthY = BudgetaUtils.getMonthWithIndex(Integer.parseInt(general.getGeneralDateRangeTo().split("/")[0]));
		String yearY = general.getGeneralDateRangeTo().split("/")[1];

		secondaryBoard.clickOnSubLine(revenues, revenuesSubLine);

		BillingsSection billings = new BillingsSection();
		Assert.assertTrue(billings.isDisplayed(), "expected billings section to be displayed");

		billings.selectRepeat(data.get("Repeat"));
		if (data.get("Repeat").equals("Once"))
			billings.selectOnDate(data.get("OnDate_Month"), data.get("OnDate_Year"));
		else
			billings.selectSpred(data.get("Spread"));
		if (data.get("Repeat").equals("Quarterly") || data.get("Repeat").equals("Yearly"))
			billings.selectAtDate(data.get("AtDate"));
		if (data.get("Spread").equals("Growth"))
			billings.setGrowth(data.get("GrowthPercent"));
		billings.setAmount(data.get("Amount"));
		billings.setPaymentAfter(data.get("PaymentAfter"));

		if (!data.get("SupportPercentage").isEmpty()) {
			billings.setSupportPercentage(data.get("SupportPercentage"));
			billings.setSupportPeriod(data.get("SupportPeriod"));
		} else {
			billings.setSupportPercentage("");
		}
		if (data.get("ShouldPass").equals("FALSE")) {
			
			DateRange from = general.openDateRangeFrom();
			if (!data.get("DateRange_from_year").isEmpty()) {
				from.setYear(data.get("DateRange_from_year"));
				from.setMonth(data.get("DateRange_from_month"));
			}
			//DateRange to = general.openDateRangeTo();
			if (!data.get("DateRange_to_year").isEmpty()) {
				from.setYear(data.get("DateRange_to_year"));
				from.setMonth(data.get("DateRange_to_month"));
			}
			
			Assert.assertTrue(billings.isBillingsHasError(), "expected to error in billings section");
		} else {

			general = new GeneralSection();
			Assert.assertTrue(general.isDisplayed(), "expected general section to be displayed");

			

			
			if (!data.get("AccountNumber").isEmpty())
			{
				TopHeaderBar topheader = new TopHeaderBar();
				topheader.openBudgetSettings();
				BudgetSettings settings = new BudgetSettings();
				boolean selectedProduct = settings.isAccountNumberFieldSelected();
				
				settings.clickCancel();
				
				if (selectedProduct)
				{
					general.setAccountNumberInRowByIndex(1, data.get("AccountNumber"));
				}
				
			}
			
			if (!data.get("Geography").isEmpty())
			{
				TopHeaderBar topheader = new TopHeaderBar();
				topheader.openBudgetSettings();
				BudgetSettings settings = new BudgetSettings();
				boolean selectedProduct = settings.isGeographyFieldSelected();
				
				settings.clickCancel();
				
				if (selectedProduct)
				{
					general.setGeography(data.get("Geography"));
				}
				
			}
			
			
			
			if (!data.get("Product").isEmpty())
			{
				TopHeaderBar topheader = new TopHeaderBar();
				topheader.openBudgetSettings();
				BudgetSettings settings = new BudgetSettings();
				boolean selectedProduct = settings.isProductFieldSelected();
				
				settings.clickCancel();
				
				if (selectedProduct)
				{
					general.setProduct(data.get("Product"));
				}
				
			}
			
			general.setNotes(data.get("Notes"));
			board.clickSaveChanges();
			board.clickSaveChanges();
			// CommentsSection comments = new CommentsSection();
			// Assert.assertTrue(comments.isDisplayed(),
			// "expected comments section to be displayed");
			// comments.setComments(data.get("Comments"));

			// start validation
			int payAfter, supportPercent, supportPeriod, growth;
			String monthX, yearX;
			if (data.get("PaymentAfter").isEmpty())
				payAfter = 0;
			else
				payAfter = Integer.parseInt(data.get("PaymentAfter"));

			if (data.get("SupportPercentage").isEmpty())
				supportPercent = 0;
			else
				supportPercent = Integer.parseInt(data.get("SupportPercentage"));

			if (data.get("SupportPeriod").isEmpty())
				supportPeriod = 0;
			else
				supportPeriod = Integer.parseInt(data.get("SupportPeriod"));
			if (data.get("GrowthPercent").isEmpty())
				growth = 0;
			else
				growth = Integer.parseInt(data.get("GrowthPercent"));

			monthX = BudgetaUtils.getMonthWithIndex(Integer.parseInt(general.getGeneralDateRangeFrom().split("/")[0]));
			yearX = general.getGeneralDateRangeFrom().split("/")[1];

			String toExactMonth, toExactYear;

			toExactMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(general.getGeneralDateRangeTo().split("/")[0]));
			toExactYear = general.getGeneralDateRangeTo().split("/")[1];

			board.clickSaveChanges();
			board.clickSaveChanges();
			topHeaderBar = new TopHeaderBar();
			if (data.get("Repeat").equals("Once")) {
				String[] expectedValues = BudgetaUtils.calculateValues_Once(monthX, yearX, monthY, yearY, data.get("OnDate_Month"), data.get("OnDate_Year"),
						Integer.parseInt(data.get("Amount")), payAfter, supportPercent, supportPeriod, toExactMonth, toExactYear);
				compareExpectedResults(expectedValues);
			}

			if (data.get("Repeat").equals("Monthly")) {
				String[] expectedValues = BudgetaUtils.calculateValues_Monthly(monthX, yearX, monthY, yearY, Integer.parseInt(data.get("Amount")), payAfter,
						supportPercent, supportPeriod, growth, toExactMonth, toExactYear);
				compareExpectedResults(expectedValues);
			}

			if (data.get("Repeat").equals("Quarterly")) {
				BudgetSettings settings = topHeaderBar.openBudgetSettings();
				String fiscal = settings.getSelectedFiscal().substring(0, 3).toUpperCase();
				settings.clickCancel();
				String[] expectedValues = BudgetaUtils.calculateValues_Quaterly(monthX, yearX, monthY, yearY, Integer.parseInt(data.get("Amount")), payAfter,
						growth, data.get("AtDate"), fiscal, toExactMonth, toExactYear);
				compareExpectedResults(expectedValues);
			}

			if (data.get("Repeat").equals("Yearly")) {
				BudgetSettings settings = topHeaderBar.openBudgetSettings();
				String fiscal = settings.getSelectedFiscal().substring(0, 3);
				settings.clickCancel();
				String[] expectedValues = BudgetaUtils.calculateValues_Yearly(monthX, yearX, monthY, yearY, Integer.parseInt(data.get("Amount")), payAfter,
						growth, data.get("AtDate"), fiscal, toExactMonth, toExactYear);
				compareExpectedResults(expectedValues);
			}

		}

	}

	@Test(dataProvider = "ExcelFileLoader", enabled = false, priority = 3)
	@DataProviderParams(sheet = "BudgetaForm", area = "CostOfSale")
	public void CostOfSaleTest(Hashtable<String, String> data) {
		board = new BudgetaBoard();
		secondaryBoard = board.getSecondaryBoard();

		secondaryBoard.clickOnLine(cost_of_revenues);
		GeneralSection general = new GeneralSection();

		Assert.assertTrue(general.isDisplayed(), "expected general section to be displayed");

		DateRange from = general.openDateRangeFrom();
		from.setYear(data.get("DateRange_from_year"));
		from.setMonth(data.get("DateRange_from_month"));

		//DateRange to = general.openDateRangeTo();
		from.setYear(data.get("DateRange_to_year"));
		from.setMonth(data.get("DateRange_to_month"));

	//	general.selectCurrency(data.get("Currency"));

	//	general.setDepartment(data.get("Departments"));
		if (!data.get("AccountNumber").isEmpty())
		{
			TopHeaderBar topheader = new TopHeaderBar();
			topheader.openBudgetSettings();
			BudgetSettings settings = new BudgetSettings();
			boolean selectedProduct = settings.isAccountNumberFieldSelected();
			
			settings.clickCancel();
			
			if (selectedProduct)
			{
				general.setAccountNumberInRowByIndex(1, data.get("AccountNumber"));
			}
			
		}
		
		if (!data.get("Geography").isEmpty())
		{
			TopHeaderBar topheader = new TopHeaderBar();
			topheader.openBudgetSettings();
			BudgetSettings settings = new BudgetSettings();
			boolean selectedProduct = settings.isGeographyFieldSelected();
			
			settings.clickCancel();
			
			if (selectedProduct)
			{
				general.setGeography(data.get("Geography"));
			}
			
		}
		
		
		
		if (!data.get("Product").isEmpty())
		{
			TopHeaderBar topheader = new TopHeaderBar();
			topheader.openBudgetSettings();
			BudgetSettings settings = new BudgetSettings();
			boolean selectedProduct = settings.isProductFieldSelected();
			
			settings.clickCancel();
			
			if (selectedProduct)
			{
				general.setProduct(data.get("Product"));
			}
			
		}
		general.setNotes(data.get("Notes"));

		if (data.get("ShouldPass").equals("FALSE"))
			Assert.assertTrue(general.isGeneralHasError(), "expected to error in general section");
		else {
			board.clickSaveChanges();
			CommentsSection comments = new CommentsSection();
			Assert.assertTrue(comments.isDisplayed(), "expected comments section to be displayed");
			comments.setComments(data.get("Comments"));

			board = new BudgetaBoard();
			board.clickSaveChanges();

			general = new GeneralSection();
			Assert.assertEquals(general.getGeneralDateRangeFrom(),
					BudgetaTest.getDateByNumbersFormat(data.get("DateRange_from_month"), data.get("DateRange_from_year")));
			Assert.assertEquals(general.getGeneralDateRangeTo(), BudgetaTest.getDateByNumbersFormat(data.get("DateRange_to_month"), data.get("DateRange_to_year")));
		//	Assert.assertEquals(general.getSelectedCurrency(), data.get("Currency"));
			//Assert.assertEquals(general.getDepartment(), data.get("Departments"));
			Assert.assertEquals(general.getGeography(), data.get("Geography"));
			Assert.assertEquals(general.getProduct(), data.get("Product"));

		}
	}

	@Test(dataProvider = "ExcelFileLoader", enabled = false, priority = 4)
	@DataProviderParams(sheet = "BudgetaForm", area = "CostOfSale_Salary&wages")
	public void CostOfSale_SalaryAndwagesTest(Hashtable<String, String> data) {
		board = new BudgetaBoard();
		secondaryBoard = board.getSecondaryBoard();
		secondaryBoard.clickOnSubLine(cost_of_revenues, cost_of_revenues_subLine, salary_and_wages);
		secondaryBoard = new SecondaryBoard();

		EmployeeAssumptions emplyeeAssumption = new EmployeeAssumptions();
		Assert.assertTrue(emplyeeAssumption.isDisplayed(), "expected employee assumption to be displayed");

		emplyeeAssumption.setGeography(data.get("Geography"));
		emplyeeAssumption.setBenefits(data.get("BenefitsPercentage"));
		emplyeeAssumption.selectPayment(data.get("Payment"));
		emplyeeAssumption.setYearlyVacatoinDays(data.get("YearlyVactaionDayes"));
		emplyeeAssumption.setAvgAccruedVacation(data.get("AvgAccruedVacation_Percentage"));
		emplyeeAssumption.setMaxAccruedVacation(data.get("MaxAccruedVacation_Percentage"));
		emplyeeAssumption.setYearlyIncrease(data.get("YearlyIncrease_Percentage"));

		GeneralSection general = new GeneralSection();
		Assert.assertTrue(general.isDisplayed(), "expected general section to be displayed");

		DateRange from = general.openDateRangeFrom();
		from.setYear(data.get("DateRange_from_year"));
		from.setMonth(data.get("DateRange_from_month"));

	//	DateRange to = general.openDateRangeTo();
		from.setYear(data.get("DateRange_to_year"));
		from.setMonth(data.get("DateRange_to_month"));

		if (!data.get("AccountNumber").isEmpty())
		{
			TopHeaderBar topheader = new TopHeaderBar();
			topheader.openBudgetSettings();
			BudgetSettings settings = new BudgetSettings();
			boolean selectedProduct = settings.isAccountNumberFieldSelected();
			
			settings.clickCancel();
			
			if (selectedProduct)
			{
				general.setAccountNumberInRowByIndex(1, data.get("AccountNumber"));
			}
			
		}
		
		if (!data.get("Geography").isEmpty())
		{
			TopHeaderBar topheader = new TopHeaderBar();
			topheader.openBudgetSettings();
			BudgetSettings settings = new BudgetSettings();
			boolean selectedProduct = settings.isGeographyFieldSelected();
			
			settings.clickCancel();
			
			if (selectedProduct)
			{
				general.setGeography(data.get("Geography"));
			}
			
		}
		
		
		
		if (!data.get("Product").isEmpty())
		{
			TopHeaderBar topheader = new TopHeaderBar();
			topheader.openBudgetSettings();
			BudgetSettings settings = new BudgetSettings();
			boolean selectedProduct = settings.isProductFieldSelected();
			
			settings.clickCancel();
			
			if (selectedProduct)
			{
				general.setProduct(data.get("Product"));
			}
			
		}
		general.setDepartment(data.get("Department"));
		general.setNotes(data.get("Notes"));

		board = new BudgetaBoard();
		board.clickSaveChanges();

		if (data.get("ShouldPass").equals("FALSE"))
			Assert.assertTrue(emplyeeAssumption.employeeAssumptionHasError() || general.isGeneralHasError(),
					"expected to error in employee assumption sectcion or in general section");
		else {

			general = new GeneralSection();
			Assert.assertEquals(general.getGeneralDateRangeFrom(),
					BudgetaTest.getDateByNumbersFormat(data.get("DateRange_from_month"), data.get("DateRange_from_year")));
			Assert.assertEquals(general.getGeneralDateRangeTo(), BudgetaTest.getDateByNumbersFormat(data.get("DateRange_to_month"), data.get("DateRange_to_year")));
			Assert.assertEquals(general.getDepartment(), data.get("Department"));
			Assert.assertEquals(general.getGeography(), data.get("Geography"));
			Assert.assertEquals(general.getProduct(), data.get("Product"));

			Assert.assertEquals(emplyeeAssumption.getGeography(), data.get("Geography"));
			Assert.assertEquals(emplyeeAssumption.getBenefits(), data.get("BenefitsPercentage"));
			Assert.assertEquals(emplyeeAssumption.getPayment(), data.get("Payment"));
			Assert.assertEquals(emplyeeAssumption.getYearlyVacatoinDays(), data.get("YearlyVactaionDayes"));
			Assert.assertEquals(emplyeeAssumption.getAvgAccruedVacation(), data.get("AvgAccruedVacation_Percentage"));
			Assert.assertEquals(emplyeeAssumption.getMaxAccruedVacation(), data.get("MaxAccruedVacation_Percentage"));
			Assert.assertEquals(emplyeeAssumption.getYearlyIncrease(), data.get("YearlyIncrease_Percentage"));

		}
	}

	@Test(dataProvider = "ExcelFileLoader", enabled = false, priority = 5)
	@DataProviderParams(sheet = "BudgetaForm", area = "CostOfSale_Salary&wages_EmployeeForm")
	public void employeeFormTest(Hashtable<String, String> data) {

		board = new BudgetaBoard();
		secondaryBoard = board.getSecondaryBoard();
		//secondaryBoard.clickOnSubLine(cost_of_revenues, cost_of_revenues_subLine, salary_and_wages, employee);
		//secondaryBoard.clickOnSubLine(OperationalExpenses, OperationalExpensesSubline);
		secondaryBoard.clickOnSubLine(OperationalExpenses, OperationalExpensesSubline, employee);
		secondaryBoard = new SecondaryBoard();

		MainSection mainSection = new MainSection();
		Assert.assertTrue(mainSection.isDisplayed(), "expected to main section to be displayed");

		EmplyeeSection employeeSection = mainSection.getEmplyeeSection();

		employeeSection.hoverToUp();
		employeeSection.setRole(data.get("Role"));
		employeeSection.setEmployeeId(data.get("EmployeeId"));
		employeeSection.setBaseSalary(data.get("BaseSalary"));
		employeeSection.selectCurrency(data.get("Currency"));
		employeeSection.selectTerm(data.get("Term"));
		employeeSection.setBonus(data.get("BonusPercentage"));
		employeeSection.setBenefit(data.get("BenefitsPercentage"));

		EmployeeAssumptions emplyeeAssumption = new EmployeeAssumptions();

		emplyeeAssumption.selectPayment(data.get("Payment"));
		emplyeeAssumption.setYearlyVacatoinDays(data.get("YearlyVactaionDays"));
		emplyeeAssumption.setAvgAccruedVacation(data.get("AvgAccruedVactaion_Percentage"));
		emplyeeAssumption.setMaxAccruedVacation(data.get("MaxAccruedVactaionDays"));
		emplyeeAssumption.setYearlyIncrease(data.get("YeralyIncreasePercentage"));

		GeneralSection general = new GeneralSection();
		Assert.assertTrue(general.isDisplayed(), "expected general section to be displayed");
		if (!data.get("HireDate_from_year").isEmpty()) {
			DateRange from = general.openHireDateFrom();
			from.setHireYear(data.get("HireDate_from_year"));
			from.setHireMonth(data.get("HireDate_from_month"));
		}
		if (!data.get("HireDate_to_year").isEmpty()) {
			DateRange to = general.openHireDateTo();
			to.setHireYear(data.get("HireDate_to_year"));
			to.setHireMonth(data.get("HireDate_to_month"));
		}

		general.hoverToNote();
		
		general.setDepartment(data.get("Department"));
		if (!data.get("AccountNumber").isEmpty())
		{
			TopHeaderBar topheader = new TopHeaderBar();
			topheader.openBudgetSettings();
			BudgetSettings settings = new BudgetSettings();
			boolean selectedProduct = settings.isAccountNumberFieldSelected();
			
			settings.clickCancel();
			
			if (selectedProduct)
			{
				general.setAccountNumberInRowByIndex(1, data.get("AccountNumber"));
			}
			
		}
		
		if (!data.get("Geography").isEmpty())
		{
			TopHeaderBar topheader = new TopHeaderBar();
			topheader.openBudgetSettings();
			BudgetSettings settings = new BudgetSettings();
			boolean selectedProduct = settings.isGeographyFieldSelected();
			
			settings.clickCancel();
			
			if (selectedProduct)
			{
				general.setGeography(data.get("Geography"));
			}
			
		}
		
		
		
		if (!data.get("Product").isEmpty())
		{
			TopHeaderBar topheader = new TopHeaderBar();
			topheader.openBudgetSettings();
			BudgetSettings settings = new BudgetSettings();
			boolean selectedProduct = settings.isProductFieldSelected();
			
			settings.clickCancel();
			
			if (selectedProduct)
			{
				general.setProduct(data.get("Product"));
			}
			
		}
		general.setNotes(data.get("Notes"));

		board = new BudgetaBoard();
		board.clickSaveChanges();

		if (data.get("ShouldPass").equals("FALSE"))
			Assert.assertTrue(mainSection.isMainSectionHasError() || general.isGeneralHasError(),
					"expected to error in employee assumption sectcion or in general section");
		else {
			board.clickSaveChanges();
			secondaryBoard.clickOnSubLine(cost_of_revenues, cost_of_revenues_subLine, salary_and_wages);
			general = new GeneralSection();
			String dateFrom = general.getDateRangeFrom();
			String dateTo = general.getDateRangeTo();
			String yearFrom = dateFrom.split("/")[1];
			String yearTo = dateTo.split("/")[1];
			secondaryBoard.clickOnSubLine(cost_of_revenues, cost_of_revenues_subLine, salary_and_wages, employee);

			int baseSalary = 0, benefits = 0, bonus = 0, AvgAccruedVacation = 0, yearlyVacationDays = 0 ;
			String monthFrom = BudgetaUtils.getMonthWithIndex(Integer.parseInt(dateFrom.split("/")[0]));
			String monthTo = BudgetaUtils.getMonthWithIndex(Integer.parseInt(dateTo.split("/")[0]));
			String payment = data.get("Payment");
			if (payment.isEmpty())
				payment = "Same month";
			general = new GeneralSection();
			String hireDate = general.getDateRangeFrom(), endDate = general.getDateRangeTo(), hireMonth, endMonth, hireYear, endYear;
			if (general.getDateRangeFrom().equals("MM/YYYY")) {
				hireDate = dateFrom;
				hireMonth = monthFrom;
				hireYear = yearFrom;
			} else {
				hireMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(hireDate.split("/")[0]));
				hireYear = hireDate.split("/")[1];
			}

			if (general.getDateRangeTo().equals("MM/YYYY")) {
				endDate = dateTo;
				endMonth = monthTo;
				endYear = yearTo;
			} else {
				endMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(endDate.split("/")[0]));
				endYear = endDate.split("/")[1];
			}
			if (!employeeSection.getBaseSalaryField().isEmpty())
				baseSalary = Integer.parseInt(employeeSection.getBaseSalaryField());
			if (!employeeSection.getBenefits().isEmpty())
				benefits = Integer.parseInt(employeeSection.getBenefits());
			if (!employeeSection.getBonus().isEmpty())
				bonus = Integer.parseInt(employeeSection.getBonus());
			
			if(!employeeSection.getAvgAccuredVacation().isEmpty())
				AvgAccruedVacation = Integer.parseInt(employeeSection.getAvgAccuredVacation());
			if(!employeeSection.getYearlyVacationDays().isEmpty())
				yearlyVacationDays = Integer.parseInt(employeeSection.getYearlyVacationDays());

			if (employeeSection.getSelecredTerm().equals("Monthly")) {
				String[] expectedValues = BudgetaUtils.calculateEmployeeValues_Monthly(monthFrom, yearFrom, monthTo, yearTo, hireMonth, hireYear, endMonth,
						endYear, baseSalary, benefits, bonus, payment, yearlyVacationDays, AvgAccruedVacation);
				compareExpectedResults(expectedValues);
			}
			if (employeeSection.getSelecredTerm().equals("Yearly")) {
				String[] expectedValues = BudgetaUtils.calculateEmployeeValues_Yearly(monthFrom, yearFrom, monthTo, yearTo, hireMonth, hireYear, endMonth,
						endYear, baseSalary, benefits, bonus, payment);
				compareExpectedResults(expectedValues);
			}

		}

	}

	@Test(dataProvider = "ExcelFileLoader", enabled = false, priority = 6)
	@DataProviderParams(sheet = "BudgetaForm", area = "GrossProfit")
	public void fillGeneralAndValidate(Hashtable<String, String> data) {
		board = new BudgetaBoard();
		secondaryBoard = board.getSecondaryBoard();

		secondaryBoard.clickOnLine("Gross Profit");
		GeneralSection general = new GeneralSection();

		Assert.assertTrue(general.isDisplayed(), "expected general section to be displayed");

		String year = data.get("DateRange_from_year");
		String month = data.get("DateRange_from_month");

		DateRange from = general.openDateRangeFrom();
		from.setYear(year);
		from.setMonth(month);

		DateRange to = general.openDateRangeTo();
		to.setYear(data.get("DateRange_to_year"));
		to.setMonth(data.get("DateRange_to_month"));

		//general.selectCurrency(data.get("Currency"));

		if (!data.get("AccountNumber").isEmpty())
		{
			TopHeaderBar topheader = new TopHeaderBar();
			topheader.openBudgetSettings();
			BudgetSettings settings = new BudgetSettings();
			boolean selectedProduct = settings.isAccountNumberFieldSelected();
			
			settings.clickCancel();
			
			if (selectedProduct)
			{
				general.setAccountNumberInRowByIndex(1, data.get("AccountNumber"));
			}
			
		}
		
		
		
		
		if (!data.get("Product").isEmpty())
		{
			TopHeaderBar topheader = new TopHeaderBar();
			topheader.openBudgetSettings();
			BudgetSettings settings = new BudgetSettings();
			boolean selectedProduct = settings.isProductFieldSelected();
			
			settings.clickCancel();
			
			if (selectedProduct)
			{
				general.setProduct(data.get("Product"));
			}
			
		}
		general.setNotes(data.get("Notes"));

		if (data.get("ShouldPass").equals("FALSE"))
			Assert.assertTrue(general.isGeneralHasError(), "expected to error in general section");
		else {
			board.clickSaveChanges();
			CommentsSection comments = new CommentsSection();
			Assert.assertTrue(comments.isDisplayed(), "expected comments section to be displayed");
			comments.setComments(data.get("Comments"));

			board = new BudgetaBoard();
			board.clickSaveChanges();

			general = new GeneralSection();
			Assert.assertEquals(general.getGeneralDateRangeFrom(),
					BudgetaTest.getDateByNumbersFormat(data.get("DateRange_from_month"), data.get("DateRange_from_year")));
			Assert.assertEquals(general.getGeneralDateRangeTo(), BudgetaTest.getDateByNumbersFormat(data.get("DateRange_to_month"), data.get("DateRange_to_year")));
		//	Assert.assertEquals(general.getSelectedCurrency(), data.get("Currency"));
		}
	}

	@Test(dataProvider = "ExcelFileLoader", enabled = false, priority = 7)
	@DataProviderParams(sheet = "BudgetaForm", area = "NetIncome")
	public void NetIncomTest(Hashtable<String, String> data) {

		board = new BudgetaBoard();
		secondaryBoard = board.getSecondaryBoard();

		secondaryBoard.clickOnLine("Net income");
		GeneralSection general = new GeneralSection();

		Assert.assertTrue(general.isDisplayed(), "expected general section to be displayed");

		DateRange from = general.openDateRangeFrom();
		from.setYear(data.get("DateRange_from_year"));
		from.setMonth(data.get("DateRange_from_month"));

		DateRange to = general.openDateRangeTo();
		to.setYear(data.get("DateRange_to_year"));
		to.setMonth(data.get("DateRange_to_month"));

		//general.selectCurrency(data.get("Currency"));

		if (!data.get("AccountNumber").isEmpty())
		{
			TopHeaderBar topheader = new TopHeaderBar();
			topheader.openBudgetSettings();
			BudgetSettings settings = new BudgetSettings();
			boolean selectedProduct = settings.isAccountNumberFieldSelected();
			
			settings.clickCancel();
			
			if (selectedProduct)
			{
				general.setAccountNumberInRowByIndex(1, data.get("AccountNumber"));
			}
			
		}
		
		if (!data.get("Geography").isEmpty())
		{
			TopHeaderBar topheader = new TopHeaderBar();
			topheader.openBudgetSettings();
			BudgetSettings settings = new BudgetSettings();
			boolean selectedProduct = settings.isGeographyFieldSelected();
			
			settings.clickCancel();
			
			if (selectedProduct)
			{
				general.setGeography(data.get("Geography"));
			}
			
		}
		
		
		
		if (!data.get("Product").isEmpty())
		{
			TopHeaderBar topheader = new TopHeaderBar();
			topheader.openBudgetSettings();
			BudgetSettings settings = new BudgetSettings();
			boolean selectedProduct = settings.isProductFieldSelected();
			
			settings.clickCancel();
			
			if (selectedProduct)
			{
				general.setProduct(data.get("Product"));
			}
			
		}
		general.setNotes(data.get("Notes"));

		if (data.get("ShouldPass").equals("FALSE"))
			Assert.assertTrue(general.isGeneralHasError(), "expected to error in general section");
		else {
			board.clickSaveChanges();
			CommentsSection comments = new CommentsSection();
			Assert.assertTrue(comments.isDisplayed(), "expected comments section to be displayed");
			comments.setComments(data.get("Comments"));

			board = new BudgetaBoard();
			board.clickSaveChanges();

			general = new GeneralSection();
			Assert.assertEquals(general.getGeneralDateRangeFrom(),
					BudgetaTest.getDateByNumbersFormat(data.get("DateRange_from_month"), data.get("DateRange_from_year")));
			Assert.assertEquals(general.getGeneralDateRangeTo(), BudgetaTest.getDateByNumbersFormat(data.get("DateRange_to_month"), data.get("DateRange_to_year")));
			//Assert.assertEquals(general.getSelectedCurrency(), data.get("Currency"));
		}

	}

	@Test(dataProvider = "ExcelFileLoader", enabled = false, priority = 8)
	@DataProviderParams(sheet = "BudgetaForm", area = "Operating profit/loss")
	public void OperatingProfitLossTest(Hashtable<String, String> data) {

		board = new BudgetaBoard();
		secondaryBoard = board.getSecondaryBoard();

		secondaryBoard.clickOnLine("Operating profit/loss");
		GeneralSection general = new GeneralSection();

		Assert.assertTrue(general.isDisplayed(), "expected general section to be displayed");

		DateRange from = general.openDateRangeFrom();
		from.setYear(data.get("DateRange_from_year"));
		from.setMonth(data.get("DateRange_from_month"));

		DateRange to = general.openDateRangeTo();
		to.setYear(data.get("DateRange_to_year"));
		to.setMonth(data.get("DateRange_to_month"));

	//	general.selectCurrency(data.get("Currency"));

		if (!data.get("AccountNumber").isEmpty())
		{
			TopHeaderBar topheader = new TopHeaderBar();
			topheader.openBudgetSettings();
			BudgetSettings settings = new BudgetSettings();
			boolean selectedProduct = settings.isAccountNumberFieldSelected();
			
			settings.clickCancel();
			
			if (selectedProduct)
			{
				general.setAccountNumberInRowByIndex(1, data.get("AccountNumber"));
			}
			
		}
		
		if (!data.get("Geography").isEmpty())
		{
			TopHeaderBar topheader = new TopHeaderBar();
			topheader.openBudgetSettings();
			BudgetSettings settings = new BudgetSettings();
			boolean selectedProduct = settings.isGeographyFieldSelected();
			
			settings.clickCancel();
			
			if (selectedProduct)
			{
				general.setGeography(data.get("Geography"));
			}
			
		}
		
		
		
		if (!data.get("Product").isEmpty())
		{
			TopHeaderBar topheader = new TopHeaderBar();
			topheader.openBudgetSettings();
			BudgetSettings settings = new BudgetSettings();
			boolean selectedProduct = settings.isProductFieldSelected();
			
			settings.clickCancel();
			
			if (selectedProduct)
			{
				general.setProduct(data.get("Product"));
			}
			
		}
		
		general.setNotes(data.get("Notes"));

		if (data.get("ShouldPass").equals("FALSE"))
			Assert.assertTrue(general.isGeneralHasError(), "expected to error in general section");
		else {
			board.clickSaveChanges();
			CommentsSection comments = new CommentsSection();
			Assert.assertTrue(comments.isDisplayed(), "expected comments section to be displayed");
			comments.setComments(data.get("Comments"));

			board = new BudgetaBoard();
			board.clickSaveChanges();

			general = new GeneralSection();
			Assert.assertEquals(general.getGeneralDateRangeFrom(),
					BudgetaTest.getDateByNumbersFormat(data.get("DateRange_from_month"), data.get("DateRange_from_year")));
			Assert.assertEquals(general.getGeneralDateRangeTo(), BudgetaTest.getDateByNumbersFormat(data.get("DateRange_to_month"), data.get("DateRange_to_year")));
			//Assert.assertEquals(general.getSelectedCurrency(), data.get("Currency"));
		}

	}

	@Test(dataProvider = "ExcelFileLoader", enabled = false, priority = 9)
	@DataProviderParams(sheet = "BudgetaForm", area = "OtherIncomeAndExpenses")
	public void OtherIncomeAndExpensesTest(Hashtable<String, String> data) {

		board = new BudgetaBoard();
		secondaryBoard = board.getSecondaryBoard();

		secondaryBoard.clickOnLine("Other income and expenses");
		GeneralSection general = new GeneralSection();

		Assert.assertTrue(general.isDisplayed(), "expected general section to be displayed");

		DateRange from = general.openDateRangeFrom();
		from.setYear(data.get("DateRange_from_year"));
		from.setMonth(data.get("DateRange_from_month"));

		//DateRange to = general.openDateRangeTo();
		from.setYear(data.get("DateRange_to_year"));
		from.setMonth(data.get("DateRange_to_month"));

	//	general.selectCurrency(data.get("Currency"));

		if (!data.get("AccountNumber").isEmpty())
		{
			TopHeaderBar topheader = new TopHeaderBar();
			topheader.openBudgetSettings();
			BudgetSettings settings = new BudgetSettings();
			boolean selectedProduct = settings.isAccountNumberFieldSelected();
			
			settings.clickCancel();
			
			if (selectedProduct)
			{
				general.setAccountNumberInRowByIndex(1, data.get("AccountNumber"));
			}
			
		}
		
		if (!data.get("Geography").isEmpty())
		{
			TopHeaderBar topheader = new TopHeaderBar();
			topheader.openBudgetSettings();
			BudgetSettings settings = new BudgetSettings();
			boolean selectedProduct = settings.isGeographyFieldSelected();
			
			settings.clickCancel();
			
			if (selectedProduct)
			{
				general.setGeography(data.get("Geography"));
			}
			
		}
		
		
		
		if (!data.get("Product").isEmpty())
		{
			TopHeaderBar topheader = new TopHeaderBar();
			topheader.openBudgetSettings();
			BudgetSettings settings = new BudgetSettings();
			boolean selectedProduct = settings.isProductFieldSelected();
			
			settings.clickCancel();
			
			if (selectedProduct)
			{
				general.setProduct(data.get("Product"));
			}
			
		}
		
		general.setNotes(data.get("Notes"));

		if (data.get("ShouldPass").equals("FALSE"))
			Assert.assertTrue(general.isGeneralHasError(), "expected to error in general section");
		else {
			board.clickSaveChanges();
			CommentsSection comments = new CommentsSection();
			Assert.assertTrue(comments.isDisplayed(), "expected comments section to be displayed");
			comments.setComments(data.get("Comments"));

			board = new BudgetaBoard();
			board.clickSaveChanges();

			general = new GeneralSection();
			Assert.assertEquals(general.getGeneralDateRangeFrom(),
					BudgetaTest.getDateByNumbersFormat(data.get("DateRange_from_month"), data.get("DateRange_from_year")));
			Assert.assertEquals(general.getGeneralDateRangeTo(), BudgetaTest.getDateByNumbersFormat(data.get("DateRange_to_month"), data.get("DateRange_to_year")));
			//Assert.assertEquals(general.getSelectedCurrency(), data.get("Currency"));
		}

	}

	@Test(dataProvider = "ExcelFileLoader", enabled = false, priority = 10)
	@DataProviderParams(sheet = "BudgetaForm", area = "OtherIncomeAndExpenses_SubLines")
	public void OtherIncomeAndExpenses_OtherIncome(Hashtable<String, String> data) {
		board = new BudgetaBoard();
		secondaryBoard = board.getSecondaryBoard();
		secondaryBoard.clickOnSubLine(OtherIncomeAndExpensesLine, OtherIncomeAndExpensesSubLine);

		GeneralSection general = new GeneralSection();
		String monthY = BudgetaUtils.getMonthWithIndex(Integer.parseInt(general.getGeneralDateRangeTo().split("/")[0]));
		String yearY = general.getGeneralDateRangeTo().split("/")[1];

		secondaryBoard.clickOnSubLine(OtherIncomeAndExpensesLine, OtherIncomeAndExpensesSubLine, OtherIncomeAndExpensesSub_SubLine);
		secondaryBoard = new SecondaryBoard();

		BillingsSection billings = new BillingsSection();
		Assert.assertTrue(billings.isDisplayed(), "expected billings section to be displayed");

		billings.selectRepeat(data.get("Occurs"));
		if (data.get("Occurs").equals("Once"))
			billings.selectOnDate(data.get("OnDate_Month"), data.get("OnDate_Year"));
		else
			billings.selectSpred(data.get("Spread"));
		if (data.get("Occurs").equals("Quarterly") || data.get("Occurs").equals("Yearly"))
			billings.selectAtDate(data.get("AtDate"));
		if (data.get("Spread").equals("Growth"))
			billings.setGrowth(data.get("GrowthPercentage"));
		billings.setAmount(data.get("Amount"));
		billings.setPaymentAfter(data.get("PaymentAfter"));

		// ////////////////////////////////////////

		Assert.assertTrue(general.isDisplayed(), "expected general section to be displayed");

		DateRange from = general.openDateRangeFrom();
		if (!data.get("DateRange_from_year").isEmpty()) {
			from.setYear(data.get("DateRange_from_year"));
			from.setMonth(data.get("DateRange_from_month"));
		}
	//	DateRange to = general.openDateRangeTo();
		if (!data.get("DateRange_to_year").isEmpty()) {
			from.setYear(data.get("DateRange_to_year"));
			from.setMonth(data.get("DateRange_to_month"));
		}

		if (!data.get("AccountNumber").isEmpty())
		{
			TopHeaderBar topheader = new TopHeaderBar();
			topheader.openBudgetSettings();
			BudgetSettings settings = new BudgetSettings();
			boolean selectedProduct = settings.isAccountNumberFieldSelected();
			
			settings.clickCancel();
			
			if (selectedProduct)
			{
				general.setAccountNumberInRowByIndex(1, data.get("AccountNumber"));
			}
			
		}
		
		if (!data.get("Geography").isEmpty())
		{
			TopHeaderBar topheader = new TopHeaderBar();
			topheader.openBudgetSettings();
			BudgetSettings settings = new BudgetSettings();
			boolean selectedProduct = settings.isGeographyFieldSelected();
			
			settings.clickCancel();
			
			if (selectedProduct)
			{
				general.setGeography(data.get("Geography"));
			}
			
		}
		
		
		
		if (!data.get("Product").isEmpty())
		{
			TopHeaderBar topheader = new TopHeaderBar();
			topheader.openBudgetSettings();
			BudgetSettings settings = new BudgetSettings();
			boolean selectedProduct = settings.isProductFieldSelected();
			
			settings.clickCancel();
			
			if (selectedProduct)
			{
				general.setProduct(data.get("Product"));
			}
			
		}
		
		general.setNotes(data.get("Notes"));
		board.clickSaveChanges();

		if (data.get("ShouldPass").equals("FALSE")) {
			Assert.assertTrue(billings.isBillingsHasError() || general.isGeneralHasError(), "expected to error in billings section");
		} else {
			// //////////////////////////////////////////////////////////////////
			board.clickSaveChanges();

			// start validation
			int payAfter, growth;
			String monthX, yearX;

			secondaryBoard.clickOnSubLine(OtherIncomeAndExpensesLine, OtherIncomeAndExpensesSubLine);

			// yearY = dateTo.split("/")[1];
			secondaryBoard.clickOnSubLine(OtherIncomeAndExpensesLine, OtherIncomeAndExpensesSubLine, OtherIncomeAndExpensesSub_SubLine);

			if (data.get("PaymentAfter").isEmpty())
				payAfter = 0;
			else
				payAfter = Integer.parseInt(data.get("PaymentAfter"));

			if (data.get("GrowthPercentage").isEmpty())
				growth = 0;
			else
				growth = Integer.parseInt(data.get("GrowthPercentage"));

			// monthX =
			// BudgetaUtils.getMonthWithIndex(Integer.parseInt(general.getDateRangeFrom().split("/")[0]));
			// yearX = general.getDateRangeFrom().split("/")[1];
			// monthY =
			// BudgetaUtils.getMonthWithIndex(Integer.parseInt(general.getDateRangeTo().split("/")[0]));
			// yearY = general.getDateRangeTo().split("/")[1];

			board.clickSaveChanges();
			general = new GeneralSection();
			String dateFrom = general.getGeneralDateRangeFrom();
			String dateTo = general.getGeneralDateRangeTo();
			yearX = dateFrom.split("/")[1];
			monthX = BudgetaUtils.getMonthWithIndex(Integer.parseInt(dateFrom.split("/")[0]));

			String toExactMonth, toExactYear;

			toExactMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(dateTo.split("/")[0]));
			toExactYear = dateTo.split("/")[1];

			topHeaderBar = new TopHeaderBar();
			if (data.get("Occurs").equals("Once")) {
				String[] expectedValues = BudgetaUtils.calculateValues_Once(monthX, yearX, monthY, yearY, data.get("OnDate_Month"), data.get("OnDate_Year"),
						Integer.parseInt(data.get("Amount")), payAfter, 0, 0, toExactMonth, toExactYear);
				compareExpectedResults(expectedValues);
			}

			if (data.get("Occurs").equals("Monthly")) {
				String[] expectedValues = BudgetaUtils.calculateValues_Monthly(monthX, yearX, monthY, yearY, Integer.parseInt(data.get("Amount")), payAfter, 0,
						0, growth, toExactMonth, toExactYear);
				compareExpectedResults(expectedValues);
			}

			if (data.get("Occurs").equals("Quarterly")) {
				BudgetSettings settings = topHeaderBar.openBudgetSettings();
				String fiscal = settings.getSelectedFiscal().substring(0, 3).toUpperCase();
				settings.clickCancel();
				String[] expectedValues = BudgetaUtils.calculateValues_Quaterly(monthX, yearX, monthY, yearY, Integer.parseInt(data.get("Amount")), payAfter,
						growth, data.get("AtDate"), fiscal, toExactMonth, toExactYear);
				compareExpectedResults(expectedValues);
			}

			if (data.get("Occurs").equals("Yearly")) {
				BudgetSettings settings = topHeaderBar.openBudgetSettings();
				String fiscal = settings.getSelectedFiscal().substring(0, 3).toUpperCase();
				settings.clickCancel();
				String[] expectedValues = BudgetaUtils.calculateValues_Yearly(monthX, yearX, monthY, yearY, Integer.parseInt(data.get("Amount")), payAfter,
						growth, data.get("AtDate"), fiscal, toExactMonth, toExactYear);
				compareExpectedResults(expectedValues);
			}
		}
	}

	private void compareExpectedResults(String[] expectedValues) {
		int total = 0;
		/*
		 * for (int i = 0; i < expectedValues.length; i++) { if
		 * (!expectedValues[i].equals("-")) {
		 * 
		 * // expectedValues[i] = String.format("%.2f", expectedValues[i]);
		 * 
		 * expectedValues[i] =
		 * String.valueOf(round(Double.parseDouble(expectedValues[i]), 2)); } }
		 */

		PreviewBoard preview = new PreviewBoard();
		for (int i = 0; i < expectedValues.length; i++) {
			Assert.assertEquals(preview.getValueByIndex(i), expectedValues[i], "error in calculation budgets in index: " + i);
			if (!expectedValues[i].equals("-"))
				total += Integer.parseInt(expectedValues[i]);
			// total += Float.parseFloat(expectedValues[i]);
		}
		for (int i = expectedValues.length; i < preview.getValuesSize(); i++) {
			Assert.assertEquals(preview.getValueByIndex(i), "-", "error in calculation budgets in index: " + i);
		}
		if (total == 0)
			Assert.assertEquals(preview.getTotalValue(), "-");
		else
			Assert.assertEquals(Integer.parseInt(preview.getTotalValue()), total, (int) (0.5 * preview.getValuesSize()));
	}

	// public double round(double value, int numberOfDigitsAfterDecimalPoint) {
	// BigDecimal bigDecimal = new BigDecimal(value);
	// bigDecimal = bigDecimal.setScale(numberOfDigitsAfterDecimalPoint,
	// BigDecimal.ROUND_HALF_UP);
	// return bigDecimal.doubleValue();
	// }
}
