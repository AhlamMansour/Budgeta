package com.budgeta.test.versions;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.CreateNewSnapshotPopup;
import com.budgeta.pom.DeletePopup;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.pom.SmallPopup;
import com.budgeta.pom.TopHeaderBar;
import com.budgeta.pom.Versions;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.KnownIssue;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestFirst;
import com.galilsoftware.AF.core.listeners.TestNGListener;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

@Listeners({ MethodListener.class, TestNGListener.class })
public class VersionsTest extends WrapperTest {

	String snapshotName = "snapshot test_";
	String newSnapshotName = "rename test";
	Versions versions;
	SecondaryBoard secondary;

//	@BeforeMethod
//	private void initTest() {	
//		
//		driver.manage().window().maximize();
//		
//		
//	}
	
	@TestFirst
	@Test(enabled = true)
	public void createSnapshot() {
		secondary = board.getSecondaryBoard();
		BudgetNavigator navigator = new BudgetNavigator();
		navigator.selectRandomBudgeta();
		//navigator.selectRandomBudgetWithPrefix("budget7_1450919934212");
		versions = new Versions();
		TopHeaderBar headerBar = new TopHeaderBar();
		headerBar.openRevisionswindow();
		CreateNewSnapshotPopup popup = versions.createNewSnapshot();
		Assert.assertTrue(popup.isDisplayed(), "expected create new version to be displayed");
		snapshotName = WebdriverUtils.getTimeStamp(snapshotName);
		popup.setName(snapshotName);
		popup.clickConfirm(true);
		versions = new Versions();

		headerBar.openRevisionswindow();
		headerBar.selectAllRevisions();
		versions = new Versions(); 
		
		Assert.assertTrue(headerBar.isVersionExist(snapshotName), "expected to add the new snapshot to view all");

		versions = new Versions();
		headerBar.selectSavedRevisions();
		Assert.assertTrue(headerBar.isVersionExist(snapshotName), "expected to add the new snapshot to Only Snapshots");
	}

	@Test(enabled = false)
	public void seeingAllSavedVersionsTest() {
		TopHeaderBar headerBar = new TopHeaderBar();
		versions = new Versions();
		headerBar.openRevisionswindow();
		headerBar.selectAllRevisions();
		Assert.assertTrue(versions.getNumberOfAutoSaveVersions() > 0, "expected number of auto saved to be bigger than 0");
		
	}

	@Test(enabled = true)
	public void seeingSnapshotsTest() {
		TopHeaderBar headerBar = new TopHeaderBar();
		versions = new Versions();
		//headerBar.openRevisionswindow();
		headerBar.selectSavedRevisions();
		versions = new Versions();
		Assert.assertFalse(headerBar.isVersionExist("Auto save"), "expected Auto Saved not included into only snapshots");
		//headerBar.openRevisionswindow();
	}

	@KnownIssue(bugID = "BUD - 3543")
	@Test(enabled = true, priority = 1)
	public void renameVersionTest() {
		TopHeaderBar headerBar = new TopHeaderBar();
		BudgetNavigator navigator = new BudgetNavigator();
		
		versions = new Versions();
	//	headerBar.openRevisionswindow();
		headerBar.selectSavedRevisions();
		headerBar.selectVersion(snapshotName);

		navigator.clickInputTab();
		
		SmallPopup popup = versions.clickRenameVersion();
		Assert.assertTrue(popup.isDisplayed(), "expected rename popup to be displayed");
		popup.setName(newSnapshotName);
		popup.clickConfirm();
		Assert.assertEquals(headerBar.selectedVesrionName(), newSnapshotName);

		popup = versions.clickRenameVersion();
		Assert.assertTrue(popup.isDisplayed(), "expected rename popup to be displayed");
		popup.setName(snapshotName);
		popup.clickConfirm();
		Assert.assertEquals(headerBar.selectedVesrionName(), snapshotName);
	}

	@Test(enabled = true, priority = 2)
	public void revertVersionTest() {
		TopHeaderBar headerBar = new TopHeaderBar();
		SmallPopup popup = new SmallPopup();
		if (popup.isDisplayed()){
			popup.clickCancel();
		}
		
		versions = new Versions();
		
		popup = versions.clickRevertVersion();
		Assert.assertTrue(popup.isDisplayed(), "expected revert popup to be displayed");
		popup.clickConfirm(true);
		BudgetNavigator navigator = new BudgetNavigator();
		navigator.clickInputTab();
		Assert.assertFalse(headerBar.selectedVersionDisplay(), "expected to remove version changes");
	}

	@KnownIssue(bugID = "BUD - 2500")
	@Test(enabled = true, priority = 4)
	public void deleteVersionTest() {
		TopHeaderBar headerBar = new TopHeaderBar();
		BudgetNavigator navigator = new BudgetNavigator();
		
		SmallPopup popup = new SmallPopup();
		if (popup.isDisplayed()){
			popup.clickCancel();
		}
		
		versions = new Versions();
		headerBar.openRevisionswindow();
		WebdriverUtils.sleep(5000);
		headerBar.selectSavedRevisions();
		versions = new Versions();
		headerBar.selectVersion(snapshotName);

		navigator.clickInputTab();

		popup = versions.clickDeleteVersion();
		Assert.assertTrue(popup.isDisplayed(), "expected rename popup to be displayed");
		popup.clickConfirm(true);
		
		navigator.clickInputTab();
		headerBar.openRevisionswindow();
		
		headerBar.selectSavedRevisions();
		Assert.assertFalse(headerBar.isVersionExist(snapshotName), "expected to add the new snapshot to Only Snapshots");
		
		
	}
	
	@KnownIssue(bugID = "BUD - 2500")
	@Test(enabled = true, priority = 3)
	public void clearVersionTest() {
		TopHeaderBar headerBar = new TopHeaderBar();
		BudgetNavigator navigator = new BudgetNavigator();
		
		SmallPopup popup = new SmallPopup();
		if (popup.isDisplayed()){
			popup.clickCancel();
		}
		
		versions = new Versions();

		headerBar.openRevisionswindow();
		versions = new Versions();
		headerBar.selectVersion("Snapshot");
		navigator.clickInputTab();
		headerBar.clearVersion();
		
		navigator.clickInputTab();
		Assert.assertFalse(headerBar.selectedVersionDisplay(), "expected to remove version changes");
	}

}
