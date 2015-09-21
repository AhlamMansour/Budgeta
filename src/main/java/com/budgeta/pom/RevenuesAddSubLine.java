package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class RevenuesAddSubLine extends AbstractPOM{

	
	@FindBy(className = "new-empty-line")
	private WebElement wrapper;
	
	private DropDown dropdown ;
	
	private By textField = By.className("inline-edit");
	private By addBtn = By.cssSelector("a.add.add-line");

	
	public RevenuesAddSubLine() {
		WebdriverUtils.waitForElementToBeFound(driver, By.className("new-empty-line"));
		dropdown = new DropDown(wrapper.findElement(By.className("select2-container")));
	}
	
	
	public void setName(String name){
		WebElement field = wrapper.findElement(textField);
		field.clear();
		field.sendKeys(name);;
	}
	
	public void selectDropDown(String option){
		dropdown.selsectOption(option);
	}
	
	public void selectRandomOption(){
		dropdown.selsectRandomOption();
	}
	
	public boolean isAddBtnEnable(){
		return wrapper.findElement(addBtn).getAttribute("class").contains("enable");
	}
	
	public void clickAdd(){
		WebdriverUtils.elementToHaveClass(wrapper.findElement(addBtn),"enable");
		wrapper.findElement(addBtn).click();
		WebdriverUtils.waitForElementToDisappear(driver, By.className("new-empty-line"));
	}
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
