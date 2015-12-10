package com.budgeta.test.view;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.Actuals;
import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.GeneralSection;
import com.budgeta.pom.InnerBar;
import com.budgeta.pom.PreviewBoard;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.pom.Sheets;
import com.budgeta.test.BudgetaUtils;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.KnownIssue;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestFirst;
import com.galilsoftware.AF.core.listeners.TestNGListener;

@Listeners({ MethodListener.class, TestNGListener.class })
public class ViewTest extends WrapperTest {
	
	SecondaryBoard secondaryBoard;
	InnerBar innerBar;
	BudgetNavigator navigator;
	Sheets sheets;
	Actuals actuals;
	String fromMonth;
	String fromYear;
	String toMonth;
	String toYear;
	List<String> dates;
	GeneralSection generalSection;

	@TestFirst
	@Test(enabled = true)
	public void setBudgetTest() {

		secondaryBoard = board.getSecondaryBoard();
		BudgetNavigator navigator = new BudgetNavigator();
		Assert.assertTrue(navigator.isDisplayed(), "expected to inner bar to be dislayed");
		navigator.selectRandomBudgetWithPrefix("budget7_144215547406");
		generalSection = new GeneralSection();

		String dateFrom = generalSection.getDateRangeFrom();
		String dateTo = generalSection.getDateRangeTo();

		fromMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(dateFrom.split("/")[0]));
		fromYear = dateFrom.split("/")[1];
		toMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(dateTo.split("/")[0]));
		toYear = dateTo.split("/")[1];
		List<String> expectedDates = BudgetaUtils.getAllMonthsBetweenTwoMonths(fromMonth, fromYear, toMonth, toYear);

		navigator.openSheetTab();
		// innerBar.openViewTab();
		// Assert.assertEquals(innerBar.getOpenTab(), "View");
		// view = new View();
		sheets = new Sheets();
		Assert.assertTrue(sheets.isDisplayed(), "expected to Sheets to be displayed");
		dates = sheets.getAllDates();
		Assert.assertEquals(dates.size(), expectedDates.size());
		for (int i = 0; i < expectedDates.size(); i++) {
			Assert.assertEquals(dates.get(i), expectedDates.get(i));
		}
	}

	@Test(enabled = true)
	public void selectReportType() {
		innerBar = board.getInnerBar();
		secondaryBoard = board.getSecondaryBoard();
		innerBar.openViewTab();
		sheets = new Sheets();
		sheets.selectReportType("Cash Flow");
		sheets.selectSubReportType("Budget");

		}

	@Test(enabled = true)
	public void validateTableDataTest() {

		innerBar = board.getInnerBar();
		secondaryBoard = board.getSecondaryBoard();
			innerBar.openViewTab();
		sheets = new Sheets();

		int numberOfRows = sheets.getNumbreOfRows();
		for (int row = 0; row < numberOfRows; row++) {
			String rowTitle = sheets.getRowTitleByIndex(row);
			sheets.clickOnLineByIndex(row);
			if (rowTitle.contains(",")) {
				rowTitle = rowTitle.split(",")[1].trim();
				Assert.assertTrue(secondaryBoard.getSelectedLine().contains(rowTitle));
			} else {
				Assert.assertTrue(rowTitle.contains(secondaryBoard.getSelectedLine()));
			}
			PreviewBoard previewBoard = new PreviewBoard();
			List<String> lineValues = new ArrayList<>();
			int startIndex = previewBoard.getIndexOfHeaderDate(fromMonth + " " + fromYear);
			int endIndex = previewBoard.getIndexOfHeaderDate(toMonth + " " + toYear);
			if (startIndex < 0 && endIndex >= 0)
				startIndex = 0;
			else if (startIndex >= 0 && endIndex < 0)
				endIndex = previewBoard.getValuesSize() - 1;

			for (int i = startIndex; i <= endIndex; i++) {
				lineValues.add(previewBoard.getValueByIndex(i));
			}

			innerBar.openViewTab();
			sheets = new Sheets();
			List<String> values = sheets.getAllValuesOfRow(row);

			for (int i = 0; i < lineValues.size(); i++) {
				Assert.assertEquals(lineValues.get(i), values.get(i),
						"... Row title is: " + rowTitle + ", in header: " + dates.get(i));
			}
			}
		}

	@KnownIssue(bugID = "BUD - 1964")
	@Test(enabled = true, expectedExceptions = AssertionError.class)
	public void validateTotalTest() {

		innerBar = board.getInnerBar();
		innerBar.openViewTab();
		sheets = new Sheets();

		int numberOfRows = sheets.getNumbreOfRows();
		for (int row = 0; row < numberOfRows; row++) {
			int total = 0;
			List<String> values = sheets.getAllValuesOfRow(row);
			for (String str : values) {
				if (!str.equals("-"))
					total += Integer.parseInt(str);
			}
			if (total == 0)
				Assert.assertEquals(sheets.getTotalOfRow(row), "-",
						"... Row title is: " + sheets.getRowTitleByIndex(row));
			else
				Assert.assertEquals(sheets.getTotalOfRow(row), total + "",
						"... Row title is: " + sheets.getRowTitleByIndex(row));

			}
		}

	@Test(enabled = false)
	public void validateBudgetVsActuals() {
		innerBar = board.getInnerBar();
		secondaryBoard = board.getSecondaryBoard();
		innerBar.openViewTab();
		sheets = new Sheets();
		sheets.selectReportType("Cash Flow");
		sheets.selectSubReportType("Budget");

		int numberOfRows = sheets.getNumbreOfRows();
		// List<String> baseValues = new ArrayList<>();
		List<List<String>> baseValues = new ArrayList<List<String>>();
			
		int index = 0;
		for (int rows = 0; rows < numberOfRows; rows++) {
			String rowTitle = sheets.getRowTitleByIndex(rows);
			if (secondaryBoard.checkIfLineTypeIsModel(rowTitle) == false) {
				baseValues.add(index, sheets.getAllValuesOfRow(rows));
				index++;
			}
		}
		int checkedRow = 0;
		for (int row = 0; row < index; row++) {

			sheets.selectReportType("Cash Flow");
			sheets.selectSubReportType("Actual");
			sheets.selectSubActualReportType("Budget vs. actuals");

			if (sheets.getRowTitleByIndex(row).contains("Uncategorized")) {
				continue;
			}

			List<String> actualeValues = sheets.getAllValuesOfRowByTitle(row, "Budget");

			// view.selectReportType("Cash Flow");
			// view.selectSubReportType("Budget");
			List<String> baseRowValues = baseValues.get(checkedRow);
			checkedRow++;
			System.out.println(actualeValues.size() + " , " + baseRowValues.size());
			for (int i = 0; i < actualeValues.size(); i++) {
				Assert.assertEquals(baseRowValues.get(i), actualeValues.get(i), " line number " + row + "in index: " + i
						+ "... Base value is" + baseRowValues.get(i) + " and Actual value is: " + actualeValues.get(i));
			}

			}

	}

	@Test(enabled = false)
	public void validateActulsVsActualsTab() {
		innerBar = board.getInnerBar();
		secondaryBoard = board.getSecondaryBoard();
		sheets = new Sheets();
		actuals = new Actuals();
		innerBar.openActualsTab();

		int numberOfRows = sheets.getNumbreOfRows();
		for (int row = 0; row < numberOfRows; row++) {

			List<String> tabValues = actuals.getAllValuesOfRow(row);

			innerBar.openViewTab();
			sheets.selectReportType("Cash Flow");
			sheets.selectSubReportType("Actual");
			sheets.selectSubActualReportType("Budget vs. actuals");

			List<String> actualeValues = sheets.getAllValuesOfRowByTitle(row, "Actual");

			for (int i = 0; i < actualeValues.size(); i++) {
				String val = tabValues.get(i);
				if (!val.equals("-"))
					val = (int) Math.round((Double.parseDouble(tabValues.get(i)))) + "";
				Assert.assertEquals(actualeValues.get(i), val, " ... actuals value is" + actualeValues.get(i)
						+ " and Actual TAb value is: " + tabValues.get(i));
			}
			}
		}

	@Test(enabled = true)
	public void calculateDifferences() {
		innerBar = board.getInnerBar();
		secondaryBoard = board.getSecondaryBoard();
			innerBar.openViewTab();
		sheets = new Sheets();
		sheets.selectReportType("Cash Flow");
		sheets.selectSubReportType("Actual");
		sheets.selectSubActualReportType("Budget vs. actuals");
	
		int numberOfRows = sheets.getNumbreOfRows();
		// List<String> baseValues = new ArrayList<>();

		for (int row = 0; row < numberOfRows; row++) {
			List<String> DifValues = sheets.getAllValuesOfRowByTitle(row, "Differrence");
			String val = DifValues.get(row);
			List<String> BudgetValues = sheets.getAllValuesOfRowByTitle(row, "Budget");
	
			List<String> actualValues = sheets.getAllValuesOfRowByTitle(row, "Actual");

			if (DifValues.get(row).equals("-")) {
				val = "0";
			}

			String budgetValue = "", actualValue = "";
			System.out.println(actualValues.size() + " , " + BudgetValues.size());
			for (int i = 0; i < actualValues.size(); i++) {
				actualValue = actualValues.get(i);
				budgetValue = BudgetValues.get(i);
				if (BudgetValues.get(i).equals("-"))
					budgetValue = "0";
				if (actualValues.get(i).equals("-"))
					actualValue = "0";
				Assert.assertEquals((Integer.parseInt(budgetValue)) - (Integer.parseInt(actualValue)) + "", val,
						" line number " + row + "in index: " + i + "... Budget value is" + BudgetValues.get(i)
								+ " and Actual value is: " + actualValues.get(i));

			}
	
		}
	
	}
	
	@Test(enabled = false)
	public void viewRollingForecast() {
		innerBar = board.getInnerBar();
		secondaryBoard = board.getSecondaryBoard();
		sheets = new Sheets();
		actuals = new Actuals();
			
		innerBar.openViewTab();
		sheets.selectCurrencyType("By currency");
			
		innerBar.openActualsTab();
			
		int numberOfRows = sheets.getNumbreOfRows();
		List<List<String>> tabValues = new ArrayList<List<String>>();
			
		for (int rows = 0; rows < numberOfRows; rows++) {
			tabValues.add(rows, actuals.getAllValuesOfRow(rows));

			}
		innerBar.openViewTab();
		sheets.selectReportType("Cash Flow");
		sheets.selectSubReportType("Actual");
		sheets.selectSubActualReportType("Rolling forecast");

		for (int row = 0; row < numberOfRows; row++) {

			List<String> actualeValues = sheets.getAllValuesOfRow(row);
			List<String> baseRowValues = tabValues.get(row);
	
			for (int i = 0; i < actualeValues.size(); i++) {
				String val = baseRowValues.get(i);
				if (!val.equals("-"))
					val = (int) Math.round((Double.parseDouble(baseRowValues.get(i)))) + "";
				Assert.assertEquals(actualeValues.get(i), val,
						" line number " + row + " Index: " + i + " ... actuals value is" + actualeValues.get(i)
								+ " and Actual TAb value is: " + baseRowValues.get(i));
			}
			}

		}
		

}
