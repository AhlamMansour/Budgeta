package com.budgeta.test.createBudget;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.BudgetaBoard;
import com.budgeta.pom.RevenuesAddSubLine;
import com.budgeta.pom.SecondaryBoard;
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

		navigator.openInputTab();
		navigator.selectRandomBudgetWithPrefix("Test Budget for 500 line_");
		int NumberOfLines = secondaryBoard.getNumberOfLines();

		System.out.println("the number of the line is: " + secondaryBoard.getNumberOfLines());

		secondaryBoard = new SecondaryBoard();
		secondaryBoard.addSubLine("Cost of Revenues");

		try {
			while (NumberOfLines < 600) {
				RevenuesAddSubLine subLine = new RevenuesAddSubLine();
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

			navigator.openInputTab();
			navigator.selectRandomBudgetWithPrefix("Test Budget for 500 line_");
			NumberOfLines = secondaryBoard.getNumberOfLines();

			System.out.println("the number of the line is: " + secondaryBoard.getNumberOfLines());

			secondaryBoard = new SecondaryBoard();

			secondaryBoard.addSubLine("Cost of Revenues");

			while (NumberOfLines < 600) {
				RevenuesAddSubLine subLine = new RevenuesAddSubLine();

				subLine.setName(costOfRevenuesSubLine);
				subLine.clickAdd();
				NumberOfLines++;
			}
		}

		System.out.println("the number of the line is: " + secondaryBoard.getNumberOfLines());

	}
}
