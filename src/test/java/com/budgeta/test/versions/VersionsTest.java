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
import com.budgeta.pom.Versions.View;
import com.budgeta.test.WrapperTest;
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

	@TestFirst
	@Test(enabled = true)
	public void createSnapshot() {
		secondary = board.getSecondaryBoard();
		BudgetNavigator navigator = new BudgetNavigator();
		navigator.selectRandomBudgeta();
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
		Assert.assertTrue(headerBar.isVersionExist(snapshotName), "expected to add the new snapshot to view all");

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
		headerBar.openRevisionswindow();
		headerBar.selectSavedRevisions();
		Assert.assertFalse(headerBar.isVersionExist("Auto save"), "expected Auto Saved not included into only snapshots");
	}

	@Test(enabled = true, priority = 1)
	public void renameVersionTest() {
		TopHeaderBar headerBar = new TopHeaderBar();
		BudgetNavigator navigator = new BudgetNavigator();
		
		versions = new Versions();
		headerBar.selectSavedRevisions();
		headerBar.selectVersion(snapshotName);

		navigator.openInputTab();
		
		SmallPopup popup = versions.clickRenameVersion();
		Assert.assertTrue(popup.isDisplayed(), "exoected rename popup to be displayed");
		popup.setName(newSnapshotName);
		popup.clickConfirm();
		Assert.assertEquals(headerBar.selectedVesrionName(), newSnapshotName);

		popup = versions.clickRenameVersion();
		Assert.assertTrue(popup.isDisplayed(), "exoected rename popup to be displayed");
		popup.setName(snapshotName);
		popup.clickConfirm();
		Assert.assertEquals(headerBar.selectedVesrionName(), snapshotName);
	}

	@Test(enabled = true, priority = 2)
	public void revertVersionTest() {
		TopHeaderBar headerBar = new TopHeaderBar();
		versions = new Versions();
		SmallPopup popup = versions.clickRevertVersion();
		Assert.assertTrue(popup.isDisplayed(), "exoected revert popup to be displayed");
		popup.clickConfirm(true);
		BudgetNavigator navigator = new BudgetNavigator();
		navigator.openInputTab();
		Assert.assertFalse(headerBar.selectedVersionDisplay(), "expected to remove version changes");
	}

	@Test(enabled = true, priority = 3)
	public void deleteVersionTest() {
		TopHeaderBar headerBar = new TopHeaderBar();
		BudgetNavigator navigator = new BudgetNavigator();
		
		versions = new Versions();
		headerBar.openRevisionswindow();
		headerBar.selectSavedRevisions();
		headerBar.selectVersion(snapshotName);

		navigator.openInputTab();
		
		DeletePopup popup = versions.clickDeleteVersion();
		Assert.assertTrue(popup.isDisplayed(), "exoected rename popup to be displayed");
		popup.clickConfirm(true);
		
		navigator.openInputTab();
		headerBar.openRevisionswindow();
		
		headerBar.selectSavedRevisions();
		Assert.assertFalse(headerBar.isVersionExist(snapshotName), "expected to add the new snapshot to Only Snapshots");
		
		
	}
	
	@Test(enabled = true, priority = 4)
	public void clearVersionTest() {
		TopHeaderBar headerBar = new TopHeaderBar();
		BudgetNavigator navigator = new BudgetNavigator();
		
		versions = new Versions();
	
		headerBar.selectVersion("Snapshot before revert");
		navigator.openInputTab();
		headerBar.clearVersion();
		
		navigator.openInputTab();
		Assert.assertFalse(headerBar.selectedVersionDisplay(), "expected to remove version changes");
	}

}
