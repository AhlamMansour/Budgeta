package com.budgeta.test.TableEditTest;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.GeneralTableEdit;
import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.BudgetaBoard;
import com.budgeta.pom.CreateNewScenarioPopup;
import com.budgeta.pom.DeletePopup;
import com.budgeta.pom.EmployeeTableEdit;
import com.budgeta.pom.Scenarios;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.pom.TopHeaderBar;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.KnownIssue;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestFirst;
import com.galilsoftware.AF.core.listeners.TestNGListener;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

@Listeners({ MethodListener.class, TestNGListener.class })
public class GeneralFields_ScenarioVsBudget extends WrapperTest{
	
	
	Scenarios scenarios;
	String scenarioName = "new general scenario to test";
	String scenarioNameToDeleteLines = "new general scenario to test delete lines_";
	protected BudgetaBoard board;
	SecondaryBoard secondaryBoard;
	GeneralTableEdit generalTableEdit;
	String newLineName = "Line to rename_";
	String newSubLineName = "Sub Line to delete_";
	
	@TestFirst
	@Test(enabled = true)
	public void setBudgetTest() {

		BudgetNavigator navigator = new BudgetNavigator();
		Assert.assertTrue(navigator.isDisplayed(), "expected to inner bar to be dislayed");
		// navigator.selectRandomBudgeta();
		navigator.selectRandomBudgetWithPrefix("TEST Forecast");
		navigator.openInputTab();
		TopHeaderBar topHeaderBar = new TopHeaderBar();
		topHeaderBar.openTableEditTab();

		topHeaderBar.openScenariowindow();
		Scenarios scenarios = new Scenarios();

		if (!topHeaderBar.isScenarioExist(scenarioName)) {
			CreateNewScenarioPopup popup = scenarios.createNewScenario();
			Assert.assertTrue(popup.isDisplayed(), "expected create new scenario popup to be displayed");
			popup.setName(scenarioName);
			popup.clickConfirm();
			topHeaderBar.openScenariowindow();
			topHeaderBar.selectScenario(scenarioName);
		} else {
			topHeaderBar.selectScenario(scenarioName);
		}
	}
	
	@KnownIssue(bugID = "BUD - 4626")
	@Test(enabled = true, priority = 1)
	public void flagGeneralLine() {
		TopHeaderBar topHeaderBar = new TopHeaderBar();
		topHeaderBar.openTableEditTab();
		generalTableEdit = new GeneralTableEdit();
		generalTableEdit.clickOnGeneralFields();
		generalTableEdit.selectRandomLine();
		int indexOfSelectedLine = generalTableEdit.getIndexOfSlectedLine();
		String selectedLine = generalTableEdit.getLineNameByIndex(indexOfSelectedLine);
		if (!generalTableEdit.isLineFlag(selectedLine, indexOfSelectedLine)) {
			generalTableEdit.flagLineBylineName(selectedLine, indexOfSelectedLine);

			Assert.assertTrue(generalTableEdit.isLineFlag(selectedLine, indexOfSelectedLine), "Selected line is not flagged :" + selectedLine);

			topHeaderBar.clearScenario();

			Assert.assertFalse(generalTableEdit.isLineFlag(selectedLine, indexOfSelectedLine), "Selected line is flagged :" + selectedLine);
			generalTableEdit.unSelectLineByIndex(indexOfSelectedLine + 1);
		}

	}

