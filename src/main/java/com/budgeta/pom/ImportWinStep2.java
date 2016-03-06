package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

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
	
	public boolean isAllLinesSelected(){
		for (WebElement el : selectAll)
		{
			if((el.findElement(By.tagName("input"))).isSelected())
				return true;
		}
		return false;
	}
	
	public int getLinesNumber(){
		return selectAll.size();
	}
	
	public void selectRandomLines(){
		int random = WebElementUtils.getRandomNumberByRange(0, getLinesNumber() - 1) , i=0;
		while(!(selectAll.get(random).findElement(By.tagName("label")).isSelected()) && i<5){
			random = WebElementUtils.getRandomNumberByRange(0, getLinesNumber() - 1);
    		i++;
    		WebElementUtils.hoverOverField(selectAll.get(random), driver, null);
    		selectAll.get(random).click();
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
	
	public void selectTypeForSelectedLines(String option){
		
		for (WebElement el : checkBox){
			if(el.findElement(By.tagName("input")).isSelected()){
				{
					el.findElement(By.className("select2-chosen")).click();
					for (WebElement type : typeOptions){
						if(type.getText().equals(option)){
							type.click();
							break;
						}

					}
				}
				
			}
		}
		
	}
	
	public void clickNext(){
		nextBtn.click();
		WebdriverUtils.waitForElementToBeFound(driver, By.className("step-3"));
	}
	
	
	
}
