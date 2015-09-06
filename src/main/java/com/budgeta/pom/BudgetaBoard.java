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
	
	@FindBy(className = "bottom-bar")
	private WebElement bottomBar;
	
	@FindBy(className = "center")
	private WebElement centerBar;
	
	
	private By noty_message = By.className("noty_message");
	
	private GeneralSection generalSection;
	
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
	
	public boolean isBottomBarDisplayed(){
		return bottomBar.getAttribute("class").contains("changed");
	}
	
	public void clickSaveChanges(){
		for(WebElement el : bottomBar.findElements(By.tagName("button"))){
			if(el.getAttribute("type").equalsIgnoreCase("submit")){
				el.click();
				WebdriverUtils.waitForElementToBeFound(driver, noty_message);
			}
		}
	}
	
	public void clickUndoChanges(){
		bottomBar.findElement(By.className("cancel")).click();
		WebdriverUtils.waitForElementToDisappear(driver, By.className("changed"));
	}
	
	public GeneralSection clickGeneralSection(String section){
		centerBar.findElement(By.id("section-General")).click();
		return getGeneralSection();
	}
	
	public GeneralSection getGeneralSection(){
		if(generalSection == null)
			generalSection = new GeneralSection();
		return generalSection;
	}
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}
	
	

}
