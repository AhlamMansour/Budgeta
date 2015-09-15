package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebElementUtils;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class Scenarios extends AbstractPOM{

	@FindBy(className = "scenario-subnav")
	private WebElement wrapper;
	
	@FindBy(id = "scenarioMenuTrigger")
	private WebElement scenarioTrigger;
	
	
	private By dropdown = By.className("dropdown");
	private By dropdownMenu = By.cssSelector("ul.dropdown-menu li");
	private By help = By.className("help-iq");
	private By actions = By.cssSelector("div.actions div.ember-view");
	private By scenarioTriggerMenu = By.cssSelector("div.qtip-focus ul.narrow li");
	
	public CreateNewScenarioPopup clickCreateNewScenario(){
		getCreateNewScenarioButton().click();
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));
		return new CreateNewScenarioPopup();
	}
	
	public String getSelectedScenario(){
		return wrapper.findElement(dropdown).findElement(By.tagName("a")).getText().replaceAll("Scenario:", "");
	}
	
	public boolean isScenarioExist(String scenario){
		for(WebElement el : wrapper.findElements(dropdownMenu)){
			if(el.getText().equals(scenario))
				return true;
		}
		return false;
	}
	
	public void selectScenario(String scenario){
		openDropDown();
		for(WebElement el : wrapper.findElements(dropdownMenu)){
			if(el.getText().equals(scenario))
				el.click();
		}
		closeDropDown();
	}
	
	public CreateNewScenarioPopup clickRenameScenario(){
		selectScenarioTrigger("Rename");
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));
		return new CreateNewScenarioPopup();
	}
	
	public void deleteScenario(){
		selectScenarioTrigger("Delete");

	}
/*************************************************************************************/
	private void openDropDown(){
		WebElement menu = wrapper.findElement(dropdown);
		if(menu.getAttribute("class").contains("open"))
			return;
		WebElementUtils.clickElementEvent(driver, menu);
		WebdriverUtils.elementToHaveClass(menu, "open");
		WebdriverUtils.sleep(200);
	}
	
	private void closeDropDown(){
		WebElement menu = wrapper.findElement(dropdown);
		if(!menu.getAttribute("class").contains("open"))
			return;
		WebElementUtils.clickElementEvent(driver, menu);
		WebdriverUtils.waitForElementToDisappear(driver, By.className("open"));
	}
	
	private WebElement getCreateNewScenarioButton(){
		List<WebElement> rightButtons =  wrapper.findElements(actions);
		for(WebElement el : rightButtons){
			if(el.getAttribute("title").equals("Create a new scenario"))
				return el;
		}
		return null;
	}
	
	private void openScenarioTrigger(){
		scenarioTrigger.click();
		WebdriverUtils.waitForElementToBeFound(driver, By.className("qtip-focus"));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(scenarioTriggerMenu));
	}
	
	private void selectScenarioTrigger(String option){
		openScenarioTrigger();
		for(WebElement el : driver.findElements(scenarioTriggerMenu)){
			if(el.getText().equals(option))
				el.click();
		}
	}
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper) && wrapper.getAttribute("class").contains("expanded");
	}

}
