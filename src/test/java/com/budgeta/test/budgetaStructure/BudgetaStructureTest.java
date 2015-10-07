package com.budgeta.test.budgetaStructure;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.BillingsSection;
import com.budgeta.pom.BudgetSettings;
import com.budgeta.pom.BudgetaBoard;
import com.budgeta.pom.CommentsSection;
import com.budgeta.pom.DateRange;
import com.budgeta.pom.EmployeeAssumptions;
import com.budgeta.pom.GeneralSection;
import com.budgeta.pom.PreviewBoard;
import com.budgeta.pom.RevenuesAddSubLine;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.test.BudgetaTest;
import com.budgeta.test.BudgetaUtils;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.DataProviderParams;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestFirst;
import com.galilsoftware.AF.core.listeners.TestNGListener;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

@Listeners({ MethodListener.class, TestNGListener.class })
public class BudgetaStructureTest extends WrapperTest{

	SecondaryBoard secondaryBoard;
	String revenuesSubLine = WebdriverUtils.getTimeStamp("revenues_");
	String revenues = "Revenues";
	String cost_of_revenues = "Cost of Revenues";
	String cost_of_revenues_subLine = "Professional Services";
	String salary_and_wages = "Salary & wages";
	String employee = "employee_";
	
	
	@TestFirst
	@Test(enabled = true)
	public void setBudgetTest(){
		
		secondaryBoard = board.getSecondaryBoard();
		secondaryBoard.selectRandomBudgetWithPrefix("budget7_");
		secondaryBoard.addAllLines();
		secondaryBoard = new SecondaryBoard();
		secondaryBoard.addSubLine("Revenues");
		RevenuesAddSubLine subLine = new RevenuesAddSubLine();
		subLine.setName(revenuesSubLine);
		subLine.selectDropDown("Perpetual License");
		subLine.clickAdd();
		secondaryBoard = new SecondaryBoard();
		secondaryBoard.addSubLine(cost_of_revenues);
		secondaryBoard = new SecondaryBoard();
		secondaryBoard.addSubLineForLine(cost_of_revenues, cost_of_revenues_subLine);
		secondaryBoard = new SecondaryBoard();
		secondaryBoard.addSubLinrForSubLine(cost_of_revenues, cost_of_revenues_subLine, salary_and_wages);
		subLine = new RevenuesAddSubLine();
		subLine.setName(WebdriverUtils.getTimeStamp(employee));
		subLine.clickAdd();
	}
	
	
	@Test(dataProvider = "ExcelFileLoader", enabled = false,priority = 1)
	@DataProviderParams(sheet = "BudgetaForm" , area = "GeneralRevenues")
	public void GeneralRevenuesTest(Hashtable<String, String> data) {
		board = new BudgetaBoard();
		secondaryBoard = board.getSecondaryBoard();
		
		secondaryBoard.clickOnLine(revenues);
		GeneralSection general = new GeneralSection();
		
		Assert.assertTrue(general.isDisplayed(), "expected general section to be displayed");
		
		DateRange from = general.openDateRangeFrom();
		from.setYear(data.get("DateRange_from_year"));
		from.setMonth(data.get("DateRange_from_month"));
		
		DateRange to = general.openDateRangeTo();
		to.setYear(data.get("DateRange_to_year"));
		to.setMonth(data.get("DateRange_to_month"));
		
		general.selectCurrency(data.get("Currency"));
		
		general.setAccountNumberInRowByIndex(1, data.get("AccountNumber"));
		general.setProduct(data.get("Product"));
		general.setNotes(data.get("Notes"));
		
		if(data.get("ShouldPass").equals("FALSE"))
			Assert.assertTrue(general.isGeneralHasError(), "expected to error in general section");
		else{
			board.clickSaveChanges();
			CommentsSection comments = new CommentsSection();
			Assert.assertTrue(comments.isDisplayed(), "expected comments section to be displayed");
			comments.setComments(data.get("Comments"));
		
			board = new BudgetaBoard();
			board.clickSaveChanges();
			
			general = new GeneralSection();
			Assert.assertEquals(general.getDateRangeFrom(), BudgetaTest.getDateByNumbersFormat(data.get("DateRange_from_month"), data.get("DateRange_from_year")));
			Assert.assertEquals(general.getDateRangeTo(), BudgetaTest.getDateByNumbersFormat(data.get("DateRange_to_month"), data.get("DateRange_to_year")));
			Assert.assertEquals(general.getSelectedCurrency(), data.get("Currency"));			
		}
	}
	
	
	
