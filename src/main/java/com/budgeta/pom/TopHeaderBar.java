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
	
	//@FindBy(className = "qtip-content")
	//private WebElement scenarioWrapper;
	
	
	@FindBy(className = "qtip-focus")
	private WebElement listWrapper;
	
	@FindBy(id = "select-revisions")
	private WebElement revisions;
	
	@FindBy(className = "select-scenario")
	private WebElement scenario;
	
	
	@FindBy(css = ".top-bar-header-line span")
	private List<WebElement> reportList;

	@FindBy(css = ".top-bar-header-line span.active")
	private WebElement activereport;

	@FindBy(className = "version-scenario-header")
	private WebElement newScenarioVersion;
	
	@FindBy(css = "div.active div.select-revision")
	private WebElement selectedVersion;
	
	
	@FindBy(className = "select-type-versions")
	private List<WebElement> versionsSelectors;
	
	@FindBy(css = "div.top-bar-scenario-header div.svg-icon")
	private List<WebElement> icons;
	
	@FindBy(css = "div.top-bar-version-header div.svg-icon")
	private List<WebElement> versionIcons; 
	
	@FindBy(css = "div.qtip-focus div.qtip-content ul.budgeta-dropdown-list li")
	private List<WebElement> list;
	 
	@FindBy(className = "create-report")
	private WebElement createReportBtn;
	 
	@FindBy(className = "settings")
	private WebElement budgetSettings;
	
	@FindBy(className = "base-header")
	private WebElement baseTab;
	 
	public void openHeaderTab(String reportName) {
		if (activereport.getText().replaceAll("[^\\d\\p{IsLetter}]+", "_").equalsIgnoreCase(reportName)) {
			return;
		}
		clickElementFromListByText(reportList, reportName);
		WebdriverUtils.sleep(1000);
	}

	public boolean clickElementFromListByText(List<WebElement> elements, String name) {
		for (WebElement element : elements) {
			String text = element.getText().replaceAll("[^\\d\\p{IsLetter}]+", "_");

			if (text.equalsIgnoreCase(name)) {
					element.click();
					return true;
				}
			}

		return false;
	}
	
	public void openScenariowindow(){
		scenario.click();
		//wait.until(ExpectedConditions.visibilityOf(scenarioWrapper));
	}
	
	public void openRevisionswindow(){
		WebdriverUtils.sleep(1000);
		revisions.click();
		wait.until(ExpectedConditions.visibilityOf(listWrapper));
		//wait.until(ExpectedConditions.visibilityOf(scenarioWrapper));
	}
	
	public boolean isScenarioAdded(){
		return WebdriverUtils.isDisplayed(newScenarioVersion);
	}
	
	public String newScenatrioText(){
		return newScenarioVersion.getText();
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
	
	
	public void clearVersion(){
		List<WebElement> Icons = versionIcons;
		
		for(WebElement el : Icons){
			if(el.getAttribute("title").equals("Clear"))
			{
				el.click();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("div.active div.select-revision")));
				break;
			}
		}
		
	}

	public boolean isScenarioExist(String scenarioName) {
    	boolean found = false;
    	for (WebElement scenario : list) {
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
	    	List<WebElement> scenarios = new ArrayList<>();
	    	for (WebElement el : list) {
	    	    if (el.getText().startsWith(prefix)) {
	    	    	scenarios.add(el);
	    	    }
	    	}
	    	int random = WebElementUtils.getRandomNumberByRange(0, scenarios.size() - 1);
	    	WebElementUtils.hoverOverField(scenarios.get(random), driver, null);
	    	scenarios.get(random).click();
	    	WebdriverUtils.sleep(300);
	 }
	 
	 
	 
	 
	 public void selectVersion(String name){
		 List<WebElement> versions = new ArrayList<>();
	    	for (WebElement el : list) {
	    		if(el.findElement(By.className("text-tag")).getText().contains(name)){
	    			versions.add(el);
	    		}
	    	}
	    	int random = WebElementUtils.getRandomNumberByRange(0, versions.size() - 1);
	    	WebElementUtils.hoverOverField(versions.get(random), driver, null);
	    	versions.get(random).click();
	    	WebdriverUtils.waitForBudgetaBusyBar(driver);
	    	WebdriverUtils.sleep(300);
	    	isScenarioAdded();
	    	
	 }
	 
	 
	 
	 
	 public boolean isVersionExist(String versionName) {
	    	boolean found = false;
	    	for (WebElement version : list) {
	    	    if (version.findElement(By.className("text-tag")).getText().equals(versionName)) {
	    		found = true;
	    		break;
	    	    }
	    	}
	    //	WebElementUtils.hoverOverField(inputs, driver, null);
	    	WebdriverUtils.sleep(300);
	    	return found;
	        }
	 
	 public void selectAllRevisions(){
	
		 selectTab("ALL");
		 
	 }
	 
	 private void selectTab(String tab){
		 List<WebElement> versions = versionsSelectors;
			
			for(WebElement el : versions){
				if(el.getText().equals(tab))
				{
					el.click();
					//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.drop-down ul.versions")));
					break;
				}
			}
	 }
 public void selectSavedRevisions(){
	
	 selectTab("Saved");
	 }
 
 public void openBaseTab(){
	 baseTab.click();
	 WebdriverUtils.elementToHaveClass(baseTab, "active");
 }
 
 
 public String selectedVesrionName(){
	 return newScenarioVersion.getText();
 }
 
 
 public boolean selectedVersionDisplay(){
	 return WebdriverUtils.isDisplayed(selectedVersion);
 }
 
 
	
 public ReportsPopup clickCreateReport(){
	 createReportBtn.click();
		return new ReportsPopup();
}
 
	public BudgetSettings openBudgetSettings() {
		budgetSettings.click();
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
		WebdriverUtils.waitForBudgetaBusyBar(driver);
		return new BudgetSettings();
	}
 
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
