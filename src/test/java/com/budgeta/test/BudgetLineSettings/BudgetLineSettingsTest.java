package com.budgeta.test.BudgetLineSettings;

import org.testng.annotations.Test;

import com.budgeta.pom.MenuTrigger;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.test.WrapperTest;

public class BudgetLineSettingsTest extends WrapperTest {
	
	@Test(enabled = true)
	public void changeSettingTest(){
		SecondaryBoard secondary = board.getSecondaryBoard();
		secondary.selectRandomBudgeta();
	
		secondary.addLine("Revenues");
		secondary = new SecondaryBoard();
		MenuTrigger trigger = secondary.getLineSettings("Revenues");
		trigger.clickDuplicate();
		
	}

}
