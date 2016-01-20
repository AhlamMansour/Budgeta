package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class BillingPage extends AbstractPOM{
	
	@FindBy(id = "content")
	private WebElement wrapper;
	
	@FindBy(id = "billing_info_address1")
	private WebElement streetAdress;
	
	@FindBy(id = "billing_info_address2")
	private WebElement secondAdress;
	
	@FindBy(id = "billing_info_city")
	private WebElement cityName;
	
	@FindBy(id = "billing_info_state")
	private WebElement state;
	
	@FindBy(id = "billing_info_zip")
	private WebElement postal;
	
	@FindBy(id = "accept")
	private WebElement accept;
	
	@FindBy(className = "pay")
	private WebElement subscribeBtn;
	
	@FindBy(id = "card-test-numbers")
	private WebElement cardNumber;
	
	@FindBy(className = "subscribe-button")
	private WebElement returnToBudgetaBtn;
	
	
	
	public void enterBillingInformation(){
		int i=-1;
		for(WebElement box : cardNumber.findElements(By.tagName("dd"))){
			if(box.getText().equals("This will succeed")){
				i++;
				break;
			}
			i++;
		}
		if(i==-1)
			return;
		cardNumber.findElements(By.tagName("dt")).get(i).click();
		
	}
	
	public void setStreetAdress(String Address){
		streetAdress.sendKeys(Address);		
	}
	
	public void setSecondAddress(String Address){
		secondAdress.sendKeys(Address);
	}
	
	public void setCity(String city){
		cityName.sendKeys(city);
	}
	
	public void setState(String state){
		this.state.sendKeys(state);
	}
	
	public void setPostal(String postal){
		this.postal.sendKeys(postal);
	}
	
	
	public void getStreetAdress(String Address){
		streetAdress.sendKeys("value");		
	}
	
	public void getSecondAddress(String Address){
		secondAdress.sendKeys("value");
	}
	
	public void getCity(String city){
		cityName.sendKeys("value");
	}
	
	public void getState(String state){
		this.state.sendKeys("value");
	}
	
	public void getPostal(String postal){
		this.postal.sendKeys("value");
	}
	
	
	public void clickAccept(){
		accept.click();
	}
	
	
	public void clickSubscribe(){
		subscribeBtn.click();
		WebdriverUtils.waitForElementToBeFound(driver, By.className("subscribe-button"));
	}
	
	
	public void clickReturnToBudgeta(){
		returnToBudgetaBtn.click();
		WebdriverUtils.waitForElementToBeFound(driver, By.className("budget-navigator-wrapper"));
	}
	
	
	
	
	
	
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
