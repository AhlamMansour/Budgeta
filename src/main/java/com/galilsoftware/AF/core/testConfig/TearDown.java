package com.galilsoftware.AF.core.testConfig;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;

import com.galilsoftware.AF.core.TestParamsThreadPool;
import com.galilsoftware.AF.core.logging.SelTestLog;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;



public class TearDown extends BaseSeleniumTest {

	@AfterClass
	public void closeBrowser() throws IOException {
		try{
			closeDriver();
		}catch(Exception e){
			SelTestLog.warn("driver instance threw an exception when trying to close", e);
		}
		TestParamsThreadPool.clear();
	}
	
//	@AfterSuite
//	public void openReport() throws IOException{
//		File dummyFile = new File("");
//		File htmlFile = new File(dummyFile.getAbsolutePath()+"/test-output/html/index.html");
//		Desktop.getDesktop().browse(htmlFile.toURI());
//		
//		SelTestLog.info("The Test Suite has finished Executing.");
//	}
	
	public void openReport() throws IOException{
		File dummyFile = new File("");
		File htmlFile = new File(dummyFile.getAbsolutePath()+"/test-output/html/index.html");
		Desktop.getDesktop().browse(htmlFile.toURI());
		
		SelTestLog.info("The Test Suite has finished Executing.");
	}
	
	private String timeConversion(long miliSecs) {
		long hours = TimeUnit.MILLISECONDS.toHours(miliSecs);
		miliSecs = miliSecs - TimeUnit.HOURS.toMillis(hours);
		long mins = TimeUnit.MILLISECONDS.toMinutes(miliSecs);
		miliSecs = miliSecs - TimeUnit.MINUTES.toMillis(mins);
		long secs = TimeUnit.MILLISECONDS.toSeconds(miliSecs);
		String time = norma(hours) + " : " + norma(mins) + " : " + norma(secs);
		return time;
	}
	
	private String norma(long value){
		if(value < 10){
			return "0" + value;
		}else
			return "" + value;
	}

	protected void closeDriver() {
		if (!chrome && !ie) {
			try{
				driver.close();
			}catch(Exception e){
				SelTestLog.info("Could not close driver", e);
			}
		}
		driver.quit();
	}

	@AfterSuite
	public void cleanChromeDrivers() throws IOException{
		killBrowserInstances();
		endTime = Double.parseDouble(WebdriverUtils.getTimeStamp(""));
		
		System.out.println(" ###########-- Execution Time Took:"+(double)((endTime-startTime)/3600000)+"  --###########");
		System.out.println();
		System.out.println(" ###########==-- Execution Time Took:\t"+ timeConversion((long) (endTime-startTime)) +"\t --==###########");
		
		//openReport();
	}

}
