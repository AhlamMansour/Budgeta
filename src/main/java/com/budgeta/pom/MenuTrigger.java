package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebElementUtils;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class MenuTrigger extends AbstractPOM {

	
	private WebElement wrapper;
	
	private SecondaryBoard secondaryBoard;
	protected BudgetaBoard board;
	
	private By lineSettingTriggerMenu = By.cssSelector("div.qtip-focus ul.narrow li");
	private By subLineSettingTriggerMenu = By.cssSelector("div.qtip-pos-ti.qtip-focus ul li");
	
	@FindBy(className = "tree-edit-mode")
	protected WebElement editwrapper;
	
	
	
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
		openLineSettings();
		for(WebElement el : driver.findElements(lineSettingTriggerMenu)){
			if(el.getText().equals("Restore")){
				el.findElement(By.tagName("input")).sendKeys(path);
				WebdriverUtils.sleep(2000);
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				WebdriverUtils.waitForBudgetaBusyBar(driver);
				return;
			}
		}
	}
	
	public void clickDuplicateBudget(){
		board = new BudgetaBoard();
		secondaryBoard = board.getSecondaryBoard();
		secondaryBoard.openBudgetsList();
		selectScenarioTrigger("Duplicate");
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}
	
	
	public void clickRenameBudget(){
		board = new BudgetaBoard();
		secondaryBoard = board.getSecondaryBoard();
		secondaryBoard.openBudgetsList();
		selectScenarioTrigger("Rename");
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}
	
	public CreateNewSnapshotPopup snapshotBudget(){
		board = new BudgetaBoard();
		secondaryBoard = board.getSecondaryBoard();
		secondaryBoard.openBudgetsList();
		selectScenarioTrigger("Snapshot");
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));
		return new CreateNewSnapshotPopup();

	}
	
	public SharePopup clickShareBudget(){
		board = new BudgetaBoard();
		secondaryBoard = board.getSecondaryBoard();
		secondaryBoard.openBudgetsList();
		selectScenarioTrigger("Share");
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));
		return new SharePopup();
	}
	
	public DeletePopup clickDeleteBudget(){
		board = new BudgetaBoard();
		secondaryBoard = board.getSecondaryBoard();
		secondaryBoard.openBudgetsList();
		selectScenarioTrigger("Delete");
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));
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
		openLineSettings();
		for(WebElement el : driver.findElements(lineSettingTriggerMenu)){
			if(el.getText().equals(option)){
				el.click();
				return;
			}
		}
	}
	
	
	
	
	private void openLineSettings(){
		WebElementUtils.hoverOverField(wrapper, driver, null);
		WebdriverUtils.sleep(1000);
		wait.until(ExpectedConditions.visibilityOf(wrapper));
		WebElementUtils.clickElementEvent(driver,wrapper);
		try{
			WebdriverUtils.waitForElementToBeFound(driver, By.className("qtip-focus"));
			wait.until(ExpectedConditions.visibilityOfElementLocated(lineSettingTriggerMenu));
		}
		catch(Exception e){
			WebElementUtils.clickElementEvent(driver,wrapper);
			WebdriverUtils.waitForElementToBeFound(driver, By.className("qtip-focus"));
			wait.until(ExpectedConditions.visibilityOfElementLocated(lineSettingTriggerMenu));
		}
	}
	
	

	

	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}
	
}
