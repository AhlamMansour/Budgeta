package com.budgeta.test.reporter;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.BudgetNavigator;
import com.budgeta.pom.ReportsPopup;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestFirst;
import com.galilsoftware.AF.core.listeners.TestNGListener;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

@Listeners({ MethodListener.class, TestNGListener.class })
public class ReporterTest extends WrapperTest{

	SecondaryBoard secondaryBoard;
	String revenuesSubLine = WebdriverUtils.getTimeStamp("revenues_");
	String cost_of_revenues = "Cost of Revenues";
	String cost_of_revenues_subLine = "Professional Services";
	String salary_and_wages = "Salary & wages";
	String employee = "employee_";
	ReportsPopup reports;

	@TestFirst
	@Test(enabled = true)
	public void setBudgetTest(){		
		secondaryBoard = board.getSecondaryBoard();
		BudgetNavigator navigator = new BudgetNavigator();
		navigator.selectRandomBudgeta();
		openReports();
	}

	private void openReports() {
		reports = secondaryBoard.clickReports();
		Assert.assertTrue(reports.isDisplayed(),"Expected the reports popup to be Displayed");
	}

	@Test(dataProvider = "reportsTypeProvider", enabled = true)
	public void fillGeneralAndValidate(String boxToCheck) {
		secondaryBoard = board.getSecondaryBoard();
		String budgetName = secondaryBoard.getSelectedBudgetName();
		if(!reports.isDisplayed())
			openReports();
		reports.checkBox(boxToCheck);
		Assert.assertTrue(reports.isBoxChecked(boxToCheck),"Expected the check box ["+boxToCheck+"] to be checked");
		reports.clickCreate();
		File f = null;
		try{
			System.out.println(new File("").getAbsolutePath()+"/browserDownloads/"+budgetName+".xlsx");
			f = new File(new File("").getAbsolutePath()+"/browserDownloads/"+budgetName+".xlsx");
			Assert.assertTrue(f.exists(),"Expected the file ["+budgetName+".xlsx] to exist");
			Assert.assertTrue(f.canExecute(),"Expected the file ["+budgetName+".xlsx] to be able to execute");
			Assert.assertTrue(f.canRead(),"Expected the file ["+budgetName+".xlsx] to be readable");
			long fileSize = f.getTotalSpace();
			Assert.assertTrue(fileSize > 10 ,"Expected the file ["+budgetName+".xlsx] size to be at least 11 bytes or more but found: "+fileSize);
			f.delete();
		}catch(Throwable e){
			if(f!= null)
				f.delete();
			throw e;
		}
	}

	@DataProvider(name = "reportsTypeProvider")
	public static Object[][] primeNumbers() {
		return new Object[][] {{"Cash Flow"}, {"Profit & Loss"}, {"Budget vs. Actual"}, {"Dashboard"}};
	}

}