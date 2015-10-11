package com.budgeta.test.view;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.BudgetSettings;
import com.budgeta.pom.InnerBar;
import com.budgeta.pom.PreviewBoard;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.pom.View;
import com.budgeta.test.BudgetaUtils;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestFirst;
import com.galilsoftware.AF.core.listeners.TestNGListener;


@Listeners({ MethodListener.class, TestNGListener.class })
public class ViewTest extends WrapperTest{
	
	SecondaryBoard secondaryBoard;
	InnerBar innerBar;
	View view;
	String fromMonth;
	String fromYear;
	String toMonth;
	String toYear;
	List<String> dates;
	
	
	@TestFirst
	@Test(enabled = true)
	public void setBudgetTest(){
		
		secondaryBoard = board.getSecondaryBoard();
		secondaryBoard.selectRandomBudgetWithPrefix("budget7_");
		
		innerBar = board.getInnerBar();
		Assert.assertTrue(innerBar.isDisplayed(), "expected to inner bar to be dislayed");
		
		secondaryBoard.openBudgetSettings();
		BudgetSettings settings = new BudgetSettings();
		String dateFrom = settings.getDateRangeFrom();
		String dateTo = settings.getDateRangeTo();
		settings.clickCancel();
		
		fromMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(dateFrom.split("/")[0]));
		fromYear = dateFrom.split("/")[1];	
		toMonth = BudgetaUtils.getMonthWithIndex(Integer.parseInt(dateTo.split("/")[0]));
		toYear = dateTo.split("/")[1];
		List<String> expectedDates = BudgetaUtils.getAllMonthsBetweenTwoMonths(fromMonth, fromYear, toMonth, toYear);
		
		innerBar.openViewTab();
		Assert.assertEquals(innerBar.getOpenTab(), "View");
		view = new View();
		Assert.assertTrue(view.isDisplayed(), "expected to View to be displayed");
		dates = view.getAllDates();
		Assert.assertEquals(dates.size(), expectedDates.size());
		for(int i = 0 ; i < expectedDates.size(); i++){
			Assert.assertEquals(dates.get(i), expectedDates.get(i));
		}
	}
	
	@Test(enabled = true)
	public void validateTableDataTest(){
		
		innerBar = board.getInnerBar();
		secondaryBoard = board.getSecondaryBoard();
		innerBar.openViewTab();
		view = new View();
		
		int numberOfRows = view.getNumbreOfRows();
		for(int row = 0 ; row < numberOfRows ; row++){
			String rowTitle = view.getRowTitleByIndex(row);
			view.clickOnLineByIndex(row);
			Assert.assertEquals(secondaryBoard.getSelectedLine(), rowTitle);
			PreviewBoard previewBoard = new PreviewBoard();
			List<String> lineValues = new ArrayList<>();
			int startIndex = previewBoard.getIndexOfHeaderDate(fromMonth+" "+fromYear);
			int endIndex = previewBoard.getIndexOfHeaderDate(toMonth+" "+toYear);
			if(startIndex < 0 && endIndex >= 0)
				startIndex = 0;
			else if(startIndex >=0 && endIndex < 0)
				endIndex = previewBoard.getValuesSize()-1;
			
			for(int i = startIndex ; i <= endIndex ; i++){
				lineValues.add(previewBoard.getValueByIndex(i));
			}
			
			innerBar.openViewTab();
			view = new View();
			List<String> values = view.getAllValuesOfRow(row);
			
			for(int i = 0 ; i < lineValues.size() ; i++){
				Assert.assertEquals(lineValues.get(i), values.get(i),"... Row title is: "+rowTitle+", in header: "+dates.get(i));
			}			
		}
	}
	
	@Test(enabled = true)
	public void validateTotalTest(){
		
		innerBar = board.getInnerBar();
		innerBar.openViewTab();
		view = new View();
		
		int numberOfRows = view.getNumbreOfRows();
		for(int row = 0 ; row < numberOfRows ; row++){
			int total = 0;
			List<String> values = view.getAllValuesOfRow(row);
			for(String str : values){
				if(!str.equals("-"))
					total += Integer.parseInt(str);
			}
			if(total == 0)
				Assert.assertEquals(view.getTotalOfRow(row), "-","... Row title is: "+view.getRowTitleByIndex(row));
			else
				Assert.assertEquals(view.getTotalOfRow(row), total+"","... Row title is: "+view.getRowTitleByIndex(row));
		
		}
	}

}
