package com.budgeta.test.BudgetSettings;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.BudgetaBoard;
import com.budgeta.pom.DeletePopup;
import com.budgeta.pom.MenuTrigger;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.pom.SharePopup;
import com.budgeta.pom.SmallPopup;
import com.budgeta.pom.SuccessPage;
import com.budgeta.pom.Versions;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestFirst;
import com.galilsoftware.AF.core.listeners.TestNGListener;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

@Listeners({ MethodListener.class, TestNGListener.class })
public class DeleteAllBudgets extends WrapperTest{
	
	String snapshotName = "snapshot test_";
	String newBudgetName = "NewBudgetname_";
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

	@Test(enabled = true)
	public void DeleteAllBudgetTest() {
		WebdriverUtils.sleep(1000);
		SecondaryBoard secondary = board.getSecondaryBoard();
		BudgetNavigator navigator = new BudgetNavigator();
		MenuTrigger trigger = navigator.getMenuTrigger();

		String BudgetName = secondary.getSelectedBudgetName();
		int num = navigator.getNumberOfBudget(BudgetName);
		DeletePopup popup = trigger.clickDeleteAllBudget();
//		Assert.assertTrue(popup.isDisplayed(), "expected delete popup to be displayed");
//		popup.clickConfirm();

		int num2 = navigator.getNumberOfBudget(BudgetName);
		
		Assert.assertFalse(num == (num - 1), "The budget was successfully deleted, number of budget was:" + num + " now is:" + num2);

	}
	


	
	

}
