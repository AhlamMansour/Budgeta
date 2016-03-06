package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.utilities.WebElementUtils;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class ImportWinStep3 extends ImportWinStep2{
	
	@FindBy(className = "step-3")
    private WebElement wrapper;
	
	@FindBy(css = "ol.numbered-steps li a")
    private WebElement editselection;
	
	@FindBy(className = "budgeta-checkbox")
    private List<WebElement> checkBox;
	
	//*[@id='ember13432']/div/span/label
	
	static final String[] Month = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov","Dec" };
	
	public void editSelectedRows(){
		editselection.click();
		WebdriverUtils.waitForElementToBeFound(driver, By.className("step-2"));
	}
	
	
	public void selectColumns(){
		
		for (WebElement el: checkBox){
			for (int i = 0; i < Month.length; i++) {
				if(el.findElement(By.className("column-content")).getText().contains(Month[i])){
					//((JavascriptExecutor) driver).executeScript("arguments[0].click();,':before'", el.findElement(By.tagName("span")).findElement(By.cssSelector("label ::before")));
					//WebElementUtils.clickElementEvent(driver,el.findElement(By.cssSelector("span ::before")));
					//el.findElement(By.tagName("span")).findElement(By.cssSelector("label ::before")).click();
					////////////////////////////
					if(!(el.findElement(By.tagName("span")).findElement(By.tagName("input"))).isSelected()){//add is selected function
						el.findElement(By.tagName("span")).findElement(By.tagName("label")).click();
						WebdriverUtils.waitForElementToBeFound(driver, By.className("noty_type_information"));
						DateRangeSelection selectDates = new DateRangeSelection();
						selectDates.confirmSelectionMonth();
						break;
					}
					
					
					
					
			}
			}
		}
		
	}
	
	
	public void clickNext(){
		nextBtn.click();
		WebdriverUtils.waitForElementToBeFound(driver, By.className("step-4"));
	}

}
