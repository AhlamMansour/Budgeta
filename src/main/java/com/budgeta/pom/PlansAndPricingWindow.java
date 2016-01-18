package com.budgeta.pom;

import java.awt.Container;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

	
	public void upgradePlan(String option){
		if (getCurrentPlanName() != option){
			if(getCurrentPlanName() == "Starter")
			{
				selectPlan(option);
			}
			
		}
	}
	
	
	
	
	public void upgradeFromBasic(String option){
		if (getCurrentPlanName().equals("BASIC")){
			selectPlan(option);
			
		}
	}
	
	
	
	
	
	
	
	
	
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
		
	}
	
	
}
