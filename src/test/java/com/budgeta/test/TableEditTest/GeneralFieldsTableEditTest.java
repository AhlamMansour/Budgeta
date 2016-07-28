package com.budgeta.test.TableEditTest;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.GeneralTableEdit;
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
public class GeneralFieldsTableEditTest extends WrapperTest {

	protected BudgetaBoard board;
	SecondaryBoard secondaryBoard;
	GeneralTableEdit generalTableEdit;
	String note = "Add new note for General fields_";
	String newLineName = "Line to delete_";
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

	}

	@Test(enabled = true)
	public void addNoteEmployeeLine() {
		note = WebdriverUtils.getTimeStamp(note);
		generalTableEdit = new GeneralTableEdit();
		generalTableEdit.clickOnGeneralFields();
		generalTableEdit.selectRandomLine();
		int indexOfSelectedLine = generalTableEdit.getIndexOfSlectedLine();
		String selectedLine = generalTableEdit.getLineNameByIndex(indexOfSelectedLine);
		generalTableEdit.addNotestoLineBylineName(selectedLine, indexOfSelectedLine);

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
		generalTableEdit.unSelectLineByIndex(indexOfSelectedLine + 1);
	}

	@Test(enabled = true)
	public void flagEmployeeLine() {
		TopHeaderBar topHeaderBar = new TopHeaderBar();
		topHeaderBar.openTableEditTab();
		generalTableEdit = new GeneralTableEdit();
		generalTableEdit.clickOnGeneralFields();
		generalTableEdit.selectRandomLine();
		int indexOfSelectedLine = generalTableEdit.getIndexOfSlectedLine();
		String selectedLine = generalTableEdit.getLineNameByIndex(indexOfSelectedLine);
		generalTableEdit.flagLineBylineName(selectedLine, indexOfSelectedLine);

		Assert.assertTrue(generalTableEdit.isLineFlag(selectedLine, indexOfSelectedLine), "Selected line is not flagged :" + selectedLine);
		generalTableEdit.unSelectLineByIndex(indexOfSelectedLine + 1);
	}

	@Test(enabled = true)
	public void deleteEmployeeLine() {
		TopHeaderBar topHeaderBar = new TopHeaderBar();
		topHeaderBar.openTableEditTab();
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

			generalTableEdit.deleteLineBylineName(selectedLine, indexOfSelectedLine);
			DeletePopup ConfirmDelete = new DeletePopup();
			ConfirmDelete.clickConfirm();

			Assert.assertFalse(generalTableEdit.isLineExistByIndex(selectedLine, indexOfSelectedLine), "Line is not deleted: " + selectedLine);

		}
		
		if (selectedLineLevel < nextOfSelectedLineLevel) {
			int subLinenumbers = generalTableEdit.getAllSublinesForLine(selectedLine);
			newLineName = WebdriverUtils.getTimeStamp(newLineName);
			generalTableEdit.renameSubLines(selectedLine, newSubLineName);
			List<String> subLinesName = generalTableEdit.getSubLinesName(selectedLine);
			List<String> getAllLinesName = generalTableEdit.getAllLinesName();
			generalTableEdit.renameLine(selectedLine, newLineName);
			selectedLine = generalTableEdit.getLineNameByIndex(indexOfSelectedLine);
			generalTableEdit.deleteLineBylineName(selectedLine, indexOfSelectedLine);
			DeletePopup ConfirmDelete = new DeletePopup();
			ConfirmDelete.clickConfirm();
			
			Assert.assertFalse(generalTableEdit.isLineExistByIndex(selectedLine, indexOfSelectedLine), "Line is not deleted: " + selectedLine);
			
			for (int i = 0; i<subLinesName.size(); i++){
				Assert.assertFalse(generalTableEdit.isLineExist(subLinesName.get(i)), "Line is not deleted: " + selectedLine);
			}
			

	}
	
	}
}
