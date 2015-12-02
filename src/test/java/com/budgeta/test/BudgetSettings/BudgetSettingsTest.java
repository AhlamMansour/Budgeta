package com.budgeta.test.BudgetSettings;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.CreateNewSnapshotPopup;
import com.budgeta.pom.DeletePopup;
import com.budgeta.pom.MenuTrigger;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.pom.SharePopup;
import com.budgeta.pom.SmallPopup;
import com.budgeta.pom.SuccessPage;
import com.budgeta.pom.Versions;
import com.budgeta.pom.Versions.View;
import com.budgeta.test.WrapperTest;
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

	@TestFirst
	@Test(enabled = true)
	public void CreateSettingTest() {
		BudgetNavigator navigator = new BudgetNavigator();
		navigator.selectRandomBudgetWithPrefix("budget7_");
		secondary = new SecondaryBoard();

	}

	@Test(enabled = false, priority = 1)
	public void duplicateBudget() {
		WebdriverUtils.sleep(1000);
		SecondaryBoard secondary = board.getSecondaryBoard();

		MenuTrigger trigger = secondary.getBudgetMenuTrigger();
		String CurrentBudgetName = secondary.getSelectedBudgetName();
		trigger.clickDuplicateBudget();
		WebdriverUtils.sleep(2000);
		String BudgetName = secondary.getSelectedBudgetName();
		
		Assert.assertEquals(BudgetName, "Copy of " + CurrentBudgetName);
		}
	
	
	@Test(enabled = true, priority = 2)
	public void RenameBudgetLineTest() {
		WebdriverUtils.sleep(1000);

		SecondaryBoard secondary = board.getSecondaryBoard();
		BudgetNavigator navigator = new BudgetNavigator();
		MenuTrigger trigger = secondary.getBudgetMenuTrigger();
		String BeforeRenameBudget = secondary.getSelectedBudgetName();
		trigger.clickRenameBudget();
		newBudgetName = WebdriverUtils.getTimeStamp(newBudgetName);
		secondary.setBudgetTitle(newBudgetName);
		Assert.assertTrue(navigator.isBudgetExist(newBudgetName),
				"expected to rename the line");
		String AfterRenameBudget = secondary.getSelectedBudgetName();	
		Assert.assertNotEquals(BeforeRenameBudget, AfterRenameBudget);
	}
	
	
	
    @Test(enabled = false, priority = 3)
    public void createSnapshot() {
    WebdriverUtils.sleep(1000);

	SecondaryBoard secondary = board.getSecondaryBoard();
	MenuTrigger trigger = secondary.getBudgetMenuTrigger();
	
	CreateNewSnapshotPopup popup = trigger.snapshotBudget();
	Assert.assertTrue(popup.isDisplayed(), "expected create new version to be displayed");
	snapshotName = WebdriverUtils.getTimeStamp(snapshotName);
	popup.setName(snapshotName);
	popup.clickConfirm(true);
	versions = new Versions();
	versions = secondary.openVersions();
	Assert.assertTrue(versions.isSnapshotExist(snapshotName, View.VIEW_ALL), "expected to add the new snapshot to view all");
	Assert.assertTrue(versions.isSnapshotExist(snapshotName, View.ONLY_SNAPSHOTS), "expected to add the new snapshot to Only Snapshots");
    }
    
    
    
    
    @Test(enabled = false, priority = 4)
	public void backupBudgetTest(){
		WebdriverUtils.sleep(1000);
		SecondaryBoard secondary = board.getSecondaryBoard();
		MenuTrigger trigger = secondary.getBudgetMenuTrigger();
		String budgetName = secondary.getSelectedBudgetName();
		trigger.clickBackupBudget();
		
		
		File f = null;
		try{
			System.out.println(new File("").getAbsolutePath()+"/browserDownloads/"+budgetName+".bdg");
			f = new File(new File("").getAbsolutePath()+"/browserDownloads/"+budgetName+".bdg");
			Assert.assertTrue(f.exists(),"Expected the file ["+budgetName+".bdg] to exist");
			Assert.assertTrue(f.canExecute(),"Expected the file ["+budgetName+".bdg] to be able to execute");
			Assert.assertTrue(f.canRead(),"Expected the file ["+budgetName+".bdg] to be readable");
			long fileSize = f.getTotalSpace();
			Assert.assertTrue(fileSize > 10 ,"Expected the file ["+budgetName+".bdg] size to be at least 11 bytes or more but found: "+fileSize);
			
		}catch(Throwable e){
			throw e;
		}


	}

	@Test(enabled = false, priority = 5)
	public void restoreBudgetTest(){
		WebdriverUtils.sleep(1000);
		SecondaryBoard secondary = board.getSecondaryBoard();
		BudgetNavigator navigator = new BudgetNavigator();
		MenuTrigger trigger = secondary.getBudgetMenuTrigger();
		String budgetName = secondary.getSelectedBudgetName();
		
		int num = navigator.getNumberOfBudget(budgetName);
		
		File f = null;
		f = new File(new File("").getAbsolutePath()+"/browserDownloads/");
		
		trigger.clickRestoreBudget(f + "\\" + budgetName +".bdg");
		secondary = board.getSecondaryBoard();
		int num2 = navigator.getNumberOfBudget(budgetName);

		Assert.assertTrue(num2 == (num + 1),"The budget was successfully restored, number of budget was:"+ num + " now is:" + num2);

	}
	
	
	@Test(enabled = false, priority = 6)
	public void ShareBudgetLineTest() {
		WebdriverUtils.sleep(1000);
		SecondaryBoard secondary = board.getSecondaryBoard();
		MenuTrigger trigger = secondary.getBudgetMenuTrigger();
		BudgetNavigator navigator = new BudgetNavigator();

		SharePopup popup = trigger.clickShareBudget();
		Assert.assertTrue(popup.isDisplayed(),
				"expected share popup to be displayed"); // popup.sendEmail(email);
		popup.setName(email);
		popup.clickSend();

		if (popup.isShareErrorAppear() == false) {
			successPage = new SuccessPage();
			Assert.assertTrue(successPage.isDisplayed(),
					"Expected To Share Seccess page to be dispaly");
			successPage.clickConfirm();
		}

		else {
			popup.clickConfirm();
		}
		
		String BudgetName = secondary.getSelectedBudgetName();

		Assert.assertTrue(navigator.isShareIconExist(BudgetName),"The budget  was shared");

	}
	
	
	@Test(enabled = false, priority = 7)
	public void DeleteBudgetLineTest() {
		WebdriverUtils.sleep(1000);
		SecondaryBoard secondary = board.getSecondaryBoard();
		BudgetNavigator navigator = new BudgetNavigator();
		MenuTrigger trigger = secondary.getBudgetMenuTrigger();
		
		String BudgetName = secondary.getSelectedBudgetName();
		int num = navigator.getNumberOfBudget(BudgetName);
		DeletePopup popup = trigger.clickDeleteBudget();
		Assert.assertTrue(popup.isDisplayed(),
				"expected delete popup to be displayed");
		popup.clickConfirm();
		
		int num2 = navigator.getNumberOfBudget(BudgetName);
		Assert.assertFalse(num == (num-1), "The budget was successfully deleted, number of budget was:"+ num + " now is:" + num2);


	}
	
	

}
