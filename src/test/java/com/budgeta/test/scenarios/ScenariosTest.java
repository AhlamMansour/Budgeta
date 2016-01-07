package com.budgeta.test.scenarios;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.CreateNewScenarioPopup;
import com.budgeta.pom.DeletePopup;
import com.budgeta.pom.MenuTrigger;
import com.budgeta.pom.RevenuesAddSubLine;
import com.budgeta.pom.Scenarios;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.pom.SmallPopup;
import com.budgeta.pom.TopHeaderBar;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestFirst;
import com.galilsoftware.AF.core.listeners.TestNGListener;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

@Listeners({ MethodListener.class, TestNGListener.class })
public class ScenariosTest extends WrapperTest{

	SecondaryBoard secondaryBoard;
	String scenarioName = "new scenario_";
	String scenarioReName = "new scenario name";
	String subLineName = "new revenues sub line";
	Scenarios scenarios;
	SecondaryBoard secondary;
	String revenuesSubLine = WebdriverUtils.getTimeStamp("revenues_");
    String revenues = "Revenues";
    
    
    
    
    @BeforeMethod
	private void initTest() {	
		
		driver.manage().window().maximize();
		
		
	}
    
    
    @TestFirst
    @Test(enabled = true)
    public void setBudgetTest() {

	secondaryBoard = board.getSecondaryBoard();
	BudgetNavigator navigator = new BudgetNavigator();
	navigator.selectRandomBudgetWithPrefix("budget7_");
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
		TopHeaderBar headerBar = new TopHeaderBar();
		headerBar.openScenariowindow();
		scenarios = new Scenarios();
		//Assert.assertTrue(scenarios.isDisplayed(), "expected scenarios to be displayed");
		
		CreateNewScenarioPopup popup = scenarios.createNewScenario();
		Assert.assertTrue(popup.isDisplayed(), "expected create new scenario popup to be displayed");
		scenarioName = WebdriverUtils.getTimeStamp(scenarioName);
		popup.setName(scenarioName);
		popup.clickConfirm();
		
		Assert.assertTrue(headerBar.isScenarioAdded(), "expected scenario trigger to be displayed");
		Assert.assertEquals(headerBar.newScenatrioText().trim(), scenarioName);
	}
	
	@Test(enabled = true, priority = 3)
	public void addLineToScenarioTest(){
		TopHeaderBar headerBar = new TopHeaderBar();
		secondaryBoard.addSubLine("Revenues");
		RevenuesAddSubLine subLine = new RevenuesAddSubLine();
		subLine.setName(subLineName);
		subLine.clickAdd();
		Assert.assertTrue(secondaryBoard.isSubLineExist("Revenues", subLineName), "expected to found the added sub line");
		scenarios = new Scenarios();
		String scenarioName = headerBar.newScenatrioText().trim(); 
		headerBar.clearScenario();
		Assert.assertFalse(secondaryBoard.isScenarioLineDisplayed(subLineName), "expected the new line to be disappear in base scenario");
		//secondary.clickClose();
		
		headerBar.openScenariowindow();
		if(headerBar.isScenarioExist(scenarioName) == true){
			headerBar.selectScenario(scenarioName);
		}
		
		
		
	}
	@Test(enabled = true, priority = 4)
	public void deleteLineFromScenarioTest(){
		MenuTrigger trigger = secondaryBoard.getSubLinSettings("Revenues", subLineName);
		DeletePopup popup = trigger.clickDelete();
		Assert.assertTrue(popup.isDisplayed(), "expected the popup to be displayed");
		popup.clickConfirm();
		Assert.assertFalse(secondaryBoard.isScenarioLineDisplayed(subLineName), "expected the new line to be deleted");
	}
	
	@Test(enabled = true, priority = 2)
	public void renameScenarioTest(){
		SmallPopup popup = scenarios.clickRenameScenario();
		Assert.assertTrue(popup.isDisplayed(), "expected rename popup to be displayed");
		popup.setName(scenarioReName);
		popup.clickConfirm();
		TopHeaderBar headerBar = new TopHeaderBar();
		String scenarioNewName = headerBar.newScenatrioText().trim();
		Assert.assertTrue(scenarios.isScenarioTriggerDisplayed(), "expected scenario trigger to be displayed");
		Assert.assertEquals(scenarioNewName, scenarioReName);
		
		
	}
	
	@Test(enabled = true, priority = 5)
	public void deleteScenarioTest(){
		TopHeaderBar headerBar = new TopHeaderBar();
		String scenarioNewName = headerBar.newScenatrioText().trim();
		DeletePopup popup = scenarios.deleteScenario();
		Assert.assertTrue(popup.isDisplayed(), "expected delete popup to be displayed");
		popup.clickConfirm();
		Assert.assertFalse(headerBar.isScenarioExist(scenarioNewName), "expected to scenario with name:"+scenarioReName+" to be deleted");
		
	}
	
}
