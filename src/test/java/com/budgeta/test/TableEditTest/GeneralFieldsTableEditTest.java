package com.budgeta.test.TableEditTest;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.AddNotePopup;
import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.BudgetaBoard;
import com.budgeta.pom.GeneralSection;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.pom.TableEdit;
import com.budgeta.pom.TopHeaderBar;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestFirst;
import com.galilsoftware.AF.core.listeners.TestNGListener;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

@Listeners({ MethodListener.class, TestNGListener.class })
public class GeneralFieldsTableEditTest extends WrapperTest{
	
	protected BudgetaBoard board;
	SecondaryBoard secondaryBoard;
	TableEdit tableEdit;
	String note = "Add new note for General fields_";
	
	
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
		tableEdit = new TableEdit();
		tableEdit.clickOnGeneralFields();
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
		tableEdit = new TableEdit();
		tableEdit.clickOnGeneralFields();
		tableEdit.selectRandomLine();
		int indexOfSelectedLine = tableEdit.getIndexOfSlectedLine();
		String selectedLine = tableEdit.getLineNameByIndex(indexOfSelectedLine);
		tableEdit.flagLineBylineName(selectedLine, indexOfSelectedLine);

		Assert.assertTrue(tableEdit.isLineFlag(selectedLine, indexOfSelectedLine), "Selected line is not flagged :" + selectedLine);
		tableEdit.unSelectLineByIndex(indexOfSelectedLine + 1);
	}
	

}
