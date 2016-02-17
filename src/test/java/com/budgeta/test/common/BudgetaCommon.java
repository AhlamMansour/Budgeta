package com.budgeta.test.common;

import org.testng.Assert;
import org.testng.annotations.Listeners;

import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.BudgetaBoard;
import com.budgeta.pom.BuildCompanyBudgetPopup;
import com.budgeta.pom.DateRange;
import com.budgeta.pom.GeneralSection;
import com.budgeta.pom.NewBudgetPopup;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.test.BudgetaTest;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestNGListener;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

@Listeners({ MethodListener.class, TestNGListener.class })
public class BudgetaCommon extends WrapperTest{

	String AccountNumber = "Yes";
	String ProductField = "Yes";
	String GeographyField = "Yes";
	String setFromYear = "2015";
	String setFromMonth = "Jan";
	String setToYear = "2018";
	String setToMonth = "Dec";
	String currency = "USD";
	public void createBudget(){
		//driver.get(baseURL);
		
		BudgetaBoard board = new BudgetaBoard();
		SecondaryBoard secondaryBoard = board.getSecondaryBoard();
		BudgetNavigator navigator = new BudgetNavigator();
		//WebdriverUtils.waitForBudgetaLoadBar(driver);
		navigator.openInputTab();
		NewBudgetPopup popup = navigator.addNewBudget();
		String budgetaName = "Test Budget";
		if(!budgetaName.isEmpty())
			budgetaName = WebdriverUtils.getTimeStamp(budgetaName+"_");
		popup.setName(budgetaName);
		popup.setType(popup.getBudgetaType("Company Budget"));
		
		DateRange from = popup.openDateRangeFrom();
		from.setYear(setFromYear);
		from.setMonth(setFromMonth);
		
		//DateRange to = popup.openDateRangeTo();
		from.setYear(setToYear);
		from.setMonth(setToMonth);
		
		
		from.closeDatePopup();

			popup.clickContinue(true);
			popup.setCurrency(currency);
		
			popup.setFiscalYearStartOn("August");
			popup.setbeginninhCashBalance("123458222");
		
			if(AccountNumber.equalsIgnoreCase("Yes"))
				popup.selectAccountNumberField();
			if(ProductField.equalsIgnoreCase("Yes"))
				popup.selectProductField();
			if(GeographyField.equalsIgnoreCase("Yes"))
				popup.selectGeographyField();
		
			popup.clickCreate();
			
			BuildCompanyBudgetPopup budgetPopup = new BuildCompanyBudgetPopup();
			budgetPopup.clickCancel();
			
			navigator.openInputTab();
			
		
			Assert.assertEquals(secondaryBoard.getSelectedBudgetName(), budgetaName);
		
			GeneralSection general = new GeneralSection();
		
			Assert.assertEquals(general.getGeneralDateRangeFrom(), BudgetaTest.getDateByNumbersFormat(setFromMonth, setFromYear));
			Assert.assertEquals(general.getGeneralDateRangeTo(), BudgetaTest.getDateByNumbersFormat(setToMonth, setToYear));
			Assert.assertEquals(general.getSelectedCurrency(), currency);
			//secondaryBoard.addAllLines();
			
	
		}
		
	
}
