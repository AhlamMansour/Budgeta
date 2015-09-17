package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebElementUtils;
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
			if(el.getText().equals(value)){
				el.click();
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
			if(el.getText().equals(value))
				return true;
		}
		return false;
	}
	
	public int getNumberOfOptions(){
		return dropdown.findElements(dropdownMenu).size();
	}
	
	public void selectCheckBox(String value){
		openDropDown();
		for(WebElement el : dropdown.findElements(dropdownMenu)){
			if(el.getAttribute("class").contains("keep-open")){
				for(WebElement checkBox : el.findElements(By.tagName("input"))){
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
		WebElementUtils.clickElementEvent(driver, dropdown);
		WebdriverUtils.elementToHaveClass(dropdown, "open");
		WebdriverUtils.sleep(200);
	}
	
	private void closeDropDown(){
		if(!dropdown.getAttribute("class").contains("open"))
			return;
		WebElementUtils.clickElementEvent(driver, dropdown);
		WebdriverUtils.waitForElementToDisappear(driver, By.className("open"));
	}
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(dropdown);
	}

}
