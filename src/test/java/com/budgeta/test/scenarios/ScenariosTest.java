package com.budgeta.test.scenarios;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.budgeta.pom.CreateNewScenarioPopup;
import com.budgeta.pom.DeletePopup;
import com.budgeta.pom.Scenarios;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.TestFirst;

public class ScenariosTest extends WrapperTest{

	String scenarioName = "new scenario";
	String scenarioReName = "new scenario name";
	Scenarios scenarios;
	
	
	@TestFirst
	@Test(enabled = true)
	public void createScenarioTest(){
		SecondaryBoard secondary = board.getSecondaryBoard();
		secondary.selectRandomBudgeta();
		
		//secondary click scenario
		scenarios = new Scenarios();
		Assert.assertTrue(scenarios.isDisplayed(), "expected scenarios to be displayed");
		
		CreateNewScenarioPopup popup = scenarios.clickCreateNewScenario();
		Assert.assertTrue(popup.isDisplayed(), "expected create new scenario popup to be displayed");
		popup.setName(scenarioName);
		popup.clickCreate();
		
		Assert.assertTrue(scenarios.isScenarioTriggerDisplayed(), "expected scenario trigger to be displayed");
		Assert.assertEquals(scenarios.getSelectedScenario(), scenarioName);
	}
	
	@Test(enabled = true, priority = 1)
	public void addLineToScenarioTest(){
		
	}
	
	@Test(enabled = true, priority = 2)
	public void deleteLinefromScenarioTest(){
		
	}
	
	@Test(enabled = true, priority = 3)
	public void renameScenarioTest(){
		CreateNewScenarioPopup popup = scenarios.clickRenameScenario();
		Assert.assertTrue(popup.isDisplayed(), "expected rename popup to be displayed");
		popup.setName(scenarioReName);
		popup.clickCreate();
		
		Assert.assertTrue(scenarios.isScenarioTriggerDisplayed(), "expected scenario trigger to be displayed");
		Assert.assertEquals(scenarios.getSelectedScenario(), scenarioReName);
		
		
	}
	
	@Test(enabled = true, priority = 4)
	public void deleteScenarioTest(){
		DeletePopup popup = scenarios.deleteScenario();
		Assert.assertTrue(popup.isDisplayed(), "expected delete popup to be displayed");
		popup.clickDelete();
		scenarios = new Scenarios();
		Assert.assertFalse(scenarios.isScenarioExist(scenarioReName), "expected to scenario with name:"+scenarioReName+" to be deleted");
		
	}
	
}
