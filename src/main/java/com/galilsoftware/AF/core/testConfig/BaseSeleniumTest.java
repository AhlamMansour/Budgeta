package com.galilsoftware.AF.core.testConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.galilsoftware.AF.core.SelTestProps;
import com.galilsoftware.AF.core.SeleniumDriver;
import com.galilsoftware.AF.core.SeleniumDriver.BrowserType;
import com.galilsoftware.AF.core.SeleniumDriver.UserAgent;
import com.galilsoftware.AF.core.TestParamsThreadPool;
import com.galilsoftware.AF.core.listeners.DataProviderParams;
import com.galilsoftware.AF.core.listeners.KnownIssue;
import com.galilsoftware.AF.core.logging.SelTestLog;
import com.galilsoftware.AF.core.utilities.ExcelUtils;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;
import com.galilsoftware.AF.core.utilities.Xls_Reader;

import flexjson.JSONException;

public class BaseSeleniumTest {
    public WebDriver driver;
    protected static String baseURL;
    protected boolean chrome;
    protected boolean ie;
    protected boolean retry;
    public static double startTime = -1;
    public static double endTime = -1;
    public static boolean local;
    protected static final String OUTPUT_PATH = "target/surefire-reports/";
    private static String testHost = System.getProperty("webdriver.test.testHost");
    protected WebDriverWait wait;
    public String myBrowser;
    public String logoPath;
    public String nameOfXls;
    protected static Xls_Reader xls;

    /**
     * initializes the driver booleans
     */
    protected void initBaseTest() {
	chrome = SelTestProps.getBoolean("common.chrome");
	ie = SelTestProps.getBoolean("common.ie");
	local = SelTestProps.getBoolean("common.local");
	retry = SelTestProps.getBoolean("common.retry", false);
	nameOfXls = SelTestProps.get("common.rest.excel.name");
	// xls = new Xls_Reader(System.getProperty("user.dir") +
	// "\\src\\main\\java\\resources\\" + nameOfXls);
	xls = new Xls_Reader(System.getProperty("user.dir") + "\\src\\main\\java\\resources\\" + nameOfXls);
    }

    @BeforeSuite
    @Parameters("testPropertiesFileName")
    protected void initSuite(@Optional("") String testPropertiesFileName) {
	SelTestLog.info("------- Starting --------");

	if (!testPropertiesFileName.equals("TestProps.properties") && !testPropertiesFileName.equals("")) {
	    SelTestLog.info("You provided the tests to read from proporties file: " + testPropertiesFileName);
	    SelTestProps.setFileName(testPropertiesFileName);
	}
	SelTestProps.instance();
	//killBrowserInstances();
	startTime = startTime == -1 ? Double.parseDouble(WebdriverUtils.getTimeStamp("")) : startTime;
    }

    @BeforeClass
    @Parameters("browser")
    public void setEnv(@Optional("") String browser) {

	System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");

	initBaseTest();

	myBrowser = browser;
	if (local) {
	    baseURL = (baseURL == null) ? SelTestProps.get("common.base.url.local") : baseURL;
	} else {

	    if (testHost != null && testHost.length() > 10) {
		baseURL = (baseURL == null) ? "http://" + testHost : baseURL;
	    } else {
		baseURL = (baseURL == null) ? SelTestProps.get("common.base.url.remote") : baseURL;
	    }
	}

	if ("chrome".equalsIgnoreCase(browser)) {
	    chrome = true;

	} else if ("ie".equalsIgnoreCase(browser)) {
	    ie = true;
	}
	driver = (WebDriver) TestParamsThreadPool.get(TestParamsThreadPool.KEY_WEB_DRIVER);
	if (driver == null) {
	    driver = createNewDriver(null);
	    TestParamsThreadPool.put(TestParamsThreadPool.KEY_WEB_DRIVER, driver);
	}
	wait = new WebDriverWait(driver, 20);

	String nodeId = null;

	if (!local) {
	    try {
		String className = this.getClass().getName();
		// nodeId =
		// SeleniumDriver.getNodeData(driver).get("proxyId").toString();
		String browserType = getBrowserType().toString();
		Reporter.log("<div><hr/><b> Running " + className + "on node id: " + nodeId + " Browser: " + browserType.toString() + "</b>  <hr/></div>");
		SelTestLog.info("Running " + TestParamsThreadPool.get(TestParamsThreadPool.TEST_NAME).toString() + " on Node ID: " + nodeId);
	    } catch (JSONException e) {
		e.printStackTrace();
	    }
	}
	driver.manage().window().maximize();
    }

