package com.budgeta.test.scenarios;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.budgeta.pom.CreateNewScenarioPopup;
import com.budgeta.pom.DeletePopup;
import com.budgeta.pom.RevenuesAddSubLine;
import com.budgeta.pom.Scenarios;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.pom.SmallPopup;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.TestFirst;

public class ScenariosTest extends WrapperTest{

	String scenarioName = "new scenario";
	String scenarioReName = "new scenario name";
	String subLineName = "new revenues sub line";
	Scenarios scenarios;
	SecondaryBoard secondary;
	
	@TestFirst
	@Test(enabled = true)
	public void createScenarioTest(){
		secondary = board.getSecondaryBoard();
		secondary.selectRandomBudgeta();
		
		scenarios = secondary.openScenarios();
		scenarios = new Scenarios();
		Assert.assertTrue(scenarios.isDisplayed(), "expected scenarios to be displayed");
		
		CreateNewScenarioPopup popup = scenarios.createNewScenario();
		Assert.assertTrue(popup.isDisplayed(), "expected create new scenario popup to be displayed");
		popup.setName(scenarioName);
		popup.clickConfirm();
		
		Assert.assertTrue(scenarios.isScenarioTriggerDisplayed(), "expected scenario trigger to be displayed");
		Assert.assertEquals(scenarios.getSelectedScenario().trim(), scenarioName);
	}
	
	@Test(enabled = true, priority = 1)
	public void addLineToScenarioTest(){
		secondary.addLine("Revenues");
		secondary.addSubLine("Revenues");
		RevenuesAddSubLine subLine = new RevenuesAddSubLine();
		subLine.setName(subLineName);
		subLine.clickAdd();
		Assert.assertTrue(secondary.isSubLineExist("Revenues", subLineName), "expected to found the added sub line");
	}
	
	@Test(enabled = true, priority = 2)
	public void deleteLinefromScenarioTest(){
		secondary.getSubLinSettings("Revenues", subLineName);
	}
	
	@Test(enabled = true, priority = 3)
	public void renameScenarioTest(){
		SmallPopup popup = scenarios.clickRenameScenario();
		Assert.assertTrue(popup.isDisplayed(), "expected rename popup to be displayed");
		popup.setName(scenarioReName);
		popup.clickConfirm();
		
		Assert.assertTrue(scenarios.isScenarioTriggerDisplayed(), "expected scenario trigger to be displayed");
		Assert.assertEquals(scenarios.getSelectedScenario().trim(), scenarioReName);
		
		
	}
	
	@Test(enabled = true, priority = 4)
	public void deleteScenarioTest(){
		DeletePopup popup = scenarios.deleteScenario();
		Assert.assertTrue(popup.isDisplayed(), "expected delete popup to be displayed");
		popup.clickConfirm();
		scenarios = new Scenarios();
		Assert.assertFalse(scenarios.isScenarioExist(scenarioReName), "expected to scenario with name:"+scenarioReName+" to be deleted");
		
	}
	
}
