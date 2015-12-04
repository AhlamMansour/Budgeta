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

public class BudgetNavigator extends AbstractPOM{
	
	@FindBy(className = "budget-navigator-wrapper")
	private WebElement wrapper;
	
	@FindBy(css = "div.navigator-headers div.dashboard")
	private WebElement dashBoard;
	
	@FindBy(css = "div.navigator-headers div.inputs")
	private WebElement inputs;
	
	@FindBy(css = "div.navigator-headers div.sheets")
	private WebElement Sheets;
	
    @FindBy(css = "div.qtip-content div.no-padding div.ember-view ul.budgeta-dropdown-list")
    private WebElement budgetsListWrapper;
    
    @FindBy(id = "selected-root-menu")
    private WebElement showBudgetsBtn;
    
    @FindBy(css = "div.qtip-content div.no-padding div.ember-view ul.budgeta-dropdown-list li")
    private List<WebElement> budgetsList;
    
    private By budgetSettingTriggerMenu = By.cssSelector("div.qtip-focus ul.budgeta-dropdown-list li");
    
    @FindBy(id = "more-budget-actions")
    private WebElement moreBtn;
    
    
    @FindBy(css = "div.budget-title a")
    private WebElement selectedBudget;
    
    @FindBy(id = "more-budget-actions")
    private WebElement budgetSetting;
    
	public void openDashboardTab(){
		clickOnTab(dashBoard);
	}
	
	public void openInputTab(){
		clickOnTab(inputs);
	}
	
	public void openSheetTab(){
		clickOnTab(Sheets);
	}
	
	
	
	public String getOpenTab(){
		return wrapper.findElement(By.className("active")).getText();
	}
	
	
	
	private void clickOnTab(WebElement el){
		if(el.getAttribute("class").contains("active"))
			return;
		el.click();
		WebdriverUtils.elementToHaveClass(el, "active");
		WebdriverUtils.waitForBudgetaBusyBar(driver);
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}
	
    public void openBudgetsList() {
	if (!WebdriverUtils.isDisplayed(budgetsListWrapper)) {
	    showBudgetsBtn.click();
	    wait.until(ExpectedConditions.visibilityOf(budgetsListWrapper));
	    WebdriverUtils.sleep(100);
	    WebElementUtils.hoverOverField(budgetsListWrapper, driver, null);
	}
    }
    
    public void openMoreBudgetList(){
    	moreBtn.click(); 
    	try{
			WebdriverUtils.waitForElementToBeFound(driver, By.className("qtip-focus"));
			wait.until(ExpectedConditions.visibilityOfElementLocated(budgetSettingTriggerMenu));
		}
		catch(Exception e){
			WebElementUtils.clickElementEvent(driver,wrapper);
			WebdriverUtils.waitForElementToBeFound(driver, By.className("qtip-focus"));
			wait.until(ExpectedConditions.visibilityOfElementLocated(budgetSettingTriggerMenu));
		}
    }
	
    public boolean isBudgetExist(String budgetaName) {
	openBudgetsList();
	boolean found = false;
	for (WebElement budget : budgetsList) {
	    if (budget.getText().equals(budgetaName)) {
		found = true;
		break;
	    }
	}
	WebElementUtils.hoverOverField(inputs, driver, null);
	WebdriverUtils.sleep(300);
	return found;
    }
	
    
    public boolean isShareIconExist(String budgetName){
    	openBudgetsList();
    	for (WebElement budget : budgetsList) {
    	    if (budget.getText().equals(budgetName)) {
    	    	budget.findElement(By.cssSelector("div.actions-toggle span.budget-name  div.svg-icon")).isDisplayed();
    	    	return true;
    	    	
    	    }
    	    }  	
    	
    	return false;
    }
    
    public void selectBudgeta(String budgetaName) {
	openBudgetsList();
	for (WebElement budget : budgetsList) {
	    if (budget.getText().equals(budgetaName)) {
		budget.click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("budgeta-dropdown-list")));
		WebdriverUtils.waitForElementToBeFound(driver, By.className("level-1"));
		break;
	    }
	}
	WebdriverUtils.waitForElementToBeFound(driver, By.id("section-General"));
    }
    
    
    
    
  
    
    
    
    
    public void selectRandomBudgeta(){
    	openBudgetsList();
    	int random = WebElementUtils.getRandomNumberByRange(0, getNumbreOfExistBudgets() - 1);
    	WebElementUtils.hoverOverField(budgetsList.get(random), driver, null);
    	budgetsList.get(random).click();
    	wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("budget-list")));
    	WebdriverUtils.waitForElementToBeFound(driver, By.className("level-1"));
        }

        public void selectRandomBudgetWithPrefix(String prefix) {
    	openBudgetsList();
    	List<WebElement> budgetsStartWithPrefix = new ArrayList<>();
    	for (WebElement el : budgetsList) {
    	    if (el.getText().startsWith(prefix)) {
    		budgetsStartWithPrefix.add(el);
    	    }
    	}
    	int random = WebElementUtils.getRandomNumberByRange(0, budgetsStartWithPrefix.size() - 1);
    	WebElementUtils.hoverOverField(budgetsStartWithPrefix.get(random), driver, null);
    	budgetsStartWithPrefix.get(random).click();
    	wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("budget-list")));
    	WebdriverUtils.waitForElementToBeFound(driver, By.className("level-1"));
        }
        
        
       

        public int getNumberOfBudgetsWithName(String name) {
    	int sum = 0;
    	openBudgetsList();
    	for (WebElement el : budgetsList) {
    	    if (el.getText().equals(name)) {
    		sum++;
    	    }
    	}
    	showBudgetsBtn.click();
    	return sum;
        }

      
        public int getNumberOfBudget(String budgetaName) {

    	openBudgetsList();
    	int num = 0;
    	for (WebElement budget : budgetsList) {
    	    if (budget.getText().equals(budgetaName)) {
    		num++;
    	    }
    	}
    	return num;
        }

        public int getNumbreOfExistBudgets() {
    	return budgetsList.size();
        }

        

            
            
            
            
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
