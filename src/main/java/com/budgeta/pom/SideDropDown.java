package com.budgeta.pom;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class SideDropDown extends AbstractPOM{

	private WebElement dropdown;
	private By dropdownMenu = By.cssSelector("ul.dropdown-menu li");
	
	
	public SideDropDown(WebElement wrapper){
		dropdown = wrapper;
	}
	
	public void selectValue(String value){
		openDropDown();
		for(WebElement el : dropdown.findElements(dropdownMenu)){
			String str = el.getText();
			if(str.contains("\n"))
				str = str.substring(0, str.indexOf("\n"));
			if(str.trim().equals(value)){
				el.click();
				WebdriverUtils.waitForElementToDisappear(driver, By.className("open"));
				WebdriverUtils.waitForBudgetaBusyBar(driver);
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				return;
			}
		}
		closeDropDown();
	}
	
	public String getSelectedValue(){
		return dropdown.findElement(By.tagName("a")).getText();
	}
	
	public boolean isValueExist(String value){
		for(WebElement el : dropdown.findElements(dropdownMenu)){
			if(el.isDisplayed())
				return true;
		}
		return false;
	}
	
	public int getNumberOfOptions(){
		return dropdown.findElements(dropdownMenu).size();
	}
	
	public List<String> getDisplayOptoins(){
		List<String> res = new ArrayList<>();
		openDropDown();
		for(WebElement el : dropdown.findElements(dropdownMenu)){
			if(el.getAttribute("class")!=null)
				if(el.getAttribute("class").contains("header"))
					continue;
			if(el.isDisplayed())
				res.add(el.getText());
		}
		closeDropDown();
		
		return res;
	}
	
	public void selectCheckBox(String value){
		openDropDown();
		for(WebElement el : dropdown.findElements(dropdownMenu)){
			if(el.getAttribute("class").contains("keep-open")){
				for(WebElement checkBox : el.findElements(By.tagName("label"))){
					if(checkBox.getText().equals(value)){
						checkBox.click();
						closeDropDown();
						return;
					}
				}
			}
		}
		closeDropDown();
	}
/****************************************************************************/
	private void openDropDown(){
		if(dropdown.getAttribute("class").contains("open"))
			return;
		dropdown.click();
		WebdriverUtils.elementToHaveClass(dropdown, "open");
		WebdriverUtils.sleep(600);
	}
	
	private void closeDropDown(){
		if(!dropdown.getAttribute("class").contains("open"))
			return;
		dropdown.click();
		WebdriverUtils.waitForElementToDisappear(driver, By.className("open"));
	}
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(dropdown);
	}

}
