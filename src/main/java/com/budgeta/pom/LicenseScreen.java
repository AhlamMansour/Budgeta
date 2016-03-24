package com.budgeta.pom;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.FindElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.web.util.WebUtils;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class LicenseScreen extends AbstractPOM {

	@FindBy(className = "my-license-page")
	private WebElement wrapper;

	@FindBy(className = "add-user-btn")
	private WebElement addUser;

	@FindBy(css = "form.my-license-form button")
	private List<WebElement> buttons;

	// @FindBy(className = "remove-btn")
	// private WebElement removeUser;

	private By removeUser = By.className("remove-btn");

	@FindBy(css = "a.is-admin-label")
	private WebElement makeAdmin;

	@FindBy(css = "div.user-input input.ember-text-field")
	private WebElement yourPlanName;

	@FindBy(className = "user-line")
	private List<WebElement> userLines;

	public void clickUpdate() {
		for (WebElement el : wrapper.findElements(By.cssSelector("form.my-license-form button"))) {
			if (el.getText().equals("Update")) {
				el.click();
				return;
			}
		}
	}

	public void clickSave() {
		for (WebElement el : wrapper.findElements(By.cssSelector("form.my-license-form button"))) {
			if (el.getText().equals("Save")) {
				el.click();
				return;
			}
		}
	}

	public void clickCancele() {
		for (WebElement el : wrapper.findElements(By.cssSelector("form.my-license-form button"))) {
			if (el.getText().equals("Cancel")) {
				el.click();
				return;
			}
		}
	}

	public void addUser() {
		addUser.click();
		WebdriverUtils.waitForElementToBeFound(driver, By.className("modal-content"));

	}

	public void removeUser() {
		
		for (WebElement el : wrapper.findElements(removeUser)){
			if (el.isDisplayed()) {
				el.click();
				break;
		}
		
		}

	}

	public void makeAdminUser() {

		try {
			wrapper.findElement(By.cssSelector("a.is-admin-label")).isDisplayed();
			makeAdmin.click();
		} catch (NoSuchElementException e) {

		}

	}

	public int numberOfAdminUsers() {
		int adminUsers = 0;
		
			try {
				for (WebElement el : wrapper.findElements(By.cssSelector("label.is-admin-label"))){
				if(el.isDisplayed())
					adminUsers ++ ;

				}
			} catch (NoSuchElementException e) {

			}
		
		
		
		return adminUsers;

	}

	public String yourPlanName() {
		return yourPlanName.getText();
	}

	public Integer usersNumber() {
		List<WebElement> users = new ArrayList<WebElement>();
		for(WebElement el : userLines){
			if(!el.findElement(By.className("user-email")).getText().equals("No users"))
				users.add(el);	
		}
		return users.size();
	}

	public boolean isRemoveButtonIsDisplay() {
		// if(WebdriverUtils.isDisplayed(removeUser))
		// if(WebdriverUtils.isDisplayed(wrapper.findElement(By.className("remove-btn"))))
		if (WebdriverUtils.isVisible(driver, removeUser))
			return true;
		return false;
	}

	public boolean isMakeAdminIsDisplay() {
		// if(WebdriverUtils.isDisplayed(removeUser))
		try {
			if (WebdriverUtils.isDisplayed(wrapper.findElement(By.cssSelector("a.is-admin-label"))))
				return true;

		} catch (NoSuchElementException e) {

		}

		return false;
	}

	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);

	}

}
