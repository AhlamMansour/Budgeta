package com.budgeta.test.ShareBudget;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.BudgetaBoard;
import com.budgeta.pom.LoginPage;
import com.budgeta.pom.MenuTrigger;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.pom.SharePopup;
import com.budgeta.pom.SuccessPage;
import com.budgeta.pom.TopBar;
import com.budgeta.pom.TopHeaderBar;
import com.budgeta.test.BudgetaUtils;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestFirst;
import com.galilsoftware.AF.core.listeners.TestNGListener;
import com.galilsoftware.AF.core.utilities.EmailReader;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

@Listeners({ MethodListener.class, TestNGListener.class })
public class ShareBudgetTest extends WrapperTest {

	String email = "galiltest123@gmail.com";
	SuccessPage successPage;
	protected BudgetaBoard board;
	SecondaryBoard secondary;
	String[] innerWords = { "wants to share a budget with you on Budgeta. Please click the following link to enter:" };
	String subLink = "dev.budgeta.com/invitation/";

	protected static final String IMAP_SERVER = "imap.gmail.com";

	private static String EMAIL = "galiltest123@gmail.com";

	private static String PASSWORD = "galil1234";
	String viewPermisssion = "Can View";

	@TestFirst
	@Test(enabled = true)
	public void CreateSettingTest() {

		BudgetNavigator navigator = new BudgetNavigator();
		navigator.selectRandomBudgeta();

		secondary = new SecondaryBoard();
		navigator.openInputTab();
		EmailReader email = new EmailReader(EMAIL, PASSWORD, IMAP_SERVER);
		email.emptyMailbox();
	}

	@Test(enabled = true, priority = 1)
	public void ShareBudgetLineTest() {
		
		
		if (loginPage.isDisplayed()){
			Assert.assertTrue(loginPage.isDisplayed(), "expected login page to be displayed");

			loginPage.setEmail("ahlam.mansor@galilsoftware.com");
			loginPage.setPassword("Ahlam123");
			loginPage.clickLogin(true);

			loginPage.setPasscode("nopasscode");
			loginPage.clicksendPasscode(true);
		}
		
		WebdriverUtils.sleep(1000);
		secondary = new SecondaryBoard();
		board = new BudgetaBoard();
		SecondaryBoard secondary = board.getSecondaryBoard();

		MenuTrigger trigger = secondary.getBudgetMenuTrigger();
		SharePopup popup = trigger.clickShareBudget();
		Assert.assertTrue(popup.isDisplayed(), "expected share popup to be displayed"); // popup.sendEmail(email);
		popup.setName(EMAIL);
		popup.selectSharePermissios(viewPermisssion);
		popup.clickSend();

		if (popup.isShareErrorAppear() == false) {
			successPage = new SuccessPage();
			Assert.assertTrue(successPage.isDisplayed(), "Expected To Share Seccess page to be dispaly");
			successPage.clickConfirm();
		}

		else {
			popup.clickConfirm();
		}

		String BudgetName = secondary.getSelectedBudgetName();

		Assert.assertTrue(secondary.isShareIconExist(BudgetName), "The budget  was shared");

		TopBar topBar = new TopBar();
		topBar.clickLogout();

	}

	@Test(enabled = true, priority = 2)
	public void validateShareBudget() throws Exception {

		WebdriverUtils.sleep(3000);
		
		BudgetaUtils email = new BudgetaUtils();

		String invitationUrl = email.checkEmail(innerWords, subLink);

		driver.get(invitationUrl);

		LoginPage loginPage = new LoginPage();
		Assert.assertTrue(loginPage.isDisplayed(), "expected login page to be displayed");

		loginPage.setEmail(EMAIL);
		loginPage.setPassword(PASSWORD);
		loginPage.clickLogin(true);

		loginPage.setPasscode("nopasscode");
		loginPage.clicksendPasscode(true);

		BudgetNavigator navigator = new BudgetNavigator();

		Assert.assertFalse(navigator.isInputTabDispaly(), "Input tab Is display, the tab should not be display in case View only mode share");
		
		TopBar topBar = new TopBar();
		topBar.clickLogout();
		

	}

