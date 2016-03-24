package com.budgeta.test.PlansAndPricing;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.AddNewUser;
import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.BudgetaBoard;
import com.budgeta.pom.BuildCompanyBudgetPopup;
import com.budgeta.pom.LicenseScreen;
import com.budgeta.pom.LimitPopup;
import com.budgeta.pom.LoginPage;
import com.budgeta.pom.MenuTrigger;
import com.budgeta.pom.NewBudgetPopup;
import com.budgeta.pom.PlansAndPricingWindow;
import com.budgeta.pom.Scenarios;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.pom.SharePopup;
import com.budgeta.pom.SmallPopup;
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
		
		loginPage.setPasscode("nopasscode");
		loginPage.clicksendPasscode(true);
		
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

	@Test(enabled = true)
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

			BuildCompanyBudgetPopup budgetPopup = new BuildCompanyBudgetPopup();
			budgetPopup.clickExit();
			
			SecondaryBoard secondaryBoard = board.getSecondaryBoard();
			Assert.assertEquals(secondaryBoard.getSelectedBudgetName(), budgetaName);

		}

	}

	@Test(enabled = true)
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

	@Test(enabled = true)
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

	@Test(enabled = true)
	public void openActualsTab() {
		LicenseScreen licenseScreen = new LicenseScreen();
		if (licenseScreen.isDisplayed()) {
			licenseScreen.clickCancele();
		}

		TopHeaderBar headerBar = new TopHeaderBar();
		headerBar.openActalsTab();
		LimitPopup limitPopup = new LimitPopup();
		Assert.assertTrue(limitPopup.isDisplayed(), "Budget Line Limit is diplay");
		Assert.assertEquals(limitPopup.getTilte(), "Actuals", "Budget line limit popup is open");
		limitPopup.clickCancel();

	}

	@Test(enabled = true)
	public void shareOneEditUser() {
		TopBar topBar = new TopBar();
		LicenseScreen licenseScreen = new LicenseScreen();
		if (!licenseScreen.isDisplayed()) {
			topBar.clickMyLicense();
		}

		licenseScreen.clickUpdate();
		PlansAndPricingWindow plans = new PlansAndPricingWindow();
		String currentEdituser = plans.getCurrentEditingUser(plans.getCurrentPlanName());
		plans.changeEditingUser(plans.getCurrentPlanName(), "1 editing user");
		SmallPopup smallPopup = new SmallPopup();
		
		if(!currentEdituser.equals("1 editing user")){
			smallPopup.clickConfirm();
		}
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
		plans.closePriceAndPlansWin();

		int currentUsers = licenseScreen.usersNumber();

		if (currentEditUsers.equals("Up to 3 user")) {

			while (currentUsers < 3) {
				licenseScreen.addUser();

				AddNewUser addPopup = new AddNewUser();
				Assert.assertTrue(addPopup.isDisplayed(), "Add user pop up is display");

				SmallPopup smallPopup = new SmallPopup();

				String prefix = email.substring(0, email.indexOf("@"));
				String suffix = email.substring(email.indexOf("@"));
				email = prefix + WebdriverUtils.getTimeStamp("_") + suffix;

				smallPopup.setName(email);
				addPopup.clickAddUser();
				BudgetaBoard board = new BudgetaBoard();
				String message = board.getNotyMessage();

				if (!message.equals("User already connected to another account")) {
					currentUsers = licenseScreen.usersNumber();
				}

				else
					Assert.assertEquals(message, "User already connected to another account",
							"excpeted message is: -User already connected to another account - but found: " + message);

				email = "ahlam_mns@hotmail.com";

			}

			if (currentUsers >= 3) {
				licenseScreen.addUser();
				LimitPopup limitPopup = new LimitPopup();
				Assert.assertTrue(limitPopup.isDisplayed(), "Budget Line Limit is diplay");
				Assert.assertEquals(limitPopup.getTilte(), "Users Limit", "Budget line limit popup is open");
				limitPopup.clickCancel();

			}
		}

		if (currentEditUsers.equals("Up to 6 user")) {

			while (currentUsers < 6) {
				licenseScreen.addUser();

				AddNewUser addPopup = new AddNewUser();
				Assert.assertTrue(addPopup.isDisplayed(), "Add user pop up is display");

				SmallPopup smallPopup = new SmallPopup();

				String prefix = email.substring(0, email.indexOf("@"));
				String suffix = email.substring(email.indexOf("@"));
				email = prefix + WebdriverUtils.getTimeStamp("_") + suffix;

				smallPopup.setName(email);
				addPopup.clickAddUser();

				BudgetaBoard board = new BudgetaBoard();
				String message = board.getNotyMessage();

				if (!message.equals("User already connected to another account")) {
					currentUsers = licenseScreen.usersNumber();
				}
				else
					Assert.assertEquals(message, "User already connected to another account",
							"excpeted message is: -User already connected to another account - but found: " + message);
				email = "ahlam_mns@hotmail.com";

			}

			if (currentUsers >= 6) {
				licenseScreen.addUser();
				LimitPopup limitPopup = new LimitPopup();
				Assert.assertTrue(limitPopup.isDisplayed(), "Budget Line Limit is diplay");
				Assert.assertEquals(limitPopup.getTilte(), "Users Limit", "Budget line limit popup is open");
				limitPopup.clickCancel();

			}
		}

	}

	@Test(enabled = true)
	public void editUserViaShare() {
		TopBar topBar = new TopBar();
		LicenseScreen licenseScreen = new LicenseScreen();
		if (!licenseScreen.isDisplayed()) {
			topBar.clickMyLicense();
		}
		PlansAndPricingWindow plans = new PlansAndPricingWindow();
		licenseScreen.clickUpdate();
		String currentEditUsers = plans.getCurrentEditingUser(plans.getCurrentPlanName());
		plans.closePriceAndPlansWin();

		int currentUsers = licenseScreen.usersNumber();

		licenseScreen.clickCancele();

		if (currentEditUsers.equals("Up to 3 user")) {

			while (currentUsers < 3) {

				String prefix = email.substring(0, email.indexOf("@"));
				String suffix = email.substring(email.indexOf("@"));
				email = prefix + WebdriverUtils.getTimeStamp("_") + suffix;

				SecondaryBoard secondaryBoard = new SecondaryBoard();
				MenuTrigger trigger = secondaryBoard.getBudgetMenuTrigger();
				SharePopup popup = trigger.clickShareBudget();
				Assert.assertTrue(popup.isDisplayed(), "expected share popup to be displayed");
				popup.setName(email);
				popup.selectSharePermissios("Can Modify");
				
				if (popup.isShareErrorAppear() == true) {
					popup.clickConfirm();
					
				}
				else{
					popup.clickSend();
					popup.clickConfirm();
				}

				topBar.clickMyLicense();
				currentUsers = licenseScreen.usersNumber();
				licenseScreen.clickCancele();

				email = "ahlam_mns@hotmail.com";

			}

			if (currentUsers >= 3) {
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

				popup.clickConfirm();

				LimitPopup limitPopup = new LimitPopup();
				Assert.assertTrue(limitPopup.isDisplayed(), "Budget Line Limit is diplay");
				Assert.assertEquals(limitPopup.getTilte(), "Users Limit", "Budget line limit popup is open");
				limitPopup.clickCancel();

			}
		}

		if (currentEditUsers.equals("Up to 6 user")) {

			while (currentUsers < 6) {
				String prefix = email.substring(0, email.indexOf("@"));
				String suffix = email.substring(email.indexOf("@"));
				email = prefix + WebdriverUtils.getTimeStamp("_") + suffix;

				SecondaryBoard secondaryBoard = new SecondaryBoard();
				MenuTrigger trigger = secondaryBoard.getBudgetMenuTrigger();
				SharePopup popup = trigger.clickShareBudget();
				Assert.assertTrue(popup.isDisplayed(), "expected share popup to be displayed");
				popup.setName(email);
				popup.selectSharePermissios("Can Modify");
				if (popup.isShareErrorAppear() == true) {
					popup.clickConfirm();
					
				}
				else{
					popup.clickSend();
					popup.clickConfirm();
				}
					

				topBar.clickMyLicense();
				currentUsers = licenseScreen.usersNumber();
				licenseScreen.clickCancele();
				email = "ahlam_mns@hotmail.com";

			}

			if (currentUsers >= 6) {

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

				LimitPopup limitPopup = new LimitPopup();
				Assert.assertTrue(limitPopup.isDisplayed(), "Budget Line Limit is diplay");
				Assert.assertEquals(limitPopup.getTilte(), "Users Limit", "Budget line limit popup is not open ");
				limitPopup.clickCancel();

			}
		}

	}

	@Test(enabled = true)
	public void removeUser() {

		TopBar topBar = new TopBar();
		LicenseScreen licenseScreen = new LicenseScreen();
		if (!licenseScreen.isDisplayed()) {
			topBar.clickMyLicense();
		}

		BudgetaBoard board = new BudgetaBoard();

		if (licenseScreen.isRemoveButtonIsDisplay()) {
			licenseScreen.removeUser();
			String message = board.getNotyMessage();
			Assert.assertEquals(message, "The user was removed from your billing account",
					"The message: The user was removed from your billing account - should be displayed but found: " + message);
		}

	}

	@Test(enabled = true)
	public void makeAdminUser() {

		TopBar topBar = new TopBar();
		LicenseScreen licenseScreen = new LicenseScreen();
		if (!licenseScreen.isDisplayed()) {
			topBar.clickMyLicense();
		}
		int adminUser = licenseScreen.numberOfAdminUsers();

		if (licenseScreen.isMakeAdminIsDisplay()) {
			licenseScreen.makeAdminUser();
			Assert.assertEquals(licenseScreen.numberOfAdminUsers(), adminUser + 1,
					"the expected result is: " + (adminUser + 1) + "But found: " + licenseScreen.numberOfAdminUsers());
		}

	}

}
