package com.budgeta.pom;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebElementUtils;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class SecondaryBoard extends AbstractPOM{
	
	
	@FindBy(className = "secondary")
	private WebElement wrapper;
	
	@FindBy(css = "ol.has-search li.budget-list-item")
	private List<WebElement> budgetsList;

	@FindBy(className = "add-root-budget")
	private WebElement addBudgetaBtn;
	
	@FindBy(className = "select-root")
	private WebElement showBudgetsBtn;
	
	@FindBy(id = "selected-root-menu")
	private WebElement budgetOptionsMenuBtn;
 
	@FindBy(css = "div.popup-menu div.drop-down ul.narrow li")
	private List<WebElement> budgetDropDownOptions;
	
	@FindBy(className = "budget-list")
	private WebElement budgetsListWrapper;
	
	@FindBy(className = "search")
	private WebElement searchBudget;
	
	@FindBy(className = "scenarios")
	private WebElement scenarios;
	
	@FindBy(className = "versions")
	private WebElement versions;
	
	@FindBy (id = "selected-root-menu")
	private WebElement settingBudgetIcon;
	
	@FindBy(css = "ol.tree.nav")
	private List<WebElement> selectedBudget;
	
	@FindBy(css = "div.tree-edit-bar div.right")
	private WebElement closeBtn;
	
	@FindBy(className = "scenario-added")
	private List<WebElement> scenarioLine;
	
	@FindBy(id = "budget-settings")
	private WebElement budgetSettings;
	
	@FindBy(css = "li.selected-root li.active")
	private WebElement selectedLine;
	
	@FindBy(className = "scenario-changed")
	private List<WebElement> versionChanges;;
	
	private By newLine = By.className("new-line");
	
	private By line = By.cssSelector("li.budget-list-item");
	
	private By budgetName = By.className("budget-name");
	
	private By addLinesBtn = By.className("add-child-budget");
	
	private By addLineBtn = By.cssSelector("a.add.add-line");
	
	private By lineName = By.className("inline-edit");
	
	private By lineSetting = By.className("budget-menu");
	
	private By nameField = By.className("ember-text-field");
	
	private By shareIcon = By.cssSelector("div.actions-toggle span.budget-name  div.svg-icon");
	
	
	public Scenarios openScenarios(){
		if(driver.findElement(By.className("scenario-subnav")).getAttribute("class").contains("collapsed")){
			scenarios.click();
			WebdriverUtils.elementToHaveClass(scenarios, "expanded");
		}
		return new Scenarios();
	}
	
	public Versions openVersions(){
		if(driver.findElement(By.className("version-subnav")).getAttribute("class").contains("collapsed")){
			versions.click();
			WebdriverUtils.elementToHaveClass(versions, "expanded");
		}
		return new Versions();
	}
	
	public NewBudgetPopup addBudgeta(){
		WebdriverUtils.waitUntilClickable(driver, addBudgetaBtn);
		addBudgetaBtn.click();
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		return new NewBudgetPopup();
		
	}
	
	public boolean isBudgetExist(String budgetaName){
		openBudgetsList();
		boolean found = false;
		for(WebElement budget : budgetsList){
			if(budget.findElement(By.className("budget-name")).getText().equals(budgetaName)){
				found =  true;
				break;
			}
		}
		showBudgetsBtn.click();
		WebdriverUtils.sleep(300);
		return found;
	}
	
	public void selectBudgeta(String budgetaName){
		openBudgetsList();
		for(WebElement budget : budgetsList){
			if(budget.findElement(By.className("budget-name")).getText().equals(budgetaName)){
				budget.click();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("budget-list")));
				WebdriverUtils.waitForElementToDisappear(driver, By.className("level-0"));
				WebdriverUtils.waitForElementToBeFound(driver, By.className("level-1"));
				break;
			}
		}
		getSelectedBudget().findElement(budgetName).click();
		WebdriverUtils.waitForElementToBeFound(driver,By.id("section-General"));
	}
	
	public void selectRandomBudgeta(){
		openBudgetsList();
		int random = WebElementUtils.getRandomNumberByRange(0, getNumbreOfExistBudgets()-1);
		WebElementUtils.hoverOverField(budgetsList.get(random), driver, null);
		if(budgetsList.get(random).findElement(budgetName).getText().equals(getSelectedBudget().findElement(budgetName).getText().replaceAll(getSelectedBudget().findElement(By.cssSelector("span.type")).getText(), "").trim()))
			showBudgetsBtn.click();
		else
			budgetsList.get(random).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("budget-list")));
		WebdriverUtils.waitForElementToDisappear(driver, By.className("level-0"));
		WebdriverUtils.waitForElementToBeFound(driver, By.className("level-1"));
	}
	
	public void selectRandomBudgetWithPrefix(String prefix){
		openBudgetsList();
		List<WebElement> budgetsStartWithPrefix = new ArrayList<>();
		for(WebElement el : budgetsList){
			if(el.findElement(budgetName).getText().startsWith(prefix)){
				budgetsStartWithPrefix.add(el);
			}
		}
		int random = WebElementUtils.getRandomNumberByRange(0, budgetsStartWithPrefix.size()-1);
		WebElementUtils.hoverOverField(budgetsStartWithPrefix.get(random), driver, null);
		if(budgetsStartWithPrefix.get(random).findElement(budgetName).getText().equals(getSelectedBudget().findElement(budgetName).getText().replaceAll(getSelectedBudget().findElement(By.cssSelector("span.type")).getText(), "").trim()))
			showBudgetsBtn.click();
		else
			budgetsStartWithPrefix.get(random).click();		
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("budget-list")));
		WebdriverUtils.waitForElementToDisappear(driver, By.className("level-0"));
		WebdriverUtils.waitForElementToBeFound(driver, By.className("level-1"));
	}
	
	public int getNumberOfBudgetsWithName(String name){
		int sum = 0;
		openBudgetsList();
		for(WebElement el : budgetsList){
			if(el.findElement(budgetName).getText().equals(name)){
				sum++;
			}
		}
		showBudgetsBtn.click();
		return sum;
	}
	
	public int getNumbreOfExistBudgets(){
		return budgetsList.size();
	}
	
	public void addLine(){
		if(!wrapper.getAttribute("class").contains("tree-edit")){	
			getSelectedBudget().findElement(addLinesBtn).click();
			WebdriverUtils.waitForElementToBeFound(driver, By.className("tree-edit"));
		}
		
	}
	
	public void addSubLine(String lineTitle){
		if(WebdriverUtils.isDisplayed(closeBtn))
			clickClose();
		WebElement lineElm = getLineByName(lineTitle);
		WebElementUtils.hoverOverField(lineElm, driver, null);
		WebdriverUtils.sleep(300);
		WebElementUtils.hoverOverField(lineElm.findElement(addLinesBtn), driver, null);
		lineElm.findElement(addLinesBtn).click();
		WebdriverUtils.sleep(500);
	}
	
	public void addAllLines(){
		if(!wrapper.getAttribute("class").contains("tree-edit")){	
			getSelectedBudget().findElement(addLinesBtn).click();
			WebdriverUtils.waitForElementToBeFound(driver, By.className("tree-edit"));
		}
		WebElement line = null;
		while(getNextLineToAdd() != null ){
			line = getNextLineToAdd();
			WebElement add = line.findElement(addLineBtn);
			if(add.getAttribute("class").contains("enable")){
				add.click();
				WebdriverUtils.waitForBudgetaBusyBar(driver);
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				WebdriverUtils.sleep(1500);
			}
		}
	}
	
	public void addLine(String lineTitle){
		if(!wrapper.getAttribute("class").contains("tree-edit")){	
			getSelectedBudget().findElement(addLinesBtn).click();
			WebdriverUtils.waitForElementToBeFound(driver, By.className("tree-edit"));
		}
		List<WebElement> lines = getLines();
		String name="";
		for(WebElement el : lines){
			name = getLineName(el).replaceAll("\\d","").trim();
			if(name.equals(lineTitle) && el.getAttribute("class").contains("new-line")){
				WebElement add = el.findElement(addLineBtn);
				if(add.getAttribute("class").contains("enable")){
					add.click();
					WebdriverUtils.sleep(300);
					WebdriverUtils.waitForBudgetaBusyBar(driver);
					WebdriverUtils.waitForBudgetaLoadBar(driver);
					return;
				}
			}
		}			
	}
	
	public String getNameOfSelectedBudgeta(){
		return getSelectedBudget().findElement(budgetName).getText().split("\n")[0];
	}
	
	public MenuTrigger getBudgetMenuTrigger(){
		return new MenuTrigger(settingBudgetIcon);
	}
	
	public void openBudgetDropDownOptionsMenu(){
		if(isBudgetDropDownOptionsOpen()){
			budgetOptionsMenuBtn.click();
			WebdriverUtils.waitForElementToBeFound(driver, By.className("qtip-focus"));
			wait.until(ExpectedConditions.visibilityOfAllElements(budgetDropDownOptions));
		}
	}
	
	public synchronized void selectBudgetOption(String option){
		openBudgetDropDownOptionsMenu();
		for(WebElement el : budgetDropDownOptions){
			if(el.getText().equalsIgnoreCase(option)){
				WebElementUtils.hoverOverField(el, driver, null);
				el.click();
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				WebdriverUtils.waitForBudgetaBusyBar(driver);
			}
		}
	}
	
	public MenuTrigger getLineSettings(String name){
		WebElement line = getLineByName(name);
		WebElementUtils.hoverOverField(line, driver, null);
		Actions act = new Actions(driver);
		act.moveToElement(line).build().perform();
		return new MenuTrigger(line.findElement(lineSetting));
	}
	
	public MenuTrigger getSubLinSettings(String lineTitle, String subLineTitle){
		List<WebElement> subLines = getSubLinesForLine(lineTitle);
		for(WebElement el : subLines){
			if(el.findElement(budgetName).getText().replaceAll
					(el.findElement(By.cssSelector("span.type")).getText(), "").trim().equals(subLineTitle)){
				WebElementUtils.hoverOverField(el, driver, null);
				Actions act = new Actions(driver);
				act.moveToElement(el).build().perform();
				return new MenuTrigger(el.findElement(lineSetting));
			}
		}
		return null;
	}
	
	public void clickClose(){
		if(WebdriverUtils.isDisplayed(closeBtn)){
			closeBtn.click();
			WebdriverUtils.waitForElementToDisappear(driver, By.cssSelector("div.tree-edit-bar div.right"));
		}
	}
	
	public boolean isLineExist(String lineTitle){
		clickClose();
		List<WebElement> lines = getLines();
		for(WebElement el : lines){
			if(el.findElement(budgetName).getText().replaceAll(el.findElement(By.className("type")).getText(), "").trim().equals(lineTitle))
				return true;
		}
		return false;
	}
	
	public boolean isSubLineExist(String lineTitle, String subLineTitle){
		List<WebElement> subLines = getSubLinesForLine(lineTitle);
		for(WebElement el : subLines){
			try{
				if(el.findElement(budgetName).getText().replaceAll(el.findElement(By.className("type")).getText(), "").trim().equals(subLineTitle))
				return true;
			}
			catch(Exception e){}
		}
		return false;
	}
	
		public boolean isLineFlag(String lineTitle){
		clickClose();
		WebElement line = getLineByName(lineTitle);
		try{
			line.findElement(By.className("flagged")).isDisplayed();
			return true;
		}
		catch(Exception e){
			return false;
		}
		
		
	}
	
		
		public boolean isLineShared(String lineTitle){
			clickClose();
			WebElement line = getLineByName(lineTitle);
			try{
				line.findElement(By.cssSelector("div.actions-toggle span.budget-name  div.svg-icon")).isDisplayed();
				return true;
			}
			catch(Exception e){
				return false;
			}
		}		
	
	public void RenameLine(String newName){
		List<WebElement> lines = getLines();
		for(WebElement el : lines){
			if(el.getAttribute("class").contains("active")){
				el.findElement(nameField).clear();
				el.findElement(nameField).sendKeys(newName);
				el.findElement(nameField).sendKeys(Keys.ENTER);
				WebdriverUtils.sleep(500);
			}
		}
	}
	
	public boolean isScenarioLineDisplayed(String name){
	for(WebElement el : scenarioLine){
		if(el.findElement(budgetName).getText().replaceAll(el.findElement(By.className("type")).getText(), "").trim().equals(name)){
			return WebdriverUtils.isDisplayed(el);
		}
	}
	return false;
	}

	public int getNumberOfSubLinesForLine(String lineTitle, String subLineTitle){
		List<WebElement> subLines = getSubLinesForLine(lineTitle);
		int num = 0;
		for(WebElement el : subLines){
			try{
				if(el.findElement(budgetName).getText().indexOf("\n") == -1){
					System.out.println("1."+el.findElement(budgetName).getText());
					if(el.findElement(budgetName).getText().trim().contains(subLineTitle))
						num++;
				}
				else if(el.findElement(budgetName).getText().substring(0, el.findElement(budgetName).getText().indexOf("\n")).trim().contains(subLineTitle)){
					System.out.println("2."+el.findElement(budgetName).getText().substring(0, el.findElement(budgetName).getText().indexOf("\n")));
					num++;
				}
			}
			catch(Exception e){
				continue;
			}
			
		}
		return num;
	}
	
	public int getNumberOfLines(String lineTitle){
		List<WebElement> Lines = getLines();
		int num = 0;
		for(WebElement el : Lines){
			try{
				if(el.findElement(budgetName).getText().indexOf("\n") == -1){
					if(el.findElement(budgetName).getText().trim().contains(lineTitle))
						num++;
				}
				else if(el.findElement(budgetName).getText().substring(0, el.findElement(budgetName).getText().indexOf("\n")).trim().contains(lineTitle)){
					num++;
				}
			}
			catch(Exception e){
				continue;
			}

		}
		return num;
	}
	
	public void clickOnLine(String name){
		clickClose();
		getLineByName(name).findElement(budgetName).click();
		WebdriverUtils.waitForBudgetaBusyBar(driver);
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}
	
	public void clickOnSubLine(String lineName, String subLineName){
		clickClose();
		List<WebElement> sublines = getSubLinesForLine(lineName);
		for(WebElement el : sublines){
			if(getLineName(el).equals(subLineName)){
				el.findElement(budgetName).click();
				WebdriverUtils.waitForBudgetaBusyBar(driver);
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				return;
			}
		}
	}
	
	 public void clickOnSubLine(String lineName, String subLineName, String sub_subLine){
		 if(WebdriverUtils.isDisplayed(closeBtn))
			 clickClose();
		 List<WebElement> sublines = getSubLinesForSubLine(lineName, subLineName);
		 for(WebElement el : sublines){
			if(getLineName(el).contains(sub_subLine)){
				el.findElement(budgetName).click();
				WebdriverUtils.waitForBudgetaBusyBar(driver);
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				return;
			}
		 }
	 }

	
	public void clickOnSubLine(String lineName, String subLineName, String sub_subLine,String nextLevelLine){
		clickClose();
		List<WebElement> sublines = getSubLinesFourthLevel(lineName, subLineName,sub_subLine);
		for(WebElement el : sublines){
			if(getLineName(el).contains(nextLevelLine)){
				el.findElement(budgetName).click();
				WebdriverUtils.elementToHaveClass(el, "active");
				WebdriverUtils.waitForBudgetaBusyBar(driver);
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				return;
			}
		}
	}
	
	public BudgetSettings openBudgetSettings(){
		budgetSettings.click();
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
		WebdriverUtils.waitForBudgetaBusyBar(driver);
		return new BudgetSettings();
	}
	
	public void addSubLineForLine(String _lineName, String subLineName){
		List<WebElement> sublines = getSubLinesForLine(_lineName);
		for(WebElement el : sublines){
			try{
				if(el.findElement(lineName).getText().equals(subLineName)){
					el.findElement(addLineBtn).click();
					WebdriverUtils.sleep(300);
					WebdriverUtils.waitForBudgetaBusyBar(driver);
					WebdriverUtils.waitForBudgetaLoadBar(driver);
					return;
				}
			}
			catch(Exception e){}
		}
	}
	
	public void addSubLineForSubLine(String lineTitle, String subLineTitle, String subLineToAdd){
		List<WebElement> subLines = getSubLinesForSubLine(lineTitle, subLineTitle);
		for(WebElement el : subLines){
			try{
				if(getLineName(el).equals(subLineToAdd)){
					WebElementUtils.hoverOverField(el, driver, null);
					WebdriverUtils.sleep(300);
					WebElementUtils.hoverOverField(el.findElement(addLineBtn), driver, null);
					el.findElement(addLineBtn).click();
					WebdriverUtils.sleep(600);
					WebdriverUtils.waitForBudgetaBusyBar(driver);
					WebdriverUtils.waitForBudgetaLoadBar(driver);
					return;
				}
			}
			catch(Exception e){}
		}
	}
	
	public void openAddChild(String lineTitle, int level){
		clickClose();
		List<WebElement> allLinesInLevel = driver.findElements(By.cssSelector("ol.tree.nav")).get(1).findElement(By.className("selected-root")).findElement(By.tagName("ol")).findElements(line);
		for(WebElement el : allLinesInLevel){
			if(getLineName(el).equals(lineTitle) && el.getAttribute("data-level").equals(level+"")){
				WebElementUtils.hoverOverField(el, driver, null);
				WebdriverUtils.sleep(300);
				WebElementUtils.hoverOverField(el.findElement(addLinesBtn), driver, null);
				el.findElement(addLinesBtn).click();
				WebdriverUtils.sleep(600);
				WebdriverUtils.waitForBudgetaBusyBar(driver);
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				return;
			}
		} 
	}
	
	public String getSelectedLine(){
		return getLineName(selectedLine);
	}
	
	public String getSelectedBudgetName(){
		return getLineName(getSelectedBudget());
	}
	
	public int getNumberOfVersionChanges(){
		return versionChanges.size();
	}
