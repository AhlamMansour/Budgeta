package com.budgeta.test.PlansAndPricing;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.LicenseScreen;
import com.budgeta.pom.LimitPopup;
import com.budgeta.pom.NewBudgetPopup;
import com.budgeta.pom.PlansAndPricingWindow;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.pom.TopBar;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestNGListener;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

@Listeners({ MethodListener.class, TestNGListener.class })
public class PermissionsForStarterUserTest extends WrapperTest{
	
	@Test(enabled = false)
	public void AddNewBudget() {
	
		
		BudgetNavigator navigator = new BudgetNavigator();
		

	
			int numberOfExistBudget = navigator.getNumbreOfExistBudgets();
			if (numberOfExistBudget > 5){
				LimitPopup popup = navigator.budgetLimit();
				Assert.assertTrue(popup.isDisplayed(), "Budget Limit pop up is display");
				Assert.assertEquals(popup.getTilte(), "Budgets Limit", "Budget Limit pop up is open");
			}
			if (numberOfExistBudget <= 5){
				NewBudgetPopup popup = navigator.addNewBudget();
				String budgetaName = "Budget";
				if(!budgetaName.isEmpty())
					budgetaName = WebdriverUtils.getTimeStamp(budgetaName+"_");
				popup.setName(budgetaName);
				popup.clickContinue(true);
				popup.clickCreate();
					
				SecondaryBoard secondaryBoard = board.getSecondaryBoard();	
				Assert.assertEquals(secondaryBoard.getSelectedBudgetName(), budgetaName);
			
			}
			
		

	}
	
	@Test(enabled = true)
	public void AddLines() {
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
		navigator.selectRandomBudgetWithPrefix("Galil123_2");
		navigator.openInputTab();
		
		SecondaryBoard secondaryBoard = board.getSecondaryBoard();
		int NumberOfBudgetLines = secondaryBoard.getNumberOfLines();

		if (currentPlan.equals("STARTER")){
			
//			if (NumberOfBudgetLines >= 14){
//				secondaryBoard.addLine();
//				LimitPopup popup = new LimitPopup();
//				Assert.assertTrue(popup.isDisplayed(), "Budget Line Limit is diplay");
//				Assert.assertEquals(popup.getTilte(), "Budget line limit", "Budget line limit popup is open");
//				
//			}
			
			while (NumberOfBudgetLines < 14){
				secondaryBoard.addAllLines();
				
				
				
			}
			
			
			
		}
		
		
		
		
			

	
	}
	

}
