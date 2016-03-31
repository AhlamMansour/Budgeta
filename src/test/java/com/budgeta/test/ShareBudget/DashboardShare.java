package com.budgeta.test.ShareBudget;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.MenuTrigger;
import com.budgeta.pom.SharePopup;
import com.budgeta.pom.TopHeaderBar;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestNGListener;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

@Listeners({ MethodListener.class, TestNGListener.class })
public class DashboardShare extends WrapperTest {

	
	
	String email = "ahlam_mns@hotmail.com";
	
	
	@Test(enabled = true, priority = 1)
	public void InputTab() {
		BudgetNavigator navigator = new BudgetNavigator();
		navigator.selectRandomBudgetWithPrefix("Copy of New Budget name_1458137461403");

		Assert.assertFalse(navigator.isInputTabDispaly(), "Icone is display");
	}

	@Test(enabled = true, priority = 2)
	public void SheetsTab() {
		BudgetNavigator navigator = new BudgetNavigator();
		navigator.selectRandomBudgetWithPrefix("Copy of New Budget name_1458137461403");

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
		navigator.selectRandomBudgetWithPrefix("Copy of New Budget name_1458137461403");

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

	@Test(enabled = true, priority = 12)
	public void shareBudget() {
		BudgetNavigator navigator = new BudgetNavigator();
		navigator.openSharePopup();
		SharePopup popup = new SharePopup();
		Assert.assertTrue(popup.isDisplayed(), "expected share popup to be displayed"); // popup.sendEmail(email);
		
		String prefix = email.substring(0, email.indexOf("@"));
		String suffix = email.substring(email.indexOf("@"));
		email = prefix + WebdriverUtils.getTimeStamp("_") + suffix;
		
		popup.setName(email);
		popup.clickSend();
		
		String message = board.getNotyMessage();
		Assert.assertEquals(message, "Could not send email. Forbidden");
	}
}
