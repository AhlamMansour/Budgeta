package com.budgeta.test.ShareBudget;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.BudgetaBoard;
import com.budgeta.pom.LoginPage;
import com.budgeta.pom.MainInputForm;
import com.budgeta.pom.MenuTrigger;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.pom.TopBar;
import com.budgeta.pom.TopBarButtons;
import com.budgeta.pom.TopHeaderBar;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestFirst;
import com.galilsoftware.AF.core.listeners.TestNGListener;

@Listeners({ MethodListener.class, TestNGListener.class })
public class ViewOnlyModeTest extends WrapperTest{
	
	
	@TestFirst
	@Test(enabled = true)
	public void setTest() {
		
		TopBar topBar = new TopBar();
		topBar.clickLogout();

		LoginPage loginPage = new LoginPage();
		Assert.assertTrue(loginPage.isDisplayed(), "expected login page to be displayed");

		loginPage.setEmail("ahlam_mns@hotmail.com");
		loginPage.setPassword("a1234567");
		loginPage.clickLogin(true);
		
		loginPage.setPasscode("nopasscode");
		loginPage.clicksendPasscode(true);
		BudgetaBoard board = new BudgetaBoard();
		Assert.assertTrue(board.isDisplayed(), "expected budgeta board to be displayed");
		BudgetNavigator navigator = new BudgetNavigator();
		navigator.selectRandomBudgetWithPrefix("TEST Forecast");
	}
	
	
	@Test(enabled = true, priority = 1)
	public void settingIcon(){
		TopHeaderBar topHeader = new TopHeaderBar();
		BudgetNavigator navigator = new BudgetNavigator();
		Assert.assertFalse(topHeader.isSettingIconDispaly(), "Icone is display");
	}
	
	@Test(enabled = true, priority = 2)
	public void editTableIcon(){
		TopHeaderBar topHeader = new TopHeaderBar();
		Assert.assertFalse(topHeader.isEditTableIconDispaly(), "Icone is display");
	}
	
	@Test(enabled = true, priority = 3)
	public void scenarioIcon(){
		TopHeaderBar topHeader = new TopHeaderBar();
		Assert.assertFalse(topHeader.isScenarioDispaly(), "Icone is display");
	}
	
	
	@Test(enabled = true, priority = 4)
	public void revisionIcon(){
		TopHeaderBar topHeader = new TopHeaderBar();
		Assert.assertFalse(topHeader.isVersionsDispaly(), "Icone is display");
	}
	
	
	@Test(enabled = false, priority = 5)
	public void viewOnlyMode(){
		MainInputForm mainForm = new MainInputForm();
		Assert.assertTrue(mainForm.isViewOnlyMode(), "Icone is display");
	}
	
	
	@Test(enabled = true, priority = 6)
	public void budgetMenuIcon(){
		SecondaryBoard secondary = new SecondaryBoard();
		Assert.assertFalse(secondary.isBudgetSettingIconDispaly(), "Icone is display");
	}
	
	@Test(enabled = false, priority = 7)
	public void budgetLineMenuIcon(){
		SecondaryBoard secondary = new SecondaryBoard();
		Assert.assertFalse(secondary.isBudgetLineSettingIconDispaly("Revenues"), "Icone is display");
	}

	
	@Test(enabled = false, priority = 8)
	public void importAndTransactionIcon(){
		TopHeaderBar topHeader = new TopHeaderBar();
		topHeader.openActalsTab();
		TopBarButtons topBar = new TopBarButtons();
		Assert.assertFalse(topBar.isImportAndTransactionIconDispaly(), "Icone is display");
	}
	
	@Test(enabled = true, priority = 9)
	public void renameBudget(){
		BudgetNavigator navigator = new BudgetNavigator();
		MenuTrigger trigger = navigator.getMenuTrigger();
		Assert.assertFalse(trigger.checkBudgetMenuTrigger("Rename"), "Icone is display");
	}
	
	@Test(enabled = true, priority = 10)
	public void restoreBudget(){
		BudgetNavigator navigator = new BudgetNavigator();
		MenuTrigger trigger = navigator.getMenuTrigger();
		Assert.assertFalse(trigger.checkBudgetMenuTrigger("Restore"), "Icone is display");
	}
}
