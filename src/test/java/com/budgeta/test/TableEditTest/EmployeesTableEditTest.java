package com.budgeta.test.TableEditTest;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.AddNotePopup;
import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.BudgetaBoard;
import com.budgeta.pom.CreateNewEmployeePopup;
import com.budgeta.pom.DeletePopup;
import com.budgeta.pom.EmployeeTableEdit;
import com.budgeta.pom.GeneralSection;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.pom.TopHeaderBar;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestFirst;
import com.galilsoftware.AF.core.listeners.TestNGListener;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

@Listeners({ MethodListener.class, TestNGListener.class })
public class EmployeesTableEditTest extends WrapperTest {

	protected BudgetaBoard board;
	SecondaryBoard secondaryBoard;
	EmployeeTableEdit tableEdit;
	CreateNewEmployeePopup addEmployee;
	String note = "Add new note_";
	String newLineName = "Line to delete_";
	String duplicateLine = "Line to duplicate";
	String employeeName = "New Employee_";

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

	}

	@Test(enabled = true, priority = 1)
	public void duplicateEmployeeLine() {
		TopHeaderBar topHeaderBar = new TopHeaderBar();
		topHeaderBar.openTableEditTab();
		tableEdit = new EmployeeTableEdit();
		tableEdit.clickOnEmployeeButton();
		int EmployeesLines = tableEdit.getNumberOflines();
		tableEdit.selectRandomLine();
		int indexOfSelectedLine = tableEdit.getIndexOfSlectedLine();
		String selectedLine = tableEdit.getLineNameByIndex(indexOfSelectedLine);
		int numberOfAllLines = tableEdit.getNumberOflines();

		while (indexOfSelectedLine == numberOfAllLines - 2) {
			tableEdit.unSelectLineByIndex(indexOfSelectedLine);
			tableEdit.selectRandomLine();
			indexOfSelectedLine = tableEdit.getIndexOfSlectedLine();
			selectedLine = tableEdit.getLineNameByIndex(indexOfSelectedLine);
		}

		duplicateLine = WebdriverUtils.getTimeStamp(duplicateLine);
		tableEdit.renameLine(selectedLine, duplicateLine);

		selectedLine = tableEdit.getLineNameByIndex(indexOfSelectedLine);
		int numberOfLinesBeforDuplicate = tableEdit.getNumberOfSpecificLine(selectedLine);

		// tableEdit.duplicateLineBylineName(selectedLine);
		tableEdit.duplicateLine(selectedLine, indexOfSelectedLine);

		int numberOfLinesAfterDuplicate = tableEdit.getNumberOfSpecificLine(selectedLine);

		Assert.assertEquals(numberOfLinesAfterDuplicate, numberOfLinesBeforDuplicate + 1, "the lines before duplicated are : " + numberOfLinesBeforDuplicate
				+ " Lines after Duplicated are: " + numberOfLinesAfterDuplicate);
		// get number of lines contains line name

		// assert true number lines

		// int DuplicatedEmployeesLines = tableEdit.getNumberOflines();
		// Assert.assertEquals(EmployeesLines + 1, DuplicatedEmployeesLines,
		// "the lines befor duplicated are : " + EmployeesLines +
		// " Lines after Duplicated are: "
		// + DuplicatedEmployeesLines);

		tableEdit.unSelectLineByIndex(indexOfSelectedLine + 1);

	}

	@Test(enabled = true, priority = 2)
	public void addNoteEmployeeLine() {
		note = WebdriverUtils.getTimeStamp(note);
		tableEdit = new EmployeeTableEdit();
		tableEdit.clickOnEmployeeButton();
		tableEdit.selectRandomLine();
		int indexOfSelectedLine = tableEdit.getIndexOfSlectedLine();
		String selectedLine = tableEdit.getLineNameByIndex(indexOfSelectedLine);
		tableEdit.addNotestoLineBylineName(selectedLine, indexOfSelectedLine);

		AddNotePopup addNote = new AddNotePopup();
		addNote.setText(note);
		String addedNote = addNote.getNoteText();
		addNote.clickConfirm();

		// validation
		TopHeaderBar topHeaderBar = new TopHeaderBar();
		topHeaderBar.openBaseTab();
		board = new BudgetaBoard();
		secondaryBoard = board.getSecondaryBoard();
		int lineLevel = secondaryBoard.getLineLevel(selectedLine);
		secondaryBoard.openLine(selectedLine, lineLevel);

		GeneralSection note = new GeneralSection();
		String noteFormText = note.getNoteText();

		Assert.assertEquals(addedNote, noteFormText, "the note not added to the line, the new note is: " + addedNote + " the current note is: " + noteFormText);

		topHeaderBar.openTableEditTab();
		tableEdit.unSelectLineByIndex(indexOfSelectedLine + 1);
	}

	@Test(enabled = true, priority = 3)
	public void flagEmployeeLine() {
		TopHeaderBar topHeaderBar = new TopHeaderBar();
		topHeaderBar.openTableEditTab();
		tableEdit = new EmployeeTableEdit();
		tableEdit.clickOnEmployeeButton();
		tableEdit.selectRandomLine();
		int indexOfSelectedLine = tableEdit.getIndexOfSlectedLine();
		String selectedLine = tableEdit.getLineNameByIndex(indexOfSelectedLine);
		tableEdit.flagLineBylineName(selectedLine, indexOfSelectedLine);

		Assert.assertTrue(tableEdit.isLineFlag(selectedLine, indexOfSelectedLine), "Selected line is not flagged :" + selectedLine);
		tableEdit.unSelectLineByIndex(indexOfSelectedLine + 1);
	}

	@Test(enabled = true, priority = 4)
	public void deleteEmployeeLine() {
		TopHeaderBar topHeaderBar = new TopHeaderBar();
		topHeaderBar.openTableEditTab();
		tableEdit = new EmployeeTableEdit();
		tableEdit.clickOnEmployeeButton();
		int EmployeesLinesBeforeDeleteLine = tableEdit.getNumberOflines();
		tableEdit.selectRandomLine();
		int indexOfSelectedLine = tableEdit.getIndexOfSlectedLine();
		String selectedLine = tableEdit.getLineNameByIndex(indexOfSelectedLine);

		newLineName = WebdriverUtils.getTimeStamp(newLineName);
		tableEdit.renameLine(selectedLine, newLineName);

		selectedLine = tableEdit.getLineNameByIndex(indexOfSelectedLine);
		tableEdit.deleteLineBylineName(selectedLine, indexOfSelectedLine);
		DeletePopup ConfirmDelete = new DeletePopup();
		ConfirmDelete.clickConfirm();
		int EmployeesLinesAfterDeleteLine = tableEdit.getNumberOflines();
		// is line exist

		Assert.assertFalse(tableEdit.isLineExistByIndex(selectedLine, indexOfSelectedLine), "Line is not deleted: " + selectedLine);

		// Assert.assertEquals(EmployeesLinesAfterDeleteLine,
		// EmployeesLinesBeforeDeleteLine - 1,
		// "Line is not deleted, Lines after deleted line: " +
		// EmployeesLinesAfterDeleteLine + " Lines before deleted line: " +
		// EmployeesLinesBeforeDeleteLine);

	}

	@Test(enabled = true, priority = 5)
	public void filterAccordingToHeadcount() {
		tableEdit = new EmployeeTableEdit();
		tableEdit.clickOnEmployeeButton();

		List<String> allTypes = tableEdit.getAllTypeForAllLines();
		List<String> allStatus = tableEdit.getAllStatusForAllLines();

		tableEdit.selectRandomType();
		String selectedOption = tableEdit.getSelectedTypeFilterOption();

		if (selectedOption.equals("New Hires")) {
			allStatus = tableEdit.getAllStatusForAllLines();
			for (int i = 0; i < allStatus.size(); i++) {
				// Assert.assertEquals(allStatus.get(i), selectedOption,
				// "Status not match");
				Assert.assertTrue(selectedOption.toLowerCase().contains(allStatus.get(i).toLowerCase()), "Status not match");
			}
		} else {
			allTypes = tableEdit.getAllTypeForAllLines();

			for (int i = 0; i < allTypes.size(); i++) {
				// Assert.assertEquals(allTypes.get(i), selectedOption,
				// "Type not match");
				Assert.assertTrue(selectedOption.contains(allTypes.get(i)), "Type not match");
			}
		}

		tableEdit.selectHeadcount("All Headcount");

	}

	@Test(enabled = true, priority = 6)
	public void filterAccordingToDepartment() {
		tableEdit = new EmployeeTableEdit();
		tableEdit.clickOnEmployeeButton();

		List<String> allDepartments = tableEdit.getAllDepartmentForAllLines();

		tableEdit.selectRandomDepartment();
		String selectedOption = tableEdit.getSelectedDepartmentFilterOption();

		while (selectedOption.equals("Professional Services")) {
			tableEdit.selectRandomGeography();
			selectedOption = tableEdit.getSelectedDepartmentFilterOption();
		}
		allDepartments = tableEdit.getAllDepartmentForAllLines();

		for (int i = 0; i < allDepartments.size(); i++) {
			Assert.assertEquals(allDepartments.get(i), selectedOption, "Department not match");
		}
		tableEdit.selectDepartment("All Departments");
	}

	@Test(enabled = true, priority = 7)
	public void filterAccordingToGeography() {
		tableEdit = new EmployeeTableEdit();
		tableEdit.clickOnEmployeeButton();

		List<String> allGeographies = tableEdit.getAllGeographyForAllLines();

		tableEdit.selectRandomGeography();

		String selectedOption = tableEdit.getSelectedGeographyFilterOption();
		allGeographies = tableEdit.getAllGeographyForAllLines();

		for (int i = 0; i < allGeographies.size(); i++) {
			Assert.assertEquals(allGeographies.get(i), selectedOption, "Geography not match");
		}

		tableEdit.selectGeopgraphy("All Geographies");
	}
	

	@Test(enabled = true, priority = 8)
	public void AddEmployee() {
		tableEdit = new EmployeeTableEdit();
		tableEdit.clickOnEmployeeButton();
		
		tableEdit.clickOnAddEmployeeButton();
		
		addEmployee = new CreateNewEmployeePopup();
		
		Assert.assertTrue(addEmployee.isDisplayed(), "Create New Employee pop up not opened");

		employeeName = WebdriverUtils.getTimeStamp(employeeName);
		
		addEmployee.setEmployeeName(employeeName);
		addEmployee.selectRandomBudgetLine();
		addEmployee.setEmployeeBaseSalary("2000");
		addEmployee.clickOnMoreOptions();
		addEmployee.setEmployeeRole("QA");
		addEmployee.setEmployeeId("122");
		addEmployee.setEmployeeBonus("10");
		//addEmployee.setEmployeeBonusPaymentAfter("0");
		addEmployee.setEmployeeYearlyIncrease("2");
		addEmployee.setEmployeeGeography("UK");

		addEmployee.clickOnSave();
		
		Assert.assertFalse(addEmployee.isDisplayed(), "Create New Emplyee Window is not closed");
		
		Assert.assertTrue(tableEdit.isLineExist(employeeName), "Employee was not added");
	
		
		
	}
	
	@Test(enabled = true, priority = 9)
	public void AddMoreThanEmployee() {
		tableEdit = new EmployeeTableEdit();
		tableEdit.clickOnEmployeeButton();
		
		tableEdit.clickOnAddEmployeeButton();
		
		addEmployee = new CreateNewEmployeePopup();
		
		Assert.assertTrue(addEmployee.isDisplayed(), "Create New Employee pop up not opened");

		employeeName = WebdriverUtils.getTimeStamp(employeeName);
		
		addEmployee.setEmployeeName(employeeName);
		addEmployee.selectRandomBudgetLine();
		addEmployee.setEmployeeBaseSalary("2000");
		addEmployee.clickOnMoreOptions();
		addEmployee.setEmployeeRole("QA");
		addEmployee.setEmployeeId("122");
		addEmployee.setEmployeeBonus("10");
		//addEmployee.setEmployeeBonusPaymentAfter("0");
		addEmployee.setEmployeeYearlyIncrease("2");
		addEmployee.setEmployeeGeography("UK");
		
		addEmployee.checkAddAnotherEmployee();

		addEmployee.clickOnSave();
		
		Assert.assertTrue(addEmployee.isDisplayed(), "Create New Emplyee Window is closed");

	}
	

}
