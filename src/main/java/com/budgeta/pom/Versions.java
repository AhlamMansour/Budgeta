package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class Versions extends AbstractPOM{
	
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

	@FindBy(className = "version-subnav")
	private WebElement wrapper;
	
	@FindBy(id = "scenarioMenuTrigger")
	private WebElement scenarioTrigger;
	
	
	private By dropdownField = By.className("dropdown");
	private By help = By.className("help-iq");
	private By actions = By.cssSelector("div.actions div.ember-view");
	private By scenarioTriggerMenu = By.cssSelector("div.qtip-focus ul.narrow li");
	
	private SideDropDown dropdown;
	
	public Versions(){
		dropdown = new SideDropDown(wrapper.findElement(dropdownField));
	}
	
	public String getSelectedVersion(){
		return dropdown.getSelectedValue().replaceAll("Version:", "");
	}
	
	public void selectVersion(View view, String version){
		dropdown.selectCheckBox(view.getName());
		dropdown.selectValue(version);
	}
	
	public int numberOfVersions(){
		return dropdown.getNumberOfOptions() - 1;
	}
	
	public boolean isScenariosOpen(){
		return wrapper.getAttribute("class").contains("expanded");
	}
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper) && wrapper.getAttribute("class").contains("expanded");
	}

/************************************************************************************************************/

	
}
