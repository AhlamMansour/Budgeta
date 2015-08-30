package com.budgeta.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.budgeta.BudgetaBoard;
import com.budgeta.TopBar;
import com.budgeta.WelcomeScreen;

public class tryTest extends WrapperTest {

	@Test
	public void try1() {
		System.out.println("success");
		WelcomeScreen wl = new WelcomeScreen();
		BudgetaBoard board = wl.clickSkipTour();
		Assert.assertTrue(board.isDisplayed(), "aaa");
		TopBar bar = new TopBar();
		bar.clickLogout();
		
		
	}
}
