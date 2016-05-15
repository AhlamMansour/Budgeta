package com.budgeta.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class ReportsPopup extends AbstractPOM {

	@FindBy(className = "modal-content")
	private WebElement wrapper;

	@FindBy(css = "div.modal-body label.full")
	private List<WebElement> checkBoxes;

	@FindBy(id = "confirm-btn")
	private WebElement createBtn;

	@FindBy(className = "type-excel")
	private WebElement excelType;
	
	@FindBy(className = "type-pdf")
	private WebElement pdfType;
	
	public ReportsPopup() {
		try {
			wait.until(WebdriverUtils.visibilityOfWebElement(wrapper));
		} catch (Exception e) {
		}
	}

	public void checkBox(String boxToCheck) {
		getCheckBoxElement(boxToCheck).click();
		WebdriverUtils.waitForBudgetaLoadBar(driver);
	}

	public void selectOtherReportes() {
		for (WebElement box : checkBoxes) {
			if (box.findElement(By.tagName("input")).getAttribute("value").equals("2")) {
				box.click();
				return;
			}

		}

	}

	public void selectReports(String option) {
		for (WebElement box : checkBoxes) {
			if (box.getText().equals(option)) {
				box.click();
				WebdriverUtils.waitForBudgetaLoadBar(driver);
				return;
			}

		}

	}

	public void selectReportType(String option) {
		for (WebElement box : checkBoxes) {
			if (box.getText().equals(option)) {
				box.click();
				return;
			}

		}

	}

	public void selectExcelReportType() {
		excelType.click();
		WebdriverUtils.sleep(1000);

	}

	public void selectPdfReportType() {
		pdfType.click();
		WebdriverUtils.sleep(1000);
	}

	public boolean isBoxChecked(String checkBox) {
		return getCheckBoxElement(checkBox).findElement(By.xpath("..")).findElement(By.tagName("input")).isSelected();
	}

	private WebElement getCheckBoxElement(String checkBox) {
		for (WebElement box : checkBoxes) {
			if (box.getText().contains(checkBox))
				return box;
		}
		return null;
	}

	@Override
	public boolean isDisplayed() {
		return WebdriverUtils.isDisplayed(wrapper);
	}

	public void clickCreate() {
		createBtn.click();
		WebdriverUtils.waitForBudgetaCreateButton(driver);
		WebdriverUtils.waitForInvisibilityOfElement(driver, wrapper, 6);
	}
}
