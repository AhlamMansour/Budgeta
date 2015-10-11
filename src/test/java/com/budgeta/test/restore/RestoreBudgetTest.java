package com.budgeta.test.restore;

import org.testng.annotations.Test;

import com.budgeta.pom.MenuTrigger;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.TestFirst;

public class RestoreBudgetTest extends WrapperTest{
	
	
	@TestFirst
	@Test(enabled = true)
	public void CreateSettingTest() {
		SecondaryBoard secondary = board.getSecondaryBoard();
		secondary.selectRandomBudgetWithPrefix("_");
		secondary = new SecondaryBoard();

	}
	
	

	
	
	@Test(enabled = true)
	public void restoreBudgetTest() throws InterruptedException {
		Thread.sleep(1000);
		SecondaryBoard secondary = board.getSecondaryBoard();
		
		MenuTrigger trigger = secondary.getBudgetMenuTrigger();
		
		trigger.clickRestoreBudget("C:\\RestoreTest.bdg");
		
		
		secondary = board.getSecondaryBoard();
	
		
	}
	

}