    @BeforeMethod
    public void cleanup() {
	Thread.interrupted();
    }

    @AfterMethod()
    public void logMethod(ITestResult result) throws IOException {
	try {
	    logMethodInner(result, driver);
	} catch (Exception e) {
	    SelTestLog.warn("failed giving proper logging", e);
	}

	// Detect Tests with known issues.
	// ignoreResultUponExpectedException(result);
    }

    /** my added after method **/

    @SuppressWarnings("deprecation")
    @AfterMethod(alwaysRun = true)
    protected void ignoreResultUponExpectedException(ITestResult result) {
	Annotation testAnnotation = null, knownIssueAnnotation = null;
	KnownIssue params = null;
	String bugID = "";
	String throwableMessage = "This test has been marked as a test with a KNOWN DEFECT!!";

	testAnnotation = getAnnotationContainsText(result, "testng.annotations.Test");

	if (result.getMethod().getMethod().getDeclaredAnnotations().length >= 1) {
	    knownIssueAnnotation = getAnnotationContainsText(result, "KnownIssue");
	}

	if (knownIssueAnnotation != null) {
	    params = (result.getMethod().getMethod()).getAnnotation(KnownIssue.class);
	    bugID = params.bugID();
	    throwableMessage += "\nBug ID is: " + bugID;
	}

	if (result.isSuccess() && testAnnotation.toString().contains("expectedExceptions=[class")) {
	    result.getTestContext().getPassedTests().removeResult(result.getMethod());
	    result.setThrowable(new Throwable(throwableMessage));
	    result.getTestContext().getSkippedTests().addResult(result, result.getMethod());
	}

    }

    @SuppressWarnings("deprecation")
    private Annotation getAnnotationContainsText(ITestResult myResult, String text) {
	Annotation[] annotations = myResult.getMethod().getMethod().getDeclaredAnnotations();
	for (Annotation annotation : annotations) {
	    if (annotation.toString().contains(text))
		return annotation;
	}
	return null;
    }

    /** my added method **/
    //
    // @SuppressWarnings("deprecation")
    // protected void ignoreResultUponExpectedException(ITestResult result) {
    // if (result.getStatus() == ITestResult.FAILURE) {
    //
    // Annotation[] annotations = (Annotation[])
    // result.getMethod().getMethod().getDeclaredAnnotations();
    // for(Annotation annotation : annotations){
    // KnownIssue params;
    // if(annotation.toString().contains("KnownIssue")){
    // params =
    // ((Method)(result.getMethod().getMethod())).getAnnotation(KnownIssue.class);
    // result.getTestContext().getFailedTests().removeResult(result.getMethod());
    // result.setThrowable(new
    // Throwable("This test has been marked as a test with a KNOWN DEFECT!!\nBug ID is: "+params.bugID()+"\nExpected Exception type: "+params.expectedException().toString()));
    // result.getTestContext().getSkippedTests().addResult(result,
    // result.getMethod());
    // break;
    // }
    // }
    // }
    // }

    public void logMethodInner(ITestResult result, WebDriver driver) throws IOException {
	if ((result.getStatus() == ITestResult.FAILURE) || (result.getStatus() == ITestResult.SKIP)) {

	    System.setProperty("org.uncommons.reportng.escape-output", "false");
	    Reporter.log("<div> Browser: " + getBrowserType().toString());

	    // Create screenshot file name
	    String failureImageFileName = result.getMethod().getMethodName() + "_"
		    + new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss").format(new GregorianCalendar().getTime());
	    takeScreenshot(driver, failureImageFileName);

	    String PageSourceFileName = result.getMethod().getMethodName()
		    + new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss").format(new GregorianCalendar().getTime()) + ".txt";
	    String stored_report = null;
	    try {
		stored_report = driver.getPageSource();
	    } catch (Exception e) {
		Reporter.log("<br /> Failed to get Page Source");
	    }
	    if (stored_report != null) {
		File f = new File(OUTPUT_PATH + PageSourceFileName);
		FileWriter writer = new FileWriter(f, true);
		writer.write(stored_report);
		writer.close();
	    }
	    try {
		if (!local) {
		    // Reporter.log("<br /> Node ID: " +
		    // SeleniumDriver.getNodeData(driver).get("proxyId"));
		}
	    } catch (JSONException e) {
		e.printStackTrace();
	    }
	    try {
		Reporter.log("URL: " + driver.getCurrentUrl());
		Reporter.log("<div>Test Class : " + result.getMethod().getTestClass().getName() + "</div>");
		Reporter.log("<div>Error on test: " + result.getMethod().getMethodName() + " <br /></div>");
		Reporter.log("<img src=\"../" + failureImageFileName + ".png" + "\" style=\"width:100%\" />");
		Reporter.log("<a href=\"../" + (stored_report != null ? PageSourceFileName : "No") + "\">page source</a>");
		Reporter.log("<hr style=\"margin: 30px;\" />");
		// Reporter.log("\n Active Element Source : " +
		// driver.switchTo().activeElement().getAttribute("innerHTML") +
		// "\n");
		Reporter.log("</div>");
	    } catch (Exception e) {
		System.err.println("Failed Reporting!");
	    }
	}
    }

