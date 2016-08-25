package com.budgeta.test.view;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.galilsoftware.AF.core.listeners.KnownIssue;
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

	@Test(enabled = true)
	public void calculateAllHeadcount() {
		secondaryBoard = new SecondaryBoard();
		Map<String, List<String>> employees = secondaryBoard.allEmployees();

		BudgetNavigator navigator = new BudgetNavigator();
		navigator.openSheetTab();
		Assert.assertEquals(navigator.getOpenTab(), "Sheets");

		sheets = new Sheets();
		Assert.assertTrue(sheets.isDisplayed(), "expected to Sheets to be displayed");

		TopHeaderBar topHeaderBar = new TopHeaderBar();
		topHeaderBar = new TopHeaderBar();
		topHeaderBar.openHeaderTab(ReportEnum.HEADCOUNT.name());
		dates = sheets.getAllDates();
		List<String> employeeCount = new ArrayList<>();

		for (int i = 0; i < dates.size(); i++) {
			int count = 0;
			for (String lines : employees.keySet()) {
				List<String> employeesDates = employees.get(lines);
				for (int j = 0; j < employeesDates.size(); j++) {
					if (employeesDates.get(j).equals(dates.get(i))) {
						count++;
						continue;
					}
				}
			}
			employeeCount.add(count + "");
		}

		for (int x = 0; x < employeeCount.size(); x++) {
			System.out.println(employeeCount.get(x));
		}

		sheets.selectSubReportType("Budget");
		sheets.selectHeadcount("Headcount");
		sheets.selectEmployees("All Headcount");

		List<String> allTotaleValues = sheets.getAllValuesOfTotalRow("Totals");

		for (int y = 0; y < allTotaleValues.size(); y++) {
			Assert.assertEquals(allTotaleValues.get(y), employeeCount.get(y), "Test failed");
		}

	}
	@KnownIssue(bugID = "BUD - 4760")
	@Test(enabled = true)
	public void viewHeadcountAndAverage() {
		BudgetNavigator navigator = new BudgetNavigator();
		navigator.openSheetTab();
		Assert.assertEquals(navigator.getOpenTab(), "Sheets");

		sheets = new Sheets();
		Assert.assertTrue(sheets.isDisplayed(), "expected to Sheets to be displayed");

		TopHeaderBar topHeaderBar = new TopHeaderBar();
		topHeaderBar = new TopHeaderBar();
		topHeaderBar.openHeaderTab(ReportEnum.HEADCOUNT.name());
		dates = sheets.getAllDates();

		Map<String, List<String>> expectedAverage = calculateAverage();

		sheets.selectSubReportType("Budget");
		sheets.selectHeadcount("Headcount & Average");
		sheets.selectEmployees("Cash");

		Map<String, List<String>> actualasAverage = sheets.HeadcountCost();

		for (Map.Entry<String, List<String>> entry1 : actualasAverage.entrySet()) {
			String key = entry1.getKey();
			List<String> value1 = entry1.getValue();
			List<String> value2 = expectedAverage.get(key);

			if (value1.size() == value2.size()) {
				for (int i = 0; i < value1.size(); i++) {
					Assert.assertEquals(value1.get(i), value2.get(i), "index of: " + i + " Row Name is: " + key );
				}
			}
		}

	}
	
	
	@Test(enabled = true)
	public void showHeadcountByCurrency(){
		
	}
	

	private Map<String, List<String>> calculateAverage() {
		Map<String, List<String>> allAverage = new HashMap<String, List<String>>();
		sheets.selectSubReportType("Budget");
		sheets.selectHeadcount("Headcount & Cost");
		sheets.selectEmployees("Cash");

		
		Map<String, List<String>> costValues = sheets.HeadcountCost();
		Map<String, List<String>> headcountValues = sheets.allHeadcount();
		sheets = new Sheets();

		for (Map.Entry<String, List<String>> entry1 : costValues.entrySet()) {
			String key = entry1.getKey();
			List<String> value1 = entry1.getValue();
			List<String> value2 = headcountValues.get(key);

			if (value1.size() == value2.size()) {
				List<String> average = new ArrayList<>();
				for (int i = 0; i < value1.size(); i++) {
					if (value1.get(i).equals("-") || value2.get(i).equals("-")) {
						average.add("-");
					} else {
						int avg = Integer.parseInt(value1.get(i)) / Integer.parseInt(value2.get(i));
						average.add(avg + "");
					}
					allAverage.put(key, average);
				
				}
				
				
			}

			
		}

		return allAverage;
	}

}
