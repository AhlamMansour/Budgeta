package com.budgeta.test.createBudget;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.BudgetaBoard;
import com.budgeta.pom.DateRange;
import com.budgeta.pom.GeneralSection;
import com.budgeta.pom.NewBudgetPopup;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.test.BudgetaTest;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.DataProviderParams;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestNGListener;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;



@Listeners({ MethodListener.class, TestNGListener.class })
public class CreateBudgetTest extends WrapperTest{
	
	
	@Test(dataProvider = "ExcelFileLoader", enabled = true)
	@DataProviderParams(sheet = "CreateBudget" , area = "CreateNewBudget")
	public void createBudgetTest(Hashtable<String, String> data) {
	
		driver.get(baseURL);
		
		BudgetaBoard board = new BudgetaBoard();
		SecondaryBoard secondaryBoard = board.getSecondaryBoard();
		
		NewBudgetPopup popup = secondaryBoard.addBudgeta();
		
		String budgetaName = WebdriverUtils.getTimeStamp(data.get("name")+"_");
		popup.setName(budgetaName);
		popup.setType(popup.getBudgetaType(data.get("Type")));
		
		DateRange from = popup.openDateRangeFrom();
		from.setYear(data.get("DateRange_year_from"));
		from.setMonth(data.get("DateRange_month_from"));
		
		DateRange to = popup.openDateRangeTo();
		to.setYear(data.get("DateRange_year_to"));
		to.setMonth(data.get("DateRange_month_to"));
		
		//error in the first page
		if(data.get("ContinueShouldPass").equals("FALSE")){
			popup.clickContinue(false);
			Assert.assertEquals(popup.getVisibleErrorText(), "Start Date must be before End Date");
		}
		//else, continue to the next page
		else{
			popup.clickContinue(true);
			popup.setCurrency(data.get("Currency"));
		
			popup.setFiscalYearStartOn(data.get("FiscalYearStartsOn"));
			popup.setbeginninhCashBalance(data.get("BeginningCashBalance"));
		
			if(data.get("AccountNumber").equalsIgnoreCase("Yes"))
				popup.selectAccountNumberField();
			if(data.get("ProductField").equalsIgnoreCase("Yes"))
				popup.selectProductField();
			if(data.get("GeographyField").equalsIgnoreCase("Yes"))
				popup.selectGeographyField();
		
			popup.clickCreate();
		
			Assert.assertEquals(secondaryBoard.getNameOfSelectedBudgeta(), budgetaName);
		
			GeneralSection general = new GeneralSection();
		
			Assert.assertEquals(general.getDateRangeFrom(), BudgetaTest.getDateByNumbersFormat(data.get("DateRange_year_from"), data.get("DateRange_year_from")));
			Assert.assertEquals(general.getDateRangeTo(), BudgetaTest.getDateByNumbersFormat(data.get("DateRange_year_to"), data.get("DateRange_year_to")));
			Assert.assertEquals(general.getSelectedCurrency(), data.get("Currency"));
		}	
	
		}
	
}
