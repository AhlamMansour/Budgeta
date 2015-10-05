package com.budgeta.pom;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;

public class CommentsSection extends AbstractPOM{
	
	
	@FindBy(id = "section-Comments")
	private WebElement wrapper;
	
	
	@FindBy(className = "add")
	private WebElement addComment;
	
	@FindBy(css = "div.comment-created div.svg-icon")
	private WebElement deleteComment;
	
	
	@FindBy(css = "div.comment-text textarea.ember-text-area")
	private WebElement CommentTextArea;

	
	
	public void setComments(String comment){
		CommentTextArea.sendKeys(comment);
	}
	
	public void getComments()
	{
		CommentTextArea.getAttribute("value");
	}
	
	
	public void AddComment(){
		addComment.click();
	}
	
	
	public void DeleteComment(){
		deleteComment.click();
	}
	
	
	
	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
