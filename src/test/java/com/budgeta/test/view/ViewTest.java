package com.budgeta.test.view;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.Actuals;
import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.BudgetSettings;
import com.budgeta.pom.GeneralSection;
import com.budgeta.pom.InnerBar;
import com.budgeta.pom.PreviewBoard;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.pom.Sheets;
import com.budgeta.pom.TopHeaderBar;
import com.budgeta.pom.Enum.ReportEnum;
import com.budgeta.test.BudgetaUtils;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.KnownIssue;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestFirst;
import com.galilsoftware.AF.core.listeners.TestNGListener;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

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
	TopHeaderBar topHeaderBar;

	@BeforeMethod
	private void initTest() {

		driver.manage().window().maximize();

	}

	@TestFirst
	@Test(enabled = true)
	public void setBudgetTest() {

		BudgetNavigator navigator = new BudgetNavigator();
		Assert.assertTrue(navigator.isDisplayed(), "expected to inner bar to be dislayed");
		//navigator.selectRandomBudgeta();
		navigator.selectRandomBudgetWithPrefix("TEST Forecast");
		generalSection = new GeneralSection();

		String dateFrom = generalSection.getGeneralDateRangeFrom();
		String dateTo = generalSection.getGeneralDateRangeTo();

		fromMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(dateFrom.split("/")[0]));
		fromYear = dateFrom.split("/")[1];
		toMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(dateTo.split("/")[0]));
		toYear = dateTo.split("/")[1];
		List<String> expectedDates = BudgetaUtils.getAllMonthsBetweenTwoMonths(fromMonth, fromYear, toMonth, toYear, 0, false);

		navigator.openSheetTab();
		Assert.assertEquals(navigator.getOpenTab(), "Sheets");

		sheets = new Sheets();
		Assert.assertTrue(sheets.isDisplayed(), "expected to Sheets to be displayed");
		dates = sheets.getAllDates();
		Assert.assertEquals(dates.size(), expectedDates.size());
		for (int i = 0; i < expectedDates.size(); i++) {
			Assert.assertEquals(dates.get(i), expectedDates.get(i));
		}
	}

	@Test(enabled = false)
	public void selectReportType() {
		navigator = board.getBudgetNavigator();
		secondaryBoard = board.getSecondaryBoard();
		navigator.openSheetTab();
		sheets = new Sheets();
		topHeaderBar = new TopHeaderBar();
		topHeaderBar.openHeaderTab(ReportEnum.CASH_FLOW.name());
		sheets.selectSubReportType("Budget");
		Assert.assertTrue(sheets.isDisplayed(), "expected to view page to be dislayed");
	}

	@KnownIssue(bugID = "BUD - 4401")
	@Test(enabled = true)
	public void validateTableDataTest() {

		navigator = board.getBudgetNavigator();
		secondaryBoard = board.getSecondaryBoard();
		navigator.openSheetTab();
		sheets = new Sheets();
		sheets.selectRepeatReportType("Monthly");

		int numberOfRows = sheets.getNumbreOfRows();
		sheets.ScrollTableToJS(driver, "50");
		for (int row = 0; row < numberOfRows -6; row++) {
	//	for (int row = numberOfRows-1; row >0 ; row--) {
			
			String rowSheetTitle = sheets.getRowTitleByIndex(row);
			String rowTitle = sheets.getRowTitleByIndex(row);
			
			//sheets.clickOnLineByIndex(row);
			sheets.clickOnLineBtTitle(rowSheetTitle);
	//		sheets.clickOnLine(row, rowSheetTitle);
			if (rowTitle.contains(",")) {
				rowTitle = rowTitle.split(",")[1].trim();
				//Assert.assertTrue(secondaryBoard.getSelectedLineName().contains(rowTitle));
				Assert.assertTrue(secondaryBoard.getSelectedLineName().contains(rowTitle), "Row name is: " + rowSheetTitle + " index of row is: " + row);
			} else {
				//Assert.assertTrue(rowTitle.contains(secondaryBoard.getSelectedLineName()));
				Assert.assertTrue(rowTitle.contains(secondaryBoard.getSelectedLineName()), "Row name is: " + rowSheetTitle + " index of row is: " + row);
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

			navigator.openSheetTab();
			sheets = new Sheets();
			int indexOfTitle = sheets.getIndexOfRowName(rowSheetTitle);
			List<String> values = sheets.getAllValuesOfRow(indexOfTitle);

			for (int i = 0; i < lineValues.size(); i++) {
				Assert.assertEquals(lineValues.get(i), values.get(i), "... Row title is: " + rowTitle + ", in header: " + dates.get(i));
			}
		}
	}

	// @KnownIssue(bugID = "BUD - 2508")
	@Test(enabled = true)
	public void validateTotalTest() {

		navigator = board.getBudgetNavigator();
		navigator.openSheetTab();
		sheets = new Sheets();
		sheets.selectRepeatReportType("Monthly");
		int numberOfRows = sheets.getNumbreOfRows();
		for (int row = 0; row < numberOfRows; row++) {
			int total = 0;
			List<String> values = sheets.getAllValuesOfRow(row);
			for (String str : values) {
				if (!str.equals("-"))
					total += Integer.parseInt(str);
			}
			if (total == 0)
				Assert.assertEquals(sheets.getTotalOfRow(row), "-", "... Row title is: " + sheets.getRowTitleByIndex(row));
			else
				Assert.assertEquals(Integer.parseInt(sheets.getTotalOfRow(row)), total, 12 , "... Row title is: " + sheets.getRowTitleByIndex(row));

		}
	}
	
	
	//////////////////////////////////////////////////////////////////////

	@Test(enabled = false)
	public void validateBudgetVsActuals() {
		navigator = board.getBudgetNavigator();
		secondaryBoard = board.getSecondaryBoard();
		navigator.openSheetTab();
		sheets = new Sheets();
		topHeaderBar = new TopHeaderBar();
		topHeaderBar.openHeaderTab(ReportEnum.CASH_FLOW.name());
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
				Assert.assertEquals(baseRowValues.get(i), actualeValues.get(i),
						" line number " + row + "in index: " + i + "... Base value is" + baseRowValues.get(i) + " and Actual value is: " + actualeValues.get(i));
			}

		}

	}

	@Test(enabled = false)
	public void validateActulsVsActualsTab() {
		navigator = board.getBudgetNavigator();
		secondaryBoard = board.getSecondaryBoard();
		sheets = new Sheets();
		actuals = new Actuals();

		innerBar.openActualsTab();

		int numberOfRows = sheets.getNumbreOfRows();
		for (int row = 0; row < numberOfRows; row++) {

			List<String> tabValues = actuals.getAllValuesOfRow(row);

			navigator.openSheetTab();
			sheets.selectReportType("Cash Flow");
			sheets.selectSubReportType("Actual");
			sheets.selectSubActualReportType("Budget vs. actuals");

			List<String> actualeValues = sheets.getAllValuesOfRowByTitle(row, "Actual");

			for (int i = 0; i < actualeValues.size(); i++) {
				String val = tabValues.get(i);
				if (!val.equals("-"))
					val = (int) Math.round((Double.parseDouble(tabValues.get(i)))) + "";
				Assert.assertEquals(actualeValues.get(i), val, " ... actuals value is" + actualeValues.get(i) + " and Actual TAb value is: " + tabValues.get(i));
			}
		}
	}

	@KnownIssue(bugID = "BUD - 3252")
	@Test(enabled = false)
	public void calculateDifferences() {
		navigator = board.getBudgetNavigator();
		secondaryBoard = board.getSecondaryBoard();
		navigator.openSheetTab();
		sheets = new Sheets();
		topHeaderBar = new TopHeaderBar();
		topHeaderBar.openHeaderTab(ReportEnum.CASH_FLOW.name());
		sheets.selectSubReportType("Actual");
		sheets.selectSubActualReportType("Budget vs. actuals");

		int numberOfRows = sheets.getNumbreOfRows();
		// List<String> baseValues = new ArrayList<>();

		for (int row = 0; row < numberOfRows; row++) {
			List<String> DifValues = sheets.getAllValuesOfRowByTitle(row, "Differrence");
			// String val = DifValues.get(row);
			List<String> BudgetValues = sheets.getAllValuesOfRowByTitle(row, "Budget");

			List<String> actualValues = sheets.getAllValuesOfRowByTitle(row, "Actuals");

			// if (DifValues.get(row).equals("-")) {
			// val = "0";
			// }

			String budgetValue = "", actualValue = "", val = "";
			System.out.println(actualValues.size() + " , " + BudgetValues.size());
			for (int i = 0; i < actualValues.size(); i++) {
				actualValue = actualValues.get(i);
				budgetValue = BudgetValues.get(i);
				val = DifValues.get(i);
				if (BudgetValues.get(i).equals("-"))
					budgetValue = "0";
				if (actualValues.get(i).equals("-"))
					actualValue = "0";
				if (DifValues.get(i).equals("-")) {
					val = "0";
				}
				Assert.assertEquals((Integer.parseInt(budgetValue)) - (Integer.parseInt(actualValue)), Integer.parseInt(val), " line number " + row
						+ "in index: " + i + "... Budget value is" + budgetValue + " and Actual value is: " + actualValue);

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
				Assert.assertEquals(actualeValues.get(i), val, " line number " + row + " Index: " + i + " ... actuals value is" + actualeValues.get(i)
						+ " and Actual TAb value is: " + baseRowValues.get(i));
			}
		}

	}
	//////////////////////////////////////////////////////////////////
	
	@Test(enabled = true)
	public void validateQuartlySheets(){
		navigator = board.getBudgetNavigator();
		secondaryBoard = board.getSecondaryBoard();
		navigator.openInputTab();
		TopHeaderBar topHeaderBar = new TopHeaderBar();
		topHeaderBar.openBudgetSettings();
		BudgetSettings settings = new BudgetSettings();
		String dateFrom = settings.getDateRangeFrom();
		String dateTo = settings.getDateRangeTo();
		String fiscal = settings.getSelectedFiscal().substring(0, 3).toUpperCase();
		settings.clickCancel();
		
		fromMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(dateFrom
				.split("/")[0]));
		fromYear = dateFrom.split("/")[1];
		toMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(dateTo
				.split("/")[0]));
		toYear = dateTo.split("/")[1];
		List<String> expectedDates = BudgetaUtils.getAllMonthsBetweenTwoMonths(
				fromMonth, fromYear, toMonth, toYear, 0,
								false);
		List<String> quarterMonths = BudgetaUtils.getBeginningOfQuaterlyMonths(fiscal);
		List<Integer> indexOfQuarterMonths  = BudgetaUtils.getIndexOfQuarterMonths(quarterMonths,expectedDates);
		
		
		navigator.openSheetTab();
		sheets = new Sheets();
		sheets.selectRepeatReportType("Monthly");

		int numberOfRows = sheets.getNumbreOfRows();
		
		for (int row =0 ;row < numberOfRows; row++){
			
			String rowTitle = sheets.getRowTitleByIndex(row);
			int indexOfTitle = sheets.getIndexOfRowName(rowTitle);
			List<String> budgetMonthlyValues = sheets.getAllValuesOfRow(indexOfTitle);
			
			sheets = new Sheets();
			topHeaderBar = new TopHeaderBar();
			topHeaderBar.openHeaderTab(ReportEnum.CASH_FLOW.name());
			sheets.selectRepeatReportType("Quarterly");
			
			List<String> budgetQuarterlyyValues = sheets.getAllValuesOfRowByTitle(rowTitle);
			
			String[] quarterlyExpectedValues = BudgetaUtils.calculateSheetValues_Quaterly(budgetMonthlyValues, indexOfQuarterMonths);
			
//			for (int j = 0; j < quarterlyExpectedValues.length; j++){
//				if (quarterlyExpectedValues [j] == "0"){
//					quarterlyExpectedValues [j]  = "-";
//				}
//			}
			
			for (int j = 0; j < budgetQuarterlyyValues.size(); j++){
				if (budgetQuarterlyyValues.get(j).equals("-")){
					budgetQuarterlyyValues.add(j, "0");
					budgetQuarterlyyValues.remove(j+1);
					
				}
			}
			
			for(int i = 0; i < budgetQuarterlyyValues.size(); i++){
				Assert.assertEquals(Integer.parseInt(budgetQuarterlyyValues.get(i)), Integer.parseInt(quarterlyExpectedValues[i]), 5 , "Row title is: " + rowTitle + " index is: " + row);
			}
			
			sheets.selectRepeatReportType("Monthly");
			
			
			
		}
		
		
		
	}
	
	@Test(enabled = true)
	public void validateYearlySheets(){
		navigator = board.getBudgetNavigator();
		secondaryBoard = board.getSecondaryBoard();
		navigator.openInputTab();
		TopHeaderBar topHeaderBar = new TopHeaderBar();
		topHeaderBar.openBudgetSettings();
		BudgetSettings settings = new BudgetSettings();
		String dateFrom = settings.getDateRangeFrom();
		String dateTo = settings.getDateRangeTo();
		String fiscal = settings.getSelectedFiscal().substring(0, 3).toUpperCase();
		settings.clickCancel();
		
		fromMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(dateFrom
				.split("/")[0]));
		fromYear = dateFrom.split("/")[1];
		toMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(dateTo
				.split("/")[0]));
		toYear = dateTo.split("/")[1];
		List<String> expectedDates = BudgetaUtils.getAllMonthsBetweenTwoMonths(
				fromMonth, fromYear, toMonth, toYear, 0,
								false);
		List<Integer> indexOfFiscal = BudgetaUtils.getIndexOfFiscalMonth(fiscal,expectedDates);
		
		navigator.openSheetTab();
		sheets = new Sheets();
		sheets.selectRepeatReportType("Monthly");
		int numberOfRows = sheets.getNumbreOfRows();
		
		for (int row =0 ;row < numberOfRows; row++){
			
			String rowTitle = sheets.getRowTitleByIndex(row);
			int indexOfTitle = sheets.getIndexOfRowName(rowTitle);
			List<String> budgetMonthlyValues = sheets.getAllValuesOfRow(indexOfTitle);
			
			sheets = new Sheets();
			topHeaderBar = new TopHeaderBar();
			topHeaderBar.openHeaderTab(ReportEnum.CASH_FLOW.name());
			sheets.selectRepeatReportType("Yearly");
			
			List<String> budgetYearlyValues = sheets.getAllValuesOfRowByTitle(rowTitle);
			
			String[] yearlyExpectedValues = BudgetaUtils.calculateSheetValues_Yearly(budgetMonthlyValues, indexOfFiscal);
			
			
			
			for(int i=0; i<budgetYearlyValues.size(); i++){
				//Assert.assertEquals(Integer.parseInt(budgetYearlyValues.get(i)), yearlyExpectedValues[i]);
				//Assert.assertEquals(budgetYearlyValues.get(i), yearlyExpectedValues[i]);
				//Assert.assertEquals(budgetYearlyValues.get(i), yearlyExpectedValues[i], "Row title is: " + rowTitle + " index is: " + row);
				Assert.assertEquals(Integer.parseInt(budgetYearlyValues.get(i)), Integer.parseInt(yearlyExpectedValues[i]), 7 , "Row title is: " + rowTitle + " index is: " + row);
			}
			
			sheets.selectRepeatReportType("Monthly");
			
			
		}
		
		
		
	}

}
