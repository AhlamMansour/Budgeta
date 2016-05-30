package com.budgeta.test.PlansAndPricing;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.BudgetaBoard;
import com.budgeta.pom.BuildCompanyBudgetPopup;
import com.budgeta.pom.LicenseScreen;
import com.budgeta.pom.LimitPopup;
import com.budgeta.pom.LoginPage;
import com.budgeta.pom.MenuTrigger;
import com.budgeta.pom.NewBudgetPopup;
import com.budgeta.pom.PlansAndPricingWindow;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.pom.SharePopup;
import com.budgeta.pom.TopBar;
import com.budgeta.test.BudgetaTest;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestFirst;
import com.galilsoftware.AF.core.listeners.TestNGListener;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

@Listeners({ MethodListener.class, TestNGListener.class })
public class PermissionsForEssentialUserTest extends BudgetaTest{
	
	
	String email = "ahlam_mns@hotmail.com";
	
	
	@TestFirst
	@Test(enabled = true)
	public void setTest() {
		driver.get(baseURL);

		LoginPage loginPage = new LoginPage();
		Assert.assertTrue(loginPage.isDisplayed(), "expected login page to be displayed");
		
		loginPage.setEmail("galiltest1234@gmail.com");
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
		
		Assert.assertEquals(currentPlan, "ESSENTIAL", "Your current plan is Starter plan");
	
			
		

	}
	
	@Test(enabled = true)
	public void AddNewBudget() {
	
		
		BudgetNavigator navigator = new BudgetNavigator();
		

	
			int numberOfExistBudget = navigator.getNumbreOfExistBudgets();
			if (numberOfExistBudget > 5){
				LimitPopup popup = navigator.budgetLimit();
				Assert.assertTrue(popup.isDisplayed(), "Budget Limit pop up is display");
				Assert.assertEquals(popup.getTilte(), "Budgets Limit", "Budget Limit pop up is open");
				popup.clickCancel();
				popup.clickCancel();
			}
			if (numberOfExistBudget <= 5){
				NewBudgetPopup popup = navigator.addNewBudget();
				String budgetaName = "Budget";
				if(!budgetaName.isEmpty())
					budgetaName = WebdriverUtils.getTimeStamp(budgetaName+"_");
				popup.setName(budgetaName);
				popup.clickContinue(true);
				popup.clickCreate();
					
				BuildCompanyBudgetPopup budgetPopup = new BuildCompanyBudgetPopup();
				budgetPopup.clickExit();
				
				BudgetaBoard board = new BudgetaBoard();
				SecondaryBoard secondaryBoard = board.getSecondaryBoard();	
				Assert.assertEquals(secondaryBoard.getSelectedBudgetName(), budgetaName);
			
			}
			
		

	}
	
	@Test(enabled = false)
	public void AddLines() {
		SecondaryBoard secondaryBoard = new SecondaryBoard();
		int NumberOfBudgetLines = secondaryBoard.getNumberOfLines();

			while (NumberOfBudgetLines < 14){
				secondaryBoard.addAllBudgetLines();
				NumberOfBudgetLines = secondaryBoard.getNumberOfLines();
				secondaryBoard.addNewline();
				NumberOfBudgetLines = secondaryBoard.getNumberOfLines();
	
			}
			
			if (NumberOfBudgetLines >= 14){
			secondaryBoard.addLine();
			LimitPopup popup = new LimitPopup();
			Assert.assertTrue(popup.isDisplayed(), "Budget Line Limit is diplay");
			Assert.assertEquals(popup.getTilte(), "Budget line limit", "Budget line limit popup is open");
			popup.clickCancel();
			
	}

	}
	
	
	@Test(enabled = true)
	public void shareBudgetPermissions() {
		
		String prefix = email.substring(0, email.indexOf("@"));
		String suffix = email.substring(email.indexOf("@"));
		email = prefix + WebdriverUtils.getTimeStamp("_") + suffix;
		
		String modifyPermisssion = "Can Modify";
		String viewPermisssion = "Can View";
		

		
			SecondaryBoard secondaryBoard = new SecondaryBoard();
			MenuTrigger trigger = secondaryBoard.getBudgetMenuTrigger();
			SharePopup popup = trigger.clickShareBudget();
			Assert.assertTrue(popup.isDisplayed(), "expected share popup to be displayed");
			popup.setName(email);
			popup.selectSharePermissios(modifyPermisssion);
	//		popup.clickSend();
			LimitPopup limitPopup = new LimitPopup();
			Assert.assertTrue(limitPopup.isDisplayed(), "Budget Line Limit is diplay");
			Assert.assertEquals(limitPopup.getTilte(), "Sharing permissions", "Budget line limit popup is open");
			limitPopup.clickCancel();
			limitPopup.clickCancel();
			
			trigger = secondaryBoard.getBudgetMenuTrigger();
			popup = trigger.clickShareBudget();
			popup =new SharePopup();
			Assert.assertTrue(popup.isDisplayed(), "expected share popup to be displayed");
			popup.setName(email);
			popup.selectSharePermissios(viewPermisssion);
			popup.clickSend();
			
			popup.clickConfirm();
		
		
		}
		
			

	
	

	
	
}
