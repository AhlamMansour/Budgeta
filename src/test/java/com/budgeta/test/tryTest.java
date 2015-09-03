package com.budgeta.test;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.BudgetaBoard;
import com.budgeta.pom.NewBudgetPopup;
import com.budgeta.pom.TopBar;
import com.budgeta.pom.WelcomeScreen;
import com.galilsoftware.AF.core.listeners.KnownIssue;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestNGListener;

@Listeners({ MethodListener.class, TestNGListener.class })
public class tryTest extends WrapperTest {

	@Test(enabled = true)
	public void try1() {
		System.out.println("success");
		WelcomeScreen wl = new WelcomeScreen();
		BudgetaBoard board = wl.clickSkipTour();

		NewBudgetPopup popup = board.addBudgeta();
		popup.isDisplayed();

		Assert.assertTrue(board.isDisplayed(), "aaa");
		TopBar bar = new TopBar();
		bar.clickLogout();

	}

	@Test(enabled = true)
	public void failTest() {
		Assert.assertTrue(false);

	}

	@KnownIssue(bugID = "231")
	@Test(enabled = true, expectedExceptions = AssertionError.class)
	public void knowFail() {
		Assert.assertEquals(false, true);
	}
}
