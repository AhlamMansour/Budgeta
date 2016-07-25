package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class AddNotePopup extends SmallPopup{
	
	@FindBy(className = "modal-body")
	private WebElement wrapper;
	
	private By textField = By.className("notes-text");
	
	public void setText(String text){
		wrapper.findElement(textField).clear();
		wrapper.findElement(textField).sendKeys(text);
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}
	
	public String getNoteText(){
		return wrapper.findElement(By.tagName("textarea")).getAttribute("value");
	}
	

}
