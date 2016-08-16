package com.budgeta.test.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.pom.Sheets;
import com.budgeta.pom.TopHeaderBar;
import com.budgeta.pom.Enum.ReportEnum;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestFirst;
import com.galilsoftware.AF.core.listeners.TestNGListener;

@Listeners({ MethodListener.class, TestNGListener.class })
public class HeadcountSheetTest extends WrapperTest {

	SecondaryBoard secondaryBoard;
	Sheets sheets;
	List<String> dates;

	@TestFirst
	@Test(enabled = true)
	public void setBudgetTest() {

		BudgetNavigator navigator = new BudgetNavigator();
		Assert.assertTrue(navigator.isDisplayed(), "expected to inner bar to be dislayed");
		// navigator.selectRandomBudgeta();
		navigator.selectRandomBudgetWithPrefix("Copy of TEST Forecast");
		navigator.openInputTab();
		TopHeaderBar topHeaderBar = new TopHeaderBar();
		topHeaderBar.openBaseTab();

	}

//	@Test(enabled = false)
//	public void calculateAllHeadcount() {
//		secondaryBoard = new SecondaryBoard();
//		Map<String, List<String>> employees = secondaryBoard.allEmployees();
//
//		BudgetNavigator navigator = new BudgetNavigator();
//		navigator.openSheetTab();
//		Assert.assertEquals(navigator.getOpenTab(), "Sheets");
//
//		sheets = new Sheets();
//		Assert.assertTrue(sheets.isDisplayed(), "expected to Sheets to be displayed");
//
//		TopHeaderBar topHeaderBar = new TopHeaderBar();
//		topHeaderBar = new TopHeaderBar();
//		topHeaderBar.openHeaderTab(ReportEnum.HEADCOUNT.name());
//		dates = sheets.getAllDates();
//		List<String> employeeCount = new ArrayList<>();
//		
//		for (int i = 0; i < dates.size(); i++) {
//			int count = 0;
//			for (String lines : employees.keySet()) {
//				List<String> employeesDates = employees.get(lines);
//				for (int j = 0; j < employeesDates.size(); j++) {
//					if (employeesDates.get(j).equals(dates.get(i))) {
//						count++;
//						continue;
//					}
//				}
//			}
//			employeeCount.add(count+"");
//		}
//
//		for (int x = 0; x < employeeCount.size(); x++) {
//			System.out.println(employeeCount.get(x));
//		}
//
//		sheets.selectSubReportType("Budget");
//		sheets.selectHeadcount("Headcount");
//		sheets.selectEmployees("All Headcount");
//		
//		
//		List<String> allTotaleValues = sheets.getAllValuesOfTotalRow("Totals");
//		
//		for(int y = 0 ; y < allTotaleValues.size(); y++){
//			Assert.assertEquals(allTotaleValues.get(y), employeeCount.get(y), "Test failed");	
//		}
//	
//	}
//	
//	
//	@Test(enabled = true)
//	public void viewHeadcountAndAverage() {
//		BudgetNavigator navigator = new BudgetNavigator();
//		navigator.openSheetTab();
//		Assert.assertEquals(navigator.getOpenTab(), "Sheets");
//
//		sheets = new Sheets();
//		Assert.assertTrue(sheets.isDisplayed(), "expected to Sheets to be displayed");
//
//		TopHeaderBar topHeaderBar = new TopHeaderBar();
//		topHeaderBar = new TopHeaderBar();
//		topHeaderBar.openHeaderTab(ReportEnum.HEADCOUNT.name());
//		dates = sheets.getAllDates();
//
//		sheets.selectSubReportType("Budget");
//		sheets.selectHeadcount("Headcount & Cost");
//		sheets.selectEmployees("Cash");
//		
//		Map<String, List<String>> costValues = sheets.HeadcountCost();
//		
//		
//		System.out.println("test");
//		
//		
//
//	}

}
