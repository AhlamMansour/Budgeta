package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class ReportsPopup extends AbstractPOM{

	@FindBy(className = "modal-content")
	private WebElement wrapper;
	
	@FindBy( css = "div.modal-body label.full")
	private List<WebElement> checkBoxes;
	
	@FindBy( id = "confirm-btn")
	private WebElement createBtn;
	
	public ReportsPopup(){
		try{
			wait.until(WebdriverUtils.visibilityOfWebElement(wrapper));
		}catch(Exception e){
		}
	}
	
	public void checkBox(String boxToCheck) {
		getCheckBoxElement(boxToCheck).click();
	}
	
	public boolean isBoxChecked(String checkBox){
		return getCheckBoxElement(checkBox).findElement(By.tagName("input")).isSelected();
	}
	
	private WebElement getCheckBoxElement(String checkBox){
		for(WebElement box : checkBoxes){
			if(box.getText().contains(checkBox))
				return box;
		}
		return null;
	}
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

	public void clickCreate() {
		createBtn.click();
		WebdriverUtils.waitForBudgetaCreateButton(driver);
		WebdriverUtils.waitForInvisibilityOfElement(driver, wrapper, 6);
	}
}
