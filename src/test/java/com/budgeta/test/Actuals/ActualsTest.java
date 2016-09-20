package com.budgeta.test.Actuals;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.Actuals;
import com.budgeta.pom.AddTransaction;
import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.BudgetSettings;
import com.budgeta.pom.DateRange;
import com.budgeta.pom.InnerBar;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.pom.SummaryTable;
import com.budgeta.pom.TopHeaderBar;
import com.budgeta.pom.TransactionTable;
import com.budgeta.test.BudgetaUtils;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestFirst;
import com.galilsoftware.AF.core.listeners.TestNGListener;

@Listeners({ MethodListener.class, TestNGListener.class })
public class ActualsTest extends WrapperTest {

	SecondaryBoard secondaryBoard;
	InnerBar innerBar;
	Actuals actuals;
	String fromMonth;
	String fromYear;
	String toMonth;
	String toYear;
	List<String> dates;
	TopHeaderBar topHeaderBar;

	/*
	 * Not Completed 
	 * */
	 
	@TestFirst
	@Test(enabled = true)
	public void setBudgetTest() {

		BudgetNavigator navigator = new BudgetNavigator();
		Assert.assertTrue(navigator.isDisplayed(), "expected to inner bar to be dislayed");
		navigator.selectRandomBudgeta();

		navigator.openInputTab();

		topHeaderBar = new TopHeaderBar();
		topHeaderBar.openActalsTab();

	}

	@Test(enabled = true, priority = 1)
	public void addTransaction() {
		actuals = new Actuals();
		int numberOfRows = actuals.getNumbreOfRows();
		for (int row = 0; row < numberOfRows; row++) {
			String rowTitle = actuals.getRowTitleByIndex(row);
			actuals.clickOnLineByIndex(row);
			if (rowTitle.contains(",")) {
				rowTitle = rowTitle.split(",")[1].trim();
				Assert.assertTrue(secondaryBoard.getSelectedLineName().contains(rowTitle));
			}
			AddTransaction transactio = new AddTransaction();
			transactio.clickTransactionTab();
			transactio.clickAddTransaction();

			TransactionTable table = new TransactionTable();

			DateRange date = table.openDate();
			date.setHireYear("2016");
			date.setHireMonth("Aug");

			table.setAmount("1000");

			table.clickSave();

			System.out.println(table.getAmountValue());
			System.out.println(table.getTotalValue());
			System.out.println(table.getTransactionDate());
//			String Month = BudgetaUtils.getMonthWithIndex(Integer.parseInt(table.getTransactionDate().split("/")[0]));
//			String Year = table.getTransactionDate().split("/")[1];

			transactio.clickSummaryTab();

			SummaryTable summary = new SummaryTable();

			topHeaderBar.openBudgetSettings();
			BudgetSettings settings = new BudgetSettings();
			String dateFrom = settings.getDateRangeFrom();
			String dateTo = settings.getDateRangeTo();
			settings.clickCancel();

			fromMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(dateFrom.split("/")[0]));
			fromYear = dateFrom.split("/")[1];
			toMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(dateTo.split("/")[0]));
			toYear = dateTo.split("/")[1];
			List<String> expectedDates = BudgetaUtils.getAllMonthsBetweenTwoMonths(fromMonth, fromYear, toMonth, toYear, 0, false);

			summary = new SummaryTable();
			dates = summary.getAllDates();

			Assert.assertEquals(dates.size(), expectedDates.size());

			System.out.println(summary.getAllValuesOfRow(row));

