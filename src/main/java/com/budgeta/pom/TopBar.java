package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebElementUtils;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class TopBar extends AbstractPOM{
	
	@FindBy(tagName = "header")
	private WebElement wrapper;
	
	@FindBy(className = "user-menu")
	private WebElement userMenu;
	
	@FindBy(className = "help-menu")
	private WebElement helpMenu;
	
	private By dropDownOptions = By.cssSelector("ul.dropdown-menu li");
	
	private boolean isdropdownOpened(WebElement el){
		return el.getAttribute("class").contains("open");
	}
	
	private void openDropDown(WebElement dropdown){
		if(isdropdownOpened(dropdown))
			return;
		dropdown.click();
		WebdriverUtils.elementToHaveClass(dropdown, "open");
	}
	
	private void clickDropDownOption(WebElement dropdown, String option){
		openDropDown(dropdown);
		for(WebElement el : dropdown.findElements(dropDownOptions)){
			if(el.getText().equals(option)){
				el.click();
				return;
			}
		}
	}
	
	private void clickHelpDropDownOption(String option){
		openDropDown(helpMenu);
		for(WebElement el : helpMenu.findElements(dropDownOptions)){
			if(el.getText().equals(option)){
				el.click();
				return;
			}
			if(el.getText().equals("Tutorials"))
				WebElementUtils.hoverOverField(el, driver, null);
		}
	}
	
	public String getUserName(){
		return userMenu.findElement(By.className("user-name")).getText();
	}
	
	public boolean isUserMenuOpened(){
		return isdropdownOpened(userMenu);
	}
	
	public boolean ishelpMenuOpened(){
		return isdropdownOpened(helpMenu);
	}
	
	public void clickAccountSettings(){
		clickDropDownOption(userMenu, "Account Settings");
	}
	
	public void clickChangePassword(){
		clickDropDownOption(userMenu, "Change Password");
	}
	
	public void clickLogout(){
		clickDropDownOption(userMenu, "Logout");
	}
	
	public void clickDocumentation(){
		clickHelpDropDownOption("Documentation");
	}
	
	public void clickIntroduction(){
		clickHelpDropDownOption("Introduction");
	}
	
	public void clickNewBudget(){
		clickHelpDropDownOption("New budget");
	}
	
	public void clickImport(){
		clickHelpDropDownOption("Import");
	}
	
	public void clickView(){
		clickHelpDropDownOption("View");
	}
	
	public void clickActuals(){
		clickHelpDropDownOption("Actuals");
	}
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
