package com.budgeta.test.restore;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.NewBudgetPopup;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestNGListener;


@Listeners({ MethodListener.class, TestNGListener.class })
public class RestoreTest extends WrapperTest{

	SecondaryBoard secondaryBoard;
	
	@Test(enabled = true)
	public void restoreBudgetTest(){
		
		secondaryBoard = board.getSecondaryBoard();
		secondaryBoard.selectRandomBudgetWithPrefix("_");
		NewBudgetPopup popup = secondaryBoard.addBudgeta();
		
		Assert.assertTrue(popup.isDisplayed(), "expected create budget popup to be displayed");
		popup.clockRestoreAndUpload("C:\\RestoreTest.bdg");
		secondaryBoard = board.getSecondaryBoard();
		Assert.assertEquals(secondaryBoard.getSelectedBudgetName(), "RestoreTest");
	}
	
}
