package com.budgeta.test.view;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.Actuals;
import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.BudgetSettings;
import com.budgeta.pom.InnerBar;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.pom.Sheets;
import com.budgeta.test.BudgetaUtils;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestFirst;
import com.galilsoftware.AF.core.listeners.TestNGListener;

@Listeners({ MethodListener.class, TestNGListener.class })
public class ProfitAndLossReportTest extends WrapperTest{
	
	SecondaryBoard secondaryBoard;
	InnerBar innerBar;
	Sheets sheets;
	Actuals actuals;
	String fromMonth;
	String fromYear;
	String toMonth;
	String toYear;
	List<String> dates;

	@TestFirst
	@Test(enabled = true)
	public void setBudgetTest() {

		secondaryBoard = board.getSecondaryBoard();
		BudgetNavigator navigator = new BudgetNavigator();
		navigator.selectRandomBudgetWithPrefix("budget7_144215547406");

		innerBar = board.getInnerBar();
		Assert.assertTrue(innerBar.isDisplayed(),
				"expected to inner bar to be dislayed");

		secondaryBoard.openBudgetSettings();
		BudgetSettings settings = new BudgetSettings();
		String dateFrom = settings.getDateRangeFrom();
		String dateTo = settings.getDateRangeTo();
		settings.clickCancel();

		fromMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(dateFrom
				.split("/")[0]));
		fromYear = dateFrom.split("/")[1];
		toMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(dateTo
				.split("/")[0]));
		toYear = dateTo.split("/")[1];
		List<String> expectedDates = BudgetaUtils.getAllMonthsBetweenTwoMonths(
				fromMonth, fromYear, toMonth, toYear);

		innerBar.openViewTab();
		Assert.assertEquals(innerBar.getOpenTab(), "View");
		sheets = new Sheets();
		Assert.assertTrue(sheets.isDisplayed(),
				"expected to View to be displayed");
		dates = sheets.getAllDates();
		Assert.assertEquals(dates.size(), expectedDates.size());
		for (int i = 0; i < expectedDates.size(); i++) {
			Assert.assertEquals(dates.get(i), expectedDates.get(i));
		}
	}
	

	@Test(enabled = true)
	public void validateBudgetVsActuals() {
		innerBar = board.getInnerBar();
		secondaryBoard = board.getSecondaryBoard();
		innerBar.openViewTab();
		sheets = new Sheets();
		sheets.selectReportType("P & L");
		sheets.selectSubReportType("Budget");
		
	}
	
	
	

}
