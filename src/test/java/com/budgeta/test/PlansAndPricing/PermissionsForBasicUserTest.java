package com.budgeta.test.PlansAndPricing;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.BudgetaBoard;
import com.budgeta.pom.LicenseScreen;
import com.budgeta.pom.LimitPopup;
import com.budgeta.pom.LoginPage;
import com.budgeta.pom.MenuTrigger;
import com.budgeta.pom.NewBudgetPopup;
import com.budgeta.pom.PlansAndPricingWindow;
import com.budgeta.pom.Scenarios;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.pom.SharePopup;
import com.budgeta.pom.TopBar;
import com.budgeta.pom.TopHeaderBar;
import com.budgeta.pom.Versions;
import com.budgeta.test.BudgetaTest;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestFirst;
import com.galilsoftware.AF.core.listeners.TestNGListener;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

@Listeners({ MethodListener.class, TestNGListener.class })
public class PermissionsForBasicUserTest extends BudgetaTest {

	// share -> 1 editing user/ up to 3 user/ up to 6 user
	String snapshotName = "snapshot test_";
	String newSnapshotName = "rename test";
	Versions versions;
	Scenarios scenarios;
	String email = "ahlam_mns@hotmail.com";

	@TestFirst
	@Test(enabled = true)
	public void setTest() {
		driver.get(baseURL);

		LoginPage loginPage = new LoginPage();
		Assert.assertTrue(loginPage.isDisplayed(), "expected login page to be displayed");

		loginPage.setEmail("galils1@hotmail.com");
		loginPage.setPassword("galil1234");
		loginPage.clickLogin(true);
		BudgetaBoard board = new BudgetaBoard();
		Assert.assertTrue(board.isDisplayed(), "expected budgeta board to be displayed");

		TopBar topBar = new TopBar();
		LicenseScreen licenseScreen = new LicenseScreen();
		if (!licenseScreen.isDisplayed()) {
			topBar.clickMyLicense();
		}

		licenseScreen.clickUpdate();
		PlansAndPricingWindow plans = new PlansAndPricingWindow();
		System.out.println("Your Plan name is: " + plans.getCurrentPlanName());
		String currentPlan = plans.getCurrentPlanName();
		plans.closePriceAndPlansWin();
		licenseScreen.clickCancele();
		BudgetNavigator navigator = new BudgetNavigator();
		navigator.openInputTab();

		Assert.assertEquals(currentPlan, "BASIC", "Your current plan is Basic plan");

	}

	@Test(enabled = false)
	public void AddNewBudget() {

		BudgetNavigator navigator = new BudgetNavigator();

		int numberOfExistBudget = navigator.getNumbreOfExistBudgets();
		if (numberOfExistBudget > 5) {
			LimitPopup popup = navigator.budgetLimit();
			Assert.assertTrue(popup.isDisplayed(), "Budget Limit pop up is display");
			Assert.assertEquals(popup.getTilte(), "Budgets Limit", "Budget Limit pop up is open");
			popup.clickCancel();
		}
		if (numberOfExistBudget <= 5) {
			NewBudgetPopup popup = navigator.addNewBudget();
			String budgetaName = "Budget";
			if (!budgetaName.isEmpty())
				budgetaName = WebdriverUtils.getTimeStamp(budgetaName + "_");
			popup.setName(budgetaName);
			popup.clickContinue(true);
			popup.clickCreate();

			SecondaryBoard secondaryBoard = board.getSecondaryBoard();
			Assert.assertEquals(secondaryBoard.getSelectedBudgetName(), budgetaName);

		}

	}

	@Test(enabled = false)
	public void addNewSnapshot() {
		versions = new Versions();
		TopHeaderBar headerBar = new TopHeaderBar();
		headerBar.openRevisionswindow();
		versions.createNewSnapshot();
		LimitPopup limitPopup = new LimitPopup();
		Assert.assertTrue(limitPopup.isDisplayed(), "Budget Line Limit is diplay");
		Assert.assertEquals(limitPopup.getTilte(), "Versions", "Budget line limit popup is open");
		limitPopup.clickCancel();

	}

	@Test(enabled = false)
	public void addNewScenario() {

		TopHeaderBar headerBar = new TopHeaderBar();
		headerBar.openScenariowindow();
		scenarios = new Scenarios();
		scenarios.createNewScenario();
		LimitPopup limitPopup = new LimitPopup();
		Assert.assertTrue(limitPopup.isDisplayed(), "Budget Line Limit is diplay");
		Assert.assertEquals(limitPopup.getTilte(), "Scenarios", "Budget line limit popup is open");
		limitPopup.clickCancel();

	}