	@Test(enabled = true, priority = 3)
	public void shareBudgetExcludeCashSheet() throws Exception {

		EmailReader email = new EmailReader(EMAIL, PASSWORD, IMAP_SERVER);
		email.emptyMailbox();
		
		
//		LoginPage loginPage = new LoginPage();
		if (loginPage.isDisplayed()){
			Assert.assertTrue(loginPage.isDisplayed(), "expected login page to be displayed");

			loginPage.setEmail("ahlam.mansor@galilsoftware.com");
			loginPage.setPassword("Ahlam123");
			loginPage.clickLogin(true);

//			loginPage.setPasscode("nopasscode");
	//		loginPage.clicksendPasscode(true);
		}
		
		
		BudgetNavigator navigator = new BudgetNavigator();
		navigator.selectRandomBudgeta();
		
		secondary = new SecondaryBoard();
		navigator.openInputTab();
		
		WebdriverUtils.sleep(1000);
		secondary = new SecondaryBoard();
		board = new BudgetaBoard();
		SecondaryBoard secondary = board.getSecondaryBoard();

		MenuTrigger trigger = secondary.getBudgetMenuTrigger();
		SharePopup popup = trigger.clickShareBudget();
		Assert.assertTrue(popup.isDisplayed(), "expected share popup to be displayed"); // popup.sendEmail(email);
		popup.setName(EMAIL);
		popup.selectSharePermissios(viewPermisssion);
		popup.selectExcludeSheet("Cash");
		
		popup.clickSend();

		if (popup.isShareErrorAppear() == false) {
			successPage = new SuccessPage();
			Assert.assertTrue(successPage.isDisplayed(), "Expected To Share Seccess page to be dispaly");
			successPage.clickConfirm();
		}

		else {
			popup.clickConfirm();
		}

		String BudgetName = secondary.getSelectedBudgetName();

		Assert.assertTrue(secondary.isShareIconExist(BudgetName), "The budget  was shared");

		TopBar topBar = new TopBar();
		topBar.clickLogout();
		
		WebdriverUtils.sleep(3000);
		
		BudgetaUtils checkIvitation = new BudgetaUtils();

		String invitationUrl = checkIvitation.checkEmail(innerWords, subLink);

		driver.get(invitationUrl);

		LoginPage loginPage = new LoginPage();
		Assert.assertTrue(loginPage.isDisplayed(), "expected login page to be displayed");

		loginPage.setEmail(EMAIL);
		loginPage.setPassword(PASSWORD);
		loginPage.clickLogin(true);

	//	loginPage.setPasscode("nopasscode");
		//loginPage.clicksendPasscode(true);

		navigator = new BudgetNavigator();

		Assert.assertFalse(navigator.isInputTabDispaly(), "Input tab Is display, the tab should not be display in case View only mode share");
		
		//Add click on sheets tab and validate that cash sheet not display
		
		
		navigator.openSheetTab();
		
		TopHeaderBar topHeader = new TopHeaderBar();
		
		Assert.assertFalse(topHeader.sheetIsDisplay("Cash"), "The tab is display, the tab should not be displayed");
		
		topBar = new TopBar();
		topBar.clickLogout();
	}

	@Test(enabled = true, priority = 4)
	public void shareBudgetExcludeProfitAndLossSheet() throws Exception {
		
		EmailReader email = new EmailReader(EMAIL, PASSWORD, IMAP_SERVER);
		email.emptyMailbox();
		
		
//		LoginPage loginPage = new LoginPage();
		if (loginPage.isDisplayed()){
			Assert.assertTrue(loginPage.isDisplayed(), "expected login page to be displayed");

			loginPage.setEmail("ahlam.mansor@galilsoftware.com");
			loginPage.setPassword("Ahlam123");
			loginPage.clickLogin(true);

//			loginPage.setPasscode("nopasscode");
	//		loginPage.clicksendPasscode(true);
		}
		
		
		BudgetNavigator navigator = new BudgetNavigator();
		navigator.selectRandomBudgeta();
		
		secondary = new SecondaryBoard();
		navigator.openInputTab();
		
		WebdriverUtils.sleep(1000);
		secondary = new SecondaryBoard();
		board = new BudgetaBoard();
		SecondaryBoard secondary = board.getSecondaryBoard();

		MenuTrigger trigger = secondary.getBudgetMenuTrigger();
		SharePopup popup = trigger.clickShareBudget();
		Assert.assertTrue(popup.isDisplayed(), "expected share popup to be displayed"); // popup.sendEmail(email);
		popup.setName(EMAIL);
		popup.selectSharePermissios(viewPermisssion);
		popup.selectExcludeSheet("Profit and Loss");
		
		popup.clickSend();

		if (popup.isShareErrorAppear() == false) {
			successPage = new SuccessPage();
			Assert.assertTrue(successPage.isDisplayed(), "Expected To Share Seccess page to be dispaly");
			successPage.clickConfirm();
		}

		else {
			popup.clickConfirm();
		}

		String BudgetName = secondary.getSelectedBudgetName();

		Assert.assertTrue(secondary.isShareIconExist(BudgetName), "The budget  was shared");

		TopBar topBar = new TopBar();
		topBar.clickLogout();
		
		WebdriverUtils.sleep(3000);
		
		BudgetaUtils checkIvitation = new BudgetaUtils();

		String invitationUrl = checkIvitation.checkEmail(innerWords, subLink);

		driver.get(invitationUrl);

		LoginPage loginPage = new LoginPage();
		Assert.assertTrue(loginPage.isDisplayed(), "expected login page to be displayed");

		loginPage.setEmail(EMAIL);
		loginPage.setPassword(PASSWORD);
		loginPage.clickLogin(true);

		loginPage.setPasscode("nopasscode");
		loginPage.clicksendPasscode(true);

		navigator = new BudgetNavigator();

		Assert.assertFalse(navigator.isInputTabDispaly(), "Input tab Is display, the tab should not be display in case View only mode share");
		
		//Add click on sheets tab and validate that cash sheet not display
		
		
		navigator.openSheetTab();
		
		TopHeaderBar topHeader = new TopHeaderBar();
		
		Assert.assertFalse(topHeader.sheetIsDisplay("Profit & Loss"), "The tab is display, the tab should not be displayed");
		
		topBar = new TopBar();
		topBar.clickLogout();
		

	}
	
