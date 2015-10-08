package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class InnerBar extends AbstractPOM{
	
	@FindBy(className = "inner-bar")
	private WebElement wrapper;

	@FindBy(id = "dashboard-tab-header")
	private WebElement dashBoard;
	
	@FindBy(id = "input-tab-header")
	private WebElement input;
	
	@FindBy(id = "view-tab-header")
	private WebElement view;
	
	@FindBy(id = "actuals-tab-header")
	private WebElement actuals;
	
	
	public void openDashboardTab(){
		clickOnTab(dashBoard);
	}
	
	public void openInputTab(){
		clickOnTab(input);
	}
	
	public void openViewTab(){
		clickOnTab(view);
	}
	
	public void openActualsTab(){
		clickOnTab(actuals);
	}
	
	public String getOpenTab(){
		return wrapper.findElement(By.className("active")).getText();
	}
	
	private void clickOnTab(WebElement el){
		if(el.getAttribute("class").contains("active"))
			return;
		el.click();
		WebdriverUtils.elementToHaveClass(el, "active");
		WebdriverUtils.waitForBudgetaBusyBar(driver);
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
