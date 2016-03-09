package com.budgeta.pom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class ImportWinStep4 extends ImportWinStep3 {

	@FindBy(className = "step-4")
	private WebElement wrapper;

	@FindBy(className = "ember-list-item-view")
	private List<WebElement> line;

	private By column = By.className("column");

	@FindBy(className = "ui-sortable-handle")
	private List<WebElement> lineTitle;

	public Map<String, List<String>> getAllValues() {
		Map<String, List<String>> values = new HashMap<String, List<String>>();
		int i = 0;
		for (WebElement row : lineTitle) {
			
			values.put(row.findElement(By.className("column-content")).getText(), getLineValues(i));
			i++;
		}
		return values;
	}

	public List<String> getLineValues(int index) {
		List<String> values = new ArrayList<String>();
		for (WebElement col : line.get(index).findElements(column)) {
			if (col.getText().isEmpty() || col.getText().equals("0"))
				values.add("-");
			else
				values.add(col.getText());
		}
		return values;
	}

	public void clickImport() {
		nextBtn.click();
		WebdriverUtils.waitForElementToDisappear(driver, By.className("step-4"));
	}

}
