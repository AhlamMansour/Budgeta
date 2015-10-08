package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class MainSection extends AbstractPOM{

	@FindBy(id = "budget-attributes-wrapper")
	private WebElement wrapper;
	
	private By error = By.className("input-error");
	
	private EmplyeeSection employeeSection;
	private EmployeeAssumptions employeeAssumptions;
	
	
	public boolean isMainSectionHasError(){
		return wrapper.findElements(error).size() > 0;
	}
	
	public EmplyeeSection getEmplyeeSection(){
		employeeSection = new EmplyeeSection();
		return employeeSection;
	}
	
	public EmployeeAssumptions getEmployeeAssumptions(){
		employeeAssumptions = new EmployeeAssumptions();
		return employeeAssumptions;
	}
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
