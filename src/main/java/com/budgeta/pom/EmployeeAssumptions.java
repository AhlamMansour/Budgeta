package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class EmployeeAssumptions extends AbstractPOM{

	@FindBy(id = "section-Employee_Assumptions")
	private WebElement wrapper;
	
	@FindBy(className = "amount-change")
	private WebElement geographyAndBenefits;
	
	@FindBy(id = "attribute-paymentAfter")
	private WebElement payment;
	
	@FindBy(name = "yearlyVacationDays")
	private WebElement yearlyVacationDays;
	
	@FindBy(name = "avgAccruedVacation")
	private WebElement avgAccruedVacation;
	
	@FindBy(name = "maxAccruedVacation")
	private WebElement maxAccruedVacation;
	
	@FindBy(name = "yearlyIncrease")
	private WebElement yearlyIncrease;
	
	private By dropdown = By.className("select2-container");
	private By error = By.className("input-error");
	
	public void setGeography(String value){
		setText(getGeographyElement(), value);
	}
	
	public void setBenefits(String value){
		setText(getBenefitsElement(), value);
	}
	
	public String getGeography(){
		return getGeographyElement().getAttribute("value");
	}
	
	public String getBenefits(){
		return getBenefitsElement().getAttribute("value");
	}
	
	public void selectPayment(String option){
		DropDown paymentDropdown = new DropDown(payment.findElement(dropdown));
		paymentDropdown.selsectOption(option);
	}
	
	public String getPayment(){
		DropDown paymentDropdown = new DropDown(payment.findElement(dropdown));
		return paymentDropdown.getSelectedValue();
	}
	
	public void setYearlyVacatoinDays(String value){
		setText(yearlyVacationDays, value);
	}
	
	public void setAvgAccruedVacation(String value){
		setText(avgAccruedVacation, value);
	}
	
	public void setMaxAccruedVacation(String value){
		setText(maxAccruedVacation, value);
	}
	
	public void setYearlyIncrease(String value){
		setText(yearlyIncrease, value);
	}
	
	public String getYearlyVacatoinDays(){
		return yearlyVacationDays.getAttribute("value");
	}
	
	public String getAvgAccruedVacation(){
		return avgAccruedVacation.getAttribute("value");
	}
	
	public String getMaxAccruedVacation(){
		return maxAccruedVacation.getAttribute("value");
	}
	
	public String getYearlyIncrease(){
		return yearlyIncrease.getAttribute("value");
	}
	
	public boolean employeeAssumptionHasError(){
		return wrapper.findElements(error).size() > 0;
	}
	
	
	/***************************************************************************/
	/***************************************************************************/
	private WebElement getGeographyElement(){
		int i = 1;
		for(WebElement el : geographyAndBenefits.findElements(By.tagName("label"))){
			if(el.getText().equals("Geography"))
				return geographyAndBenefits.findElements(By.tagName("input")).get(i);
			i++;
		}
		return null;
	}
	
	private WebElement getBenefitsElement(){
		int i = 1;
		for(WebElement el : geographyAndBenefits.findElements(By.tagName("label"))){
			if(el.getText().equals("Benefits"))
				return geographyAndBenefits.findElements(By.tagName("input")).get(i);
			i++;
		}
		return null;
	}
	
	private void setText(WebElement el, String value){
		el.clear();
		el.sendKeys(value);
	}
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