	@KnownIssue(bugID = "BUD - 4626")
	@Test(enabled = true, priority = 2)
	public void renameGeneralLine() {
		TopHeaderBar topHeaderBar = new TopHeaderBar();
		topHeaderBar.openTableEditTab();
		topHeaderBar.openScenariowindow();
		topHeaderBar.selectScenario(scenarioName);

		generalTableEdit = new GeneralTableEdit();
		generalTableEdit.clickOnGeneralFields();
		generalTableEdit.selectRandomLine();
		int indexOfSelectedLine = generalTableEdit.getIndexOfSlectedLine();
		String selectedLine = generalTableEdit.getLineNameByIndex(indexOfSelectedLine);

		newLineName = WebdriverUtils.getTimeStamp(newLineName);

		if (generalTableEdit.isLineExistByIndex(newLineName, indexOfSelectedLine)) {
			newLineName = WebdriverUtils.getTimeStamp(newLineName);
		} else {
			generalTableEdit.renameLine(selectedLine, newLineName);
			String newLineName = generalTableEdit.getLineNameByIndex(indexOfSelectedLine);
			Assert.assertTrue(generalTableEdit.isLineExistByIndex(newLineName, indexOfSelectedLine), "Line is not exist" + newLineName);

			topHeaderBar.clearScenario();
			Assert.assertFalse(generalTableEdit.isLineExistByIndex(newLineName, indexOfSelectedLine), "Line is renamed also for budget" + newLineName);

			Assert.assertTrue(generalTableEdit.isLineExistByIndex(selectedLine, indexOfSelectedLine), "Line is not exist" + selectedLine);

			generalTableEdit.unSelectLineByIndex(indexOfSelectedLine + 1);
		}

	}

