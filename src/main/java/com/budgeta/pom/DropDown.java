package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebElementUtils;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class DropDown extends AbstractPOM{

	private WebElement dropdown;
	
	private By selectedDropdown = By.className("select2-chosen");
	
	private String dropdownOpenString = "select2-dropdown-open";
	
	@FindBy(css = "ul.select2-results li")
	List<WebElement> dropdownOptions;
	
	@FindBy(className = "select2-input")
	private List<WebElement> inputText;
	
	
	//wrapper must be with class name = select2-container
	public DropDown(WebElement wrapper){
		dropdown = wrapper;
	}
	
	
	public void selsectOption(String option){
		openDropDown();
		for(WebElement el : dropdownOptions){
			if(el.getText().equals(option)){
				el.click();
				WebdriverUtils.waitForElementToDisappear(driver ,By.className("select2-dropdown-open"));
				WebdriverUtils.sleep(200);
				return;
			}
		}
		closeDropDown();
	}
	
	public void selsectRandomOption(){
		openDropDown();
		int optionsSize = dropdownOptions.size();
		int random = WebElementUtils.getRandomNumberByRange(0, optionsSize-1);
		dropdownOptions.get(random).click();
		WebdriverUtils.waitForElementToDisappear(driver ,By.className("select2-dropdown-open"));
		WebdriverUtils.sleep(200);
	}
	
	public void selectOptionByIndex(int index){
		openDropDown();
		dropdownOptions.get(index).click();
		WebdriverUtils.waitForElementToDisappear(driver ,By.className("select2-dropdown-open"));
		WebdriverUtils.sleep(200);

	}
	
	public String getSelectedValue(){
		return dropdown.findElement(selectedDropdown).getText();
	}
	
	public void sendKeysToDropDown(String value){
		openDropDown();
		WebElement el = returnVisibleElement(inputText);
		el.clear();
		el.sendKeys(value);
		WebdriverUtils.sleep(300);
		dropdownOptions.get(0).click();
		WebdriverUtils.waitForElementToDisappear(driver ,By.className("select2-dropdown-open"));
		WebdriverUtils.sleep(200);
	}
	
	private void openDropDown(){
		if(dropdown.getAttribute("class").contains(dropdownOpenString))
			return;
		dropdown.click();		
		WebdriverUtils.elementToHaveClass(dropdown, dropdownOpenString);
		try{
			wait.until(ExpectedConditions.visibilityOf(dropdownOptions.get(0)));
		}
		catch(Exception e){}
	}
	
	private void closeDropDown(){
		if(!dropdown.getAttribute("class").contains(dropdownOpenString))
			return;
		WebElementUtils.clickElementEvent(driver, dropdown);		
		WebdriverUtils.waitForElementToDisappear(driver, By.className(dropdownOpenString));
	}
	
	private WebElement returnVisibleElement(List<WebElement> elms){
		for(WebElement el : elms){
			if(el.isDisplayed())
				return el;
		}
		return null;
	}
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(dropdown);
	}

}
