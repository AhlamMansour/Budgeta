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
	
	@FindBy(css = "div.month-picker.from")
	private WebElement dateRange_from;
	
	@FindBy(css = "div.month-picker.to")
	private WebElement dateRange_to;
	
	@FindBy(id = "cancel-btn")
	private WebElement cancelBtn;
	
	@FindBy(id = "continue-btn")
	private WebElement continueBtn;
	
	@FindBy(id = "back-btn")
	private WebElement backBtn;
	
	@FindBy(id = "confirm-btn")
	private WebElement createBtn;

	@FindBy(id = "ember1564")
	private WebElement currency;
	
	@FindBy(id = "ember1565")
	private WebElement start;
	
	@FindBy(id = "ember1626")
	private WebElement beginningCashBalance;
	
	@FindBy(id = "ember1579")
	private WebElement accountNumberCheckBox;
	
	@FindBy(id = "ember1581")
	private WebElement productFieldCheckBox;
	
	@FindBy(id = "ember1583")
	private WebElement geographyFieldCheckBox;
	
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
		setOptionInDropDown(type, budgetaType.getName());
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
	
	public void clickCreate(){
		createBtn.click();
		WebdriverUtils.waitForBudgetaBusyBar(driver);
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}
	
	public BudgetaType getBudgetaType(String type){
		for(BudgetaType budgeta : BudgetaType.values()){
			if(budgeta.getName().equalsIgnoreCase(type))
				return budgeta;
		}
		return null;
	}
	
	public void setCurrency(String curr){
		setOptionInDropDown(currency, curr);
	}
	
	public void setFiscalYearStartOn(String _year){
		setOptionInDropDown(start, _year);
	}
	
	public void setbeginninhCashBalance(String cash){
		beginningCashBalance.sendKeys(cash);
	}
	
	public void selectAccountNumberField(){
		selectCheckBox(accountNumberCheckBox);
	}
	
	public void removeAccountNumberField(){
		removeCheckBox(accountNumberCheckBox);
	}
	
	public void selectProductField(){
		selectCheckBox(productFieldCheckBox);
	}
	
	public void removeProductField(){
		removeCheckBox(productFieldCheckBox);
	}
	
	public void selectGeographyField(){
		selectCheckBox(geographyFieldCheckBox);
	}
	
	public void removeGeographyField(){
		removeCheckBox(geographyFieldCheckBox);
	}
	
	public DateRange openDateRangeFrom(){
		dateRange_from.click();
		return new DateRange("from");
	}
	
	public DateRange openDateRangeTo(){
		dateRange_to.click();
		return new DateRange("to");
	}
/***********************************************************************************/	
	private void openDropDown(WebElement dropdown){
		if(!dropdown.getAttribute("class").contains("open")){
			dropdown.click();
			WebdriverUtils.elementToHaveClass(dropdown, "open");
		}
	}
	
	private void setOptionInDropDown(WebElement dropdown, String option){
		openDropDown(dropdown);
		for(WebElement el : dropdown.findElements(dropdownOptions)){
			if(el.getText().equalsIgnoreCase(option)){
				el.click();
				WebdriverUtils.waitForElementToDisappear(driver, By.className("open"));
			}
		}
	}
	
	private void selectCheckBox(WebElement checkBox){
		if(checkBox.isSelected())
			return;
		checkBox.click();
	}
	
	private void removeCheckBox(WebElement checkBox){
		if(!checkBox.isSelected())
			return;
		checkBox.click();
	}
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
