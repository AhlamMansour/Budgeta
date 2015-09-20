package com.budgeta.test.BudgetLineSettings;

import javax.swing.plaf.PopupMenuUI;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.budgeta.pom.DeletePopup;
import com.budgeta.pom.MenuTrigger;
import com.budgeta.pom.SecondaryBoard;
import com.budgeta.pom.SharePopup;
import com.budgeta.pom.SignUpSuccessPage;
import com.budgeta.pom.SmallPopup;
import com.budgeta.pom.SuccessPage;
import com.budgeta.test.WrapperTest;
import com.galilsoftware.AF.core.listeners.TestFirst;
import com.thoughtworks.selenium.Selenium;

import org.openqa.selenium.Keys;

public class BudgetLineSettingsTest extends WrapperTest {
	
	
	MenuTrigger menuTrigger;
	String email = "ahlam_mns@hotmail.com";
	SuccessPage successPage;
	SmallPopup smallPopup;
	String budgetLineName = "New Name";
	
	
	
	@TestFirst
	@Test(enabled = true)
	public void CreateSettingTest(){
		SecondaryBoard secondary = board.getSecondaryBoard();
		secondary.selectRandomBudgeta();
		secondary.addLine("Revenues");
		secondary = new SecondaryBoard();
		
		
		
	}
	
	
	@Test(enabled = true, priority=1)
	public void DuplicateBudgetLineTest() throws InterruptedException{
		Thread.sleep(1000);
		SecondaryBoard secondary = board.getSecondaryBoard();
		MenuTrigger trigger = secondary.getLineSettings("Revenues");
		trigger.clickDuplicate();
		
	}
	
	@Test(enabled = true, priority=2)
	public void FlagBudgetLineTest() throws InterruptedException{
		Thread.sleep(1000);
		SecondaryBoard secondary = board.getSecondaryBoard();
		MenuTrigger trigger = secondary.getLineSettings("Revenues");
		trigger.clickFlag();
		
	}
	
	
	
	
	
	@Test(enabled = true, priority=3)
	public void RenameBudgetLineTest() throws InterruptedException{
		Thread.sleep(1000);
		SecondaryBoard secondary = board.getSecondaryBoard();
		MenuTrigger trigger = secondary.getLineSettings("Revenues");
		trigger.clickRename();
		
		//smallPopup.setName(budgetLineName);
		
		
		
		
	}
	
	

	
	@Test(enabled = true, priority=4)
	public void ShareBudgetLineTest() throws InterruptedException{
		Thread.sleep(1000);
		SecondaryBoard secondary = board.getSecondaryBoard();
		MenuTrigger trigger = secondary.getLineSettings("Revenues");
		
		SharePopup popup = trigger.clickShare();
		Assert.assertTrue(popup.isDisplayed(), "expected delete popup to be displayed");
//		popup.sendEmail(email);
		popup.setName(email);
		popup.clickSend();
		
		Thread.sleep(1000);
		Assert.assertTrue(successPage.isDisplayed(), "Expected To Share Seccess page to be dispaly");
		successPage.clickConfirm();
	
		
		/*
		SignUpSuccessPage SuccessPage = new SignUpSuccessPage();
		Assert.assertTrue(SuccessPage.isDisplayed(), "Expected To Share Seccess page to be dispaly");
		SuccessPage.ConfirmSignUp();
		*/
		
		
	}
	

	@Test(enabled = true, priority=5)
	public void DeleteBudgetLineTest() throws InterruptedException{
		Thread.sleep(1000);
		SecondaryBoard secondary = board.getSecondaryBoard();
		MenuTrigger trigger = secondary.getLineSettings("Revenues");
		
		DeletePopup popup = trigger.clickDelete();
		Assert.assertTrue(popup.isDisplayed(), "expected delete popup to be displayed");
		popup.clickConfirm();
		
	}
	

}
