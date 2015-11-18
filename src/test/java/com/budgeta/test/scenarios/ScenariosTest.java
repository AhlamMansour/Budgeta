package com.budgeta.test.scenarios;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.CreateNewScenarioPopup;
import com.budgeta.pom.DeletePopup;
import com.budgeta.pom.MenuTrigger;
import com.budgeta.pom.RevenuesAddSubLine;
import com.budgeta.pom.Scenarios;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.pom.SmallPopup;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestFirst;
import com.galilsoftware.AF.core.listeners.TestNGListener;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

@Listeners({ MethodListener.class, TestNGListener.class })
public class ScenariosTest extends WrapperTest{

	SecondaryBoard secondaryBoard;
	String scenarioName = "new scenario";
	String scenarioReName = "new scenario name";
	String subLineName = "new revenues sub line";
	Scenarios scenarios;
	SecondaryBoard secondary;
	String revenuesSubLine = WebdriverUtils.getTimeStamp("revenues_");
    String revenues = "Revenues";
    
    
    
    
    
    
    
    @TestFirst
    @Test(enabled = true)
    public void setBudgetTest() {

	secondaryBoard = board.getSecondaryBoard();
	secondaryBoard.selectRandomBudgetWithPrefix("budget7_");
	secondaryBoard.addAllLines();
	secondaryBoard = new SecondaryBoard();
	secondaryBoard.addSubLine("Revenues");
	RevenuesAddSubLine subLine = new RevenuesAddSubLine();
	subLine.setName(revenuesSubLine);
	subLine.selectDropDown("Perpetual License");
	subLine.clickAdd();
	
    }
    
	@Test(enabled = true, priority = 1)
	public void createScenarioTest(){
		secondary = board.getSecondaryBoard();
		
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
		secondary.addSubLine("Revenues");
		RevenuesAddSubLine subLine = new RevenuesAddSubLine();
		subLine.setName(subLineName);
		subLine.clickAdd();
		Assert.assertTrue(secondary.isSubLineExist("Revenues", subLineName), "expected to found the added sub line");
		scenarios = new Scenarios();
		scenarios.selectScenario("Base");
		Assert.assertFalse(secondary.isScenarioLineDisplayed(subLineName), "expected the new line to be disappear in base scenario");
		
		scenarios.selectScenario(scenarioName);
		secondary = new SecondaryBoard();
		Assert.assertTrue(secondary.isScenarioLineDisplayed(subLineName), "expected the new line to be displayed in created scenario");
	}
	
	@Test(enabled = true, priority = 2)
	public void deleteLineFromScenarioTest(){
		MenuTrigger trigger = secondary.getSubLinSettings("Revenues", subLineName);
		DeletePopup popup = trigger.clickDelete();
		Assert.assertTrue(popup.isDisplayed(), "expected the popup to be displayed");
		popup.clickConfirm();
		Assert.assertFalse(secondary.isScenarioLineDisplayed(subLineName), "expected the new line to be deleted");
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
