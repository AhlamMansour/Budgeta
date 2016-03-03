package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.utilities.WebElementUtils;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class ImportWinStep2 extends ImportWinStep1{

	@FindBy(className = "step-2")
    private WebElement wrapper;

	@FindBy(css = "div.left-side div.import-step input.ember-checkbox")
    private WebElement checkImportThousand;
	
//	@FindBy(id = "importSelectAll")
//    private WebElement selectAll;
	
	@FindBy(className = "small-checkbox")
    private List<WebElement> selectAll;
	
	@FindBy(className = "small-checkbox")
    private List<WebElement> checkBox;
	
	@FindBy(className = "select2-chosen")
    private WebElement selecttype; 
	
	@FindBy(css = "div.select2-drop ul.select2-results li")
    private List<WebElement> typeOptions; 
	
	public void selectAllRows(){
		//selectAll.findElement(By.tagName("label")).getAttribute("for").equals("importSelectAll");
		
		for (WebElement el : selectAll){
			if(el.findElement(By.tagName("label")).getAttribute("for").equals("importSelectAll")){
				el.findElement(By.tagName("label")).click();
				break;
			}
		}
		
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
	
	
	public void selectType(String option){
		selecttype.click();
		for (WebElement el : typeOptions){
			if(el.getText().equals(option)){
				el.click();
				break;
			}
				
			
		}
		
		
		
		
	}
	
	public void clickNext(){
		nextBtn.click();
		WebdriverUtils.waitForElementToBeFound(driver, By.className("step-3"));
	}
	
	
	
}
