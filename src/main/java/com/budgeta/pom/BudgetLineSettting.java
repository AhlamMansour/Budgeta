package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class BudgetLineSettting extends AbstractPOM {

	
	@FindBy(className = "budget-list-item")
	private WebElement wrapper;
	
	@FindBy(css = "ol.has-search li.budget-list-item")
	private List<WebElement> budgetsList;
	
	
	@FindBy(className = "select-root")
	private WebElement showBudgetsBtn;
	
	@FindBy(className = "budget-list")
	private WebElement budgetsListWrapper;
	
	
	@FindBy(css = "ol.tree.nav")
	private List<WebElement> selectedBudget;
	
	private By line = By.className("new-line");
	
	private By budgetName = By.className("budget-name");
	
	
	private By addLineBtn = By.cssSelector("a.add.add-line");
	
	private By lineSetting = By.className("budget-menu");
	private By lineSettingTriggerMenu = By.cssSelector("div.qtip-focus ul.narrow li");
	
	
	
	
	public void selectBudgeta(String budgetaName){
		openBudgetsList();
		for(WebElement budget : budgetsList){
			if(budget.findElement(By.className("budget-name")).getText().equals(budgetaName)){
				budget.click();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("budget-list")));
				WebdriverUtils.waitForElementToDisappear(driver, By.className("level-0"));
				WebdriverUtils.waitForElementToBeFound(driver, By.className("level-1"));
				break;
			}
		}
		getSelectedBudget().findElement(budgetName).click();
		WebdriverUtils.waitForElementToBeFound(driver,By.id("section-General"));
	}
	
	
	
	private void openBudgetsList(){
		if(!WebdriverUtils.isDisplayed(budgetsListWrapper)){
			showBudgetsBtn.click();
			wait.until(ExpectedConditions.visibilityOf(budgetsListWrapper));
			WebdriverUtils.sleep(300);
		}
	}
	
	private WebElement getSelectedBudget(){
		return selectedBudget.get(1).findElement(By.className("selected-root")).findElements(By.className("actions-toggle")).get(0);
	}
	
	
	
	private void openBudgetLineTrigger(){
		if(!wrapper.getAttribute("class").contains("tree-edit")){	
			getSelectedBudget().findElement(lineSetting).click();
			WebdriverUtils.waitForElementToBeFound(driver, lineSettingTriggerMenu);
		}
	}
	
	
	private void selectBudgetLineTrigger(String option){
		openBudgetLineTrigger();
		for(WebElement el : driver.findElements(lineSettingTriggerMenu)){
			if(el.getText().equals(option))
				el.click();
		}
	}
	
	public void clickDuplicateBudgetLine(){
		selectBudgetLineTrigger("Duplicate");
		//WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));
	
	}
	

	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}
	
}
