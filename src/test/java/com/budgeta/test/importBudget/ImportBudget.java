package com.budgeta.test.importBudget;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.PreviewBoard;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.test.WrapperTest;
import com.budgeta.test.common.BudgetaCommon;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestFirst;
import com.galilsoftware.AF.core.listeners.TestNGListener;

@Listeners({ MethodListener.class, TestNGListener.class })
public class ImportBudget extends WrapperTest{
	
	SecondaryBoard secondaryBoard;
	Map<String,Map<String, List<String>>> allMaps;
	String fromMonth = "Jan";
	String fromYear = "2016";
	String toMonth = "Dec";
	String toYear = "2016"; 
	
	@TestFirst
	@Test(enabled = true)
	public void importBudget(){
		BudgetaCommon create = new BudgetaCommon();
		create.createBudget();
		secondaryBoard = board.getSecondaryBoard();
		allMaps = secondaryBoard.importAllBudgetLines();
		
		// validate import budget lines

	}
	
	
	@Test(enabled = true)
	public void ValidateRevenues(){
		Map<String, List<String>> revenuesValues = allMaps.get("Revenues");
		for(String line : revenuesValues.keySet()){
			List<String> values = revenuesValues.get(line);
			
			secondaryBoard.clickOnSubLine("Revenues", line);
			
			
			PreviewBoard previewBoard = new PreviewBoard();
			List<String> lineValues = new ArrayList<>();
			int startIndex = previewBoard.getIndexOfHeaderDate(fromMonth + " " + fromYear);
			int endIndex = previewBoard.getIndexOfHeaderDate(toMonth + " " + toYear);
			if (startIndex < 0 && endIndex >= 0)
				startIndex = 0;
			else if (startIndex >= 0 && endIndex < 0)
				endIndex = previewBoard.getValuesSize() - 1;

			for (int i = startIndex; i <= endIndex; i++) {
				lineValues.add(previewBoard.getValueByIndex(i));
			}


			for (int i = 0; i < lineValues.size(); i++) {
				Assert.assertEquals(lineValues.get(i), values.get(i), "in index : " + i );
			}
			
		}
	}
	
	@Test(enabled = true)
	public void ValidateCostOfRevenues(){
		Map<String, List<String>> revenuesValues = allMaps.get("Cost of Revenues");
		for(String line : revenuesValues.keySet()){
			List<String> values = revenuesValues.get(line);
			
			secondaryBoard.clickOnSubLine("Cost of Revenues", line);
			
			
			PreviewBoard previewBoard = new PreviewBoard();
			List<String> lineValues = new ArrayList<>();
			int startIndex = previewBoard.getIndexOfHeaderDate(fromMonth + " " + fromYear);
			int endIndex = previewBoard.getIndexOfHeaderDate(toMonth + " " + toYear);
			if (startIndex < 0 && endIndex >= 0)
				startIndex = 0;
			else if (startIndex >= 0 && endIndex < 0)
				endIndex = previewBoard.getValuesSize() - 1;

			for (int i = startIndex; i <= endIndex; i++) {
				lineValues.add(previewBoard.getValueByIndex(i));
			}


			for (int i = 0; i < lineValues.size(); i++) {
				Assert.assertEquals(lineValues.get(i), values.get(i), "in index : " + i );
			}
			
		}
	}
	
	@Test(enabled = true)
	public void ValidateOperationalExpenses(){
		Map<String, List<String>> revenuesValues = allMaps.get("Operational Expenses");
		for(String line : revenuesValues.keySet()){
			List<String> values = revenuesValues.get(line);
			
			secondaryBoard.clickOnSubLine("Operational Expenses", line);
			
			
			PreviewBoard previewBoard = new PreviewBoard();
			List<String> lineValues = new ArrayList<>();
			int startIndex = previewBoard.getIndexOfHeaderDate(fromMonth + " " + fromYear);
			int endIndex = previewBoard.getIndexOfHeaderDate(toMonth + " " + toYear);
			if (startIndex < 0 && endIndex >= 0)
				startIndex = 0;
			else if (startIndex >= 0 && endIndex < 0)
				endIndex = previewBoard.getValuesSize() - 1;

			for (int i = startIndex; i <= endIndex; i++) {
				lineValues.add(previewBoard.getValueByIndex(i));
			}


			for (int i = 0; i < lineValues.size(); i++) {
				Assert.assertEquals(lineValues.get(i), values.get(i), "in index : " + i );
			}
			
		}
	}
	
	@Test(enabled = true)
	public void ValidateOtherIncomeAndExpenses(){
		Map<String, List<String>> revenuesValues = allMaps.get("Other income and expenses");
		for(String line : revenuesValues.keySet()){
			List<String> values = revenuesValues.get(line);
			
			secondaryBoard.clickOnSubLine("Other income and expenses", line);
			
			
			PreviewBoard previewBoard = new PreviewBoard();
			List<String> lineValues = new ArrayList<>();
			int startIndex = previewBoard.getIndexOfHeaderDate(fromMonth + " " + fromYear);
			int endIndex = previewBoard.getIndexOfHeaderDate(toMonth + " " + toYear);
			if (startIndex < 0 && endIndex >= 0)
				startIndex = 0;
			else if (startIndex >= 0 && endIndex < 0)
				endIndex = previewBoard.getValuesSize() - 1;

			for (int i = startIndex; i <= endIndex; i++) {
				lineValues.add(previewBoard.getValueByIndex(i));
			}


			for (int i = 0; i < lineValues.size(); i++) {
				Assert.assertEquals(lineValues.get(i), values.get(i), "in index : " + i );
			}
			
		}
	}
}
