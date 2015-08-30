package com.galilsoftware.AF.core.testConfig;

import java.io.IOException;

import org.testng.annotations.AfterClass;

import com.galilsoftware.AF.core.TestParamsThreadPool;
import com.galilsoftware.AF.core.logging.SelTestLog;



public class TearDown extends BaseSeleniumTest {

	@AfterClass
	public void closeBrowser() throws IOException {
		try{
			closeDriver();
		}catch(Exception e){
			SelTestLog.warn("driver instance threw an exception when trying to close", e);
		}
	}
	
//	@AfterSuite
//	public void openReport() throws IOException{
//		File dummyFile = new File("");
//		File htmlFile = new File(dummyFile.getAbsolutePath()+"/test-output/html/index.html");
//		Desktop.getDesktop().browse(htmlFile.toURI());
//		
//		SelTestLog.info("The Test Suite has finished Executing.");
//	}

	protected void closeDriver() {
		if (!chrome && !ie) {
			try{
				driver.close();
			}catch(Exception e){
				SelTestLog.info("Could not close driver", e);
			}
		}
		driver.quit();
		TestParamsThreadPool.clear();
	}


}
