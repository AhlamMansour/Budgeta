package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class BudgetaBoard extends AbstractPOM {

	@FindBy(className = "main-content")
	private WebElement wrapper;

	@FindBy(className = "bottom-bar")
	private WebElement bottomBar;

	@FindBy(className = "center")
	private WebElement centerBar;

	private final By noty_message = By.className("noty_text");

	private GeneralSection generalSection;
	private CommentsSection commentsSection;
	private PreviewBoard preview;
	private EmployeeAssumptions employeeAssumption;
	private BillingsSection billings;
	private InnerBar innerBar;

	public BudgetaBoard() {
		WebdriverUtils.waitForVisibilityofElement(driver, wrapper, 60);
	}

	public SecondaryBoard getSecondaryBoard() {
		// WebdriverUtils.waitForBudgetaBusyBar(driver);
		// WebdriverUtils.waitForBudgetaLoadBar(driver);
		WebdriverUtils.sleep(1000);
		return new SecondaryBoard();
	}

	public boolean isBottomBarDisplayed() {
		return bottomBar.getAttribute("class").contains("changed");
	}

	public String getNotyMessage() {
		//WebdriverUtils.waitForBudgetaBusyBar(driver);
		String notyMessage = "no_message";
		try {
			//WebdriverUtils.waitForBudgetaBusyBar(driver);
			WebdriverUtils.waitForElementToBeFound(driver, noty_message);
			notyMessage = driver.findElement(noty_message).getText().trim();
			WebdriverUtils.waitForElementToDisappear(driver, noty_message);

			 WebdriverUtils.waitForBudgetaLoadBar(driver);
		} catch (Exception e) {
			 WebdriverUtils.waitForElementToBeFound(driver, noty_message);
			try{
				notyMessage = driver.findElement(noty_message).getText().trim();
				WebdriverUtils.waitForElementToDisappear(driver, noty_message);
			}catch (NoSuchElementException em){
				
			}
			
		}
		return notyMessage;
	}

	public void clickSaveChanges() {
		String saveMessage = "";
		for (WebElement el : bottomBar.findElements(By.tagName("button"))) {
			if (el.getAttribute("type").equalsIgnoreCase("submit")) {
				el.click();
				try {
					WebdriverUtils.waitForElementToBeFound(driver, noty_message);
					saveMessage = driver.findElement(noty_message).getText();
					WebdriverUtils.waitForElementToDisappear(driver, noty_message);
					WebdriverUtils.waitForBudgetaBusyBar(driver);
					// WebdriverUtils.waitForBudgetaLoadBar(driver);
					WebdriverUtils.sleep(1000);
					break;
				} catch (Exception e) {
				}
			}
		}
		WebdriverUtils.waitForBudgetaBusyBar(driver);
		// WebdriverUtils.waitForBudgetaLoadBar(driver);
		WebdriverUtils.sleep(1000);
		Assert.assertFalse(saveMessage.contains("unexpected error"), "BUG Happedned , unexpected error found");
	}

	public void clickUndoChanges() {
		bottomBar.findElement(By.className("cancel")).click();
		WebdriverUtils.waitForElementToDisappear(driver, By.className("changed"));
	}

	public GeneralSection clickGeneralSection(String section) {
		centerBar.findElement(By.id("section-General")).click();
		return getGeneralSection();
	}

	public GeneralSection getGeneralSection() {
		if (generalSection == null)
			generalSection = new GeneralSection();
		return generalSection;
	}

	public GeneralSection clickCommentsSection(String section) {
		centerBar.findElement(By.id("section-Comments")).click();
		return getGeneralSection();
	}

	public CommentsSection getCommentsSection() {
		if (commentsSection == null)
			commentsSection = new CommentsSection();
		return commentsSection;
	}

	public PreviewBoard getPreviewBoard() {
		preview = new PreviewBoard();
		return preview;
	}

	public BillingsSection getBillingsSection() {
		billings = new BillingsSection();
		return billings;
	}

	public EmployeeAssumptions getEmployeeAssumptions() {
		employeeAssumption = new EmployeeAssumptions();
		return employeeAssumption;
	}

	public BudgetNavigator getBudgetNavigator() {
		return new BudgetNavigator();
	}

	public InnerBar getInnerBar() {
		innerBar = new InnerBar();
		return innerBar;
	}

	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
