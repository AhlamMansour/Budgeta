package com.budgeta.test.PlansAndPricing;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.budgeta.pom.BillingPage;
import com.budgeta.pom.LicenseScreen;
import com.budgeta.pom.PlansAndPricingWindow;
import com.budgeta.pom.SmallPopup;
import com.budgeta.pom.TopBar;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestNGListener;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

@Listeners({ MethodListener.class, TestNGListener.class })
public class UpgradeAndDowngradePlanTest extends WrapperTest {

	@Test(enabled = true)
	public void upgradePlantoEnterprise() {
		TopBar topBar = new TopBar();
		LicenseScreen licenseScreen = new LicenseScreen();
		if (!licenseScreen.isDisplayed()) {
			topBar.clickMyLicense();
		}

		licenseScreen.clickUpdate();
		PlansAndPricingWindow plans = new PlansAndPricingWindow();
		System.out.println("Your Plan name is: " + plans.getCurrentPlanName());

		plans.upgradeFromBasic("ENTERPRISE", "UPGRADE");

		SmallPopup popup = new SmallPopup();
		Assert.assertTrue(popup.isDisplayed(), "Enterpise plan code pop up is displayed");

		Assert.assertTrue(popup.isDisplayed(), "Advanced plan code pop up is displayed");
		popup.clickConfirm();

		licenseScreen.clickUpdate();

		Assert.assertEquals(plans.getCurrentPlanName(), "ENTERPRISE");

		plans.closePriceAndPlansWin();

	}

	@Test(enabled = false)
	public void upgradeToBasic() {

		PlansAndPricingWindow plans = new PlansAndPricingWindow();
		if(plans.isDisplayed()){
			plans.closePriceAndPlansWin();
		}
		selectOptionToUpgrade("BASIC", "UPGRADE");
	

	}

	@Test(enabled = true)
	public void upgradeToPremium() {

		PlansAndPricingWindow plans = new PlansAndPricingWindow();
		if(plans.isDisplayed()){
			plans.closePriceAndPlansWin();
		}
		selectOptionToUpgrade("PREMIUM", "UPGRADE");
	
	}

	@Test(enabled = false)
	public void downgradeToBasic() {

		PlansAndPricingWindow plans = new PlansAndPricingWindow();
		if(plans.isDisplayed()){
			plans.closePriceAndPlansWin();
		}
		selectOptionToUpgrade("BASIC", "DOWNGRADE");

	}

	@Test(enabled = true)
	public void downgradeToPremium() {

		PlansAndPricingWindow plans = new PlansAndPricingWindow();
		if(plans.isDisplayed()){
			plans.closePriceAndPlansWin();
		}
		selectOptionToUpgrade("PREMIUM", "DOWNGRADE");
		
		
	}

	@Test(enabled = true)
	public void downgradeToEssential() {

		TopBar topBar = new TopBar();
		LicenseScreen licenseScreen = new LicenseScreen();
		if (!licenseScreen.isDisplayed()) {
			topBar.clickMyLicense();
		}

		licenseScreen.clickUpdate();
		PlansAndPricingWindow plans = new PlansAndPricingWindow();
		System.out.println("Your Plan name is: " + plans.getCurrentPlanName());

		if (!plans.subscriptionEndMsg("ESSENTIAL")) {
			plans.upgradeFromBasic("ESSENTIAL", "DOWNGRADE");

			SmallPopup popup = new SmallPopup();
			Assert.assertTrue(popup.isDisplayed(), "Essential plan code pop up is displayed");
			popup.clickConfirm();

			licenseScreen.clickUpdate();

			Assert.assertTrue(plans.subscriptionEndMsg("ESSENTIAL"), " Essential");
			plans.closePriceAndPlansWin();
		} else
			Assert.assertTrue(plans.subscriptionEndMsg("ESSENTIAL"), " Essential");
	
		
		if(plans.isDisplayed()){
			plans.closePriceAndPlansWin();
		}
		

	}

	private void selectOptionToUpgrade(String option, String action) {
		TopBar topBar = new TopBar();
		LicenseScreen licenseScreen = new LicenseScreen();
		if (!licenseScreen.isDisplayed()) {
			topBar.clickMyLicense();
		}

		licenseScreen.clickUpdate();
		PlansAndPricingWindow plans = new PlansAndPricingWindow();
		System.out.println("Your Plan name is: " + plans.getCurrentPlanName());

		if (plans.getCurrentPlanName().equals("ESSENTIAL")) {
			
			upgradeFromEssentialPlan(option,action);
			
			topBar.clickMyLicense();
			licenseScreen.clickUpdate();
			
			Assert.assertEquals(plans.getCurrentPlanName(), option);
			
			plans.closePriceAndPlansWin();
			

		} else {
			if (!plans.getCurrentPlanName().equals(option) && plans.getButtonNameOfCurrentPlanName(option).equals(action)) {

				plans.upgradeFromBasic(option, action);

				SmallPopup popup = new SmallPopup();
				Assert.assertTrue(popup.isDisplayed(), "Change plan confirmation is displayed");

				popup.clickConfirm();

				licenseScreen.clickUpdate();

				Assert.assertEquals(plans.getCurrentPlanName(), option);
			} else {
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
	
	
	public void upgradeFromEssentialPlan(String option, String action){
		PlansAndPricingWindow plans = new PlansAndPricingWindow();
		plans.upgradeFromBasic(option, action);
		WebdriverUtils.sleep(1000);
		
		BillingPage billing = new BillingPage();
		
		Assert.assertTrue(billing.isDisplayed(), "Billing Information page is open");
		
		billing.enterBillingInformation();
		billing.setStreetAdress("Isreal");
		billing.setSecondAddress("Tel");
		billing.setCity("Tel");
		billing.setState("14789");
		billing.setPostal("256");
		billing.clickAccept();
		billing.clickSubscribe();
		billing.clickReturnToBudgeta();
		
	}

}
