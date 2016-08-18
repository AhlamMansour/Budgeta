package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class EmplyeeSection extends AbstractPOM{

	
	@FindBy(id = "budget-attributes-wrapper")
	private WebElement wrapper;
	
	@FindBy(id = "see-change-log")
	private WebElement changeLogBtn;

	@FindBy(name = "role")
	private WebElement role;
	
	@FindBy(name = "employeeId")
	private WebElement employeeId;

	@FindBy(name = "base")
	private WebElement baseSalaryField;
	
	@FindBy(id = "attribute-term")
	private WebElement term;
	
	@FindBy(name = "bonus")
	private WebElement bonus;
	
	@FindBy(name = "benefits")
	private WebElement benefits;
	
	@FindBy(name = "avgAccruedVacation")
	private WebElement avgAccruedVacation;

	@FindBy(name = "yearlyVacationDays")
	private WebElement yearlyVacationDays;
	
	
	@FindBy(name = "yearlyIncrease")
	private WebElement yearlyIncrease;
	
	
	@FindBy(css = "div.currency div.select2-container a")
	private WebElement currency;
	
	@FindBy(css = "div.budgeta-type-inner span.select2-chosen")
	private WebElement employeeType;
	
	@FindBy(name = "amount")
	private WebElement numberOfEmployee;
	
	
	
	public void setRole(String value){
		setText(role, value);
	}
	
	public void setEmployeeId(String value){
		setText(employeeId, value);
	}
	
	public void hoverToUp() {
		Actions act = new Actions(driver);
		act.moveToElement(changeLogBtn).build().perform();
	}

	public void setBaseSalary(String value){
		setText(baseSalaryField, value);
	}
	
	public void selectCurrency(String option){
		DropDown dropdown = new DropDown(currency);
		dropdown.selsectOption(option);
	}
	
	public String getSelecredCurrency(){
		DropDown dropdown = new DropDown(currency);
		return dropdown.getSelectedValue();		
	}
	
	public void selectTerm(String option){
		DropDown dropdown = new DropDown(term.findElement(By.className("select2-container")));
		dropdown.selsectOption(option);
	}
	
	public String getSelecredTerm(){
		DropDown dropdown = new DropDown(term.findElement(By.className("select2-container")));
		return dropdown.getSelectedValue();		
	}
	
	public void setBonus(String value){
		setText(bonus, value);
	}
	
	public void setBenefit(String value){
		setText(benefits, value);
	}
	
	public void setAvgAccuredVacation(String value){
		setText(avgAccruedVacation, value);
	}
	
	public void setYearlyVacationDays(String value){
		setText(yearlyVacationDays, value);
	}
	
	public void setNumberOfEmployees(String value){
		setText(numberOfEmployee, value);
	}
	
	
	
	public String getRole(){
		return role.getAttribute("value");
	}
	
	public String getEmployeeId(){
		return employeeId.getAttribute("value");
	}
	
	public String getBaseSalaryField(){
		return baseSalaryField.getAttribute("title").replaceAll("[^0-9]+", "");
	}
	
	public String getBonus(){
		return bonus.getAttribute("value");
	}
	
	public String getBenefits(){
		return benefits.getAttribute("value");
	}
	
	public String getYearlyVacationDays(){
		return yearlyVacationDays.getAttribute("value");
	}
	
	public String getYearlyIncrease(){
		return yearlyIncrease.getAttribute("value");
	}
	
	
	public String getNumberOfEmployees(){
		return numberOfEmployee.getAttribute("value");
	}
	
	
	public String getAvgAccuredVacation(){
		return avgAccruedVacation.getAttribute("value");
	}
	/***************************************************************************/
	private void setText(WebElement el, String value){
		el.clear();
		el.sendKeys(value);
	}
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

	
}
