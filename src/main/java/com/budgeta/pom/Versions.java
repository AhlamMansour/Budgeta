package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class Versions extends AbstractPOM{
	
	@FindBy(className = "version-subnav")
	private WebElement wrapper;
	
	@FindBy(id = "versionMenuTrigger")
	private WebElement versionTrigger;
	
	
	private By dropdownField = By.className("dropdown");
	private By help = By.className("help-iq");
	private By actions = By.cssSelector("div.actions div.ember-view");
	private By versionTriggerMenu = By.cssSelector("div.qtip-focus ul li");
	private By autoSave = By.className("auto-version");
	
	
	private SideDropDown dropdown;
	
	public enum View{
		VIEW_ALL("View all"), ONLY_SNAPSHOTS("Only snapshots");
		
		private String name;
		
		private View(String name){
			this.name = name;
		}
		
		public String getName() {
			if(name.equals(""))
				return this.name();
			return name;
		}
		
	}
	
	
	public Versions(){
		dropdown = new SideDropDown(wrapper.findElement(dropdownField));
	}
	
	public MenuTrigger getMenuTrigger(){
		return new MenuTrigger(versionTrigger);
	}
	
	public CreateNewSnapshotPopup createNewSnapshot(){
		getCreateNewVersionButton().click();
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));
		return new CreateNewSnapshotPopup();
	}
	
	public String getSelectedVersion(){
		String str = dropdown.getSelectedValue().replaceAll("Version:", "");
		if(str.contains("/"))
			str = str.substring(0, str.indexOf("/")-2);
		return str.trim();	
	}
	
	public Versions selectVersion(View view, String version){
		if(!getSelectedVersion().equals(version)){
			dropdown.selectCheckBox(view.getName());
			dropdown.selectValue(version);
		}
		return new Versions();
			
	}
	
	public int getNumberOfVersions(){
		return dropdown.getNumberOfOptions() - 1;//-1 because of checkbox
	}
	
	public int getNumberOfAutoSaveVersions(){
		return wrapper.findElements(autoSave).size();
	}
	
	public List<String> getDisplayedOptions(View view){
		dropdown.selectCheckBox(view.getName());
		dropdown = new SideDropDown(wrapper.findElement(dropdownField));
		return dropdown.getDisplayOptoins();
	}
	
	public boolean isVersionsOpen(){
		return wrapper.getAttribute("class").contains("expanded");
	}
	
	public SmallPopup clickRevertVersion(){
		selectScenarioTrigger("Revert");
		return new SmallPopup();
	}	
	
	public SmallPopup clickRenameVersion(){
		selectScenarioTrigger("Rename");
		return new SmallPopup();
	}
	
	public DeletePopup clickDeleteVersion(){
		selectScenarioTrigger("Delete");
		return new DeletePopup();
	}
	
	public boolean isSnapshotExist(String snapshot, View view){
		List<String> snapshots = getDisplayedOptions(view);
		for(String str : snapshots){
			if(str.contains("\n"))
				str = str.substring(0, str.indexOf("\n"));
			if(str.equals(snapshot))
				return true;
		}
		return false;
	}
	
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper) && wrapper.getAttribute("class").contains("expanded");
	}

/************************************************************************************************************/
	private void openVersionTrigger(){
		versionTrigger.click();
		WebdriverUtils.waitForElementToBeFound(driver, By.className("qtip-focus"));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(versionTriggerMenu));
	}
	
	private void selectScenarioTrigger(String option){
		openVersionTrigger();
		for(WebElement el : driver.findElements(versionTriggerMenu)){
			if(el.getText().equals(option))
				el.click();
		}
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));
	}
	
	private WebElement getCreateNewVersionButton(){
		List<WebElement> rightButtons =  wrapper.findElements(actions);
		for(WebElement el : rightButtons){
			if(el.getAttribute("title").equals("Create a new version"))
				return el;
		}
		return null;
	}
	
}
