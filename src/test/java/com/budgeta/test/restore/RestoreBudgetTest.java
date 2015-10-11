package com.budgeta.test.restore;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.budgeta.pom.MenuTrigger;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.TestFirst;

public class RestoreBudgetTest extends WrapperTest{
	
	
	@TestFirst
	@Test(enabled = false)
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
		
		
		int num = secondary.getNumberOfBudget("new test budget");
		trigger.clickRestoreBudget(System.getProperty("user.dir")+"\\new test budget.bdg");
		secondary = board.getSecondaryBoard();
		int num2 = secondary.getNumberOfBudget("new test budget");
		
		Assert.assertTrue(num2 == (num+1), "The budget was successfully restored, number of budget was:" + num + "now is:" + num2);
		
	}
	

}
