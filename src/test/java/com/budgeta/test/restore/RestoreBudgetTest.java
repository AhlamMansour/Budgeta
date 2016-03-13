package com.budgeta.test.restore;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.NewBudgetPopup;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestFirst;
import com.galilsoftware.AF.core.listeners.TestNGListener;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;


@Listeners({ MethodListener.class, TestNGListener.class })
public class RestoreBudgetTest extends WrapperTest{
	
	SecondaryBoard secondaryBoard;
	
	@TestFirst
	@Test(enabled = true)
	public void CreateSettingTest() {
		
		BudgetNavigator navigator = new BudgetNavigator();
		navigator.selectRandomBudgetWithPrefix("_");
		

	}
	
	

	
	
	@Test(enabled = true)
	public void restoreBudgetTest(){
		WebdriverUtils.sleep(1000);
		secondaryBoard = board.getSecondaryBoard();
		BudgetNavigator navigator = new BudgetNavigator();
		int num = navigator.getNumberOfBudget("new test budget");
		
		NewBudgetPopup popup = navigator.addNewBudget();
		AssertJUnit.assertTrue(popup.isDisplayed(), "expected create budget popup to be displayed");
		popup.clickRestoreAndUpload(System.getProperty("user.dir")+"C:\\new test budget.bdg");
		
		navigator = new BudgetNavigator();
		int num2 = navigator.getNumberOfBudget("new test budget");
		AssertJUnit.assertEquals(num + 1 , num2);
		
		
	}
	
	
	
/*	
	@Test(enabled = true)
	public void restoreBudgetTest(){
		
		secondaryBoard = board.getSecondaryBoard();
		BudgetNavigator navigator = new BudgetNavigator();
		int num = navigator.getNumberOfBudgetsWithName("RestoreTest");
		navigator.selectRandomBudgetWithPrefix("RestoreTest");
		NewBudgetPopup popup = secondaryBoard.addBudgeta();
		
		Assert.assertTrue(popup.isDisplayed(), "expected create budget popup to be displayed");
		popup.clickRestoreAndUpload("C:\\RestoreTest.bdg");
		secondaryBoard = board.getSecondaryBoard();
		int num2 = navigator.getNumberOfBudgetsWithName("RestoreTest");
		Assert.assertEquals(num + 1 , num2);
	}
	
*/
}
