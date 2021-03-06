package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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
	
	@FindBy(css = ".modal-content div.month-picker.from")
	private WebElement dateRange_from;
	
	@FindBy(css = ".modal-content div.month-picker.to")
	private WebElement dateRange_to;
	
	@FindBy(id = "cancel-btn")
	private WebElement cancelBtn;
	
	@FindBy(id = "continue-btn")
	private WebElement continueBtn;
	
	@FindBy(id = "back-btn")
	private WebElement backBtn;
	
	@FindBy(id = "confirm-btn")
	private WebElement createBtn;
	
	@FindBy(className = "flex")
	private List<WebElement> fields;
	
	@FindBy(className = "ember-checkbox")
	private List<WebElement> checkBoxes;
	
	@FindBy(css = "label.error")
	private List<WebElement> errors;
	
	private WebElement currency;
	
	private WebElement Fiscalstart;
	
	private WebElement beginningCashBalance;
	
	private WebElement accountNumberCheckBox;
	
	private WebElement productFieldCheckBox;
	
	private WebElement geographyFieldCheckBox;
	
	private By dropdownOptions = By.cssSelector("ul.dropdown-menu li");
	
	//private By dropdownOptionsCurrency = By.cssSelector("ul.select2-result-sub li");
	@FindBy(css = "ul.select2-result-sub li")
	private List<WebElement> dropdownOptionsCurrency;
	
	@FindBy(className = "upload")
	private WebElement restore;
	
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
	
	public void clickContinue(boolean doContinue){
		continueBtn.click();
		if(!doContinue)
			return;
		
		WebdriverUtils.waitForElementToBeFound(driver, By.id("back-btn"));
		WebdriverUtils.waitForElementToBeFound(driver, By.id("confirm-btn"));
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		
		for(WebElement el : fields){
			if(el.findElement(By.tagName("label")).getText().contains("Currency"))
				currency = el;
			if(el.findElement(By.tagName("label")).getText().contains("Fiscal"))
				Fiscalstart = el;
			if(el.findElement(By.tagName("label")).getText().contains("Beginning cash"))
				beginningCashBalance = el.findElement(By.tagName("input"));
		}
		
		for(WebElement el : checkBoxes){
			if(el.findElement(By.xpath("..")).getText().contains("account number"))
				accountNumberCheckBox = el;
			if(el.findElement(By.xpath("..")).getText().contains("product field"))
				productFieldCheckBox = el;
			if(el.findElement(By.xpath("..")).getText().contains("geography field"))
				geographyFieldCheckBox = el;
		}
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
		setCurrencyOptionInDropDown(currency, curr);
	}
	
	public void setFiscalYearStartOn(String _year){
		setOptionInDropDown(Fiscalstart, _year);
	}
	
	public void setbeginninhCashBalance(String cash){
		beginningCashBalance.click();
		beginningCashBalance.clear();
		beginningCashBalance.sendKeys(cash);
	}
	
	public void selectAccountNumberField(){
		selectCheckBox(accountNumberCheckBox);
	}
	
	public void removeAccountNumberField(){
		removeCheckBox(accountNumberCheckBox);
	}
	
	public boolean isAccountNumberFieldSelected(){
		return isCheckBoxIsSelected(accountNumberCheckBox);
	}
	
	public void selectProductField(){
		selectCheckBox(productFieldCheckBox);
	}
	
	public void removeProductField(){
		removeCheckBox(productFieldCheckBox);
	}
	
	public boolean isProductFieldSelected(){
		return isCheckBoxIsSelected(productFieldCheckBox);
	}
	
	public void selectGeographyField(){
		selectCheckBox(geographyFieldCheckBox);
	}
	
	public void removeGeographyField(){
		removeCheckBox(geographyFieldCheckBox);
	}
	
	public boolean isGeographyFieldSelected(){
		return isCheckBoxIsSelected(geographyFieldCheckBox);
	}
	
	public DateRange openDateRangeFrom(){
		dateRange_from.click();
		return new DateRange("From");
	}
	
	public DateRange openDateRangeTo(){
		try{
			if(!driver.findElement(By.cssSelector("div.qtip-focus div.month-picker-control")).isDisplayed())
				dateRange_to.click();
		}
		catch(NoSuchElementException e){
			dateRange_to.click();
		}
		return new DateRange("To");
	}
	
	public boolean pageHasError(){
		return !getVisibleErrorText().isEmpty();
	}
	
	public String getVisibleErrorText(){
		for(WebElement error : errors){
			if(!error.getText().isEmpty())
				return error.getText();
		}
		return "";
	}
	
	public void clickonName()
	{
		name.click();
	}
	
	public void clickRestoreAndUpload(String path){
		/*wrapper.findElement(restore).sendKeys(path);
		WebdriverUtils.sleep(5000);
		
		try{
			WebdriverUtils.waitForBudgetaLoadBar(driver);
			WebdriverUtils.waitForBudgetaBusyBar(driver);
		}catch(Exception e){
			WebdriverUtils.waitForBudgetaBusyBar(driver);
		}
		*/
		
		restore.findElement(By.tagName("input")).sendKeys(path);
		WebdriverUtils.sleep(2000);
		//WebdriverUtils.waitForBudgetaLoadBar(driver);
		WebdriverUtils.waitForBudgetaBusyBar(driver);
		
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
	
	private void setCurrencyOptionInDropDown(WebElement dropdown, String option){
		openDropDown(dropdown);
		for(WebElement el : dropdownOptionsCurrency){
			if(el.getText().equalsIgnoreCase(option)){
				el.click();
				WebdriverUtils.waitForElementToDisappear(driver, By.className("open"));
				break;
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
	
	
	public boolean isCheckBoxIsSelected(WebElement checkBox){
		if(checkBox.isSelected())
			return true;
		else 
			return false;
	}
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
