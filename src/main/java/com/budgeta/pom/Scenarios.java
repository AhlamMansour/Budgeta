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
	
	private By dropdownField = By.className("dropdown");
	
	private By help = By.className("help-iq");
	private By actions = By.cssSelector("div.actions div.ember-view");
	private By scenarioTriggerMenu = By.cssSelector("div.qtip-focus ul.narrow li");
	
	private SideDropDown dropdown;
	
	public Scenarios(){
		dropdown = new SideDropDown(wrapper.findElement(dropdownField));
	}
	
	public MenuTrigger getMEnuTrigger(){
		return new MenuTrigger(scenarioTrigger);
	}
	
	public CreateNewScenarioPopup createNewScenario(){
		getCreateNewScenarioButton().click();
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));
		return new CreateNewScenarioPopup();
	}
	
	public String getSelectedScenario(){
		return dropdown.getSelectedValue().replaceAll("Scenario:", "");
	}
	
	public boolean isScenarioExist(String scenario){
		return dropdown.isValueExist(scenario);
	}
	
	public void selectScenario(String scenario){
		dropdown.selectValue(scenario);
		WebdriverUtils.waitForBudgetaBusyBar(driver);
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		
	}
	
	public SmallPopup clickRenameScenario(){
		selectScenarioTrigger("Rename");
		return new SmallPopup();
	}
	
	public DeletePopup deleteScenario(){
		selectScenarioTrigger("Delete");
		return new DeletePopup();
	}
	
	public boolean isScenarioTriggerDisplayed(){
		return WebdriverUtils.isDisplayed(scenarioTrigger);
	}
	
	public boolean isScenariosOpen(){
		return wrapper.getAttribute("class").contains("expanded");
	}
/*************************************************************************************/
	
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
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));
	}
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper) && wrapper.getAttribute("class").contains("expanded");
	}

}
