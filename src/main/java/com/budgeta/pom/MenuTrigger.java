package com.budgeta.pom;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebElementUtils;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class MenuTrigger extends AbstractPOM {

	
	private WebElement wrapper;
	
	private SecondaryBoard secondaryBoard;
	protected BudgetaBoard board;
	
	private BudgetNavigator budgetNavigator;
	
	private By lineSettingTriggerMenu = By.cssSelector("div.qtip-focus ul.narrow li");
	private By subLineSettingTriggerMenu = By.cssSelector("div.qtip-pos-ti.qtip-focus ul li");
	
	private By budgetSettingTriggerMenu = By.cssSelector("div.qtip-focus ul.budgeta-dropdown-list li");
	private By triggerMenu = By.cssSelector("div.qtip-focus ul.budgeta-dropdown-list");
	
	@FindBy(className = "tree-edit-mode")
	protected WebElement editwrapper;
	
	@FindBy(className = "root-budget")
	private WebElement selectedBudget;
	
	@FindBy(className = "upload")
	private WebElement restore;
	
	
	
	public MenuTrigger(WebElement _wrapper){
		wrapper = _wrapper;
	}
	
	
	public void clickDuplicate(){
		selectScenarioTrigger("Duplicate");
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		
	}
	
	public void clickRename(){
		selectScenarioTrigger("Rename");
		
	}

	public void clickFlag(){
		
		selectScenarioTrigger("Flag");
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("flagged")));
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		
	}
	
	public void clickUnFlagBudgetLine(){
		selectScenarioTrigger("Unflag");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("flagged")));
	}
	
	
	
	public void clickCopy(){
		selectScenarioTrigger("Copy");
		WebdriverUtils.waitForElementToBeFound(driver, By.cssSelector("div.qtip-pos-ti.qtip-focus"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.qtip-pos-ti.qtip-focus")));
		selectCopyLocation("Other income and expenses");
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}
	
	public void clickRestoreBudget(String path){
		openTrigger();
		for(WebElement el : driver.findElements(budgetSettingTriggerMenu)){
			if(el.getText().equals("Restore")){
				el.findElement(By.tagName("input")).sendKeys(path);
				WebdriverUtils.sleep(2000);
				//WebdriverUtils.waitForBudgetaLoadBar(driver);
				//WebdriverUtils.waitForBudgetaBusyBar(driver);
				return;
			}
		}
	}
	
	public void clickDuplicateBudget(){
		
		selectDuplicateFromMenuTrigger("Duplicate");
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		WebdriverUtils.waitForBudgetaBusyBar(driver);
		WebdriverUtils.sleep(5000);
	}
	
	
	public void clickRenameBudget(){
		board = new BudgetaBoard();
		secondaryBoard = board.getSecondaryBoard();
		selectScenarioTrigger("Rename");
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}
	
	public void clickRenameBudgetFromNav(){
		selectBudgetMenuTrigger("Rename");
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}
	
	public void clickFlagBudget(){
		board = new BudgetaBoard();
		secondaryBoard = board.getSecondaryBoard();
		selectScenarioTrigger("Flag");
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}
	
	public void clickBackupBudget(){
		selectBudgetMenuTrigger("Backup");
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		WebdriverUtils.sleep(1000);
	}
	
	
	
	
	
	public CreateNewSnapshotPopup snapshotBudget(){
		board = new BudgetaBoard();
		secondaryBoard = board.getSecondaryBoard();
		selectScenarioTrigger("Snapshot");
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));
		return new CreateNewSnapshotPopup();

	}
	
	public SharePopup clickShareBudget(){
		board = new BudgetaBoard();
		secondaryBoard = board.getSecondaryBoard();

		selectScenarioTrigger("Share");
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));
		return new SharePopup();
	}
	
	public DeletePopup clickDeleteBudget(){
	
		selectBudgetMenuTrigger("Delete");
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
		WebdriverUtils.sleep(1000);
		return new DeletePopup();
	}
	
	public DeletePopup clickDeleteAllBudget(){
		BudgetNavigator navigator = new BudgetNavigator();
		int ExistBudget = navigator.getNumbreOfExistBudgets();
		System.out.println(ExistBudget);
		while (ExistBudget >5){
			selectBudgetMenuTrigger("Delete");
			WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
			WebdriverUtils.sleep(1000);
			DeletePopup popup = new DeletePopup();
			Assert.assertTrue(popup.isDisplayed(), "expected delete popup to be displayed");
			popup.clickConfirm();
			ExistBudget = ExistBudget - 1;
		}
		
		return new DeletePopup();
	}
	
	public SecondaryBoard clickMove(){
		selectScenarioTrigger("Move");
		WebdriverUtils.waitForElementToBeFound(driver, By.cssSelector("div.qtip-pos-ti.qtip-focus"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.qtip-pos-ti.qtip-focus")));
		selectCopyLocation("Other income and expenses");
		return new SecondaryBoard();
	}
	
	public SharePopup clickShare(){
		selectScenarioTrigger("Share");
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));
		return new SharePopup();
	}
	
	public DeletePopup clickDelete(){
		selectScenarioTrigger("Delete");
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));
		return new DeletePopup();
	}
	
	public SmallPopup clickRevert(){
		selectScenarioTrigger("Revert");
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));
		return new SmallPopup();
	}
	
	private void selectCopyLocation(String option){
		for(WebElement el : driver.findElements(subLineSettingTriggerMenu)){
			if(el.getText().equals(option)){
				el.click();
				WebdriverUtils.waitForBudgetaBusyBar(driver);
				WebdriverUtils.waitForBudgetaLoadBar(driver);
			}
		}
		
	}
	
	private void selectScenarioTrigger(String option){
		openTrigger();
		for(WebElement el : driver.findElements(lineSettingTriggerMenu)){
			if(el.getText().equals(option)){
				el.click();
				return;
			}
		}
	}
	
	public void selectBudgetMenuTrigger(String option){
	//BudgetNavigator navigator = new BudgetNavigator();
	//navigator.openMoreBudgetList();
		openTrigger();
		for(WebElement el : driver.findElements(budgetSettingTriggerMenu)){
			if(el.getText().equals(option)){
				try{
					el.click();
					return;
				}catch(WebDriverException e){
					WebDriverWait wait = new WebDriverWait(driver, 2);
			        wait.until(ExpectedConditions.alertIsPresent());
			        Alert alert = driver.switchTo().alert();
					alert.accept();
				}
				
			}
		}
	}

	public void selectDuplicateFromMenuTrigger(String option){
		BudgetNavigator navigator = new BudgetNavigator();
		navigator.openMoreBudgetList();
			//openTrigger();
			for(WebElement el : driver.findElements(budgetSettingTriggerMenu)){
				if(el.getText().equals(option)){
					el.click();
					return;
				}
			}
		}

	
	
	private void openTrigger(){
		WebElementUtils.hoverOverField(wrapper, driver, null);
		WebdriverUtils.sleep(1000);
		wait.until(ExpectedConditions.visibilityOf(wrapper));
		WebElementUtils.clickElementEvent(driver,wrapper);
		try{
			WebdriverUtils.waitForElementToBeFound(driver, By.className("qtip-focus"));
			wait.until(ExpectedConditions.visibilityOfElementLocated(triggerMenu));
		}
		catch(Exception e){
			WebElementUtils.clickElementEvent(driver,wrapper);
			WebdriverUtils.waitForElementToBeFound(driver, By.className("qtip-focus"));
			wait.until(ExpectedConditions.visibilityOfElementLocated(triggerMenu));
		}
	}

	
/***********************************************************************************/	
	public void selectOption(String option){
		openTrigger();
		for(WebElement el : driver.findElements(lineSettingTriggerMenu)){
			if(el.getText().equals(option)){
				el.click();
				return;
			}
		}
	}
	

	public boolean checkBudgetMenuTrigger(String option){
			boolean flag = true;
			openTrigger();
			for(WebElement el : driver.findElements(budgetSettingTriggerMenu)){
				if(el.getText().equals(option)){
					flag = true;
				}else{
					flag = false;
				}
				
			}
			return flag;
		}

	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}
	
}
