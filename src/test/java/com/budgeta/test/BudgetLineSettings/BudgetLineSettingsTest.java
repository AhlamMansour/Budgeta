package com.budgeta.test.BudgetLineSettings;

import org.testng.annotations.Test;

import com.budgeta.pom.BudgetLineSettting;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.test.WrapperTest;

public class BudgetLineSettingsTest extends WrapperTest {
	
	
	
	
	
	@Test(enabled = true)
	public void changeSettingTest(){
		
		BudgetLineSettting budgetLineSettting = new BudgetLineSettting();
		
		//SecondaryBoard secondary = board.getSecondaryBoard();
		//secondary.selectRandomBudgeta();
		
		
		budgetLineSettting.clickDuplicateBudgetLine();
	
		
	}

}
