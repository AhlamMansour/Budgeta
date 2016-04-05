package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebElementUtils;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class RevenuesAddSubLine extends AbstractPOM{

	
	@FindBy(className = "new-empty-line")
	private WebElement wrapper;
	
//	private DropDown dropdown ;
	MenuTrigger trigger;
	
	@FindBy(className = "inline-edit")
	private WebElement textField;
	
	private By textFields = By.className("inline-edit");
	private By addBtn = By.cssSelector("div.tree-edit-mode div.add-line .add-budget-line");

	
	public RevenuesAddSubLine() {
		WebdriverUtils.waitForElementToBeFound(driver, By.className("new-empty-line"));
		//dropdown = new DropDown(wrapper.findElement(By.className("select2-container")));
		
	}
	
	MenuTrigger getTrigger(){
		trigger = new MenuTrigger(wrapper.findElement(By.className("dropdown-value")));
		return trigger;
	}
	
	public void setName(String name){
		WebElement field = wrapper.findElement(textFields);
		field.click();
		WebdriverUtils.elementToHaveClass(field, "editing");
		Actions act = new Actions(driver);
		act.moveToElement(field).build().perform();
		WebdriverUtils.sleep(200);
		act.sendKeys(field,name).build().perform();
	}
	
	public void selectDropDown(String option){
		getTrigger();
		trigger.selectBudgetMenuTrigger(option);
		WebElementUtils.hoverOverField(textField, driver, null);
		textField.click();
		
		
	}
	
//	public void selectRandomOption(){
//		trigger.selsectRandomOption();
//	}
	
	public boolean isAddBtnEnable(){
		return wrapper.findElement(addBtn).getAttribute("class").contains("enable");
	}
	
	public void clickAdd(){
		WebdriverUtils.elementToHaveClass(wrapper.findElement(addBtn),"enable");
		WebElementUtils.hoverOverField(textField, driver, null);
		wrapper.findElement(addBtn).click();
		WebdriverUtils.waitForElementToBeFound(driver, By.className("new-empty-line"));
		//WebdriverUtils.waitForBudgetaBusyBar(driver);
	}
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
