package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

/**
 * 
 * @author Rabia Manna
 *
 */

public class BudgetaBoard extends AbstractPOM{

	@FindBy(className = "main-content")
	private WebElement wrapper;
	
	@FindBy(className = "add-root-budget")
	private WebElement addBudgetaBtn;
	
	@FindBy(className = "select-root")
	private WebElement showBudgetsBtn;

	@FindBy(className = "budget-list")
	private WebElement budgetsListWrapper;
	
	@FindBy(className = "budget-list-item")
	private List<WebElement> budgetsList;
	
	@FindBy(className = "search")
	private WebElement searchBudget;
	
	@FindBy(className = "select-root")
	private WebElement expandBudgetListBtn;
	
	public NewBudgetPopup addBudgeta(){
		WebdriverUtils.waitUntilClickable(driver, addBudgetaBtn);
		addBudgetaBtn.click();
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		return new NewBudgetPopup();
	}
	
	public boolean isBudgetExist(String budgetaName){
		if(!WebdriverUtils.isDisplayed(budgetsListWrapper)){
			expandBudgetListBtn.click();
			wait.until(ExpectedConditions.visibilityOf(budgetsListWrapper));
			WebdriverUtils.sleep(300);
		}
		for(WebElement budget : budgetsList){
			if(budget.findElement(By.className("budget-name")).getText().equals(budgetaName)){
				expandBudgetListBtn.click();
				WebdriverUtils.sleep(300);
				return true;
			}
		}
		expandBudgetListBtn.click();
		WebdriverUtils.sleep(300);
		return false;
	}
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}
	
	

}
