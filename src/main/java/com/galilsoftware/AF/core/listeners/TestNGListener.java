package com.galilsoftware.AF.core.listeners;

import java.util.Iterator;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import com.galilsoftware.AF.core.TestParamsThreadPool;
import com.galilsoftware.AF.core.logging.SelTestLog;


public class TestNGListener implements ITestListener {
	String browser;

	@Override
	public void onFinish(ITestContext arg0) {
		Iterator<ITestResult> listOfFailedTests = arg0.getFailedTests().getAllResults().iterator();
		while (listOfFailedTests.hasNext()) {
			ITestResult failedTest = listOfFailedTests.next();
			ITestNGMethod method = failedTest.getMethod();
			if (arg0.getFailedTests().getResults(method).size() > 1) {
				listOfFailedTests.remove();
			} else {
				if (arg0.getPassedTests().getResults(method).size() > 0) {
					listOfFailedTests.remove();
				}
			}
		}
	}

	@Override
	public void onStart(ITestContext arg0) {

		browser = arg0.getCurrentXmlTest().getParameter("browser");
		String testName = arg0.getName() + " - " + browser;
		arg0.setAttribute("name", testName);
		if (browser != null) {
			TestParamsThreadPool.clear();
		} else if (System.getProperty("browser") != null) {
			Map<String, String> paramMap = arg0.getCurrentXmlTest().getTestParameters();
			paramMap.put("browser", System.getProperty("browser"));
			arg0.getCurrentXmlTest().setParameters(paramMap);
			browser = System.getProperty("browser");
		}
		TestParamsThreadPool.clear();
		TestParamsThreadPool.put(TestParamsThreadPool.TEST_NAME, testName);
		SelTestLog.info(">>>> Testing on browser: " + browser);
		System.out.println(">>>> Testing on browser: " + browser);
		SelTestLog.info("Thread id: " + Thread.currentThread().getId());
		System.out.println("Thread id: " + Thread.currentThread().getId());
		SelTestLog.info("Thread name: " + Thread.currentThread().getName());
		System.out.println("Thread name: " + Thread.currentThread().getName());
		SelTestLog.info("Starting Test Class: " + arg0.getName());
		System.out.println("Starting Test Class: " + arg0.getName());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {

	}

	@Override
	public void onTestFailure(ITestResult arg0) {
		SelTestLog.error(arg0.getName() + " Failed!!!");
		System.err.println(arg0.getName() + " Failed!!!");
		SelTestLog.error("Exception!!!", arg0.getThrowable());
		System.err.println("Exception!! : "+ arg0.getThrowable().getMessage()+arg0.getThrowable().getStackTrace().toString());
	}

	@Override
	public void onTestSkipped(ITestResult arg0) {

	}

	@Override
	public void onTestStart(ITestResult arg0) {
		SelTestLog.info(" *** Running:  " + arg0.getName() + " ***");
		System.out.println(" *** Running:  " + arg0.getName() + " ***");
	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
		SelTestLog.info("*** Success on:  " + arg0.getName() + " ***");
		System.out.println("*** Success on:  " + arg0.getName() + " ***");
	}



}
