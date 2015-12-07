package com.budgeta.pom;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebElementUtils;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class TopHeaderBar extends AbstractPOM{
	
	@FindBy(className = "top-bar-header-line")
	private WebElement wrapper;
	
	@FindBy(className = "qtip-content")
	private WebElement scenarioWrapper;
	
	@FindBy(id = "select-revisions")
	private WebElement revisions;
	
	@FindBy(className = "select-scenario")
	private WebElement scenario;
	
	
	@FindBy(className = "version-scenario-header")
	private WebElement newScenario;
	
	@FindBy(css = "div.top-bar-scenario-header div.svg-icon")
	private List<WebElement> icons;
	
	 @FindBy(css = "div.qtip-focus div.qtip-content ul.budgeta-dropdown-list li")
	    private List<WebElement> scenariosList;
	
	
	public void openScenariowindow(){
		scenario.click();
		//wait.until(ExpectedConditions.visibilityOf(scenarioWrapper));
	}
	
	public boolean isScenarioAdded(){
		return WebdriverUtils.isDisplayed(newScenario);
	}
	
	public String newScenatrioText(){
		return newScenario.getText();
	}
	
	public void clearScenario(){
		List<WebElement> Icons = icons;
		
		for(WebElement el : Icons){
			if(el.getAttribute("title").equals("Clear"))
			{
				el.click();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("div.active div.select-scenario")));
				break;
			}
		}
		
	}
	
	
	
	

	public boolean isScenarioExist(String scenarioName) {
    	boolean found = false;
    	for (WebElement scenario : scenariosList) {
    	    if (scenario.getText().equals(scenarioName)) {
    		found = true;
    		break;
    	    }
    	}
    //	WebElementUtils.hoverOverField(inputs, driver, null);
    	WebdriverUtils.sleep(300);
    	return found;
        }
	
	
	 public void selectScenario(String prefix) {
	    	List<WebElement> budgetsStartWithPrefix = new ArrayList<>();
	    	for (WebElement el : scenariosList) {
	    	    if (el.getText().startsWith(prefix)) {
	    		budgetsStartWithPrefix.add(el);
	    	    }
	    	}
	    	int random = WebElementUtils.getRandomNumberByRange(0, budgetsStartWithPrefix.size() - 1);
	    	WebElementUtils.hoverOverField(budgetsStartWithPrefix.get(random), driver, null);
	    	budgetsStartWithPrefix.get(random).click();
	    	WebdriverUtils.sleep(300);
	 }
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
