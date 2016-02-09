package com.budgeta.pom;

import java.awt.Container;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.FindElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class PlansAndPricingWindow extends AbstractPOM {

	
	
	@FindBy(className = "modal-content")
	private WebElement wrapper;
	
	@FindBy(className = "btn")
	private List<WebElement> btn;
	
	private By DropdownUser = By.className("select2-chosen");
	
	@FindBy(className = "current-plan")
	private WebElement currentPlan;
	
	@FindBy(className = "plan-box")
	private List<WebElement> planBox;
	
	@FindBy(className = "subscription-end-msg")
	private WebElement endMsg;
	
	@FindBy(className = "svg-icon")
	private WebElement close;
	
	private By editUsers = By.cssSelector("div.select2-drop-active ul.select2-results li");
	
	public void openEditUsers(){
		
	}
	
	
	public void clickOnUpgrade(){
		
		
		
	}
	
	
	public void clickOnDowngrade(){
		
	}
	
	
	public String getCurrentPlanName(){
		String yourPlan = currentPlan.findElement(By.xpath("../../..")).getText().replaceAll("\\d", "").trim();
		if (yourPlan.contains("\n"))
			yourPlan = yourPlan.substring(0, yourPlan.indexOf("\n"));
		
		return yourPlan;
	}
	
	public String getCurrentEditingUser(String option){
		for (WebElement el : planBox){
			String planName = el.findElement(By.tagName("h2")).getText().replaceAll("\\d", "").trim();
			if (planName.contains("\n"))
				planName = planName.substring(0, planName.indexOf("\n"));
			
			if(planName.equals(option)){
				String editUsers = el.findElement(By.className("select2-chosen")).getText();
				return editUsers;
			}
	
			continue;

		}
		return null;
	}
	
	public String getButtonNameOfCurrentPlanName(String option){
		for (WebElement el : planBox){
			String planName = el.findElement(By.tagName("h2")).getText().replaceAll("\\d", "").trim();
			if (planName.contains("\n"))
				planName = planName.substring(0, planName.indexOf("\n"));
			String buttonName = el.findElement(By.className("btn")).getText();
			if(planName.equals(option)){
				return buttonName;
			}
	
			continue;

		}
		return null;
	}
	
	
	

	
	
	public void selectPlan(String option){
		
		for (WebElement el : btn){
			if(el.getText().equals("UPGRADE")){
				String yourPlan = el.findElement(By.xpath("../../..")).getText().replaceAll("\\d", "").trim();
				if (yourPlan.contains("\n"))
					yourPlan = yourPlan.substring(0, yourPlan.indexOf("\n"));
				
				if (yourPlan.equals(option))
				{
					el.click();
					break;
				}
				continue;
			}

		}

	}
	
	
	public void selectPlan2(String option, String action){
		
		for (WebElement el : planBox){
			String planName = el.findElement(By.tagName("h2")).getText().replaceAll("\\d", "").trim();
			if (planName.contains("\n"))
				planName = planName.substring(0, planName.indexOf("\n"));
			String buttonName = el.findElement(By.className("btn")).getText();
			if(planName.equals(option) && buttonName.equals(action)){
				el.findElement(By.className("btn")).click();
				break;
			}
			
			if (planName.equals(option) && !buttonName.equals(action)){
					
			}
			
			
			
			continue;

		}
	}

	
private void selectEditingUser(String option, String action){
		
		for (WebElement el : planBox){
			String planName = el.findElement(By.tagName("h2")).getText().replaceAll("\\d", "").trim();
			if (planName.contains("\n"))
				planName = planName.substring(0, planName.indexOf("\n"));
			if(planName.equals(option) ){			
				String users = el.findElement(By.className("select2-chosen")).getText();
				if(users.equals(action)){
					closePriceAndPlansWin();
					break;
				}
				el.findElement(By.className("select2-chosen")).click();
				for(WebElement edit : driver.findElements(editUsers)){
					if(edit.getText().equals(action)){
						edit.click();
						el.findElement(By.className("btn")).click();
						SmallPopup popup = new SmallPopup();
						popup.clickConfirm();
						
						return;
					}
				
				}
			}
			
			
			
			
			continue;

		}
	}

public void changeEditingUser(String option, String action){
	selectEditingUser(option,action);
	
	
	
}
	
	public void upgradePlan(String option){
		if (getCurrentPlanName() != option){
			if(getCurrentPlanName() == "Starter")
			{
				selectPlan(option);
			}
			
		}
	}
	
	
	
	
	public void upgradeFromBasic(String option, String action){
//		if (getCurrentPlanName().equals("BASIC")){
//			
//			
//		}
		selectPlan2(option,action);
	}
	
	public void selectEditUsers(String option, String action){

		selectEditingUser(option,action);
	}
	
	
	
	
	public boolean subscriptionEndMsg(String option){
		for (WebElement el : planBox){
			String planName = el.findElement(By.tagName("h2")).getText().replaceAll("\\d", "").trim();
			if (planName.contains("\n"))
				planName = planName.substring(0, planName.indexOf("\n"));
			if (planName.equals(option)){
				try{
					if(endMsg.isDisplayed())
						return true;
					return false;
				}
				catch(NoSuchElementException e){
					return false;
				}
			}
		}
		return false;
	}
	
	
	public void closePriceAndPlansWin(){
		close.click();
		WebdriverUtils.waitForElementToDisappear(driver, By.className("modal-content"));
	}
	
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
		
	}
	
	
}
