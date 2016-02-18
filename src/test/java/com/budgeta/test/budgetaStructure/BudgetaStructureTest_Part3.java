package com.budgeta.test.budgetaStructure;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.BillingsSection;
import com.budgeta.pom.BudgetSettings;
import com.budgeta.pom.BudgetaBoard;
import com.budgeta.pom.CommentsSection;
import com.budgeta.pom.DateRange;
import com.budgeta.pom.GeneralSection;
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
public class BudgetaStructureTest_Part3 extends WrapperTest{
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
		secondaryBoard.addAllSubBudgetLines();
		secondaryBoard = new SecondaryBoard();
		secondaryBoard.addSubLineForLine(cost_of_revenues, cost_of_revenues_subLine);
		secondaryBoard = new SecondaryBoard();

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

		secondaryBoard = new SecondaryBoard();
		secondaryBoard.addSubLine(OtherIncomeAndExpensesLine);
		secondaryBoard.addAllSubBudgetLines();
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

	@Test(dataProvider = "ExcelFileLoader", enabled = true, priority = 9)
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
			//Assert.assertEquals(general.getSelectedCurrency(), data.get("Currency"));
		}

	}

	@Test(dataProvider = "ExcelFileLoader", enabled = true, priority = 10)
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

	
			int payAfter, growth;
			String monthX, yearX;

			secondaryBoard.clickOnSubLine(OtherIncomeAndExpensesLine, OtherIncomeAndExpensesSubLine);

			secondaryBoard.clickOnSubLine(OtherIncomeAndExpensesLine, OtherIncomeAndExpensesSubLine, OtherIncomeAndExpensesSub_SubLine);

			if (data.get("PaymentAfter").isEmpty())
				payAfter = 0;
			else
				payAfter = Integer.parseInt(data.get("PaymentAfter"));

			if (data.get("GrowthPercentage").isEmpty())
				growth = 0;
			else
				growth = Integer.parseInt(data.get("GrowthPercentage"));


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
