package com.budgeta.test.PlansAndPricing;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.Actuals;
import com.budgeta.pom.AddNewUser;
import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.BudgetaBoard;
import com.budgeta.pom.BuildCompanyBudgetPopup;
import com.budgeta.pom.CreateNewScenarioPopup;
import com.budgeta.pom.CreateNewSnapshotPopup;
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
public class PermissionsForEnterpriseUserTest extends BudgetaTest{
	
	
	String snapshotName = "snapshot test_";
	String newSnapshotName = "rename test";
	Versions versions;
	Scenarios scenarios;
	String email = "ahlam_mns@hotmail.com";
	String scenarioName = "new scenario_";

	@TestFirst
	@Test(enabled = true)
	public void setTest() {
		driver.get(baseURL);

		LoginPage loginPage = new LoginPage();
		Assert.assertTrue(loginPage.isDisplayed(), "expected login page to be displayed");

		loginPage.setEmail("galiltest123@gmail.com");
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

		Assert.assertEquals(currentPlan, "ENTERPRISE", "Your current plan is not professional plan");

	}
	
	@Test(enabled = true, priority = 1)
	public void openActualsTab() {
		
		LicenseScreen licenseScreen = new LicenseScreen();
		if (licenseScreen.isDisplayed()) {
			licenseScreen.clickCancele();
		}
		
		BudgetNavigator navigator = new BudgetNavigator();
		//navigator.selectRandomBudgeta();
		navigator.openInputTab();
		

		TopHeaderBar headerBar = new TopHeaderBar();
		headerBar.openActalsTab();
		
		Actuals actual = new Actuals();
		Assert.assertTrue(actual.isDisplayed(), "expected true but found: " + actual.isDisplayed());

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
		BudgetNavigator navigator = new BudgetNavigator();
		navigator.selectRandomBudgeta();
		//navigator.selectRandomBudgetWithPrefix("budget7_1450919934212");
		versions = new Versions();
		TopHeaderBar headerBar = new TopHeaderBar();
		headerBar.openRevisionswindow();
		CreateNewSnapshotPopup popup = versions.createNewSnapshot();
		Assert.assertTrue(popup.isDisplayed(), "expected create new version to be displayed");
		snapshotName = WebdriverUtils.getTimeStamp(snapshotName);
		popup.setName(snapshotName);
		popup.clickConfirm(true);
		versions = new Versions();

		headerBar.openRevisionswindow();
		headerBar.selectAllRevisions();
		
		Assert.assertTrue(headerBar.isVersionExist(snapshotName), "expected to add the new snapshot to view all");

		versions = new Versions();
		headerBar.selectSavedRevisions();
		Assert.assertTrue(headerBar.isVersionExist(snapshotName), "expected to add the new snapshot to Only Snapshots");

	}

	@Test(enabled = true)
	public void addNewScenario() {
		BudgetNavigator navigator = new BudgetNavigator();
		navigator.selectRandomBudgeta();
		TopHeaderBar headerBar = new TopHeaderBar();
		headerBar.openScenariowindow();
		scenarios = new Scenarios();
		//Assert.assertTrue(scenarios.isDisplayed(), "expected scenarios to be displayed");
		
		CreateNewScenarioPopup popup = scenarios.createNewScenario();
		Assert.assertTrue(popup.isDisplayed(), "expected create new scenario popup to be displayed");
		scenarioName = WebdriverUtils.getTimeStamp(scenarioName);
		popup.setName(scenarioName);
		popup.clickConfirm();
		headerBar.openScenariowindow();
		headerBar.selectScenario(scenarioName);
		
		Assert.assertTrue(headerBar.isScenarioAdded(), "expected scenario trigger to be displayed");
		Assert.assertEquals(headerBar.newScenatrioText().trim(), scenarioName);
		
		headerBar.clearScenario();

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
		String currentPlan = plans.getCurrentPlanName();
		String currentEdituser = plans.getCurrentEditingUser(plans.getCurrentPlanName());
		plans.changeEditingUser(currentPlan, "1 editing user");
		
		if(currentPlan.equals("ENTERPRISE") && !currentEdituser.equals("1 editing user")){
			
			SmallPopup popup = new SmallPopup();
			Assert.assertTrue(popup.isDisplayed(), "Advanced plan code pop up is displayed");

			popup.setName("beadvanced");
			popup.clickConfirm(false);
			popup = new SmallPopup();
			Assert.assertTrue(popup.isDisplayed(), "Advanced plan code pop up is displayed");
			popup.clickConfirm();
		}
		else
		{
			
			SmallPopup smallPopup = new SmallPopup();
			if (smallPopup.isDisplayed())
				smallPopup.clickConfirm();
			
		}
		
		
		licenseScreen.clickUpdate();
		String currentEditUsers = plans.getCurrentEditingUser(plans.getCurrentPlanName());

		plans.closePriceAndPlansWin();

		if (currentEditUsers.equals("1 editing user")) {
			licenseScreen.addUser();
			LimitPopup limitPopup = new LimitPopup();
			Assert.assertTrue(limitPopup.isDisplayed(), "Budget Line Limit is diplay");
			Assert.assertEquals(limitPopup.getTilte(), "Users Limit", "Budget line limit popup is open");
			limitPopup.clickCancel();
			licenseScreen.clickCancele();
			
			
			BudgetNavigator navigator = new BudgetNavigator();

			navigator.openInputTab();

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
		
		
	

		if (message.equals("User already connected to another account")) {
			 addPopup = new AddNewUser();
			Assert.assertTrue(addPopup.isDisplayed(), "Add user pop up is display");

			 smallPopup = new SmallPopup();

			 prefix = email.substring(0, email.indexOf("@"));
			 suffix = email.substring(email.indexOf("@"));
			email = prefix + WebdriverUtils.getTimeStamp("_") + suffix;

			smallPopup.setName(email);
			addPopup.clickAddUser();
			 board = new BudgetaBoard();
			 message = board.getNotyMessage();
		}

		else
			Assert.assertEquals(message, "The user was added");


		email = "ahlam_mns@hotmail.com";
		
		
		
		
		

	}

	@Test(enabled = false)
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
