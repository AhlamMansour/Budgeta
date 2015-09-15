package com.budgeta.test.scenarios;

import org.testng.annotations.Test;

import com.budgeta.pom.SecondaryBoard;
import com.budgeta.test.WrapperTest;

public class ScenariosTest extends WrapperTest{

	@Test(enabled = true)
	public void createScenarioTest(){
		SecondaryBoard secondary = board.getSecondaryBoard();
		secondary.selectRandomBudgeta();
		
	}
	
}