/*************************************************************************************************************/
/*************************************************************************************************************/
	
	private WebElement getLineByName(String name){
		List<WebElement> lines = getLines();
		for(WebElement el : lines){
			if(getLineName(el).contains(name))//if(getLineName(el).replaceAll("\\d","").trim().equals(name))
				return el;
		}
		return null;
	}
	
	private String getLineName(WebElement el){
		if(el.getAttribute("class").contains("new-line"))
			return el.findElement(lineName).getText();
		
		if(el.findElement(budgetName).getText().indexOf("\n") == -1)
			return el.findElement(budgetName).getText().trim();
		return el.findElement(budgetName).getText().substring(0, el.findElement(budgetName).getText().indexOf("\n")).trim();
			
	}
	
	private boolean isBudgetDropDownOptionsOpen(){
		try{
			return driver.findElement(By.className("qtip-focus")).isDisplayed();
		}
		catch(Exception e){
			return false;
		}
	}
	
	private void openBudgetsList(){
		if(!WebdriverUtils.isDisplayed(budgetsListWrapper)){
			showBudgetsBtn.click();
			wait.until(ExpectedConditions.visibilityOf(budgetsListWrapper));
			WebdriverUtils.sleep(300);
		}
	}
	
	private WebElement getSelectedBudget(){
		return selectedBudget.get(1).findElement(By.className("selected-root")).findElements(By.className("actions-toggle")).get(0);
	}
	
	private List<WebElement> getLines(){
	List<WebElement> list = new ArrayList<WebElement>();
	try{
		for(WebElement el : driver.findElements(By.cssSelector("ol.tree.nav")).get(1).findElement(By.className("selected-root")).findElement(By.tagName("ol")).findElements(line)){
			if(el.getAttribute("data-level").equals("1"))
				list.add(el);
		}
	}
	catch(Exception e){}
	return list;
	}
	
	private WebElement getNextLineToAdd(){
		List<WebElement> lines = getLines();
		for(WebElement el : lines){
			if(el.getAttribute("data-level").equals("1") && el.getAttribute("class").contains("new-line")){
				if(el.findElement(lineName).getText().contains("Model") && isLineExist("Model"))
					return null;
				try{
					if(el.findElement(addLineBtn).getAttribute("class").contains("enable"))
						return el;
				}
				catch(Exception e){
					continue;
				}
			}
		}
		return null;
	}
	
	private List<WebElement> getSubLinesForLine(String lineTitle){
		WebElement lineElm = getLineByName(lineTitle);
		if(lineElm.getAttribute("class").contains("collapsed")){
			lineElm.findElement(By.tagName("i")).click();
			WebdriverUtils.elementToHaveClass(lineElm, "expanded");
			WebdriverUtils.sleep(200);
		}
		return lineElm.findElements(line);
	}
	
	private List<WebElement> getSubLinesForSubLine(String lineTitle, String subLineTitle){
		List<WebElement> sublines = getSubLinesForLine(lineTitle);
		for(WebElement el : sublines){
			if(getLineName(el).equals(subLineTitle)){
				if(el.getAttribute("class").contains("collapsed")){
					el.findElement(By.tagName("i")).click();
					WebdriverUtils.elementToHaveClass(el, "expanded");
					WebdriverUtils.sleep(200);
				}
				return el.findElements(line);
			}
		}
		return null;
	}
	
	private List<WebElement> getSubLinesFourthLevel(String lineTitle, String subLineTitle, String sub_subLine){
		List<WebElement> sublines = getSubLinesForSubLine(lineTitle,subLineTitle);
		for(WebElement el : sublines){
			if(getLineName(el).equals(sub_subLine)){
				if(el.getAttribute("class").contains("collapsed")){
					el.findElement(By.tagName("i")).click();
					WebdriverUtils.elementToHaveClass(el, "expanded");
					WebdriverUtils.sleep(200);
				}
				return el.findElements(line);
			}
		}
		return null;
	}

	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}
;
}
