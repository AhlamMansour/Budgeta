package com.budgeta.test.TableEditTest;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

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
public class Employees_ScenarioVsBudget extends WrapperTest {

	Scenarios scenarios;
	String scenarioName = "new scenario to test";
	protected BudgetaBoard board;
	SecondaryBoard secondaryBoard;
	EmployeeTableEdit tableEdit;
	String newLineName = "Line to rename_";
	String scenarioNameToDeleteLines = "new employee scenario to test delete lines_";

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
	public void flagEmployeeLine() {
		TopHeaderBar topHeaderBar = new TopHeaderBar();
		topHeaderBar.openTableEditTab();
		tableEdit = new EmployeeTableEdit();
		tableEdit.clickOnEmployeeButton();
		tableEdit.selectRandomLine();
		int indexOfSelectedLine = tableEdit.getIndexOfSlectedLine();
		String selectedLine = tableEdit.getLineNameByIndex(indexOfSelectedLine);
		if (!tableEdit.isLineFlag(selectedLine, indexOfSelectedLine)) {
			tableEdit.flagLineBylineName(selectedLine, indexOfSelectedLine);

			Assert.assertTrue(tableEdit.isLineFlag(selectedLine, indexOfSelectedLine), "Selected line is not flagged :" + selectedLine);

			topHeaderBar.clearScenario();

			Assert.assertFalse(tableEdit.isLineFlag(selectedLine, indexOfSelectedLine), "Selected line is flagged :" + selectedLine);
			tableEdit.unSelectLineByIndex(indexOfSelectedLine + 1);
		}

	}

	@KnownIssue(bugID = "BUD - 4626")
	@Test(enabled = true, priority = 2)
	public void renameEmployeeLine() {
		TopHeaderBar topHeaderBar = new TopHeaderBar();
		topHeaderBar.openTableEditTab();
		topHeaderBar.openScenariowindow();
		topHeaderBar.selectScenario(scenarioName);

		tableEdit = new EmployeeTableEdit();
		tableEdit.clickOnEmployeeButton();
		tableEdit.selectRandomLine();
		int indexOfSelectedLine = tableEdit.getIndexOfSlectedLine();
		String selectedLine = tableEdit.getLineNameByIndex(indexOfSelectedLine);

		newLineName = WebdriverUtils.getTimeStamp(newLineName);

		if (tableEdit.isLineExistByIndex(newLineName, indexOfSelectedLine)) {
			newLineName = WebdriverUtils.getTimeStamp(newLineName);
		} else {
			tableEdit.renameLine(selectedLine, newLineName);
			String newLineName = tableEdit.getLineNameByIndex(indexOfSelectedLine);
			Assert.assertTrue(tableEdit.isLineExistByIndex(newLineName, indexOfSelectedLine), "Line is not exist" + newLineName);

			topHeaderBar.clearScenario();
			Assert.assertFalse(tableEdit.isLineExistByIndex(newLineName, indexOfSelectedLine), "Line is renamed also for budget" + newLineName);

			Assert.assertTrue(tableEdit.isLineExistByIndex(selectedLine, indexOfSelectedLine), "Line is not exist" + selectedLine);

			tableEdit.unSelectLineByIndex(indexOfSelectedLine + 1);
		}

	}

	@Test(enabled = true, priority = 3)
	public void deleteEmployeeLine() {
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
		tableEdit = new EmployeeTableEdit();
		tableEdit.clickOnEmployeeButton();
		tableEdit.selectRandomLine();
		int indexOfSelectedLine = tableEdit.getIndexOfSlectedLine();
		String selectedLine = tableEdit.getLineNameByIndex(indexOfSelectedLine);

		newLineName = WebdriverUtils.getTimeStamp(newLineName);
		tableEdit.renameLine(selectedLine, newLineName);

		selectedLine = tableEdit.getLineNameByIndex(indexOfSelectedLine);
		tableEdit.deleteLineBylineName(selectedLine, indexOfSelectedLine);
//		DeletePopup ConfirmDelete = new DeletePopup();
//		ConfirmDelete.clickConfirm();

		Assert.assertTrue(tableEdit.isLineRemovedByIndex(selectedLine, indexOfSelectedLine), "Line is not deleted: " + selectedLine);

		topHeaderBar.clearScenario();
		Assert.assertTrue(tableEdit.isLineExistByIndex(selectedLine, indexOfSelectedLine), "Line is deleted also from budget: " + selectedLine);


	}
}
