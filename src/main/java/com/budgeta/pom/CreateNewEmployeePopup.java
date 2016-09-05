package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class CreateNewEmployeePopup extends AbstractPOM {

	@FindBy(className = "modal-content")
	private WebElement wrapper;

	@FindBy(id = "attribute-notes")
	private WebElement notes;

	@FindBy(name = "notes")
	private WebElement note;

	@FindBy(id = "attribute-region")
	private WebElement geography;

	@FindBy(id = "attribute-product")
	private WebElement product;

	@FindBy(id = "attribute-accountId")
	private WebElement actualsAccount;

	@FindBy(id = "attribute-department")
	private WebElement departments;

	@FindBy(id = "attribute-paymentAfter")
	private WebElement paymentAfter;

	@FindBy(name = "maxAccruedVacation")
	private WebElement maxAccruedVacation;

	@FindBy(name = "yearlyIncrease")
	private WebElement yearlyIncrease;

	@FindBy(name = "yearlyVacationDays")
	private WebElement yearlyVacationDays;

	@FindBy(name = "avgAccruedVacation")
	private WebElement avgAccruedVacation;

	@FindBy(css = "[id^=attribute-bonus]")
	private WebElement bonusPaymentAfter;

	@FindBy(name = "benefits")
	private WebElement benefits;

	@FindBy(id = "attribute-bonusPeriod")
	private WebElement bonusPeriod;

	@FindBy(className = "attribute-modifier-content")
	private WebElement bonus;

	@FindBy(id = "attribute-role")
	private WebElement role;

	@FindBy(name = "employeeId")
	private WebElement employeeId;

	@FindBy(className = "show-more-btn")
	private WebElement showMoreOptionsBtn;

	@FindBy(id = "attribute-term")
	private WebElement term;

	@FindBy(id = "attribute-undefined")
	private WebElement employeeName;

	@FindBy(css = "[id^=attribute-base]")
	private WebElement baseSalary;

	@FindBy(css = "div.add-another-wrapper div.budgeta-checkbox")
	private WebElement addAnotherEmployee;

	@FindBy(className = "budgeta-type-dropdown-max")
	private WebElement budgetLine;

	@FindBy(className = "budgeta-type-currency")
	private WebElement currencyType;

	@FindBy(id = "cancel-btn")
	private WebElement cancleBtn;

	@FindBy(id = "confirm-btn")
	private WebElement confirmBtn;
	
	
	@FindBy(css = "[id^=attribute-undefined]")
	private WebElement newBudgetName;
	
	private SideDropDown BudgetLineDropDown;

	public void clickOnMoreOptions() {
		showMoreOptionsBtn.click();
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}

	public void checkAddAnotherEmployee() {
		addAnotherEmployee.click();
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}

	public void setEmployeeName(String name) {
		newBudgetName.findElement(By.tagName("input")).clear();
		newBudgetName.findElement(By.tagName("input")).sendKeys(name);
		WebdriverUtils.waitForBudgetaLoadBar(driver);

	}

	public String getEmployeeName() {

		return newBudgetName.findElement(By.tagName("input")).getAttribute("value");
	}
	
	public void setEmployeeBaseSalary(String name) {
		baseSalary.findElement(By.tagName("input")).clear();
		baseSalary.findElement(By.tagName("input")).sendKeys(name);
		WebdriverUtils.waitForBudgetaLoadBar(driver);

	}

	public String getEmployeeBaseSalary() {

		return baseSalary.findElement(By.tagName("input")).getAttribute("value");
	}


	private SideDropDown getBudgetLineDropDown() {

		if (BudgetLineDropDown == null) {
			// for(WebElement el : wrapper.findElements(subReportType)){
			// if(el.getText().trim().equals("Budget"))
			BudgetLineDropDown = new SideDropDown(driver.findElement(By.cssSelector("div.budgeta-type-dropdown-max div.select2-container")));
			// }
		}
		return BudgetLineDropDown;
	}

	public void selectRandomBudgetLine() {
		getBudgetLineDropDown().selectRandomValue();
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}

	public void setEmployeeRole(String name) {
		role.findElement(By.className("tt-input")).clear();
		role.findElement(By.className("tt-input")).sendKeys(name);
		WebdriverUtils.waitForBudgetaLoadBar(driver);

	}

	public String getEmployeeRole() {

		return role.findElement(By.className("tt-input")).getAttribute("value");
	}

	public void setEmployeeId(String name) {
		employeeId.clear();
		employeeId.sendKeys(name);
		WebdriverUtils.waitForBudgetaLoadBar(driver);

	}

	public String getEmployeeId() {

		return employeeId.getText();
	}

	public void selectBonusPeriod() {

	}

	public void setEmployeeBonus(String name) {
		bonus.findElement(By.className("overflow-ellipsis")).clear();
		bonus.findElement(By.className("overflow-ellipsis")).sendKeys(name);
		WebdriverUtils.waitForBudgetaLoadBar(driver);

	}

	public String getEmployeeBonus() {

		return bonus.findElement(By.className("overflow-ellipsis")).getText();
	}

	public void setEmployeeBonusPaymentAfter(String name) {
		bonusPaymentAfter.findElement(By.tagName("input")).clear();
		bonusPaymentAfter.findElement(By.tagName("input")).sendKeys(name);
		WebdriverUtils.waitForBudgetaLoadBar(driver);

	}

	public String getEmployeeBonusPaymentAfter() {

		return bonusPaymentAfter.findElement(By.tagName("input")).getAttribute("value");
	}

	public void setEmployeeBenefits(String name) {
		benefits.clear();
		benefits.sendKeys(name);
		WebdriverUtils.waitForBudgetaLoadBar(driver);

	}

	public String getEmployeeBenefits() {

		return benefits.getText();
	}

	public void setEmployeeYearlyVacationDays(String name) {
		yearlyVacationDays.clear();
		yearlyVacationDays.sendKeys(name);
		WebdriverUtils.waitForBudgetaLoadBar(driver);

	}

	public String getEmployeeYearlyVacationDays() {

		return yearlyVacationDays.getAttribute("value");
	}

	public void setEmployeeAvgAccruedVacation(String name) {
		avgAccruedVacation.clear();
		avgAccruedVacation.sendKeys(name);
		WebdriverUtils.waitForBudgetaLoadBar(driver);

	}

	public String getEmployeeAvgAccruedVacation() {

		return avgAccruedVacation.getAttribute("value");
	}

	public void setEmployeeMaxAccruedVacation(String name) {
		maxAccruedVacation.clear();
		maxAccruedVacation.sendKeys(name);
		WebdriverUtils.waitForBudgetaLoadBar(driver);

	}

	public String getEmployeeMaxAccruedVacation() {

		return maxAccruedVacation.getAttribute("value");
	}

	public void setEmployeeYearlyIncrease(String name) {
		yearlyIncrease.clear();
		yearlyIncrease.sendKeys(name);
		WebdriverUtils.waitForBudgetaLoadBar(driver);

	}

	public String getEmployeeYearlyIncrease() {

		return yearlyIncrease.getAttribute("value");
	}

	public void selectRandomPayment() {

	}

	public void setEmployeeActualsAccount(String name) {
		actualsAccount.findElement(By.tagName("input")).clear();
		actualsAccount.findElement(By.tagName("input")).sendKeys(name);
		WebdriverUtils.waitForBudgetaLoadBar(driver);

	}

	public String getEmployeeActualsAccount() {

		return actualsAccount.findElement(By.tagName("input")).getAttribute("value");
	}

	public void setEmployeeGeography(String name) {
		geography.findElement(By.className("tt-input")).clear();
		geography.findElement(By.className("tt-input")).sendKeys(name);
		WebdriverUtils.waitForBudgetaLoadBar(driver);

	}

	public String getEmployeeGeography() {

		return geography.findElement(By.className("tt-input")).getAttribute("value");
	}

	public void setEmployeeProduct(String name) {
		product.findElement(By.className("tt-input")).clear();
		product.findElement(By.className("tt-input")).sendKeys(name);
		WebdriverUtils.waitForBudgetaLoadBar(driver);

	}

	public String getEmployeeProduct() {

		return product.findElement(By.className("tt-input")).getAttribute("value");
	}

	public void setEmployeeNotes(String name) {
		note.clear();
		note.sendKeys(name);
		WebdriverUtils.waitForBudgetaLoadBar(driver);

	}

	public String getEmployeeNotes() {

		return note.getText();
	}

	public void clickOnCancle() {
		cancleBtn.click();
		WebdriverUtils.waitForElementToDisappear(driver, By.className("modal-content"));
	}

	public void clickOnSave() {
		confirmBtn.click();
		WebdriverUtils.waitForElementToDisappear(driver, By.className("modal-content"));
	}

	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
