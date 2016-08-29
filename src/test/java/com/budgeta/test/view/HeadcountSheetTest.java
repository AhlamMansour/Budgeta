package com.budgeta.test.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.EmplyeeSection;
import com.budgeta.pom.GeneralSection;
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
import com.galilsoftware.AF.core.utilities.WebElementUtils;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

@Listeners({ MethodListener.class, TestNGListener.class })
public class HeadcountSheetTest extends WrapperTest {

	SecondaryBoard secondaryBoard;
	Sheets sheets;
	List<String> dates;
	
	String fromMonth;
	String fromYear;
	String toMonth;
	String toYear;

	String parentFromDate;
	String parentToDate;

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
		Map<String, List<String>> employees = allEmployees();

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
	@Test(enabled = false)
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
	
	
	
	private Map<String, List<String>> allEmployees() {
		List<WebElement> lines = secondaryBoard.getAllLines();
		// List<String> employees = new ArrayList<>();
		Map<String, List<String>> employees = new HashMap<String, List<String>>();
		for (WebElement el : lines) {
			WebElementUtils.hoverOverField(el, driver, null);
			if (WebdriverUtils.hasClass("has-children", el)) {
				try {
					el.click();
					GeneralSection dates = new GeneralSection();
					if (!dates.getHireDateRangeFrom().equals("MM/YYYY")) {
						parentFromDate = dates.getHireDateRangeFrom();
						parentToDate = dates.getHireDateRangeTo();
					}

				} catch (ElementNotVisibleException e) {
					continue;
				}

			}
			if (secondaryBoard.getTypeLine(el).equals("Employee") || secondaryBoard.getTypeLine(el).equals("Contractor")
					|| secondaryBoard.getTypeLine(el).equals("Multiple employees")) {
				String employeeName = el.findElement(By.className("budget-name-text-display")).getText();
				String lineType = el.findElement(By.className("type")).getText();
				el.click();
				GeneralSection dates = new GeneralSection();
				String fromDate = dates.getHireDateRangeFrom();
				String toDate = dates.getHireDateRangeTo();
				if (fromDate.equals("MM/YYYY")) {
					fromDate = parentFromDate;
					toDate = parentToDate;
					fromMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(fromDate.split("/")[0]));
					fromYear = fromDate.split("/")[1];
					toMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(toDate.split("/")[0]));
					toYear = toDate.split("/")[1];
					List<String> expectedDates = BudgetaUtils.getAllMonthsBetweenTwoMonths(fromMonth, fromYear, toMonth, toYear, 0, false);
					// employees.add(employeeName + "," + fromDate + "-" +
					// toDate);
					if (lineType.equals("Multiple employees")) {
						EmplyeeSection employee = new EmplyeeSection();
						String numberOfEmployees = employee.getNumberOfEmployees();
						if (!numberOfEmployees.isEmpty()) {
							int numberOfEmployee = Integer.parseInt(numberOfEmployees);
							for (int i = 0; i < numberOfEmployee; i++) {
								employeeName = el.findElement(By.className("budget-name-text-display")).getText();
								employeeName = WebdriverUtils.getTimeStamp(employeeName);
								employees.put(employeeName, expectedDates);
								employeeName = el.findElement(By.className("budget-name-text-display")).getText();
								employeeName = WebdriverUtils.getTimeStamp(employeeName);
							}
						}
					}
					employees.put(employeeName, expectedDates);
					System.out.println(employees);

				} else {
					fromMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(fromDate.split("/")[0]));
					fromYear = fromDate.split("/")[1];
					toMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(toDate.split("/")[0]));
					toYear = toDate.split("/")[1];
					List<String> expectedDates = BudgetaUtils.getAllMonthsBetweenTwoMonths(fromMonth, fromYear, toMonth, toYear, 0, false);
					// employees.add(employeeName + "," + fromDate + "-" +
					// toDate);

					if (lineType.equals("Multiple employees")) {
						EmplyeeSection employee = new EmplyeeSection();
						String numberOfEmployees = employee.getNumberOfEmployees();
						if (!numberOfEmployees.isEmpty()) {
							int numberOfEmployee = Integer.parseInt(numberOfEmployees);
							for (int i = 0; i < numberOfEmployee - 1; i++) {
								employeeName = el.findElement(By.className("budget-name-text-display")).getText();
								employeeName = WebdriverUtils.getTimeStamp(employeeName);
								employees.put(employeeName, expectedDates);
								employeeName = el.findElement(By.className("budget-name-text-display")).getText();
								employeeName = WebdriverUtils.getTimeStamp(employeeName);
								
							}
						}
					}

					employees.put(employeeName, expectedDates);
					System.out.println(employees);
				}

			}

		}
		return employees;

	}

}
