package com.budgeta.test.budgetaStructure;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.BudgetaBoard;
import com.budgeta.pom.DateRange;
import com.budgeta.pom.GeneralSection;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.DataProviderParams;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestFirst;
import com.galilsoftware.AF.core.listeners.TestNGListener;

@Listeners({ MethodListener.class, TestNGListener.class })
public class BudgetaStructureTest extends WrapperTest{

	SecondaryBoard secondaryBoard;
	
	@TestFirst
	@Test(enabled = true)
	public void setBudgetTest(){
		
		secondaryBoard = board.getSecondaryBoard();
		secondaryBoard.selectRandomBudgetWithPrefix("budget7_");
		secondaryBoard.addAllLines();
		//secondaryBoard.addSubLine(lineTitle);
	}
	
	
	@Test(dataProvider = "ExcelFileLoader", enabled = true)
	@DataProviderParams(sheet = "BudgetaForm" , area = "GeneralRevenues")
	public void GeneralRevenuesTest(Hashtable<String, String> data) {
		board = new BudgetaBoard();
		secondaryBoard = board.getSecondaryBoard();
		
		secondaryBoard.clickOnLine("Revenues");
		GeneralSection general = new GeneralSection();
		
		Assert.assertTrue(general.isDisplayed(), "expected general section to be displayed");
		
		DateRange from = general.openDateRangeFrom();
		from.setYear(data.get("DateRange_from_year"));
		from.setMonth(data.get("DateRange_from_month"));
		
		DateRange to = general.openDateRangeTo();
		to.setYear(data.get("DateRange_to_year"));
		to.setMonth(data.get("DateRange_to_month"));
		
		general.selectCurrency(data.get("Currency"));
		
		general.setAccountNumberInRowByIndex(1, data.get("AccountNumber"));
		general.setProduct(data.get("Product"));
		general.setNotes(data.get("Notes"));
		
		if(data.get("ShouldPass").equals("FALSE")){
			Assert.assertTrue(general.isGeneralHasError(), "expected to error in general section");
		}
		else{
			
		}
	}
	
	
	
}
