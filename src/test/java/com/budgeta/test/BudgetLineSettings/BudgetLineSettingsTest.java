package com.budgeta.test.BudgetLineSettings;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.DeletePopup;
import com.budgeta.pom.MenuTrigger;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.pom.SharePopup;
import com.budgeta.pom.SmallPopup;
import com.budgeta.pom.SuccessPage;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.TestFirst;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class BudgetLineSettingsTest extends WrapperTest {

	MenuTrigger menuTrigger;
	BudgetNavigator budgetNavigator;
	String email = "ahlam_mns@hotmail.com";
	SuccessPage successPage;
	SmallPopup smallPopup;
	String budgetLineName = "New Name";
	String currentLine = "Revenues";
	SharePopup sharePopup;

	@BeforeMethod
	private void initTest() {

		driver.manage().window().maximize();

	}

	@TestFirst
	@Test(enabled = true)
	public void CreateSettingTest() {
		SecondaryBoard secondary = board.getSecondaryBoard();
		BudgetNavigator navigator = new BudgetNavigator();
		navigator.selectRandomBudgeta();
		

		budgetNavigator = new BudgetNavigator();
		budgetNavigator.openInputTab();

		secondary.addLine("Revenues");
		secondary = new SecondaryBoard();

	}

	// done
	@Test(enabled = true, priority = 1)
	public void DuplicateBudgetLineTest() {
		WebdriverUtils.sleep(1000);
		SecondaryBoard secondary = board.getSecondaryBoard();
		// secondary.selectRandomBudgetWithPrefix("budget7_144215547406");
		MenuTrigger trigger = secondary.getLineSettings("Revenues");
		int num = secondary.getNumberOfLines("Revenues");
		trigger.clickDuplicate();
		secondary = new SecondaryBoard();
		int num2 = secondary.getNumberOfLines("Revenues");
		Assert.assertTrue(num2 == (num + 1), "the line is duplicated, number of lines was: " + num + " and now is: " + num2);
	}

	// done
	@Test(enabled = true, priority = 2)
	public void CopyBudgetLineTest() {
		WebdriverUtils.sleep(1000);
		SecondaryBoard secondary = board.getSecondaryBoard();
		secondary = new SecondaryBoard();
		int num = secondary.getNumberOfSubLinesForLine("Other income and expenses", "Revenues");

		MenuTrigger trigger = secondary.getLineSettings("Revenues");
		trigger.clickCopy();
		secondary = new SecondaryBoard();
		int num2 = secondary.getNumberOfSubLinesForLine("Other income and expenses", "Revenues");
		Assert.assertTrue(num2 > num, "The line is coppied");
	}

	// Done
	@Test(enabled = true, priority = 3)
	public void MoveBudgetLineTest() {
		WebdriverUtils.sleep(1000);
		SecondaryBoard secondary = board.getSecondaryBoard();
		int num = secondary.getNumberOfSubLinesForLine("Other income and expenses", "Revenues");
		int num2;
		// secondary = new SecondaryBoard();
		if (secondary.isLineExist("Other income and expenses") == true) {
			MenuTrigger trigger = secondary.getLineSettings("Revenues");
			secondary = trigger.clickMove();
		} else {
			secondary.addLine("Other income and expenses");
			secondary = new SecondaryBoard();
			MenuTrigger trigger = secondary.getLineSettings("Revenues");
			secondary = trigger.clickMove();
		}
		num2 = secondary.getNumberOfSubLinesForLine("Other income and expenses", "Revenues");
		Assert.assertTrue(num2 > num, "The line is Moved, before move: " + num + "... after move: " + num2);

		// secondary.getNumberOfSubLinesForLine("Other income and expenses","Revenues");
	}

	@Test(enabled = true, priority = 4)
	public void FlagBudgetLineTest() {
		WebdriverUtils.sleep(1000);
		SecondaryBoard secondary = board.getSecondaryBoard();
		MenuTrigger trigger = secondary.getLineSettings("Revenues");
		if (secondary.isLineFlag("Revenues") == false) {
			secondary = new SecondaryBoard();
			trigger = secondary.getLineSettings("Revenues");
			trigger.clickFlag();

		}
		secondary = new SecondaryBoard();
		trigger = secondary.getLineSettings("Revenues");

		Assert.assertTrue(secondary.isLineFlag(currentLine), "Budget line is flaged");

	}

	// Done
	@Test(enabled = true, priority = 5)
	public void RenameBudgetLineTest() {
		WebdriverUtils.sleep(1000);

		SecondaryBoard secondary = board.getSecondaryBoard();
		MenuTrigger trigger = secondary.getLineSettings("Revenues");
		trigger.clickRename();

		// smallPopup.setName(budgetLineName);

		secondary.RenameLine(budgetLineName);
		Assert.assertTrue(secondary.isLineExist(budgetLineName), "expected to rename the line");

		secondary = new SecondaryBoard();
		trigger = secondary.getLineSettings(budgetLineName);
		trigger.clickRename();
		secondary.RenameLine(currentLine);

	}

	@Test(enabled = true, priority = 6)
	public void ShareBudgetLineTest() {
		WebdriverUtils.sleep(1000);
		SecondaryBoard secondary = board.getSecondaryBoard();
		MenuTrigger trigger = secondary.getLineSettings("Revenues");

		SharePopup popup = trigger.clickShare();
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

		Assert.assertTrue(secondary.isLineShared(currentLine), "The budget line was shared");

	}

	@Test(enabled = true, priority = 7)
	public void DeleteBudgetLineTest() {
		WebdriverUtils.sleep(1000);
		SecondaryBoard secondary = board.getSecondaryBoard();
		MenuTrigger trigger = secondary.getLineSettings("Revenues");
		int num = secondary.getNumberOfLines("Revenues");

		DeletePopup popup = trigger.clickDelete();
		Assert.assertTrue(popup.isDisplayed(), "expected delete popup to be displayed");
		popup.clickConfirm();

		// Assert.assertFalse(secondary.isLineExist(budgetLineName),"Budget line is deleted");
		int num2 = secondary.getNumberOfLines("Revenues");
		Assert.assertTrue(num2 == (num - 1), "the line is deleted, number of lines was: " + num + "and now is: " + num2);

	}

}
