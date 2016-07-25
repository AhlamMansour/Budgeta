package com.budgeta.test.TableEditTest;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.AddNotePopup;
import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.BudgetaBoard;
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
	String note = "Add new note_";

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

	@Test(enabled = true)
	public void duplicateEmployeeLine() {
		TopHeaderBar topHeaderBar = new TopHeaderBar();
		topHeaderBar.openTableEditTab();
		tableEdit = new EmployeeTableEdit();
		tableEdit.clickOnEmployeeButton();
		int EmployeesLines = tableEdit.getNumberOflines();
		tableEdit.selectRandomLine();
		int indexOfSelectedLine = tableEdit.getIndexOfSlectedLine();
		String selectedLine = tableEdit.getLineNameByIndex(indexOfSelectedLine);

		// tableEdit.duplicateLineBylineName(selectedLine);
		tableEdit.duplicateLine(selectedLine, indexOfSelectedLine);

		int DuplicatedEmployeesLines = tableEdit.getNumberOflines();
		Assert.assertEquals(EmployeesLines + 1, DuplicatedEmployeesLines, "the lines befor duplicated are : " + EmployeesLines + " Lines after Duplicated are: "
				+ DuplicatedEmployeesLines);
		
		tableEdit.unSelectLineByIndex(indexOfSelectedLine +1 );

	}

	@Test(enabled = true)
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

	@Test(enabled = true)
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

	@Test(enabled = true)
	public void deleteEmployeeLine() {
		TopHeaderBar topHeaderBar = new TopHeaderBar();
		topHeaderBar.openTableEditTab();
		tableEdit = new EmployeeTableEdit();
		tableEdit.clickOnEmployeeButton();
		int EmployeesLinesBeforeDeleteLine = tableEdit.getNumberOflines();
		tableEdit.selectRandomLine();
		int indexOfSelectedLine = tableEdit.getIndexOfSlectedLine();
		String selectedLine = tableEdit.getLineNameByIndex(indexOfSelectedLine);
		tableEdit.deleteLineBylineName(selectedLine, indexOfSelectedLine);
		DeletePopup ConfirmDelete = new DeletePopup();
		ConfirmDelete.clickConfirm();
		int EmployeesLinesAfterDeleteLine = tableEdit.getNumberOflines();

		Assert.assertEquals(EmployeesLinesAfterDeleteLine, EmployeesLinesBeforeDeleteLine - 1, "Line is not deleted, Lines after deleted line: "
				+ EmployeesLinesAfterDeleteLine + " Lines before deleted line: " + EmployeesLinesBeforeDeleteLine);
		
	

	}

}
