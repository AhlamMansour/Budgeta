package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class ImportWinStep2 extends ImportWinStep1{

	@FindBy(className = "step-2")
    private WebElement wrapper;

	@FindBy(css = "div.left-side div.import-step input.ember-checkbox")
    private WebElement checkImportThousand;
	
	@FindBy(id = "importSelectAll")
    private WebElement selectAll;
	
	@FindBy(className = "small-checkbox")
    private List<WebElement> checkBox;
	
	@FindBy(className = "select2-chosen")
    private WebElement selecttype; 
	
	
	public void selectAllRows(){
		selectAll.click();
	}
	
	public void selectLines(String option){
		for(WebElement el : checkBox){
			if(el.getText().equals(option))
			{
				el.click();
				//Add wait 
				// check if el.gettext is correct
			}
				
		}
	}
	
	public void clickNext(){
		nextBtn.click();
		WebdriverUtils.waitForElementToBeFound(driver, By.className("step-3"));
	}
	
	
	
}
