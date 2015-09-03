package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class NewBudgetPopup extends AbstractPOM{

	@FindBy(className = "modal-content")
	private WebElement wrapper;
	
	@FindBy(id = "new-budget-name-input")
	private WebElement name;
	
	@FindBy(id = "budget-type-dropdown")
	private WebElement type;
	
	@FindBy(id = "cancel-btn")
	private WebElement cancelBtn;
	
	@FindBy(id = "continue-btn")
	private WebElement continueBtn;
	
	@FindBy(id = "back-btn")
	private WebElement backBtn;
	
	@FindBy(id = "confirm-btn")
	private WebElement createBtn;
	
	
	private By dropdownOptions = By.cssSelector("ul.dropdown-menu li");
	
	public enum BudgetaType {
		COMPANY_BUDGET("Company Budget"), DEPARTMENT_BUDGET("Department Budget"), REVENUES("Revenues");
		
		private String name;
		
		private BudgetaType(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name().replaceAll("_", " ");
		}
	}

	
	
	public NewBudgetPopup() {
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
	}

	public void setName(String budgetaName){
		name.sendKeys(budgetaName);
	}
	
	public String getName(){
		return name.getAttribute("value");
	}
	
	public void setType(BudgetaType budgetaType){
		openDropDownBudgetaType();
		for(WebElement el : type.findElements(dropdownOptions)){
			if(budgetaType.getName().equalsIgnoreCase(el.getText())){
				el.click();
				WebdriverUtils.waitForElementToDisappear(driver, By.className("open"));
			}
		}
	}
	
	public void clickContinue(){
		continueBtn.click();
		WebdriverUtils.waitForElementToBeFound(driver, By.id("back-btn"));
		WebdriverUtils.waitForElementToBeFound(driver, By.id("confirm-btn"));
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}
	
	public BudgetaBoard clickCancel(){
		cancelBtn.click();
		WebdriverUtils.waitForElementToDisappear(driver, By.className("modal-content"));
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		return new BudgetaBoard();
	}
	
	public void clickBack(){
		backBtn.click();
		WebdriverUtils.waitForElementToDisappear(driver, By.id("back-btn"));
		WebdriverUtils.waitForElementToDisappear(driver, By.id("confirm-btn"));
		WebdriverUtils.waitForElementToBeFound(driver, By.id("continue-btn"));
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}
	
	public BudgetaType getBudgetaType(String type){
		for(BudgetaType budgeta : BudgetaType.values()){
			if(budgeta.getName().equalsIgnoreCase(type))
				return budgeta;
		}
		return null;
	}
/***********************************************************************************/	
	private void openDropDownBudgetaType(){
		if(!type.getAttribute("class").contains("open")){
			type.click();
			WebdriverUtils.elementToHaveClass(type, "open");
		}
	}
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