	@Test(dataProvider = "ExcelFileLoader", enabled = false,priority = 2)
	@DataProviderParams(sheet = "BudgetaForm" , area = "Revenues Form_Details")
	public void revenuesFormDetailsTest(Hashtable<String, String> data) {
		board = new BudgetaBoard();
		secondaryBoard = board.getSecondaryBoard();
		secondaryBoard.clickOnSubLine(revenues, revenuesSubLine);
		
		BillingsSection billings = new BillingsSection();
		Assert.assertTrue(billings.isDisplayed(), "expected billings section to be displayed");
		
		billings.selectRepeat(data.get("Repeat"));
		if(data.get("Repeat").equals("Once"))
			billings.selectOnDate(data.get("OnDate_Month"), data.get("OnDate_Year"));
		else
			billings.selectSpred(data.get("Spread"));
		if(data.get("Repeat").equals("Quarterly") || data.get("Repeat").equals("Yearly"))
			billings.selectAtDate(data.get("AtDate"));
		if(data.get("Spread").equals("Growth"))
			billings.setGrowth(data.get("GrowthPercent"));
		billings.setAmount(data.get("Amount"));
		billings.setPaymentAfter(data.get("PaymentAfter"));	
		
		if(!data.get("SupportPercentage").isEmpty()){
			billings.setSupportPercentage(data.get("SupportPercentage"));
			billings.setSupportPeriod(data.get("SupportPeriod"));
		}
		else{
			billings.setSupportPercentage("");
		}
		if(data.get("ShouldPass").equals("FALSE")){
			Assert.assertTrue(billings.isBillingsHasError(), "expected to error in billings section");
		}
		else{
		
			GeneralSection general = new GeneralSection();
			Assert.assertTrue(general.isDisplayed(), "expected general section to be displayed");
		
			DateRange from = general.openDateRangeFrom();
			if(!data.get("DateRange_from_year").isEmpty()){
				from.setYear(data.get("DateRange_from_year"));
				from.setMonth(data.get("DateRange_from_month"));
			}
			DateRange to = general.openDateRangeTo();
			if(!data.get("DateRange_to_year").isEmpty()){
				to.setYear(data.get("DateRange_to_year"));
				to.setMonth(data.get("DateRange_to_month"));
			}
		
			general.setAccountNumberInRowByIndex(1, data.get("AccountNumber"));
			general.setGeography(data.get("Geography"));
			general.setProduct(data.get("Product"));
			general.setNotes(data.get("Notes"));
			board.clickSaveChanges();
			board.clickSaveChanges();
//			CommentsSection comments = new CommentsSection();
//			Assert.assertTrue(comments.isDisplayed(), "expected comments section to be displayed");
//			comments.setComments(data.get("Comments"));
			
			//start validation
			int payAfter,supportPercent,supportPeriod,growth;
			String monthX,yearX,monthY,yearY;
			if(data.get("PaymentAfter").isEmpty())
				payAfter = 0;
			else
				payAfter = Integer.parseInt(data.get("PaymentAfter"));
			
			if(data.get("SupportPercentage").isEmpty())
				supportPercent = 0;
			else
				supportPercent = Integer.parseInt(data.get("SupportPercentage"));
			
			if(data.get("SupportPeriod").isEmpty())
				supportPeriod = 0;
			else
				supportPeriod = Integer.parseInt(data.get("SupportPeriod"));
			if(data.get("GrowthPercent").isEmpty())
				growth = 0;
			else
				growth = Integer.parseInt(data.get("GrowthPercent"));
			
			monthX = BudgetaUtils.getMonthWithIndex(Integer.parseInt(general.getDateRangeFrom().split("/")[0]));
			yearX = general.getDateRangeFrom().split("/")[1];
			monthY = BudgetaUtils.getMonthWithIndex(Integer.parseInt(general.getDateRangeTo().split("/")[0]));
			yearY = general.getDateRangeTo().split("/")[1];
				
			board.clickSaveChanges();
			
			if(data.get("Repeat").equals("Once")){
				String[] expectedValues = BudgetaUtils.calculateValues_Once(monthX, yearX, monthY, yearY, data.get("OnDate_Month"), data.get("OnDate_Year"), Integer.parseInt(data.get("Amount")), payAfter, supportPercent, supportPeriod);
				compareExpectedResults(expectedValues);
			}
			
			if(data.get("Repeat").equals("Monthly")){
				String[] expectedValues = BudgetaUtils.calculateValues_Monthly(monthX, yearX,  monthY, yearY, Integer.parseInt(data.get("Amount")), payAfter, supportPercent, supportPeriod,growth);
				compareExpectedResults(expectedValues);
			}
			
			if(data.get("Repeat").equals("Quarterly")){
				String[] expectedValues = BudgetaUtils.calculateValues_Quaterly(monthX, yearX, monthY, yearY, Integer.parseInt(data.get("Amount")), payAfter, growth, data.get("AtDate"));
				compareExpectedResults(expectedValues);
			}
			
			if(data.get("Repeat").equals("Yearly")){
				BudgetSettings settings = secondaryBoard.openBudgetSettings();
				String fiscal = settings.getSelectedFiscal().substring(0, 3);
				settings.clickCancel();
				String[] expectedValues = BudgetaUtils.calculateValues_Yearly(monthX, yearX, monthY, yearY, Integer.parseInt(data.get("Amount")), payAfter, growth, data.get("AtDate"),fiscal);
				compareExpectedResults(expectedValues);
			}
			
		}
			
	}
	
	
	@Test(dataProvider = "ExcelFileLoader", enabled = false,priority = 3)
	@DataProviderParams(sheet = "BudgetaForm" , area = "CostOfSale")
	public void CostOfSaleTest(Hashtable<String, String> data) {
		board = new BudgetaBoard();
		secondaryBoard = board.getSecondaryBoard();
		
		secondaryBoard.clickOnLine(cost_of_revenues);
		GeneralSection general = new GeneralSection();
		
		Assert.assertTrue(general.isDisplayed(), "expected general section to be displayed");
		
		DateRange from = general.openDateRangeFrom();
		from.setYear(data.get("DateRange_from_year"));
		from.setMonth(data.get("DateRange_from_month"));
		
		DateRange to = general.openDateRangeTo();
		to.setYear(data.get("DateRange_to_year"));
		to.setMonth(data.get("DateRange_to_month"));
		
		general.selectCurrency(data.get("Currency"));
		
		general.setAccountNumberInRowByIndex(1, data.get("AccountNumber"));
		general.setDepartment(data.get("Departments"));
		general.setGeography(data.get("Geography"));
		general.setProduct(data.get("Product"));
		general.setNotes(data.get("Notes"));
		
		if(data.get("ShouldPass").equals("FALSE"))
			Assert.assertTrue(general.isGeneralHasError(), "expected to error in general section");
		else{
			board.clickSaveChanges();
			CommentsSection comments = new CommentsSection();
			Assert.assertTrue(comments.isDisplayed(), "expected comments section to be displayed");
			comments.setComments(data.get("Comments"));
		
			board = new BudgetaBoard();
			board.clickSaveChanges();
			
			general = new GeneralSection();
			Assert.assertEquals(general.getDateRangeFrom(), BudgetaTest.getDateByNumbersFormat(data.get("DateRange_from_month"), data.get("DateRange_from_year")));
			Assert.assertEquals(general.getDateRangeTo(), BudgetaTest.getDateByNumbersFormat(data.get("DateRange_to_month"), data.get("DateRange_to_year")));
			Assert.assertEquals(general.getSelectedCurrency(), data.get("Currency"));
			Assert.assertEquals(general.getDepartment(), data.get("Departments"));
			Assert.assertEquals(general.getGeography(), data.get("Geography"));
			Assert.assertEquals(general.getProduct(), data.get("Product"));
			
		}
	}
	
	
	@Test(dataProvider = "ExcelFileLoader", enabled = true,priority = 4)
	@DataProviderParams(sheet = "BudgetaForm" , area = "CostOfSale_Salary&wages")
	public void CostOfSale_SalaryAndwages(Hashtable<String, String> data) {
		board = new BudgetaBoard();
		secondaryBoard = board.getSecondaryBoard();
		secondaryBoard.clickOnSubLine(cost_of_revenues, cost_of_revenues_subLine,salary_and_wages);
		secondaryBoard = new SecondaryBoard();
		
		EmployeeAssumptions emplyeeAssumption = new EmployeeAssumptions();
		Assert.assertTrue(emplyeeAssumption.isDisplayed(), "expected employee assumption to be displayed");
		
		emplyeeAssumption.setGeography(data.get("Geography"));
		emplyeeAssumption.setBenefits(data.get("BenefitsPercentage"));
		emplyeeAssumption.selectPayment(data.get("Payment"));
		emplyeeAssumption.setYearlyVacatoinDays(data.get("YearlyVactaionDayes"));
		emplyeeAssumption.setAvgAccruedVacation(data.get("AvgAccruedVacation_Percentage"));
		emplyeeAssumption.setMaxAccruedVacation(data.get("MaxAccruedVacation_Percentage"));
		emplyeeAssumption.setYearlyIncrease(data.get("YearlyIncrease_Percentage"));


		GeneralSection general = new GeneralSection();
		Assert.assertTrue(general.isDisplayed(), "expected general section to be displayed");
		
		DateRange from = general.openDateRangeFrom();
		from.setYear(data.get("DateRange_from_year"));
		from.setMonth(data.get("DateRange_from_month"));
		
		DateRange to = general.openDateRangeTo();
		to.setYear(data.get("DateRange_to_year"));
		to.setMonth(data.get("DateRange_to_month"));
		
		general.setAccountNumberInRowByIndex(1, data.get("AccountNumber"));
		general.setDepartment(data.get("Department"));
		general.setGeography(data.get("Geography"));
		general.setProduct(data.get("Product"));
		general.setNotes(data.get("Notes"));
		
		board = new BudgetaBoard();
		board.clickSaveChanges();
		
		if(data.get("ShouldPass").equals("FALSE"))
			Assert.assertTrue(emplyeeAssumption.employeeAssumptionHasError() || general.isGeneralHasError(), "expected to error in employee assumption sectcion or in general section");
		else{
		
			general = new GeneralSection();
			Assert.assertEquals(general.getDateRangeFrom(), BudgetaTest.getDateByNumbersFormat(data.get("DateRange_from_month"), data.get("DateRange_from_year")));
			Assert.assertEquals(general.getDateRangeTo(), BudgetaTest.getDateByNumbersFormat(data.get("DateRange_to_month"), data.get("DateRange_to_year")));
			Assert.assertEquals(general.getDepartment(), data.get("Department"));
			Assert.assertEquals(general.getGeography(), data.get("Geography"));
			Assert.assertEquals(general.getProduct(), data.get("Product"));
			
			Assert.assertEquals(emplyeeAssumption.getGeography(), data.get("Geography"));
			Assert.assertEquals(emplyeeAssumption.getBenefits(), data.get("BenefitsPercentage"));
			Assert.assertEquals(emplyeeAssumption.getPayment(), data.get("Payment"));
			Assert.assertEquals(emplyeeAssumption.getYearlyVacatoinDays(), data.get("YearlyVactaionDayes"));
			Assert.assertEquals(emplyeeAssumption.getAvgAccruedVacation(), data.get("AvgAccruedVacation_Percentage"));
			Assert.assertEquals(emplyeeAssumption.getMaxAccruedVacation(), data.get("MaxAccruedVacation_Percentage"));
			Assert.assertEquals(emplyeeAssumption.getYearlyIncrease(), data.get("YearlyIncrease_Percentage"));
	
		}
	}
	
	
	
	
	
	
	
	private void compareExpectedResults(String[] expectedValues){
		int total = 0;
		PreviewBoard preview = new PreviewBoard();
		for(int i = 0; i < preview.getValuesSize(); i++){
			Assert.assertEquals(preview.getValueByIndex(i), expectedValues[i],"error in calculation budgets in index: "+i);
			if(!expectedValues[i].equals("-"))
				total += Integer.parseInt(expectedValues[i]);
		}
		if(total == 0)
			Assert.assertEquals(preview.getTotalValue(), "-");
		else
			Assert.assertEquals(preview.getTotalValue(), total+"");
	}
}