			transactio = new AddTransaction();
			transactio.clickTransactionTab();

		}

	}

	@Test(enabled = true, priority = 2)
	public void ValidateActualToltal() {
		actuals = new Actuals();

		int numberOfRows = actuals.getNumbreOfRows();
		AddTransaction transaction = new AddTransaction();
		for (int row = 0; row < numberOfRows; row++) {
			String rowTitle = actuals.getRowTitleByIndex(row);
			actuals.clickOnLineByIndex(row);
			if (rowTitle.contains(",")) {
				rowTitle = rowTitle.split(",")[1].trim();
				Assert.assertTrue(secondaryBoard.getSelectedLineName().contains(rowTitle));
			}

			transaction.clickTransactionTab();
			transaction.clickAddTransaction();

			TransactionTable table = new TransactionTable();

			DateRange date = table.openDate();
			date.setHireYear("2016");
			date.setHireMonth("Aug");

			table.setAmount("1000");

			table.clickSave();

			System.out.println(table.getAmountValue());
			System.out.println(table.getTotalValue());
			System.out.println(table.getTransactionDate());
			String Month = BudgetaUtils.getMonthWithIndex(Integer.parseInt(table.getTransactionDate().split("/")[0]));
			String Year = table.getTransactionDate().split("/")[1];

			String actualsDate = Month + " " + Year;

			String totalRowValue = table.getTotalValue();
		//	String amountRowValue = table.getAmountValue();

			transaction.clickSummaryTab();

			SummaryTable summary = new SummaryTable();

			// Assert.assertEquals(summary.getTotalOfRow(row), totalRowValue);

			topHeaderBar.openBudgetSettings();
			BudgetSettings settings = new BudgetSettings();
			String dateFrom = settings.getDateRangeFrom();
			String dateTo = settings.getDateRangeTo();
			settings.clickCancel();

			fromMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(dateFrom.split("/")[0]));
			fromYear = dateFrom.split("/")[1];
			toMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(dateTo.split("/")[0]));
			toYear = dateTo.split("/")[1];
			List<String> expectedDates = BudgetaUtils.getAllMonthsBetweenTwoMonths(fromMonth, fromYear, toMonth, toYear, 0, false);

			summary = new SummaryTable();
			dates = summary.getAllDates();

			Assert.assertEquals(dates.size(), expectedDates.size());

			for (int i = 0; i < expectedDates.size(); i++) {
				String getDate = dates.get(i);
				if (getDate.equals(actualsDate)) {
					Assert.assertEquals(summary.getActualsTotalOfRow(row, "Actual"), totalRowValue,
							"... Row title is: " + rowTitle + ", in header: " + dates.get(i));

				}
			}

			System.out.println(summary.getAllValuesOfRow(row));

			transaction = new AddTransaction();
			transaction.clickTransactionTab();

		}

	}

	@Test(enabled = true, priority = 3)
	public void ValidateActualSummary() {
		actuals = new Actuals();

		topHeaderBar.openBudgetSettings();
		BudgetSettings settings = new BudgetSettings();
		String dateFrom = settings.getDateRangeFrom();
		String dateTo = settings.getDateRangeTo();

		String currency = settings.getSelectedCurrency();
		settings.clickCancel();

		AddTransaction transaction = new AddTransaction();
		transaction.selectSubReportType("Cash contribution");

		int numberOfRows = actuals.getNumbreOfRows();
		for (int row = 0; row < numberOfRows; row++) {
			String rowTitle = actuals.getRowTitleByIndex(row);
			actuals.clickOnLineByIndex(row);
			if (rowTitle.contains(",")) {
				rowTitle = rowTitle.split(",")[1].trim();
				Assert.assertTrue(secondaryBoard.getSelectedLineName().contains(rowTitle));
			}

			transaction.clickTransactionTab();

			TransactionTable table = new TransactionTable();

			int transactionRows = table.getNumberOfTransactionRows();
			if (transactionRows == 0) {
				transaction.clickAddTransaction();
				DateRange date = table.openDate();
				date.setHireYear("2016");
				date.setHireMonth("Aug");

				table.setAmount("1000");

				if (!currency.equals(table.getCurrentCurrency())) {
					table.selectCurrency(currency);

				}

				table.clickSave();

				System.out.println(table.getAmountValue());
				System.out.println(table.getTotalValue());
				System.out.println(table.getTransactionDate());
				String Month = BudgetaUtils.getMonthWithIndex(Integer.parseInt(table.getTransactionDate().split("/")[0]));
				String Year = table.getTransactionDate().split("/")[1];
				String actualsDate = Month + " " + Year;

				String totalRowValue = table.getTotalValue();
		//		String amountRowValue = table.getAmountValue();
				transaction.clickSummaryTab();
				transaction = new AddTransaction();

				transaction.selectSubReportType("Cash contribution");

				SummaryTable summary = new SummaryTable();

				fromMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(dateFrom.split("/")[0]));
				fromYear = dateFrom.split("/")[1];
				toMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(dateTo.split("/")[0]));
				toYear = dateTo.split("/")[1];
				List<String> expectedDates = BudgetaUtils.getAllMonthsBetweenTwoMonths(fromMonth, fromYear, toMonth, toYear, 0, false);

				summary = new SummaryTable();

				dates = summary.getAllDates();

				Assert.assertEquals(dates.size(), expectedDates.size(), "Summary dates not equals Expected dates as in budget settings ");

				for (int i = 0; i < expectedDates.size(); i++) {
					String getDate = dates.get(i);
					if (getDate.equals(actualsDate)) {
						System.out.println("****************");
						System.out.println(summary.getRowTitleByIndex(row));
						Assert.assertEquals(summary.getActualsAmountOfRow(row, "Actuals"), totalRowValue, "... Row title is: " + rowTitle + ", in header: "
								+ dates.get(i));

					}
				}

			}

			else if (transactionRows > 0) {
				if (table.sameDateInAllLines()) {
					String totalRowValue = table.getTotalValue();

					String Month = BudgetaUtils.getMonthWithIndex(Integer.parseInt(table.getTransactionDate().split("/")[0]));
					String Year = table.getTransactionDate().split("/")[1];

					String actualsDate = Month + " " + Year;

					transaction.clickSummaryTab();
					transaction = new AddTransaction();

					transaction.selectSubReportType("Cash contribution");

					SummaryTable summary = new SummaryTable();

					fromMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(dateFrom.split("/")[0]));
					fromYear = dateFrom.split("/")[1];
					toMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(dateTo.split("/")[0]));
					toYear = dateTo.split("/")[1];
					List<String> expectedDates = BudgetaUtils.getAllMonthsBetweenTwoMonths(fromMonth, fromYear, toMonth, toYear, 0, false);

					summary = new SummaryTable();

					dates = summary.getAllDates();

					Assert.assertEquals(dates.size(), expectedDates.size(), "Summary dates not equals Expected dates as in budget settings ");

					for (int i = 0; i < expectedDates.size(); i++) {
						String getDate = dates.get(i);
						if (getDate.equals(actualsDate)) {
							System.out.println("****************");
							Assert.assertEquals(summary.getActualsAmountOfRow(row, "Actuals"), totalRowValue, "... Row title is: " + rowTitle + ", in header: "
									+ dates.get(i));

						}
					}
				}
			}
		}

	}


}
