package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class TestModal extends AbstractPOM {

	@FindBy(className = "modal-content")
	private WebElement wrapper;

	@FindBy(css = ".modal-content .tab-content")
	private WebElement tabContent;

	@FindBy(css = ".modal-content .scrollable li")
	private List<WebElement> errorsList;

	@FindBy(xpath = "//*[contains(text(),'Run test')]")
	private WebElement runTestBtn;

	@FindBy(xpath = "//*[contains(text(),'Test completed')]")
	private WebElement testCompleted;

	public TestModal() {
		WebdriverUtils.waitForBudgetaBusyBar(driver);
		WebdriverUtils.waitForBudgetaLoadBar(driver);
		WebdriverUtils.waitForVisibilityofElement(driver, wrapper, 600);
	}

	public void clickOnRunTest(){
		runTestBtn.click();
		WebdriverUtils.waitForVisibilityofElement(driver, testCompleted, 600);
		
	}

	public boolean checkErrorsResult() {
		int errorNum = Integer.parseInt(testCompleted.getText().replaceAll("[^0-9]+", ""));
		if (errorsList.size() == errorNum)
			return true;
		return false;

	}

	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
