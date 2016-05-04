package com.budgeta.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebElementUtils;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class CommentsSection extends AbstractPOM{
	
	
	@FindBy(className = "section-Comments")
	private WebElement wrapper;
	
	
	@FindBy(className = "add")
	private WebElement addComment;
	
	@FindBy(css = "div.comment-created div.svg-icon")
	private WebElement deleteComment;

	private By add = By.className("add");
	private By CommentsArea = By.cssSelector("textarea.ember-text-area");
	
	
	public void setComments(String comment){
		addComment();
		WebElement area = getLastCommentsArea();
		WebElementUtils.hoverOverField(area, driver, null);
		area.clear();
		area.sendKeys(comment);
	}
	
	public void getComments()
	{
		getLastCommentsArea().getAttribute("value");
	}
	
	
	public void addComment(){
		WebElementUtils.hoverOverField(wrapper.findElement(add), driver, null);
		WebElementUtils.clickElementEvent(driver, wrapper.findElement(add));
		WebdriverUtils.waitForElementToBeFound(driver, By.className("comment-text"));
		//WebdriverUtils.waitForBudgetaBusyBar(driver);
		WebdriverUtils.sleep(1000);
	}
	
	
	public void DeleteComment(){
		deleteComment.click();
	}
	
	private WebElement getLastCommentsArea(){
		return wrapper.findElements(CommentsArea).get(wrapper.findElements(CommentsArea).size()-1);
	}
	
	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

}
