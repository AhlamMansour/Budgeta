package com.budgeta.pom;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebElementUtils;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

/**
 * 
 * @author Rabia Manna
 *
 */
public class SecondaryBoard extends AbstractPOM{
	
	
	@FindBy(className = "secondary")
	private WebElement wrapper;
	
	@FindBy(css = "ol.has-search li.budget-list-item")
	private List<WebElement> budgetsList;

	@FindBy(className = "add-root-budget")
	private WebElement addBudgetaBtn;
	
	@FindBy(className = "select-root")
	private WebElement showBudgetsBtn;
	
	@FindBy(id = "selected-root-menu")
	private WebElement budgetOptionsMenuBtn;
 
	@FindBy(css = "div.popup-menu div.drop-down ul.narrow li")
	private List<WebElement> budgetDropDownOptions;
	
	@FindBy(className = "budget-list")
	private WebElement budgetsListWrapper;
	
	@FindBy(className = "search")
	private WebElement searchBudget;
	
	@FindBy(className = "scenarios")
	private WebElement scenarios;
	
	@FindBy(className = "versions")
	private WebElement versions;
	
	@FindBy(css = "ol.tree.nav")
	private List<WebElement> selectedBudget;
	
	private By newLine = By.className("new-line");
	
	private By line = By.cssSelector("li.budget-list-item");
	
	private By budgetName = By.className("budget-name");
	
	private By addLinesBtn = By.className("add-child-budget");
	
	private By addLineBtn = By.cssSelector("a.add.add-line");
	
	private By lineName = By.className("inline-edit");
	
	private By lineSetting = By.className("budget-menu");
	
	public Scenarios openScenarios(){
		if(driver.findElement(By.className("scenario-subnav")).getAttribute("class").contains("collapsed")){
			scenarios.click();
			WebdriverUtils.elementToHaveClass(scenarios, "expanded");
		}
		return new Scenarios();
	}
	
	public Versions openVersions(){
		if(driver.findElement(By.className("version-subnav")).getAttribute("class").contains("collapsed")){
			versions.click();
			WebdriverUtils.elementToHaveClass(versions, "expanded");
		}
		return new Versions();
	}
	
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
	
	public void selectRandomBudgeta(){
		openBudgetsList();
		int random = WebElementUtils.getRandomNumberByRange(0, getNumbreOfExistBudgets()-1);
		WebElementUtils.hoverOverField(budgetsList.get(random), driver, null);
		budgetsList.get(random).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("budget-list")));
		WebdriverUtils.waitForElementToDisappear(driver, By.className("level-0"));
		WebdriverUtils.waitForElementToBeFound(driver, By.className("level-1"));
	}
	
	public int getNumbreOfExistBudgets(){
		return budgetsList.size();
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
		String name="";
		for(WebElement el : lines){
			if(el.getAttribute("class").contains("new-line"))
				name = el.findElement(lineName).getText();
			else
				name = el.findElement(budgetName).getText();
			if(name.equals(lineTitle) && el.getAttribute("class").contains("new-line")){
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
	
	public void openBudgetDropDownOptionsMenu(){
		if(isBudgetDropDownOptionsOpen()){
			budgetOptionsMenuBtn.click();
			WebdriverUtils.waitForElementToBeFound(driver, By.className("qtip-focus"));
			wait.until(ExpectedConditions.visibilityOfAllElements(budgetDropDownOptions));
		}
	}
	
	public synchronized void selectBudgetOption(String option){
		openBudgetDropDownOptionsMenu();
		for(WebElement el : budgetDropDownOptions){
			if(el.getText().equalsIgnoreCase(option)){
				WebElementUtils.hoverOverField(el, driver, null);
				el.click();
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				WebdriverUtils.waitForBudgetaBusyBar(driver);
			}
		}
	}
	
	
	public MenuTrigger getLineSettings(String name){
		return new MenuTrigger(getLineByName(name).findElement(lineSetting));
	}
	
	
	private WebElement getLineByName(String name){
		List<WebElement> lines = getLines();
		for(WebElement el : lines){
			if(getLineName(el).contains(name))
				return el;
		}
		return null;
	}
	
	private String getLineName(WebElement el){
		if(el.getAttribute("class").contains("new-line"))
			return el.findElement(lineName).getText();
		return el.findElement(budgetName).getText(); 
	}
	
	private boolean isBudgetDropDownOptionsOpen(){
		try{
			return driver.findElement(By.className("qtip-focus")).isDisplayed();
		}
		catch(Exception e){
			return false;
		}
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
		List<WebElement> list = new ArrayList<WebElement>();
		for(WebElement el : driver.findElements(By.cssSelector("ol.tree.nav")).get(1).findElement(By.className("selected-root")).findElement(By.tagName("ol")).findElements(line)){
			if(el.getAttribute("data-level").equals("1"))
				list.add(el);
		}
		return list;
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
;
}