	@Test(enabled = false, priority = 3)
	public void deleteEmployeeLine() {
		TopHeaderBar topHeaderBar = new TopHeaderBar();
		topHeaderBar.openTableEditTab();
		topHeaderBar.openScenariowindow();
		scenarioNameToDeleteLines = WebdriverUtils.getTimeStamp(scenarioNameToDeleteLines);
		topHeaderBar.selectScenario(scenarioNameToDeleteLines);
		generalTableEdit = new GeneralTableEdit();
		generalTableEdit.clickOnGeneralFields();
		generalTableEdit.selectRandomLine();
		int indexOfSelectedLine = generalTableEdit.getIndexOfSlectedLine();
		String selectedLine = generalTableEdit.getLineNameByIndex(indexOfSelectedLine);

		newLineName = WebdriverUtils.getTimeStamp(newLineName);
		generalTableEdit.renameLine(selectedLine, newLineName);

		selectedLine = generalTableEdit.getLineNameByIndex(indexOfSelectedLine);
		generalTableEdit.deleteLineBylineName(selectedLine, indexOfSelectedLine);
//		DeletePopup ConfirmDelete = new DeletePopup();
//		ConfirmDelete.clickConfirm();

		Assert.assertTrue(generalTableEdit.isLineRemovedByIndex(selectedLine), "Line is not deleted: " + selectedLine);

		topHeaderBar.clearScenario();
		Assert.assertTrue(generalTableEdit.isLineExistByIndex(selectedLine, indexOfSelectedLine), "Line is deleted also from budget: " + selectedLine);


	}
	
	
	@Test(enabled = true, priority = 3)
	public void deleteLine() {
		TopHeaderBar topHeaderBar = new TopHeaderBar();
		topHeaderBar.openTableEditTab();
		scenarioNameToDeleteLines = WebdriverUtils.getTimeStamp(scenarioNameToDeleteLines);
		topHeaderBar.openScenariowindow();
		Scenarios scenarios = new Scenarios();

			CreateNewScenarioPopup popup = scenarios.createNewScenario();
			Assert.assertTrue(popup.isDisplayed(), "expected create new scenario popup to be displayed");
			popup.setName(scenarioNameToDeleteLines);
			popup.clickConfirm();
			topHeaderBar.openScenariowindow();
			topHeaderBar.selectScenario(scenarioNameToDeleteLines);
			
		generalTableEdit = new GeneralTableEdit();
		generalTableEdit.clickOnGeneralFields();
		int EmployeesLinesBeforeDeleteLine = generalTableEdit.getNumberOflines();
		generalTableEdit.selectRandomLine();
		int indexOfSelectedLine = generalTableEdit.getIndexOfSlectedLine();
		String selectedLine = generalTableEdit.getLineNameByIndex(indexOfSelectedLine);
		int numberOfLines = generalTableEdit.getNumberOflines();
		
		

		while (indexOfSelectedLine == numberOfLines - 2) {
			generalTableEdit.unSelectLineByIndex(indexOfSelectedLine);
			generalTableEdit.selectRandomLine();
			indexOfSelectedLine = generalTableEdit.getIndexOfSlectedLine();
			selectedLine = generalTableEdit.getLineNameByIndex(indexOfSelectedLine);
		}
		int selectedLineLevel = generalTableEdit.getLineLevel(selectedLine);
		String nextOfSelectedLine = generalTableEdit.getLineNameByIndex(indexOfSelectedLine + 1);
		int nextOfSelectedLineLevel = generalTableEdit.getLineLevel(nextOfSelectedLine);

		while (selectedLineLevel == 0){
			generalTableEdit.unSelectLineByIndex(indexOfSelectedLine);
			generalTableEdit.selectRandomLine();
			indexOfSelectedLine = generalTableEdit.getIndexOfSlectedLine();
			selectedLine = generalTableEdit.getLineNameByIndex(indexOfSelectedLine);
			selectedLineLevel = generalTableEdit.getLineLevel(selectedLine);
			
			
		}
		if (selectedLineLevel == nextOfSelectedLineLevel) {
			newLineName = WebdriverUtils.getTimeStamp(newLineName);
			generalTableEdit.renameLine(selectedLine, newLineName);
			selectedLine = generalTableEdit.getLineNameByIndex(indexOfSelectedLine);
			
			topHeaderBar.openScenariowindow();
			topHeaderBar.selectScenario(scenarioNameToDeleteLines);
	
			generalTableEdit.deleteLineBylineName(selectedLine, indexOfSelectedLine);
//			DeletePopup ConfirmDelete = new DeletePopup();
//			ConfirmDelete.clickConfirm();
			
			Assert.assertTrue(generalTableEdit.isLineRemovedByIndex(selectedLine), "Line is not deleted: " + selectedLine);

			topHeaderBar.clearScenario();
			
			Assert.assertTrue(generalTableEdit.isLineExistByIndex(selectedLine, indexOfSelectedLine), "Line is not deleted: " + selectedLine);
			Assert.assertFalse(generalTableEdit.isLineRemovedByIndex(selectedLine), "Line is not deleted: " + selectedLine);

		}
		
		if (selectedLineLevel < nextOfSelectedLineLevel) {
			int subLinenumbers = generalTableEdit.getAllSublinesForLine(selectedLine);
			newLineName = WebdriverUtils.getTimeStamp(newLineName);
			generalTableEdit.renameSubLines(selectedLine, newSubLineName);
			List<String> subLinesName = generalTableEdit.getSubLinesName(selectedLine);
			List<String> getAllLinesName = generalTableEdit.getAllLinesName();
			generalTableEdit.renameLine(selectedLine, newLineName);
			selectedLine = generalTableEdit.getLineNameByIndex(indexOfSelectedLine);
			
			topHeaderBar.openScenariowindow();
			topHeaderBar.selectScenario(scenarioNameToDeleteLines);
			
			generalTableEdit.deleteLineBylineName(selectedLine, indexOfSelectedLine);
//			DeletePopup ConfirmDelete = new DeletePopup();
//			ConfirmDelete.clickConfirm();
			
			Assert.assertTrue(generalTableEdit.isLineRemovedByIndex(selectedLine), "Line is not deleted: " + selectedLine);
			
			for (int i = 0; i<subLinesName.size(); i++){
				Assert.assertTrue(generalTableEdit.isLineRemovedByIndex(subLinesName.get(i)), "Line is not deleted: " + selectedLine);
			}
			
			topHeaderBar.clearScenario();
			Assert.assertTrue(generalTableEdit.isLineExistByIndex(selectedLine, indexOfSelectedLine), "Line is not deleted: " + selectedLine);
			
			for (int i = 0; i<subLinesName.size(); i++){
				Assert.assertFalse(generalTableEdit.isLineRemovedByIndex(subLinesName.get(i)), "Line is not deleted: " + selectedLine);
			}

	}
	
	}

}