	@Test(enabled = true, priority = 5)
	public void shareBudgetExcludeBalanceSheet() throws Exception {

		EmailReader email = new EmailReader(EMAIL, PASSWORD, IMAP_SERVER);
		email.emptyMailbox();
		
		
//		LoginPage loginPage = new LoginPage();
		if (loginPage.isDisplayed()){
			Assert.assertTrue(loginPage.isDisplayed(), "expected login page to be displayed");

			loginPage.setEmail("ahlam.mansor@galilsoftware.com");
			loginPage.setPassword("Ahlam123");
			loginPage.clickLogin(true);

//			loginPage.setPasscode("nopasscode");
	//		loginPage.clicksendPasscode(true);
		}
		
		
		BudgetNavigator navigator = new BudgetNavigator();
		navigator.selectRandomBudgeta();
		
		secondary = new SecondaryBoard();
		navigator.openInputTab();
		
		WebdriverUtils.sleep(1000);
		secondary = new SecondaryBoard();
		board = new BudgetaBoard();
		SecondaryBoard secondary = board.getSecondaryBoard();

		MenuTrigger trigger = secondary.getBudgetMenuTrigger();
		SharePopup popup = trigger.clickShareBudget();
		Assert.assertTrue(popup.isDisplayed(), "expected share popup to be displayed"); // popup.sendEmail(email);
		popup.setName(EMAIL);
		popup.selectSharePermissios(viewPermisssion);
		popup.selectExcludeSheet("Balance Sheet");
		
		popup.clickSend();

		if (popup.isShareErrorAppear() == false) {
			successPage = new SuccessPage();
			Assert.assertTrue(successPage.isDisplayed(), "Expected To Share Seccess page to be dispaly");
			successPage.clickConfirm();
		}

		else {
			popup.clickConfirm();
		}

		String BudgetName = secondary.getSelectedBudgetName();

		Assert.assertTrue(secondary.isShareIconExist(BudgetName), "The budget  was shared");

		TopBar topBar = new TopBar();
		topBar.clickLogout();
		
		WebdriverUtils.sleep(3000);
		
		BudgetaUtils checkIvitation = new BudgetaUtils();

		String invitationUrl = checkIvitation.checkEmail(innerWords, subLink);

		driver.get(invitationUrl);

		LoginPage loginPage = new LoginPage();
		Assert.assertTrue(loginPage.isDisplayed(), "expected login page to be displayed");

		loginPage.setEmail(EMAIL);
		loginPage.setPassword(PASSWORD);
		loginPage.clickLogin(true);

		loginPage.setPasscode("nopasscode");
		loginPage.clicksendPasscode(true);

		navigator = new BudgetNavigator();

		Assert.assertFalse(navigator.isInputTabDispaly(), "Input tab Is display, the tab should not be display in case View only mode share");
		
		//Add click on sheets tab and validate that cash sheet not display
		
		
		navigator.openSheetTab();
		
		TopHeaderBar topHeader = new TopHeaderBar();
		
		Assert.assertFalse(topHeader.sheetIsDisplay("Balance Sheet"), "The tab is display, the tab should not be displayed");
		
		topBar = new TopBar();
		topBar.clickLogout();
		
	}

}
