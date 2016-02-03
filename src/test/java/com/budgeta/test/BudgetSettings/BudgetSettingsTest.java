package com.budgeta.test.BudgetSettings;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.BudgetaBoard;
import com.budgeta.pom.CreateNewSnapshotPopup;
import com.budgeta.pom.DeletePopup;
import com.budgeta.pom.MenuTrigger;
import com.budgeta.pom.NewBudgetPopup;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.pom.SharePopup;
import com.budgeta.pom.SmallPopup;
import com.budgeta.pom.SuccessPage;
import com.budgeta.pom.Versions;
import com.budgeta.pom.Versions.View;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.KnownIssue;
import com.galilsoftware.AF.core.listeners.TestFirst;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class BudgetSettingsTest extends WrapperTest {

	String snapshotName = "snapshot test_";
	String newBudgetName = "New Budget name_";
	String newSnapshotName = "rename test";
	Versions versions;
	SecondaryBoard secondary;
	String email = "ahlam_mns@hotmail.com";
	SuccessPage successPage;
	SmallPopup smallPopup;

	@BeforeMethod
	private void initTest() {	
		
		driver.manage().window().maximize();
		
		
	}
	
	@TestFirst
	@Test(enabled = true)
	public void CreateSettingTest() {
		BudgetNavigator navigator = new BudgetNavigator();
		navigator.selectRandomBudgeta();
		
		
		secondary = new SecondaryBoard();
		navigator.openInputTab();

	}

	@Test(enabled = true, priority = 1)
	public void RenameBudgetLineTest() {
		WebdriverUtils.sleep(1000);

		SecondaryBoard secondary = board.getSecondaryBoard();
		BudgetNavigator navigator = new BudgetNavigator();
		MenuTrigger trigger = secondary.getBudgetMenuTrigger();
		String BeforeRenameBudget = secondary.getSelectedBudgetName();
		trigger.clickRenameBudget();
		newBudgetName = WebdriverUtils.getTimeStamp(newBudgetName);
		secondary.setBudgetTitle(newBudgetName);
		Assert.assertTrue(navigator.isBudgetExist(newBudgetName), "expected to rename the line");
		String AfterRenameBudget = secondary.getSelectedBudgetName();
		Assert.assertNotEquals(BeforeRenameBudget, AfterRenameBudget);
	}

	@Test(enabled = true, priority = 2)
	public void FlagBudgetLineTest() {
		WebdriverUtils.sleep(1000);
		SecondaryBoard secondary = board.getSecondaryBoard();
		MenuTrigger trigger = secondary.getBudgetMenuTrigger();
		String BudgetName = secondary.getSelectedBudgetName();
		if (secondary.isBudgetFlag(BudgetName) == false) {
			secondary = new SecondaryBoard();
			trigger.clickFlagBudget();

		}
		secondary = new SecondaryBoard();

		Assert.assertTrue(secondary.isBudgetFlag(BudgetName), "Budget line is flaged");

	}

	@KnownIssue(bugID = "BUD - 2537")
	@Test(enabled = true, priority = 3)
	public void ShareBudgetLineTest() {
		WebdriverUtils.sleep(1000);
		SecondaryBoard secondary = board.getSecondaryBoard();
		MenuTrigger trigger = secondary.getBudgetMenuTrigger();
		SharePopup popup = trigger.clickShareBudget();
		Assert.assertTrue(popup.isDisplayed(), "expected share popup to be displayed"); // popup.sendEmail(email);
		popup.setName(email);
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

	}

	@Test(enabled = true, priority = 6)
	public void duplicateBudget() {
		WebdriverUtils.sleep(1000);

		MenuTrigger trigger = secondary.getBudgetMenuTrigger();
		String CurrentBudgetName = secondary.getSelectedBudgetName();
		trigger.clickDuplicateBudget();
		WebdriverUtils.sleep(2000);
		String BudgetName = secondary.getSelectedBudgetName();
		WebdriverUtils.sleep(2000);
		Assert.assertEquals(BudgetName, "Copy of " + CurrentBudgetName);
	}

	@Test(enabled = true, priority = 4)
	public void backupBudgetTest() {
		WebdriverUtils.sleep(1000);
		SecondaryBoard secondary = board.getSecondaryBoard();
		BudgetNavigator navigator = new BudgetNavigator();
		MenuTrigger trigger = navigator.getMenuTrigger();
		String budgetName = secondary.getSelectedBudgetName();
		trigger.clickBackupBudget();

		File f = null;
		try {
			System.out.println(new File("").getAbsolutePath() + "/browserDownloads/" + budgetName + ".bdg");
			f = new File(new File("").getAbsolutePath() + "/browserDownloads/" + budgetName + ".bdg");
			Assert.assertTrue(f.exists(), "Expected the file [" + budgetName + ".bdg] to exist");
			Assert.assertTrue(f.canExecute(), "Expected the file [" + budgetName + ".bdg] to be able to execute");
			Assert.assertTrue(f.canRead(), "Expected the file [" + budgetName + ".bdg] to be readable");
			long fileSize = f.getTotalSpace();
			Assert.assertTrue(fileSize > 10, "Expected the file [" + budgetName + ".bdg] size to be at least 11 bytes or more but found: " + fileSize);

		} catch (Throwable e) {
			throw e;
		}

	}
	
	
//	@Test(enabled = true, priority = 5)
//	public void restoreBudgetTest(){
//		WebdriverUtils.sleep(1000);
//		secondary = board.getSecondaryBoard();
//		BudgetNavigator navigator = new BudgetNavigator();
//		String budgetName = secondary.getSelectedBudgetName();
//		int num = navigator.getNumberOfBudget(budgetName);
//		
//		NewBudgetPopup popup = navigator.addNewBudget();
//		Assert.assertTrue(popup.isDisplayed(), "expected create budget popup to be displayed");
//		popup.clickRestoreAndUpload(new File("").getAbsolutePath() + "/browserDownloads/" + budgetName + ".bdg");
//		board = new BudgetaBoard();
//		String message = board.getNotyMessage();
//		Assert.assertEquals(message, "Budget restored successfully.");
//		navigator = new BudgetNavigator();
//		int num2 = navigator.getNumberOfBudget(budgetName);
//		Assert.assertEquals(num2,num + 1 );
//		
//		
//	}
	
	@Test(enabled = true, priority = 5)
	public void restoreBudgetTest(){
		WebdriverUtils.sleep(1000);
		secondary = board.getSecondaryBoard();
		BudgetNavigator navigator = new BudgetNavigator();
		String budgetName = secondary.getSelectedBudgetName();
		int num = navigator.getNumberOfBudget(budgetName);
		
//		NewBudgetPopup popup = navigator.addNewBudget();
//		Assert.assertTrue(popup.isDisplayed(), "expected create budget popup to be displayed");
		MenuTrigger trigger = navigator.getMenuTrigger();
		trigger.clickRestoreBudget(new File("").getAbsolutePath() + "/browserDownloads/" + budgetName + ".bdg");
		//popup.clickRestoreAndUpload(new File("").getAbsolutePath() + "/browserDownloads/" + budgetName + ".bdg");
		board = new BudgetaBoard();
		
		SmallPopup popup = new SmallPopup();
		Assert.assertTrue(popup.isDisplayed(), "Confirm Restore pop up is display");
		popup.clickConfirm();
		String message = board.getNotyMessage();
		Assert.assertEquals(message, "Budget restored successfully.");
//		navigator = new BudgetNavigator();
//		int num2 = navigator.getNumberOfBudget(budgetName);
//		Assert.assertEquals(num2,num + 1 );
		
		
	}
	

	@Test(enabled = true, priority = 7)
	public void DeleteBudgetTest() {
		WebdriverUtils.sleep(1000);
		SecondaryBoard secondary = board.getSecondaryBoard();
		BudgetNavigator navigator = new BudgetNavigator();
		MenuTrigger trigger = navigator.getMenuTrigger();

		String BudgetName = secondary.getSelectedBudgetName();
		int num = navigator.getNumberOfBudget(BudgetName);
		DeletePopup popup = trigger.clickDeleteBudget();
		Assert.assertTrue(popup.isDisplayed(), "expected delete popup to be displayed");
		popup.clickConfirm();

		int num2 = navigator.getNumberOfBudget(BudgetName);
		Assert.assertFalse(num == (num - 1), "The budget was successfully deleted, number of budget was:" + num + " now is:" + num2);

	}

	@Test(enabled = true, priority = 8)
	public void RenameBudgetFromNavTest() {
		WebdriverUtils.sleep(1000);

		SecondaryBoard secondary = board.getSecondaryBoard();
		BudgetNavigator navigator = new BudgetNavigator();
		MenuTrigger trigger = navigator.getMenuTrigger();
		String BeforeRenameBudget = secondary.getSelectedBudgetName();
		trigger.clickRenameBudgetFromNav();
		newBudgetName = WebdriverUtils.getTimeStamp(newBudgetName);
		navigator.setBudgetTitle(newBudgetName);
		Assert.assertTrue(navigator.isBudgetExist(newBudgetName), "expected to rename the line");
		String AfterRenameBudget = secondary.getSelectedBudgetName();
		Assert.assertNotEquals(BeforeRenameBudget, AfterRenameBudget);

		trigger.clickRenameBudgetFromNav();
		navigator.setBudgetTitle(BeforeRenameBudget);
	}
	
	

	
	

}
