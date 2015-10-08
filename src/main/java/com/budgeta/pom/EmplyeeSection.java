package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class EmplyeeSection extends AbstractPOM{

	
	@FindBy(id = "budget-attributes-wrapper")
	private WebElement wrapper;
	
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

	@FindBy(css = "div.currency div.select2-container")
	private WebElement currency;
	
	
	
	public void setRole(String value){
		setText(role, value);
	}
	
	public void setEmployeeId(String value){
		setText(employeeId, value);
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
	
	public String getRole(){
		return role.getAttribute("value");
	}
	
	public String getEmployeeId(){
		return employeeId.getAttribute("value");
	}
	
	public String getBaseSalaryField(){
		return baseSalaryField.getAttribute("value").replaceAll(",", "");
	}
	
	public String getBonus(){
		return bonus.getAttribute("value");
	}
	
	public String getBenefits(){
		return benefits.getAttribute("value");
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
