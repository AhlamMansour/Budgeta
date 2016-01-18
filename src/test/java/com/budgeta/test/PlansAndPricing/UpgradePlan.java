package com.budgeta.test.PlansAndPricing;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.budgeta.pom.LicenseScreen;
import com.budgeta.pom.PlansAndPricingWindow;
import com.budgeta.pom.SmallPopup;
import com.budgeta.pom.TopBar;
import com.budgeta.test.WrapperTest;

public class UpgradePlan extends WrapperTest{
	
	@Test(enabled = true)
	public void upgradePlan(){
		TopBar topBar = new TopBar();
		topBar.clickMyLicense();
		LicenseScreen licenseScreen = new LicenseScreen();
		licenseScreen.clickUpdate();
		PlansAndPricingWindow plans = new PlansAndPricingWindow();
		System.out.println("Your Plan name is: " + plans.getCurrentPlanName());
		
		plans.upgradeFromBasic("ADVANCED");
		
		SmallPopup popup = new SmallPopup();
		Assert.assertTrue(popup.isDisplayed(), "Advanced plan code pop up is displayed");
		
		popup.setName("beadvanced");
		popup.clickConfirm();
		
		Assert.assertTrue(popup.isDisplayed(), "Advanced plan code pop up is displayed");
		popup.clickConfirm();
		
		licenseScreen.clickUpdate();
		
		Assert.assertEquals(plans.getCurrentPlanName(), "ADVANCED");
		
	}
	
	
	
	public void upgradeBasictoProffesional(){
		
		
		
		
	}

}
