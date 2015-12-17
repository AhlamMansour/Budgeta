package com.budgeta.test;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.GeneralSection;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.pom.TestModal;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestNGListener;

@Listeners({ MethodListener.class, TestNGListener.class })
public class RunErrorTest extends WrapperTest {

	private BudgetNavigator navigator;
	private SecondaryBoard secondaryBoard;
	private GeneralSection general;
	private TestModal testModal;

	@Test(enabled = true)
	public void errorOnRunTest() {
		navigator = board.getBudgetNavigator();
		secondaryBoard = board.getSecondaryBoard();
		navigator.openInputTab();
		general = new GeneralSection();
		testModal = general.openBugetaErrorModal();
		testModal.clickOnRunTest();
		Assert.assertTrue(testModal.checkErrorsResult(),
				"The error result doesn't reflect the actual number of errors");

	}

}
