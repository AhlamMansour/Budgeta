package com.budgeta.test.PlansAndPricing;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.LicenseScreen;
import com.budgeta.pom.PlansAndPricingWindow;
import com.budgeta.pom.SmallPopup;
import com.budgeta.pom.TopBar;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestNGListener;

@Listeners({ MethodListener.class, TestNGListener.class })
public class UpgradeAndDowngradePlanTest extends WrapperTest {

	
	
	@Test(enabled = true)
	public void upgradePlantoAdvanced() {
		TopBar topBar = new TopBar();
		LicenseScreen licenseScreen = new LicenseScreen();
		if (!licenseScreen.isDisplayed()){
			topBar.clickMyLicense();
		}
		
		licenseScreen.clickUpdate();
		PlansAndPricingWindow plans = new PlansAndPricingWindow();
		System.out.println("Your Plan name is: " + plans.getCurrentPlanName());

		plans.upgradeFromBasic("ADVANCED", "UPGRADE");

		SmallPopup popup = new SmallPopup();
		Assert.assertTrue(popup.isDisplayed(), "Advanced plan code pop up is displayed");

		popup.setName("beadvanced");
		popup.clickConfirm(false);
		popup = new SmallPopup();
		Assert.assertTrue(popup.isDisplayed(), "Advanced plan code pop up is displayed");
		popup.clickConfirm();

		licenseScreen.clickUpdate();

		Assert.assertEquals(plans.getCurrentPlanName(), "ADVANCED");
		
		plans.closePriceAndPlansWin();

	}

	@Test(enabled = true)
	public void upgradeToBasic() {

		selectOptionToUpgrade("BASIC", "UPGRADE");
		

	}

	@Test(enabled = true)
	public void upgradeToProffesional() {

		selectOptionToUpgrade("PROFESSIONAL", "UPGRADE");
	

	}
	
	@Test(enabled = true)
	public void downgradeToBasic() {

		selectOptionToUpgrade("BASIC", "DOWNGRADE");
		

	}
	
	@Test(enabled = true)
	public void downgradeToProfessional() {

		selectOptionToUpgrade("PROFESSIONAL", "DOWNGRADE");
		

	}
	
	@Test(enabled = false)
	public void downgradeToStarter() {

		TopBar topBar = new TopBar();
		LicenseScreen licenseScreen = new LicenseScreen();
		if (!licenseScreen.isDisplayed()){
			topBar.clickMyLicense();
		}
		
		licenseScreen.clickUpdate();
		PlansAndPricingWindow plans = new PlansAndPricingWindow();
		System.out.println("Your Plan name is: " + plans.getCurrentPlanName());
		
		if (!plans.subscriptionEndMsg("STARTER")){
			plans.upgradeFromBasic("STARTER", "DOWNGRADE");

			SmallPopup popup = new SmallPopup();
			Assert.assertTrue(popup.isDisplayed(), "Advanced plan code pop up is displayed");
			popup.clickConfirm();

			licenseScreen.clickUpdate();

			Assert.assertTrue(plans.subscriptionEndMsg("STARTER"), " Starter");
			plans.closePriceAndPlansWin();
		}
		else 
			Assert.assertTrue(plans.subscriptionEndMsg("STARTER"), " Starter");
		
		plans.closePriceAndPlansWin();
		

	}

	private void selectOptionToUpgrade(String option, String action) {
		TopBar topBar = new TopBar();
		LicenseScreen licenseScreen = new LicenseScreen();
		if (!licenseScreen.isDisplayed()){
			topBar.clickMyLicense();
		}

		licenseScreen.clickUpdate();
		PlansAndPricingWindow plans = new PlansAndPricingWindow();
		System.out.println("Your Plan name is: " + plans.getCurrentPlanName());


		if (!plans.getCurrentPlanName().equals(option) && plans.getButtonNameOfCurrentPlanName(option).equals(action)) {
			plans.upgradeFromBasic(option, action);

			SmallPopup popup = new SmallPopup();
			Assert.assertTrue(popup.isDisplayed(), "Change plan confirmation is displayed");

			popup.clickConfirm();

			licenseScreen.clickUpdate();

			Assert.assertEquals(plans.getCurrentPlanName(), option);
		}
		else{
			if (plans.getCurrentPlanName().equals(option) && !plans.getButtonNameOfCurrentPlanName(option).equals(action)) {
				if (plans.getButtonNameOfCurrentPlanName(option).equals("YOUR PLAN")) {

			
					Assert.assertEquals(plans.getCurrentPlanName(), option, "Your Current palne is: " + option);
				}

				if (plans.getButtonNameOfCurrentPlanName(option).equals("DOWNGRADE")) {

					Assert.assertFalse(plans.getCurrentPlanName().equals(option), "Your Current palne is: " + option);

				}

				if (plans.getButtonNameOfCurrentPlanName(option).equals("UPGRADE")) {

					Assert.assertFalse(plans.getCurrentPlanName().equals(option), "Your Current palne is: " + option);

				}

			}
			
		}
		 
		plans.closePriceAndPlansWin();
	}

}
