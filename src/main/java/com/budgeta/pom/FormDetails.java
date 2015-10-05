package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class FormDetails extends AbstractPOM {

	private WebElement dropdown;
	
	
	@FindBy(className = "inner-content")
	private WebElement wrapper;
	
	private By innerBar = By.cssSelector("div.ember-view h2.inner-bar");
	
	private By FormBar = By.cssSelector("div.subnav div.left");
	
	private By previewExpanded  = By.className("preview expanded");
	
	private By menuForm  = By.className("navbar-nav");
	
	private By budgetAttributesWrapper  = By.className("my-qtip-containe");
	
	
	private By DropdownOpend = By.className("select2-drop-mask");
	
	
	private By DropdownClosed = By.className("select2-container");
	
	private By selectedDropdown = By.className("select2-chosen");
	
	private By saveBtn  = By.className("");
	
	private By cancelBtn  = By.className("div.bottom-bar button.cancel");
	
	private By addComment  = By.className("");
	
	private By deleteComment  = By.className("div.comment-created div.svg-icon");
	
	private By CommentTextArea  = By.className("div.comment-text textarea.ember-text-area");
	
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}
}