	@Test(enabled = false)
	public void openActualsTab() {
		TopHeaderBar headerBar = new TopHeaderBar();
		headerBar.openActalsTab();
		LimitPopup limitPopup = new LimitPopup();
		Assert.assertTrue(limitPopup.isDisplayed(), "Budget Line Limit is diplay");
		Assert.assertEquals(limitPopup.getTilte(), "Actuals", "Budget line limit popup is open");
		limitPopup.clickCancel();

	}

	@Test(enabled = false)
	public void shareOneEditUser() {
		TopBar topBar = new TopBar();
		LicenseScreen licenseScreen = new LicenseScreen();
		if (!licenseScreen.isDisplayed()) {
			topBar.clickMyLicense();
		}

		licenseScreen.clickUpdate();
		PlansAndPricingWindow plans = new PlansAndPricingWindow();
		plans.changeEditingUser(plans.getCurrentPlanName(), "1 editing user");

		licenseScreen.clickUpdate();
		String currentEditUsers = plans.getCurrentEditingUser(plans.getCurrentPlanName());
		// System.out.println("current edit users: " +
		// plans.getCurrentEditingUser(plans.getCurrentPlanName()));
		plans.closePriceAndPlansWin();

		if (currentEditUsers.equals("1 editing user")) {
			licenseScreen.addUser();
			LimitPopup limitPopup = new LimitPopup();
			Assert.assertTrue(limitPopup.isDisplayed(), "Budget Line Limit is diplay");
			Assert.assertEquals(limitPopup.getTilte(), "Users Limit", "Budget line limit popup is open");
			limitPopup.clickCancel();

			licenseScreen.clickCancele();

			String prefix = email.substring(0, email.indexOf("@"));
			String suffix = email.substring(email.indexOf("@"));
			email = prefix + WebdriverUtils.getTimeStamp("_") + suffix;

			SecondaryBoard secondaryBoard = new SecondaryBoard();
			MenuTrigger trigger = secondaryBoard.getBudgetMenuTrigger();
			SharePopup popup = trigger.clickShareBudget();
			Assert.assertTrue(popup.isDisplayed(), "expected share popup to be displayed");
			popup.setName(email);
			popup.selectSharePermissios("Can Modify");
			popup.clickSend();

			// Assert.assertTrue(limitPopup.isDisplayed(),
			// "Budget Line Limit is diplay");
			Assert.assertEquals(limitPopup.getTilte(), "Users Limit", "Budget line limit popup is open");
			limitPopup.clickCancel();

		}

	}

	@Test(enabled = true)
	public void addUsers() {
		TopBar topBar = new TopBar();
		LicenseScreen licenseScreen = new LicenseScreen();
		if (!licenseScreen.isDisplayed()) {
			topBar.clickMyLicense();
		}
		PlansAndPricingWindow plans = new PlansAndPricingWindow();
		licenseScreen.clickUpdate();
		String currentEditUsers = plans.getCurrentEditingUser(plans.getCurrentPlanName());
		// System.out.println("current edit users: " +
		// plans.getCurrentEditingUser(plans.getCurrentPlanName()));
		plans.closePriceAndPlansWin();

		int currentUsers = licenseScreen.usersNumber();
		// System.out.println("the current users are: " +
		// licenseScreen.usersNumber());

		if (currentEditUsers.equals("Up to 3 user")) {

			if (currentUsers < 3) {

			}

			if (currentUsers >= 3) {
				
				LimitPopup limitPopup = new LimitPopup();
				Assert.assertTrue(limitPopup.isDisplayed(), "Budget Line Limit is diplay");
				Assert.assertEquals(limitPopup.getTilte(), "Users Limit", "Budget line limit popup is open");
				limitPopup.clickCancel();

			}
		}
		
		
		if (currentEditUsers.equals("Up to 6 user")) {

			if (currentUsers < 6) {
				licenseScreen.addUser();

			}

			if (currentUsers >= 6) {
				
				LimitPopup limitPopup = new LimitPopup();
				Assert.assertTrue(limitPopup.isDisplayed(), "Budget Line Limit is diplay");
				Assert.assertEquals(limitPopup.getTilte(), "Users Limit", "Budget line limit popup is open");
				limitPopup.clickCancel();

			}
		}

	}

}
