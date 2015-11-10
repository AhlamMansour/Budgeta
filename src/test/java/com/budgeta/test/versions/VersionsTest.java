package com.budgeta.test.versions;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.CreateNewSnapshotPopup;
import com.budgeta.pom.DeletePopup;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.pom.SmallPopup;
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
	secondary.selectRandomBudgeta();
	versions = new Versions();
	versions = secondary.openVersions();
	CreateNewSnapshotPopup popup = versions.createNewSnapshot();
	Assert.assertTrue(popup.isDisplayed(), "expected create new version to be displayed");
	snapshotName = WebdriverUtils.getTimeStamp(snapshotName);
	popup.setName(snapshotName);
	popup.clickConfirm(true);
	versions = new Versions();
	Assert.assertTrue(versions.isSnapshotExist(snapshotName, View.VIEW_ALL), "expected to add the new snapshot to view all");
	Assert.assertTrue(versions.isSnapshotExist(snapshotName, View.ONLY_SNAPSHOTS), "expected to add the new snapshot to Only Snapshots");
    }

    @Test(enabled = false)
    public void seeingAllSavedVersionsTest() {
	versions = new Versions();
	Assert.assertTrue(versions.getNumberOfAutoSaveVersions() > 0, "expected number of auto saved to be bigger than 0");
    }

    @Test(enabled = false)
    public void seeingSnapshotsTest() {
	versions = new Versions();
	Assert.assertFalse(versions.isSnapshotExist("Auto Saved", View.ONLY_SNAPSHOTS), "expected Auto Saved not included into only snapshots");
    }

    @Test(enabled = false, priority = 1)
    public void renameVersionTest() {
	versions = new Versions();
	versions = versions.selectVersion(View.ONLY_SNAPSHOTS, snapshotName);
	SmallPopup popup = versions.clickRenameVersion();
	Assert.assertTrue(popup.isDisplayed(), "exoected rename popup to be displayed");
	popup.setName(newSnapshotName);
	popup.clickConfirm();
	Assert.assertEquals(versions.getSelectedVersion(), newSnapshotName);

	popup = versions.clickRenameVersion();
	Assert.assertTrue(popup.isDisplayed(), "exoected rename popup to be displayed");
	popup.setName(snapshotName);
	popup.clickConfirm();
	Assert.assertEquals(versions.getSelectedVersion(), snapshotName);
    }

    @Test(enabled = false, priority = 2)
    public void revertVersionTest() {
	versions = new Versions();
	versions = versions.selectVersion(View.ONLY_SNAPSHOTS, snapshotName);
	SmallPopup popup = versions.clickRevertVersion();
	Assert.assertTrue(popup.isDisplayed(), "exoected revert popup to be displayed");
	popup.clickConfirm(true);
	secondary = new SecondaryBoard();
	secondary.openVersions();
	versions = new Versions();
	versions = versions.selectVersion(View.ONLY_SNAPSHOTS, snapshotName);
	Assert.assertTrue(secondary.getNumberOfVersionChanges() == 0, "expected to remove version changes");
    }

    @Test(enabled = false, priority = 3)
    public void deleteVersionTest() {
	secondary = new SecondaryBoard();
	secondary.openVersions();
	versions = new Versions();
	versions = versions.selectVersion(View.ONLY_SNAPSHOTS, snapshotName);
	DeletePopup popup = versions.clickDeleteVersion();
	Assert.assertTrue(popup.isDisplayed(), "exoected rename popup to be displayed");
	popup.clickConfirm(true);
	secondary = new SecondaryBoard();
	secondary.openVersions();
	versions = new Versions();
	Assert.assertFalse(versions.isSnapshotExist(snapshotName, View.VIEW_ALL), "expected " + snapshotName + " to be deleted from view all");
	Assert.assertFalse(versions.isSnapshotExist(snapshotName, View.ONLY_SNAPSHOTS), "expected " + snapshotName + " to be deleted from only snapshots");
    }

}
