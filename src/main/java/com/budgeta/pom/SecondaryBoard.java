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
public class SecondaryBoard extends AbstractPOM{
	
	
	@FindBy(className = "secondary")
	private WebElement wrapper;
	
	@FindBy(className = "budget-list-item")
	private List<WebElement> budgetsList;

	@FindBy(className = "add-root-budget")
	private WebElement addBudgetaBtn;
	
	@FindBy(className = "select-root")
	private WebElement showBudgetsBtn;

	@FindBy(className = "budget-list")
	private WebElement budgetsListWrapper;
	
	@FindBy(className = "search")
	private WebElement searchBudget;
	
	@FindBy(css = "ol.tree.nav")
	private List<WebElement> selectedBudget;
	
	private By line = By.className("new-line");
	
	private By budgetName = By.className("budget-name");
	
	private By addLinesBtn = By.className("add-child-budget");
	
	private By addLineBtn = By.cssSelector("a.add.add-line");
	
	private By lineName = By.className("inline-edit");
	
	
	public NewBudgetPopup addBudgeta(){
		WebdriverUtils.waitUntilClickable(driver, addBudgetaBtn);
		addBudgetaBtn.click();
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		return new NewBudgetPopup();
	}
	
	public boolean isBudgetExist(String budgetaName){
		openBudgetsList();
		boolean found = false;
		for(WebElement budget : budgetsList){
			if(budget.findElement(By.className("budget-name")).getText().equals(budgetaName)){
				found =  true;
				break;
			}
		}
		showBudgetsBtn.click();
		WebdriverUtils.sleep(300);
		return found;
	}
	
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
	
	public void addLine(){
		if(!wrapper.getAttribute("class").contains("tree-edit")){	
			getSelectedBudget().findElement(addLinesBtn).click();
			WebdriverUtils.waitForElementToBeFound(driver, By.className("tree-edit"));
		}
		
	}
	
	public void addAllLines(){
		if(!wrapper.getAttribute("class").contains("tree-edit")){	
			getSelectedBudget().findElement(addLinesBtn).click();
			WebdriverUtils.waitForElementToBeFound(driver, By.className("tree-edit"));
		}
		WebElement line = null;
		while(getNextLineToAdd() != null ){
			line = getNextLineToAdd();
			WebElement add = line.findElement(addLineBtn);
			if(add.getAttribute("class").contains("enable")){
				add.click();
				WebdriverUtils.waitForBudgetaBusyBar(driver);
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				WebdriverUtils.sleep(1500);
			}
		}
	}
	
	public void addLine(String lineTitle){
		if(!wrapper.getAttribute("class").contains("tree-edit")){	
			getSelectedBudget().findElement(addLinesBtn).click();
			WebdriverUtils.waitForElementToBeFound(driver, By.className("tree-edit"));
		}
		List<WebElement> lines = getLines();
		for(WebElement el : lines){
			if(el.findElement(lineName).getText().equals(lineTitle)){
				WebElement add = el.findElement(addLineBtn);
				if(add.getAttribute("class").contains("enable")){
					add.click();
					WebdriverUtils.elementToHaveClass(add,"enable");
				}
			}
		}			
	}
	
	public String getNameOfSelectedBudgeta(){
		return getSelectedBudget().findElement(budgetName).getText().split("\n")[0];
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
	
	private List<WebElement> getLines(){
		return driver.findElements(By.cssSelector("ol.tree.nav")).get(1).findElement(By.className("selected-root")).findElement(By.tagName("ol")).findElements(line);
	}
	
	private WebElement getNextLineToAdd(){
		List<WebElement> lines = getLines();
		for(WebElement el : lines){
			if(el.getAttribute("data-level").equals("1") && el.getAttribute("class").contains("new-line")){
				if(el.findElement(lineName).getText().equals("Model 1"))
					return null;
				try{
					if(el.findElement(addLineBtn).getAttribute("class").contains("enable"))
						return el;
				}
				catch(Exception e){
					continue;
				}
			}
		}
		return null;
	}
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
