package main.java.budgeta.test;



import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import web.infra.GalilDriver;

import com.base.infra.ddt.DDTBase;
import com.ddt.infra.excel.ExcelConnection;

public class Test extends DDTBase {

	String path = "C:\\git\\Budgeta-Core-Test\\src\\main\\java\\budgeta\\test\\BudgetaDDT.xlsx";
	String path1 = "C:\\git\\Budgeta-Core-Test\\src\\main\\java\\budgeta\\test\\UserDetails.xlsx";
	String url =  "http://dev.budgeta.com/";
	//String url =  "http://dev.budgeta.com/login?action=signup";
	
	@Override
	public void ddtSetup() throws Exception {
		GalilDriver.getWebDriver().get(url);
	}
	
	@Override
	public void setConnection() {
		//ddtCon = new ExcelConnection(path, "Sheet1", 2);
		ddtCon = new ExcelConnection(path, "Sheet1", 2);
	
	}
	
	@Override
	public void configureTest() throws Exception {
		
		
		
		
		//LoginTest1();
		//SignupTest();
		
		
		
		//String username = tcList.get(5);
		//String password = tcList.get(6);
		//String BudgetaCode = tcList.get(7);
		//String firstname = tcList.get(8);
		//String lastname = tcList.get(9);
		/*
		//WebElement SignupLinkWebElement = GalilDriver.getWebDriver().findElement(By.xpath("//*[@id='inviteCode']"));
		//WebElement budgetacodeWebElement = GalilDriver.getWebDriver().findElement(By.id("inviteCode"));
		WebElement budgetacodeWebElement = GalilDriver.getWebDriver().findElement(By.id("inviteCode"));
		WebElement firstnameWebElement = GalilDriver.getWebDriver().findElement(By.id("firstName"));
		WebElement lastnameWebElement = GalilDriver.getWebDriver().findElement(By.id("lastName"));
		WebElement emailWebElement = GalilDriver.getWebDriver().findElement(By.id("email"));
		WebElement passwordWebElement = GalilDriver.getWebDriver().findElement(By.id("password"));
		WebElement passwordVerifyWebElement = GalilDriver.getWebDriver().findElement(By.id("passwordVerify"));
		WebElement SignupWebElement = GalilDriver.getWebDriver().findElement(By.id("signup-btn"));
		
		
		
		//SignupLinkWebElement.click();
		budgetacodeWebElement.sendKeys("trybudgeta");
		firstnameWebElement.sendKeys("saleh");
		lastnameWebElement.sendKeys("kopty");
		emailWebElement.sendKeys("saleh.kopty@galilsoftware.com");
		passwordWebElement.sendKeys("saleh123");
		passwordVerifyWebElement.sendKeys("saleh123");
		SignupWebElement.click();
		
		
		//if (true==false)
		//	fail("Rabea");
		 
		 */
		LoginTest1();
		
	}
	
	@Override
	public void SignupTest() throws Exception{
		
		/*
		String username = tcList.get(5);
		String password = tcList.get(6);
		String BudgetaCode = tcList.get(7);
		String firstname = tcList.get(8);
		String lastname = tcList.get(9);
		
		WebElement SignupLinkWebElement = GalilDriver.getWebDriver().findElement(By.id("inviteCode"));
		WebElement budgetacodeWebElement = GalilDriver.getWebDriver().findElement(By.id("inviteCode"));
		WebElement firstnameWebElement = GalilDriver.getWebDriver().findElement(By.id("firstName"));
		WebElement lastnameWebElement = GalilDriver.getWebDriver().findElement(By.id("lastName"));
		WebElement emailWebElement = GalilDriver.getWebDriver().findElement(By.id("email"));
		WebElement passwordWebElement = GalilDriver.getWebDriver().findElement(By.id("password"));
		WebElement passwordVerifyWebElement = GalilDriver.getWebDriver().findElement(By.id("passwordVerify"));
		WebElement SignupWebElement = GalilDriver.getWebDriver().findElement(By.id("//*[@id='ember580']/div[1]/div[1]/form/div[9]/button"));
		
		
		
		SignupLinkWebElement.click();
		budgetacodeWebElement.sendKeys(BudgetaCode);
		firstnameWebElement.sendKeys(firstname);
		lastnameWebElement.sendKeys(lastname);
		emailWebElement.sendKeys(username);
		passwordWebElement.sendKeys(password);
		passwordVerifyWebElement.sendKeys(password);
		SignupWebElement.click();
		
		*/
		
	}
	
	
	@Override
	public void LoginTest1() throws Exception{
		String username = tcList.get(5);
		String password = tcList.get(6);
		//String BudgetaCode = tcList.get(7);
	//	String firstname = tcList.get(8);
		//String lastname = tcList.get(9);
		
		WebElement usernameWebElement = GalilDriver.getWebDriver().findElement(By.id("identification"));
		//WebElement passwordWebElement = GalilDriver.getWebDriver().findElement(By.className("ember-text-field"));
		
		WebElement passwordWebElement = GalilDriver.getWebDriver().findElement(By.xpath("//*[@id='ember604']"));
		
		WebElement Login = GalilDriver.getWebDriver().findElement(By.xpath("//*[@id='ember580']/div[1]/div[1]/form/div[5]/button"));
		
		
		
		usernameWebElement.sendKeys(username);
		passwordWebElement.sendKeys(password);
		Login.click();
	}
	@Override
	public void validateTest() throws Exception {	
	}
	@Override
	public void removeConfigurationTest() throws Exception {
	}
	
	@Override
	public void ddtTeardown() throws Exception {
		System.out.println("Rabea");
	}
	
	

}