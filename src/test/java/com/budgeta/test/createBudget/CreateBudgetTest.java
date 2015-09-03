package com.budgeta.test.createBudget;

import java.util.Hashtable;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.BudgetaBoard;
import com.budgeta.pom.NewBudgetPopup;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.DataProviderParams;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestNGListener;



@Listeners({ MethodListener.class, TestNGListener.class })
public class CreateBudgetTest extends WrapperTest{
	
	
	@Test(dataProvider = "ExcelFileLoader", enabled = true)
	@DataProviderParams(sheet = "CreateBudget" , area = "CreateNewBudget")
	public void createBudgetTest(Hashtable<String, String> data) {
	
		BudgetaBoard board = new BudgetaBoard();
		NewBudgetPopup popup = board.addBudgeta();
		
		popup.setName(data.get("name"));
		popup.setType(popup.getBudgetaType(data.get("Type")));
		
	}
	
}
