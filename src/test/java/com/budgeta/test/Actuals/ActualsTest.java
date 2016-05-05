package com.budgeta.test.Actuals;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.interceptor.TransactionAttributeEditor;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.Actuals;
import com.budgeta.pom.AddTransaction;
import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.BudgetSettings;
import com.budgeta.pom.DateRange;
import com.budgeta.pom.InnerBar;
import com.budgeta.pom.PreviewBoard;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.pom.Sheets;
import com.budgeta.pom.TopHeaderBar;
import com.budgeta.pom.TransactionTable;
import com.budgeta.test.BudgetaUtils;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.KnownIssue;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestFirst;
import com.galilsoftware.AF.core.listeners.TestNGListener;

@Listeners({ MethodListener.class, TestNGListener.class })
public class ActualsTest extends WrapperTest {
	
	SecondaryBoard secondaryBoard;
	InnerBar innerBar;
	Sheets sheets;
	Actuals actuals;
	String fromMonth;
	String fromYear;
	String toMonth;
	String toYear;
	List<String> dates;
	TopHeaderBar topHeaderBar;

	@BeforeMethod
	private void initTest() {	
		//((JavascriptExecutor)driver).executeScript("");
		
		driver.manage().window().maximize();
		
		
	}
	
	@TestFirst
	@Test(enabled = true)
	public void setBudgetTest() {

//		BudgetNavigator navigator = new BudgetNavigator();
//		navigator.selectRandomBudgeta();
//
//		//innerBar = board.getInnerBar();
//		//Assert.assertTrue(innerBar.isDisplayed(),	"expected to inner bar to be dislayed");
//
//		secondaryBoard.openBudgetSettings();
//		BudgetSettings settings = new BudgetSettings();
//		String dateFrom = settings.getDateRangeFrom();
//		String dateTo = settings.getDateRangeTo();
//		settings.clickCancel();
//
//		fromMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(dateFrom
//				.split("/")[0]));
//		fromYear = dateFrom.split("/")[1];
//		toMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(dateTo
//				.split("/")[0]));
//		toYear = dateTo.split("/")[1];
//		List<String> expectedDates = BudgetaUtils.getAllMonthsBetweenTwoMonths(
//fromMonth, fromYear, toMonth, toYear, 0,
//				false);
//
//		innerBar.openActualsTab();
//		Assert.assertEquals(innerBar.getOpenTab(), "Actuals");
//		sheets = new Sheets();
//		Assert.assertTrue(sheets.isDisplayed(),
//				"expected to View to be displayed");
//		dates = sheets.getAllDates();
//		Assert.assertEquals(dates.size(), expectedDates.size());
//		for (int i = 0; i < expectedDates.size(); i++) {
//			Assert.assertEquals(dates.get(i), expectedDates.get(i));
//		}
		
		BudgetNavigator navigator = new BudgetNavigator();
		Assert.assertTrue(navigator.isDisplayed(), "expected to inner bar to be dislayed");
		navigator.selectRandomBudgeta();
		
		navigator.openInputTab();
		
		topHeaderBar = new TopHeaderBar();
		topHeaderBar.openActalsTab();

	}

	@Test(enabled = true)
	public void addTransaction(){
		actuals = new Actuals();
		int numberOfRows = actuals.getNumbreOfRows();
		for (int row = 0; row < numberOfRows; row++) {
			String rowTitle = actuals.getRowTitleByIndex(row);
			actuals.clickOnLineByIndex(row);
			if (rowTitle.contains(",")) {
				rowTitle = rowTitle.split(",")[1].trim();
				Assert.assertTrue(secondaryBoard.getSelectedLine().contains(
						rowTitle));
			} 
			AddTransaction add = new AddTransaction();
			add.clickAddTransaction();
			
			TransactionTable table = new TransactionTable();
			
			DateRange date = table.openDate();
			date.setHireYear("2016");
			date.setHireMonth("Aug");
			
			table.setAmount("1000");
			
			table.clickSave();
			

//			innerBar.openActualsTab();
//			sheets = new Sheets();
//			List<String> values = sheets.getAllValuesOfRow(row);
//
//			for (int i = 0; i < lineValues.size(); i++) {
//				Assert.assertEquals(lineValues.get(i), values.get(i),
//						"... Row title is: " + rowTitle + ", in header: "
//								+ dates.get(i));
//			}
		}

	}
	

	@Test(enabled = false)
	public void validateTableDataTest() {

		innerBar = board.getInnerBar();
		secondaryBoard = board.getSecondaryBoard();
		innerBar.openActualsTab();
		sheets = new Sheets();

		int numberOfRows = sheets.getNumbreOfRows();
		for (int row = 0; row < numberOfRows; row++) {
			String rowTitle = sheets.getRowTitleByIndex(row);
			sheets.clickOnLineByIndex(row);
			if (rowTitle.contains(",")) {
				rowTitle = rowTitle.split(",")[1].trim();
				Assert.assertTrue(secondaryBoard.getSelectedLine().contains(
						rowTitle));
			} else {
				Assert.assertTrue(rowTitle.contains(secondaryBoard
						.getSelectedLine()));
			}
			PreviewBoard previewBoard = new PreviewBoard();
			List<String> lineValues = new ArrayList<>();
			int startIndex = previewBoard.getIndexOfHeaderDate(fromMonth + " "
					+ fromYear);
			int endIndex = previewBoard.getIndexOfHeaderDate(toMonth + " "
					+ toYear);
			if (startIndex < 0 && endIndex >= 0)
				startIndex = 0;
			else if (startIndex >= 0 && endIndex < 0)
				endIndex = previewBoard.getValuesSize() - 1;

			for (int i = startIndex; i <= endIndex; i++) {
				lineValues.add(previewBoard.getValueByIndex(i));
			}

			innerBar.openActualsTab();
			sheets = new Sheets();
			List<String> values = sheets.getAllValuesOfRow(row);

			for (int i = 0; i < lineValues.size(); i++) {
				Assert.assertEquals(lineValues.get(i), values.get(i),
						"... Row title is: " + rowTitle + ", in header: "
								+ dates.get(i));
			}
		}
	}

	@KnownIssue(bugID = "BUD - 1964")
	@Test(enabled = false, expectedExceptions = AssertionError.class)
	public void validateTotalTest() {

		innerBar = board.getInnerBar();
		innerBar.openActualsTab();
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

}
