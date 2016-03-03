package com.budgeta.test.importBudget;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.SecondaryBoard;
import com.budgeta.test.WrapperTest;
import com.budgeta.test.common.BudgetaCommon;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestNGListener;

@Listeners({ MethodListener.class, TestNGListener.class })
public class ImportBudget extends WrapperTest{
	
	SecondaryBoard secondaryBoard;

	@Test(enabled = true)
	public void createBudget(){
		BudgetaCommon create = new BudgetaCommon();
		create.createBudget();
		secondaryBoard = board.getSecondaryBoard();
		secondaryBoard.importAllBudgetLines();
		
		
	}
}
