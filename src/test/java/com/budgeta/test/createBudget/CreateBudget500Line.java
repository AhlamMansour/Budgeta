package com.budgeta.test.createBudget;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.SessionNotFoundException;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.BudgetaBoard;
import com.budgeta.pom.BuildCompanyBudgetPopup;
import com.budgeta.pom.DateRange;
import com.budgeta.pom.GeneralSection;
import com.budgeta.pom.NewBudgetPopup;
import com.budgeta.pom.RevenuesAddSubLine;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.test.BudgetaTest;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestFirst;
import com.galilsoftware.AF.core.listeners.TestNGListener;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

@Listeners({ MethodListener.class, TestNGListener.class })
public class CreateBudget500Line extends WrapperTest {
	String AccountNumber = "Yes";
	String ProductField = "Yes";
	String GeographyField = "Yes";
	String setFromYear = "2015";
	String setFromMonth = "Jan";
	String setToYear = "2018";
	String setToMonth = "Dec";
	String currency = "USD";

	String OperationalExpenses = "Operational Expenses";
	String OperationalExpensesSubline = "Salary & wages";
	String salary_and_wages = "Salary & wages";
	String employee = "employee_";
	String OtherIncomeAndExpensesLine = "Other income and expenses";
	String OtherIncomeAndExpensesSubLine = "Other income";
	String OtherIncomeAndExpensesSub_SubLine = "Simple_";
	String revenuesSubLine = WebdriverUtils.getTimeStamp("revenues_");
	String costOfRevenuesSubLine = WebdriverUtils.getTimeStamp("costOfRevenues_");
	
	
	@TestFirst
	@Test
	public void createBudget() {
		driver.get(baseURL);

		BudgetaBoard board = new BudgetaBoard();
		SecondaryBoard secondaryBoard = board.getSecondaryBoard();
		BudgetNavigator navigator = new BudgetNavigator();
		// // WebdriverUtils.waitForBudgetaLoadBar(driver);
		// navigator.openInputTab();
		// //navigator.selectRandomBudgetWithPrefix("Test Budget for 500 line_");
		// NewBudgetPopup popup = navigator.addNewBudget();
		// String budgetaName = "Test Budget for 500 line";
		// if (!budgetaName.isEmpty())
		// budgetaName = WebdriverUtils.getTimeStamp(budgetaName + "_");
		// popup.setName(budgetaName);
		// popup.setType(popup.getBudgetaType("Company Budget"));
		//
		// DateRange from = popup.openDateRangeFrom();
		// from.setYear(setFromYear);
		// from.setMonth(setFromMonth);
		//
		// // DateRange to = popup.openDateRangeTo();
		// from.setYear(setToYear);
		// from.setMonth(setToMonth);
		//
		// from.closeDatePopup();
		//
		// popup.clickContinue(true);
		// popup.setCurrency(currency);
		//
		// popup.setFiscalYearStartOn("August");
		// popup.setbeginninhCashBalance("123458222");
		//
		// if (AccountNumber.equalsIgnoreCase("Yes"))
		// popup.selectAccountNumberField();
		// if (ProductField.equalsIgnoreCase("Yes"))
		// popup.selectProductField();
		// if (GeographyField.equalsIgnoreCase("Yes"))
		// popup.selectGeographyField();
		//
		// popup.clickCreate();
		//
		// BuildCompanyBudgetPopup budgetPopup = new BuildCompanyBudgetPopup();
		// budgetPopup.clickCancel();
		//
		// navigator.openInputTab();
		//
		// Assert.assertEquals(secondaryBoard.getSelectedBudgetName(),
		// budgetaName);
		//
		// GeneralSection general = new GeneralSection();
		//
		// Assert.assertEquals(general.getGeneralDateRangeFrom(),
		// BudgetaTest.getDateByNumbersFormat(setFromMonth, setFromYear));
		// Assert.assertEquals(general.getGeneralDateRangeTo(),
		// BudgetaTest.getDateByNumbersFormat(setToMonth, setToYear));
		// Assert.assertEquals(general.getSelectedCurrency(), currency);
		//
		// secondaryBoard.addAllBudgetLines();
		navigator.openInputTab();
		navigator.selectRandomBudgetWithPrefix("Test Budget for 500 line_");
		int NumberOfLines = secondaryBoard.getNumberOfLines();

		System.out.println("the number of the line is: " + secondaryBoard.getNumberOfLines());

		secondaryBoard = new SecondaryBoard();
		//secondaryBoard.openAddChild(OperationalExpensesSubline, 2);
		//secondaryBoard.openAddChild(OtherIncomeAndExpensesSubLine, 2);
//		secondaryBoard.addSubLine("Revenues");
		secondaryBoard.addSubLine("Cost of Revenues");

		try {
			while (NumberOfLines < 600) {
				RevenuesAddSubLine subLine = new RevenuesAddSubLine();
//				employee = WebdriverUtils.getTimeStamp(employee);
//				subLine.setName(employee);
//				OtherIncomeAndExpensesSub_SubLine = WebdriverUtils.getTimeStamp(OtherIncomeAndExpensesSub_SubLine);
//				subLine.setName(OtherIncomeAndExpensesSub_SubLine);
				//subLine.setName(revenuesSubLine);
				subLine.setName(costOfRevenuesSubLine);
				subLine.clickAdd();
				NumberOfLines++;
			}
		}catch (Throwable ex){
			System.err.println("****************************");
			ex.printStackTrace();
			System.err.println("****************************");
			driver.get(baseURL);

			board = new BudgetaBoard();
			secondaryBoard = board.getSecondaryBoard();
			navigator = new BudgetNavigator();
			// WebdriverUtils.waitForBudgetaLoadBar(driver);
			navigator.openInputTab();
			navigator.selectRandomBudgetWithPrefix("Test Budget for 500 line_");
			NumberOfLines = secondaryBoard.getNumberOfLines();

			System.out.println("the number of the line is: " + secondaryBoard.getNumberOfLines());

			secondaryBoard = new SecondaryBoard();
			//secondaryBoard.openAddChild(OperationalExpensesSubline, 2);
			//secondaryBoard.openAddChild(OtherIncomeAndExpensesSubLine, 2);
			//secondaryBoard.addSubLine("Revenues");
			secondaryBoard.addSubLine("Cost of Revenues");

			while (NumberOfLines < 600) {
				RevenuesAddSubLine subLine = new RevenuesAddSubLine();
//				employee = WebdriverUtils.getTimeStamp(employee);
//				subLine.setName(employee);
				//subLine.setName(revenuesSubLine);
				subLine.setName(costOfRevenuesSubLine);
				subLine.clickAdd();
				NumberOfLines++;
			}
		}
		//		 if (ex instanceof SessionNotFoundException){
		//		 driver.get(baseURL);
		//		 }
		// board = new BudgetaBoard();
		// secondaryBoard = board.getSecondaryBoard();
		// navigator = new BudgetNavigator();
		// // WebdriverUtils.waitForBudgetaLoadBar(driver);
		// navigator.openInputTab();
		// navigator.selectRandomBudgetWithPrefix("Test Budget for 500 line_");
		// NumberOfLines = secondaryBoard.getNumberOfLines();
		//
		// System.out.println("the number of the line is: " +
		// secondaryBoard.getNumberOfLines());
		//
		// secondaryBoard = new SecondaryBoard();
		// secondaryBoard.openAddChild(OperationalExpensesSubline, 2);
		//
		// while(NumberOfLines < 200){
		// RevenuesAddSubLine subLine = new RevenuesAddSubLine();
		// employee = WebdriverUtils.getTimeStamp(employee);
		// subLine.setName(employee);
		// subLine.clickAdd();
		// NumberOfLines++;
		// } }
		// else if(ex instanceof WebDriverException){
		// driver.get(baseURL);
		//
		// board = new BudgetaBoard();
		// secondaryBoard = board.getSecondaryBoard();
		// navigator = new BudgetNavigator();
		// // WebdriverUtils.waitForBudgetaLoadBar(driver);
		// navigator.openInputTab();
		// navigator.selectRandomBudgetWithPrefix("Test Budget for 500 line_");
		// NumberOfLines = secondaryBoard.getNumberOfLines();
		//
		// System.out.println("the number of the line is: " +
		// secondaryBoard.getNumberOfLines());
		//
		// secondaryBoard = new SecondaryBoard();
		// secondaryBoard.openAddChild(OperationalExpensesSubline, 2);
		//
		// while(NumberOfLines < 200){
		// RevenuesAddSubLine subLine = new RevenuesAddSubLine();
		// employee = WebdriverUtils.getTimeStamp(employee);
		// subLine.setName(employee);
		// subLine.clickAdd();
		// NumberOfLines++;
		// }
		// }
		// }

		System.out.println("the number of the line is: " + secondaryBoard.getNumberOfLines());

	}
}
