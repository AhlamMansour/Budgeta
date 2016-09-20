package com.budgeta.test.ShareBudget;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.BudgetaBoard;
import com.budgeta.pom.LicenseScreen;
import com.budgeta.pom.LoginPage;
import com.budgeta.pom.MenuTrigger;
import com.budgeta.pom.PlansAndPricingWindow;
import com.budgeta.pom.SharePopup;
import com.budgeta.pom.TopBar;
import com.budgeta.pom.TopHeaderBar;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestFirst;
import com.galilsoftware.AF.core.listeners.TestNGListener;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

@Listeners({ MethodListener.class, TestNGListener.class })
public class DashboardShare extends WrapperTest {

	
	
	String email = "ahlam_mns@hotmail.com";
	
	
	
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
		navigator.selectRandomBudgetWithPrefix("Copy of TEST Forecast");
	}
	
	@Test(enabled = true, priority = 1)
	public void InputTab() {
		BudgetNavigator navigator = new BudgetNavigator();


		Assert.assertFalse(navigator.isInputTabDispaly(), "Icone is display");
	}

	@Test(enabled = true, priority = 2)
	public void SheetsTab() {
		BudgetNavigator navigator = new BudgetNavigator();


		Assert.assertFalse(navigator.isSheetsTabDispaly(), "Icone is display");
	}

	@Test(enabled = true, priority = 3)
	public void scenarioIcon() {
		TopHeaderBar topHeader = new TopHeaderBar();
		Assert.assertFalse(topHeader.isScenarioDispaly(), "Icone is display");
	}

	@Test(enabled = true, priority = 4)
	public void revisionIcon() {
		TopHeaderBar topHeader = new TopHeaderBar();
		Assert.assertFalse(topHeader.isVersionsDispaly(), "Icone is display");
	}

	@Test(enabled = true, priority = 5)
	public void DashboardTab() {
		BudgetNavigator navigator = new BudgetNavigator();


		Assert.assertTrue(navigator.isDashboardTabDispaly(), "Icone is display");

	}

	@Test(enabled = true, priority = 6)
	public void MainTab() {
		TopHeaderBar topHeader = new TopHeaderBar();
		Assert.assertFalse(topHeader.isMainTabDispaly(), "Icone is display");
	}

	@Test(enabled = true, priority = 7)
	public void ModelTab() {
		TopHeaderBar topHeader = new TopHeaderBar();
		Assert.assertFalse(topHeader.isModelTabDispaly(), "Icone is display");
	}

	@Test(enabled = true, priority = 8)
	public void ActualsTab() {
		TopHeaderBar topHeader = new TopHeaderBar();
		Assert.assertFalse(topHeader.isActualsTabDispaly(), "Icone is display");

	}

	@Test(enabled = true, priority = 9)
	public void renameBudget() {
		BudgetNavigator navigator = new BudgetNavigator();
		MenuTrigger trigger = navigator.getMenuTrigger();
		Assert.assertFalse(trigger.checkBudgetMenuTrigger("Rename"), "Icone is display");
	}

	@Test(enabled = true, priority = 10)
	public void restoreBudget() {
		BudgetNavigator navigator = new BudgetNavigator();
		MenuTrigger trigger = navigator.getMenuTrigger();
		Assert.assertFalse(trigger.checkBudgetMenuTrigger("Restore"), "Icone is display");
	}

	@Test(enabled = true, priority = 11)
	public void backupBudget() {
		BudgetNavigator navigator = new BudgetNavigator();
		MenuTrigger trigger = navigator.getMenuTrigger();
		Assert.assertFalse(trigger.checkBudgetMenuTrigger("Backup"), "Icone is display");
	}

	@Test(enabled = true, priority = 12)
	public void duplicateBudget() {
		BudgetNavigator navigator = new BudgetNavigator();
		MenuTrigger trigger = navigator.getMenuTrigger();
		Assert.assertFalse(trigger.checkBudgetMenuTrigger("Duplicate"), "Icone is display");
	}

}
