package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class BuildCompanyBudgetPopup extends SmallPopup {

	@FindBy(className = "wizard-question-radio")
	private List<WebElement> selectoption;

	@FindBy(className = "Create with Budgeta")
	private WebElement createBudget;
	
//	@FindBy(className = "inactive")
//	private List<WebElement> create;
	
	@FindBy(id = "confirm-btn")
	private WebElement nextmBtn;

	@FindBy(className = "manual")
	private WebElement create;
	
	@FindBy(className = "import")
	private WebElement importBtn;
	
	
	@FindBy(css = "a.add")
	private WebElement addRevenue;

	@FindBy(css = "input.add-multi")
	private List<WebElement> lineNumbers;
	
	
	
	public void slectOption(String option, String expenses) {

		for (WebElement el : selectoption) {
			if (el.findElement(By.tagName("label")).getText().equals(option) || el.findElement(By.tagName("label")).getText().equals(expenses)) {
				if(el.findElement(By.tagName("input")).getAttribute("type").equals("radio"))
				{
					el.click();
					WebdriverUtils.sleep(1000);
				}
				
				
			}
		}
	}
	
	public void selectOption() {
		
		selectoption.get(0).click();
	}

//	public void clickCreateBudget(String option) {
//
//		for (WebElement el : create){
//			if(el.getText().equals(option)){
//				el.click();
//				break;
//			}
//		}
//
//	}
	
	public void clickCreateBudget() {
		create.click();

	}
	
	public void clickImportBudget() {
		importBtn.click();

	}
	
	
	public void clickAdd() {

		addRevenue.click();

	}
	
	
	public void clickNext() {
		nextmBtn.click();

	}
	
	
	public void selectAllcheckBoxes(){
		
		for (WebElement el : selectoption) {
			if(el.findElement(By.tagName("input")).getAttribute("type").equals("checkbox"))
				el.click();
			
		}
		
	}
	
	public void setLineNumber(String name){
		
		for(WebElement el : lineNumbers){
//			wrapper.findElement(lineNumbers).clear();
//			wrapper.findElement(lineNumbers).sendKeys(name);
			el.clear();
			el.sendKeys(name);
			
		}
		
	}

}