    /**
     * takes a screenshot of current open page on the browser.
     * 
     * @param driver
     *            - the webdriver
     * @param ImageFileName
     *            - image name.
     */
    public static void takeScreenshot(WebDriver driver, String ImageFileName) {
	boolean wasInterrupted = false;
	try {
	    File imageFile;
	    if (local) {
		imageFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	    } else {
		WebDriver augmentedDriver = new Augmenter().augment(driver);
		imageFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
	    }
	    String failureImageFileName = ImageFileName + ".png";
	    File failureImageFile = new File(OUTPUT_PATH + failureImageFileName);
	    wasInterrupted = Thread.currentThread().isInterrupted();
	    if (wasInterrupted) { // remove interrupted flag
		Thread.interrupted();
	    }
	    FileUtils.moveFile(imageFile, failureImageFile);
	} catch (IOException e) {
	    SelTestLog.warn("Failed creating screenshot", e);
	    Reporter.log("<br /> Failed to Take screen Shot");
	} finally {
	    if (wasInterrupted) { // return interrupted flag
		Thread.currentThread().interrupt();
	    }
	}
    }

    public WebDriver getDriver() {
	return driver;
    }

    public BrowserType getBrowserType() {
	if (chrome) {
	    return BrowserType.CHROME;
	} else if (ie) {
	    return BrowserType.IE;
	} else {
	    return BrowserType.FIREFOX;
	}
    }

    public static String getBaseURL() {
	return baseURL;
    }

    public WebDriver createNewDriver(UserAgent agent) {
	WebDriver myDriver;
	if (chrome) {
	    try {
		myDriver = SeleniumDriver.createDriver(BrowserType.CHROME, agent);
	    } catch (WebDriverException wdEx) {
		wdEx.printStackTrace();
		System.err.println("tried creating chrome driver but WebDriverException was thrown, retrying... ");
		myDriver = SeleniumDriver.createDriver(BrowserType.CHROME, agent);
	    }
	} else if (ie) {
	    myDriver = SeleniumDriver.createDriver(BrowserType.IE, agent);
	} else {
	    myDriver = SeleniumDriver.createDriver(BrowserType.FIREFOX, agent);
	}
	return myDriver;
    }

    @DataProvider(name = "ExcelFileLoader")
    public Object[][] getDataFromXmlFile(final Method testMethod) {
	DataProviderParams parameters = testMethod.getAnnotation(DataProviderParams.class);

	if (parameters != null) {
	    String sheet = parameters.sheet();
	    String area = parameters.area();
	    return ExcelUtils.getExcelData(area, xls, sheet);
	} else {
	    throw new RuntimeException("Couldn't find the annotation");
	}
    }

    protected void killBrowserInstances() {
	// if (!local) {
	Runtime rt = Runtime.getRuntime();
	try {
	    System.out.println("Killing all chrome and chromedriver instances ...");
	   // rt.exec("taskkill /IM chrome.exe /f");
	    rt.exec("taskkill /IM chromedriver.exe /f");
	} catch (IOException e) {
	    System.err.println("Warning - Could not kill chrome and chromeDriver instances");
	    return;
	}
	System.out.println("chrome and chromedriver instances have been killed ...");
	// }
    }
    
}
