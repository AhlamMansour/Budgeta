package com.budgeta.pom;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.galilsoftware.AF.core.AbstractPOM;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class UploadImportFile extends AbstractPOM{

	
	@FindBy(className = "import")
    private WebElement wrapper;
	
	@FindBy(className = "import-select-file")
    private WebElement upload;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
    public boolean isDisplayed() {
	return WebdriverUtils.isDisplayed(wrapper);
    }
}
