package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebElementUtils;
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
	
	
	private By dropdown = By.className("dropdown");
	private By dropdownMenu = By.cssSelector("ul.dropdown-menu li");
	private By help = By.className("help-iq");
	private By actions = By.cssSelector("div.actions div.ember-view");
	private By scenarioTriggerMenu = By.cssSelector("div.qtip-focus ul.narrow li");
	
	
	public String getSelectedVersion(){
		return wrapper.findElement(dropdown).findElement(By.tagName("a")).getText().replaceAll("Version:", "");
	}
	
	public void selectVersion(View view, String version){
		
	}
	
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper) && wrapper.getAttribute("class").contains("expanded");
	}

}
