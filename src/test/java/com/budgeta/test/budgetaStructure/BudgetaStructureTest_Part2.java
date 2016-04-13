package com.budgeta.test.budgetaStructure;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

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
public class BudgetaStructureTest_Part2 extends WrapperTest{
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
	//	secondaryBoard.addSubLine("Revenues");
		RevenuesAddSubLine subLine = new RevenuesAddSubLine();
		//subLine.setName(revenuesSubLine);
//		subLine.selectDropDown("Perpetual License");
//		subLine.clickAdd();

//		secondaryBoard = new SecondaryBoard();
//		secondaryBoard.addSubLine(cost_of_revenues);
//		secondaryBoard.addAllSubBudgetLines();
//		secondaryBoard = new SecondaryBoard();
//		secondaryBoard.addSubLineForLine(cost_of_revenues, cost_of_revenues_subLine);
//		secondaryBoard = new SecondaryBoard();

		//secondaryBoard.addSubLineForSubLine(cost_of_revenues, cost_of_revenues_subLine, salary_and_wages);
		secondaryBoard.addSubLine(OperationalExpenses);
		secondaryBoard.addAllSubBudgetLines();
		secondaryBoard = new SecondaryBoard();
		secondaryBoard.openAddChild(OperationalExpensesSubline, 2);
		subLine = new RevenuesAddSubLine();
		employee = WebdriverUtils.getTimeStamp(employee);
		subLine.setName(employee);
		subLine.clickAdd();
		secondaryBoard.selectDropDownInLine("Select Grouping", "No Grouping");

//		secondaryBoard = new SecondaryBoard();
//		secondaryBoard.addSubLine(OtherIncomeAndExpensesLine);
//		secondaryBoard.addAllSubBudgetLines();
//		secondaryBoard = new SecondaryBoard();
//		secondaryBoard.addSubLineForLine(OtherIncomeAndExpensesLine, OtherIncomeAndExpensesSubLine);
//		secondaryBoard = new SecondaryBoard();
//		// secondaryBoard.addSubLinrForSubLine(OtherIncomeAndExpensesLine,
//		// OtherIncomeAndExpensesSubLine, OtherIncomeAndExpensesSub_SubLine);
//		secondaryBoard.addSubLineForSubLine(OtherIncomeAndExpensesLine, OtherIncomeAndExpensesSubLine, OtherIncomeAndExpensesSub_SubLine);
//		secondaryBoard = new SecondaryBoard();
//		secondaryBoard.openAddChild(OtherIncomeAndExpensesSubLine, 2);
//		subLine = new RevenuesAddSubLine();
//		OtherIncomeAndExpensesSub_SubLine = WebdriverUtils.getTimeStamp(OtherIncomeAndExpensesSub_SubLine);
//		subLine.setName(OtherIncomeAndExpensesSub_SubLine);
//		subLine.clickAdd();


	}

	@Test(dataProvider = "ExcelFileLoader", enabled = true, priority = 3)
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

			Assert.assertEquals(general.getGeography(), data.get("Geography"));
			Assert.assertEquals(general.getProduct(), data.get("Product"));

		}
	}

	@Test(dataProvider = "ExcelFileLoader", enabled = true, priority = 5)
	@DataProviderParams(sheet = "BudgetaForm", area = "CostOfSale_Salary&wages_EmployeeForm")
	public void employeeFormTest(Hashtable<String, String> data) {

		board = new BudgetaBoard();
		secondaryBoard = board.getSecondaryBoard();
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
		
		TopHeaderBar topheader = new TopHeaderBar();
		BudgetSettings settings = topheader.openBudgetSettings();
		String fiscal = settings.getSelectedFiscal().substring(0, 3).toUpperCase();
		settings.clickCancel();
		
		general.setNotes(data.get("Notes"));

		board = new BudgetaBoard();
		board.clickSaveChanges();

		if (data.get("ShouldPass").equals("FALSE"))
			Assert.assertTrue(mainSection.isMainSectionHasError() || general.isGeneralHasError(),
					"expected to error in employee assumption sectcion or in general section");
		else {
			board.clickSaveChanges();
			secondaryBoard.clickOnSubLine(OperationalExpenses, OperationalExpensesSubline);
			general = new GeneralSection();
			///////////////////////
			String dateFrom = general.getGeneralDateRangeFrom();
			/////////////////////
			String dateTo = general.getGeneralDateRangeTo();
			String yearFrom = dateFrom.split("/")[1];
			String yearTo = dateTo.split("/")[1];
			secondaryBoard.clickOnSubLine(OperationalExpenses, OperationalExpensesSubline, employee);

			int baseSalary = 0, benefits = 0, bonus = 0, AvgAccruedVacation = 0, yearlyVacationDays = 0, YearlyIncrease = 0 ;
			String monthFrom = BudgetaUtils.getMonthWithIndex(Integer.parseInt(dateFrom.split("/")[0]));
			String monthTo = BudgetaUtils.getMonthWithIndex(Integer.parseInt(dateTo.split("/")[0]));
			String payment = data.get("Payment");
			if (payment.isEmpty())
				payment = "Same month";
			general = new GeneralSection();
			String hireDate = general.getHireDateRangeFrom(), endDate = general.getHireDateRangeTo(), hireMonth, endMonth, hireYear, endYear;
			if (general.getHireDateRangeFrom().equals("MM/YYYY")) {
				hireDate = dateFrom;
				hireMonth = monthFrom;
				hireYear = yearFrom;
			} else {
				hireMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(hireDate.split("/")[0]));
				hireYear = hireDate.split("/")[1];
			}

			if (general.getHireDateRangeTo().equals("MM/YYYY")) {
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

			if(!employeeSection.getYearlyIncrease().isEmpty())
				YearlyIncrease = Integer.parseInt(employeeSection.getYearlyIncrease());
			
			if (employeeSection.getSelecredTerm().equals("Monthly")) {
				String[] expectedValues = BudgetaUtils.calculateEmployeeValues_Monthly(monthFrom, yearFrom, monthTo, yearTo, hireMonth, hireYear, endMonth,
						endYear, baseSalary, benefits, bonus, payment, yearlyVacationDays, AvgAccruedVacation, YearlyIncrease, fiscal);
				compareExpectedResults(expectedValues);
			}
			if (employeeSection.getSelecredTerm().equals("Yearly")) {
				String[] expectedValues = BudgetaUtils.calculateEmployeeValues_Yearly(monthFrom, yearFrom, monthTo, yearTo, hireMonth, hireYear, endMonth,
						endYear, baseSalary, benefits, bonus, payment);
				compareExpectedResults(expectedValues);
			}

		}

	}

	private void compareExpectedResults(String[] expectedValues) {
		int total = 0;
		PreviewBoard preview = new PreviewBoard();
		for (int i = 0; i < expectedValues.length; i++) {
			Assert.assertEquals(preview.getValueByIndex(i), expectedValues[i], "error in calculation budgets in index: " + i);
			if (!expectedValues[i].equals("-"))
				total += Integer.parseInt(expectedValues[i]);
		}
		for (int i = expectedValues.length; i < preview.getValuesSize(); i++) {
			Assert.assertEquals(preview.getValueByIndex(i), "-", "error in calculation budgets in index: " + i);
		}
		if (total == 0)
			Assert.assertEquals(preview.getTotalValue(), "-");
		else
			Assert.assertEquals(Integer.parseInt(preview.getTotalValue()), total, (int) (0.5 * preview.getValuesSize()));
	}


}
