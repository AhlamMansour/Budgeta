package main.java.budgeta.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;



//import com.similarweb.lite.pom.component.ContactUs.FileType;

/**
 * static class containing webdriver utilities
 * 
 * @author Amir Najjar
 * 
 */
public class WebdriverUtils {

	public static class EmptyElemetCondition implements
			ExpectedCondition<Boolean> {

		private final WebElement element;

		public EmptyElemetCondition(WebElement element) {
			this.element = element;
		}

		@Override
		public Boolean apply(WebDriver input) {
			return StringUtils.isEmpty(element.getAttribute("value"));
		}
	}
	
	
	/**
	 * delete attribue for web element
	 * 
	 * @param driver
	 * @param element
	 * @param attributeToRemove
	 */
	public static void removeAttributeForElement(WebDriver driver, WebElement el, String attributToRemove){
		 ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('"+attributToRemove+"')",el);
	}
	
	
	/**
	 * return the text of web element in javaScript
	 * 
	 * @param driver
	 * @param web element
	 * @return String
	 */
	public static String getTextInJS(WebDriver driver, WebElement el){
		return ((JavascriptExecutor) driver).executeScript("return arguments[0].innerText",el).toString();
	}

	/**
	 * opens a link in a new tab, and returns reference to old tab
	 * 
	 * @param driver
	 * @param link
	 * @return String handle of the old tab
	 */
	public static String openLinkInNewTab(WebDriver driver, String link) {
		// considering that there is only one tab opened in that point.
		String oldTab = driver.getWindowHandle();

		/*
		 * Actions builder = new Actions(driver);
		 * 
		 * builder.keyDown(Keys.CONTROL) .sendKeys("t") .keyUp(Keys.CONTROL);
		 * 
		 * Action selectMultiple = builder.build();
		 * 
		 * selectMultiple.perform();
		 */
		((JavascriptExecutor) driver)
				.executeScript("window.open('about:blank', 'myPopup');");
		// Actions action = new Actions(driver);
		// action.sendKeys(Keys.CONTROL, "t").build().perform();
		// action.keyDown(Keys.CONTROL).build().perform();
		// driver.findElement(By.className("input-search")).sendKeys(Keys.CONTROL,
		// "t");
		// action.sendKeys("t").build().perform();

		// List<WebElement> aElms = driver.findElements(By.tagName("a"));
		//
		// for (WebElement a : aElms) {
		// if (isDisplayed(a) && a.getAttribute("href") != null) {
		// a.click();
		// break;
		// }
		// }

		// driver.findElement().sendKeys(Keys.CONTROL+"t");

		// action.keyUp(Keys.CONTROL).build().perform();

		WebdriverUtils.sleep(3000);
		System.out.println("Getting redirected to link: " + link);
		ArrayList<String> newTab = new ArrayList<String>(
				driver.getWindowHandles());
		if (newTab.size() == 1) {
//			SelTestLog.error("could not open up a new tab");
			return "";
		}
		newTab.remove(oldTab);
		// change focus to new tab and close it
		driver.switchTo().window(newTab.get(0));
		driver.get(link);
		return oldTab;
	}

	public static String getTimeStamp(String prefix) {
		Calendar cal = Calendar.getInstance();
		String timeStamp = prefix
				+ String.valueOf(cal.getTimeInMillis()
						+ WebElementUtils.getRandomNumberByRange(0, 1000));
		return timeStamp;
	}

	public static String focusOnOtherTab(String oldTab, WebDriver driver) {
		ArrayList<String> newTab = new ArrayList<String>(
				driver.getWindowHandles());
		if (newTab.size() == 1) {
//			SelTestLog.error("could not open up a new tab");
			return "";
		}
		newTab.remove(oldTab);
		// change focus to new tab and close it
		driver.switchTo().window(newTab.get(0));
		sleep(1500);
		return newTab.get(0);
	}

//	public static WebDriver openLinkInNewBrowser(String link) {
//		WebDriver newDriver = SeleniumDriver
//				.createDriver(SeleniumDriver.BrowserType.CHROME);
//		WebdriverUtils.sleep(200);
//		System.out.println("Getting redirected to link: " + link);
//		newDriver.get(link);
//		return newDriver;
//	}

	public static float cleanAndParseFloat(String stringToParse) {
		return Float.parseFloat(stringToParse.replaceAll("[^-?0-9^.]", ""));
	}

	public static void closeNewBrowser(WebDriver driver, WebDriver driverToClose) {
		driverToClose.close();
		driver.switchTo().defaultContent();
	}

	public static void closeTabAndFocusOnPreviousTab(WebDriver driver,
			String oldTab) {
		driver.close();
		// change focus back to old tab
		driver.switchTo().window(oldTab);
	}

	public static String[] cleanNulls(final String[] v) {
		List<String> list = new ArrayList<String>(Arrays.asList(v));
		list.removeAll(Collections.singleton(null));
		return list.toArray(new String[list.size()]);
	}

	// public static String uploadExcelFile(WebElement uploadButton, WebDriver
	// driver, String fileName, FileType fileType) {
	// String fileNamex = "";
	// if (SelTestProps.getBoolean("common.local")) {
	// File file;
	// file = new File(System.getProperty("user.dir") + "\\resources\\files\\" +
	// fileName + "." + fileType.name().toLowerCase());
	// uploadButton.sendKeys(file.getAbsolutePath());
	// fileNamex = file.getName();
	// } else {
	// System.out.println("Uploading File: " + fileName);
	// if (getCurrentRunningBrowserType(driver).toLowerCase().equals("chrome"))
	// {
	// uploadButton.sendKeys(System.getProperty("user.dir") +
	// "//resources//files" + fileName + "." + fileType.name().toLowerCase());
	// } else {
	// uploadButton.sendKeys(System.getProperty("user.dir") +
	// "//resources//files" + fileName + "." + fileType.name().toLowerCase());
	// }
	//
	// }
	//
	// WebdriverUtils.sleep(1000);
	// driver.switchTo().defaultContent();
	// return fileNamex;
	// }

	public static void hoverOverElement(WebDriver driver, WebElement element,
			By elmLocator) {
		// Actions builder = new Actions(driver);
		/*
		 * if (driver instanceof ChromeDriver) { Actions hoverOverRegistrar =
		 * builder.moveToElement(element); hoverOverRegistrar.perform();
		 * hoverOverRegistrar.moveToElement(element).build().perform(); } else {
		 */

		String js = "arguments[0].style.height='auto'; arguments[0].style.display='visible';";

		((JavascriptExecutor) driver).executeScript(js,
				element.findElement(elmLocator));

		sleep(1200);
	}

	public static void enableElement(WebDriver driver, WebElement element,
			String attribute) {

		// String js = "arguments[0].removeAttribute(\""+attribute+"\")";
		// String jss = "arguments[0].setAttribute(\""+attribute+"\",\"\");";
		String jsx = "arguments[0].disabled=false;";
		try {
			((JavascriptExecutor) driver).executeScript(jsx, element);
		} catch (Exception e) {
//			SelTestLog.warn("in the script: " + jsx, e);
		}
		sleep(600);
	}

	public static void forceElementVisibility(WebDriver driver,
			WebElement element) {

		String js = "arguments[0].style.height='auto'; arguments[0].style.display='block';";

		((JavascriptExecutor) driver).executeScript(js, element);

		sleep(800);

	}

	public static void forceElementInvisibility(WebDriver driver,
			WebElement element) {

		String js = "arguments[0].style.height='auto'; arguments[0].style.display='none';";

		((JavascriptExecutor) driver).executeScript(js, element);

		sleep(800);

	}

	public static String getFieldValue(WebDriver driver, WebElement element) {

		String js = "return arguments[0].value;";
		String jsx = "return arguments[0].val()";

		String val = (String) ((JavascriptExecutor) driver).executeScript(js, element);
		sleep(800);
		val = val.equals("undefined") ? (String) ((JavascriptExecutor) driver).executeScript(jsx, element) : val;
		return val;
	}

	/*
	 * public boolean waitForPageLoad() throws InterruptedException { for (int
	 * second = 0;; second++) { if (second >= 60) return false; try { if
	 * ("visibility: visible;".equals(
	 * selenium.getAttribute("top_right_form:connectStat:connection-idle@style"
	 * ))) { break; } } catch (Exception e) {
	 * 
	 * } Thread.sleep(1000); } }
	 */

//	public static String uploadFileFromResources(WebElement uploadButton,
//			WebDriver driver, int pictureIndex) {
//		return uploadFileFromResources(uploadButton, driver, pictureIndex, null);
//	}

//	private static String uploadFileFromResources(WebElement uploadButton,
//			WebDriver driver, int pictureIndex, String path) {
//		String fileName;
//		if (SelTestProps.getBoolean("common.local")) {
//			File file;
//			if (path == null)
//				file = new File(System.getProperty("user.dir")
//						+ "/resources/images/pic" + pictureIndex + ".jpg");
//			else
//				file = new File(System.getProperty("user.dir") + path);
//			uploadButton.sendKeys(file.getAbsolutePath());
//			fileName = file.getName();
//		} else {
//			System.out.println("Uploading img: pic" + pictureIndex);
//			String pathToFile = "";
//			if (path == null)
//				pathToFile = "C://selenium//resources//images//pic"
//						+ pictureIndex + ".jpg";
//			else {
//				pathToFile = "C://selenium" + (path.replaceAll("/", "//"));
//			}
//			if (getCurrentRunningBrowserType(driver).toLowerCase().equals(
//					"chrome"))
//				uploadButton.sendKeys(pathToFile);
//			else
//				uploadButton.sendKeys("file:" + pathToFile);
//			fileName = "pic" + pictureIndex + ".jpg";
//		}
//
//		WebdriverUtils.sleep(1000);
//		driver.switchTo().defaultContent();
//		return fileName;
//	}

//	public static String uploadExcelFile(WebElement uploadButton,
//			WebDriver driver, String fileName, FileTypes fileType) {
//		String fileNamex = "";
//		if (SelTestProps.getBoolean("common.local")) {
//			File file;
//			file = new File(System.getProperty("user.dir")
//					+ "\\resources\\files\\" + fileName + "."
//					+ fileType.name().toLowerCase());
//			uploadButton.sendKeys(file.getAbsolutePath());
//			fileNamex = file.getName();
//		} else {
//			System.out.println("Uploading File: " + fileName);
//			if (getCurrentRunningBrowserType(driver).toLowerCase().equals("chrome")) {
//				uploadButton.sendKeys(System.getProperty("user.dir") + "//resources//files//" + fileName + "." + fileType.name().toLowerCase());
//			} else {
//				uploadButton.sendKeys(System.getProperty("user.dir") + "//resources//files//" + fileName + "." + fileType.name().toLowerCase());
//			}
//
//		}
//
//		WebdriverUtils.sleep(1000);
//		driver.switchTo().defaultContent();
//		return fileNamex;
//	}

	public static boolean isDisplayed(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public static void waitForSimilarWebLoadBar(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 90);
		sleep(600);

		wait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver arg0) {
				try {
					return !hasClass("loading",
							arg0.findElement(By.className("sw_loader")));
				} catch (Exception e) {
					return true;
				}
			}
		});
		sleep(1300);
	}

	public static void waitForSimilarWebChartsBar(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		sleep(600);

		wait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver arg0) {
				try {
					return !arg0.findElement(By.className("sw-spinner"))
							.isDisplayed();
				} catch (Exception e) {
					return true;
				}
			}
		});
		sleep(1200);
	}

	public static void waitForElementToDisappear(WebDriver driver,
			final By elementLocator) {
		WebDriverWait wait = new WebDriverWait(driver, 45);
		sleep(600);

		wait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver arg0) {
				try {
					return !arg0.findElements(elementLocator).get(0)
							.isDisplayed();
				} catch (Exception e) {
					return true;
				}
			}
		});
		sleep(800);
	}

	/**
	 * gets an element from a list using get(index) and waits until that element
	 * doesn't have a certain class.
	 * 
	 * @param driver
	 * @param classToHave
	 * @param elementLocator
	 * @param index
	 */

	public static void waitForElementInAListToNotHaveClass(WebDriver driver, final String classToHave, final By elementLocator, final int index) {
		WebDriverWait wait = new WebDriverWait(driver, 90);

		sleep(600);

		wait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver arg0) {
				try {
					return !hasClass(classToHave,
							arg0.findElements(elementLocator).get(index));
				} catch (Exception e) {
					return true;
				}
			}
		});
		sleep(800);
	}

	public static void waitForElementInAListToNotHaveClassWithTime(
			WebDriver driver, final String classToHave,
			final By elementLocator, int waitTime) {
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		sleep(600);
		wait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver arg0) {
				try {
					return !hasClass(classToHave,
							arg0.findElement(elementLocator));
				} catch (Exception e) {
					return true;
				}
			}
		});
		sleep(800);
	}

//	public static void uploadSpecificFileFromResources(WebElement uploadButton,
//			WebDriver driver, String relativePathWithFileName) {
//		uploadFileFromResources(uploadButton, driver, -1,
//				relativePathWithFileName);
//	}

	public static Wait<WebDriver> initializePOM(WebDriver driver, Object pom) {
		// driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 4), pom);
		return new FluentWait<WebDriver>(driver).withTimeout(30,
				TimeUnit.SECONDS).pollingEvery(900, TimeUnit.MILLISECONDS);
	}

	public static Wait<WebDriver> initializePOM(WebDriver driver, Object pom,
			int pollingTime, int elementsTimeout, int waitTimeout) {
		// driver.manage().timeouts().pageLoadTimeout(pageLoadTimeout,
		// TimeUnit.SECONDS);
		PageFactory.initElements(new AjaxElementLocatorFactory(driver,
				elementsTimeout), pom);
		return new FluentWait<WebDriver>(driver).withTimeout(waitTimeout,
				TimeUnit.SECONDS).pollingEvery(pollingTime,
				TimeUnit.MILLISECONDS);
	}

	public static void removeAttribute(WebDriver driver, WebElement element,
			String attribute) {

		String js = "arguments[0].removeAttribute(\"" + attribute + "\")";

		try {
			((JavascriptExecutor) driver).executeScript(js, element);
		} catch (Exception e) {
//			SelTestLog.warn("in the script: " + js, e);
		}
		sleep(600);
	}

	public static void setAttribute(WebDriver driver, WebElement element,
			String attribute, String attributeValue) {

		String js = "arguments[0].setAttribute('" + attribute + "' , '"
				+ attributeValue + "');";

		try {
			((JavascriptExecutor) driver).executeScript(js, element);
		} catch (Exception e) {
//			SelTestLog.warn("in the script: " + js, e);
		}
		sleep(600);
	}

//	public static String getCurrentRunningBrowserType(WebDriver driver) {
//		if (SelTestProps.getBoolean("common.local")) {
//			if (driver instanceof ChromeDriver) {
//				return BrowserType.CHROME;
//			} else if (driver instanceof FirefoxDriver) {
//				return BrowserType.FIREFOX;
//			} else
//				return BrowserType.IE;
//		} else if (driver instanceof RemoteWebDriver) {
//			String browser = ((RemoteWebDriver) driver).getCapabilities()
//					.getBrowserName().toLowerCase();
//
//			if (browser.contains("chrome")) {
//				return BrowserType.CHROME;
//			} else if (browser.contains("firefox")) {
//				return BrowserType.FIREFOX;
//			} else
//				return BrowserType.IE;
//		}
//		return null;
//	}

	public enum Align {
		LEFT("left"), RIGHT("right"), CENTER("none");

		private String floatCssVal;

		private Align(String floatCssVal) {
			this.floatCssVal = floatCssVal;
		}

		public String getFloatCssVal() {
			return floatCssVal;
		}
	}

	/**
	 * clicks save on the element and wait until saving goes away.
	 * 
	 * @param driver
	 * @param saveButton
	 */
	public static boolean save(WebDriver driver, WebElement saveButton) {
		waitForSpinner(driver);
		saveButton.click();
		int tries = 5, tryIndex = 0;
		while (hasClass("wlGreyBtnSaved", saveButton)) {
			WebdriverUtils.sleep(200);
			if (tryIndex == tries) {
				return false;
			}
			tryIndex++;
		}
		return true;
	}

	public static ExpectedCondition<WebElement> visibilityOfElementLocated(
			final By locator) {
		return new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				return getDisplayedElement(locator, driver);
			}
		};
	}

	public static WebElement getDisplayedElement(final By locator,
			WebDriver driver) {
		List<WebElement> elemList = driver.findElements(locator);
		for (WebElement webElement : elemList) {
			if (webElement.isDisplayed()) {
				return webElement;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param driver
	 *            - the webdriver
	 * @param elementToClick
	 *            - element to click on
	 * @param by
	 *            - inner locator
	 * @return rue if condition fulfilled. false otherwise.
	 */
	public static Exception clickUntilAnotherElementIsFound(WebDriver driver,
			WebElement elementToClick, By by) {
		final int maxTries = 3;
		Exception e = null;
		for (int i = 0; i < maxTries; i++) {
			try {
				elementToClick.click();
				sleep(1000);
				WebElement currentElement = driver.switchTo().activeElement();
				waitForVisibility(driver, by, 3);
				currentElement.findElement(by);
				return null;
			} catch (Exception ex) {
				e = ex;
				// if we get here then webdriver couldn't find the element sent
				// with param by!
				// try again!
			}
		}
		return e;
	}

	public static ExpectedCondition<WebElement> visibilityOfWebElement(
			final WebElement elm) {
		return new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				if (elm.isDisplayed()) {
					return elm;
				}
				return null;
			}
		};
	}

	public static WebElement waitForVisibilityofElement(WebDriver driver,
			WebElement elm, int waitTime) {
		Wait<WebDriver> wait = new WebDriverWait(driver, waitTime);
		WebElement divElement = wait.until(visibilityOfWebElement(elm));
		return divElement;
	}

	public static WebElement waitForVisibility(WebDriver driver, By by,
			int waitTime) {
		Wait<WebDriver> wait = new WebDriverWait(driver, waitTime);
		WebElement divElement = wait.until(visibilityOfElementLocated(by));
		return divElement;
	}

	public static WebElement waitUntilClickable(WebDriver driver, By by) {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		WebElement element = wait.until(ExpectedConditions
				.elementToBeClickable(by));
		return element;
	}

	public static WebElement waitUntilClickable(WebDriver driver, WebElement elm) {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		WebElement element = wait.until(elementToBeClickableByWebElement(elm));
		return element;
	}

	public static ExpectedCondition<WebElement> elementToBeClickableByWebElement(
			final WebElement elm) {
		return new ExpectedCondition<WebElement>() {

			public ExpectedCondition<WebElement> visibilityOfElementLocated = ExpectedConditions
					.visibilityOf(elm);

			@Override
			public WebElement apply(WebDriver driver) {
				WebElement element = visibilityOfElementLocated.apply(driver);
				try {
					if (element != null && element.isEnabled()) {
						return element;
					} else {
						return null;
					}
				} catch (StaleElementReferenceException e) {
					return null;
				}
			}

			@Override
			public String toString() {
				return "element to be clickable: " + elm.toString();
			}
		};
	}

	public static ExpectedCondition<WebElement> elementToBeselected(
			final WebElement elm) {
		return new ExpectedCondition<WebElement>() {

			public ExpectedCondition<WebElement> visibilityOfElementLocated = ExpectedConditions
					.visibilityOf(elm);

			@Override
			public WebElement apply(WebDriver driver) {
				WebElement element = visibilityOfElementLocated.apply(driver);
				try {
					if (element != null && hasClass("selected", element)) {
						return element;
					} else {
						return null;
					}
				} catch (StaleElementReferenceException e) {
					return null;
				}
			}
		};
	}

	public static ExpectedCondition<WebElement> elementToHaveAttribute(
			final WebElement elm, final String attribute) {
		return new ExpectedCondition<WebElement>() {

			public ExpectedCondition<WebElement> visibilityOfElementLocated = ExpectedConditions
					.visibilityOf(elm);

			@Override
			public WebElement apply(WebDriver driver) {
				WebElement element = visibilityOfElementLocated.apply(driver);
				try {
					if (element != null && hasAttribute(attribute, element)) {
						return element;
					} else {
						return null;
					}
				} catch (StaleElementReferenceException e) {
					return null;
				}
			}
		};
	}

	public static ExpectedCondition<WebElement> elementToHaveAttributeEqualTo(
			final WebElement elm, final String attribute,
			final String attributeString) {
		return new ExpectedCondition<WebElement>() {

			public ExpectedCondition<WebElement> visibilityOfElementLocated = ExpectedConditions
					.visibilityOf(elm);

			@Override
			public WebElement apply(WebDriver driver) {
				WebElement element = visibilityOfElementLocated.apply(driver);
				try {
					if (element != null
							&& hasAttributeEqualTo(attribute, attributeString,
									element)) {
						return element;
					} else {
						return null;
					}
				} catch (StaleElementReferenceException e) {
					return null;
				}
			}
		};
	}

	public static ExpectedCondition<WebElement> elementToHaveClass(
			final WebElement elm, final String classToHave) {
		return new ExpectedCondition<WebElement>() {

			public ExpectedCondition<WebElement> visibilityOfElementLocated = ExpectedConditions
					.visibilityOf(elm);

			@Override
			public WebElement apply(WebDriver driver) {
				WebElement element = visibilityOfElementLocated.apply(driver);
				try {
					if (element != null && hasClass(classToHave, element)) {
						return element;
					} else {
						return null;
					}
				} catch (StaleElementReferenceException e) {
					return null;
				}
			}
		};
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {

		}
	}

	public static void waitForLoad(WebDriver driver) {
		waitForLoad(driver, 25);
	}

	public static void waitForLoad(WebDriver driver, long sec) {

		WebDriverWait wait = new WebDriverWait(driver, sec);
		sleep(700);
		wait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver arg0) {
				try {
					WebElement disabledImageZone = arg0.findElement(By
							.id("disabledImageZone"));
					return (!disabledImageZone.getAttribute("style").contains(
							"progress")
							&& !disabledImageZone.getAttribute("style")
									.contains("block") && !disabledImageZone
							.getAttribute("style").contains("cursor: wait"));
				} catch (Exception e) {
					return false;
				}

			}
		});
		sleep(700);
	}

	public static void waitForForesightPOMLoad(WebDriver driver,
			final By objectLocator) {

		WebDriverWait wait = new WebDriverWait(driver, 20);
		sleep(600);

		wait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver arg0) {
				try {
					return !hasClass("wait", arg0.findElement(objectLocator));
				} catch (Exception e) {
					return true;
				}
			}
		});
		sleep(800);
	}

	public static void waitForWochitSpinner(WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, 20);
		sleep(600);
		final By locator = By.id("spinner");

		wait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver arg0) {
				try {
					return hasAttributeEqualTo("display: none;", "style",
							arg0.findElement(locator));
				} catch (Exception e) {
					return true;
				}
			}
		});
		sleep(800);
	}

	/**
	 * wait for the generated spinner to disappear while polling every 100
	 * milliseconds.
	 * 
	 * @param driver
	 */
	public static void waitForSpinner(WebDriver driver) {

		sleep(700);
		int tries = 20, tryIndex = 0;
		while (locateForesightSpinner(driver)) {
			WebdriverUtils.sleep(100);
			if (tryIndex == tries)
				break;
			tryIndex++;
		}
		sleep(500);
	}

	private static boolean locateForesightSpinner(WebDriver driver) {
		try {
			driver.findElement(By.className("j15")).findElement(
					By.className("generatedSpinner"));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void waitForElementToBeFound(WebDriver driver,
			final By elmLocator) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		//sleep(600);
		wait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver arg0) {
				try {
					return arg0.findElement(elmLocator).isDisplayed();
				} catch (Exception e) {
					return true;
				}
			}
		});
		sleep(800);
	}

	public static void waitForEmulatorLoad(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 12);

		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
		wait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver arg0) {
				try {
					WebElement disabledImageZone = arg0.findElement(By
							.id("disabledImageZone"));
					return (!disabledImageZone.getAttribute("style").contains(
							"block") && !disabledImageZone
							.getAttribute("style").contains("cursor: wait"));
				} catch (Exception e) {
//					SelTestLog.info("Exception!!!", e);
					return false;
				}
			}
		});
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

	public static void click(WebElement clickable) {
		boolean flag = true;
		long endTime = System.currentTimeMillis() + 4000;
		WebDriverException e = null;
		while (flag && System.currentTimeMillis() < endTime) {
			sleep(250);
			try {
				clickable.click();
				flag = false;
			} catch (WebDriverException wde) {
				e = wde;
			}
		}
		if (flag) {
			throw e;
		}
	}

	public static int calculateWidthPercent(WebElement elem, int parentDivWIdth) {
		System.out.println(Float.parseFloat(elem.getCssValue("width")
				.replaceAll("[^0-9.]", "")) + " / " + parentDivWIdth);
		return Math.round((Float.parseFloat(elem.getCssValue("width")
				.replaceAll("[^0-9.]", "")) / parentDivWIdth) * 100);
	}

	public static boolean isAligned(WebElement elm, Align align) {

		return elm.getCssValue("float")
				.equalsIgnoreCase(align.getFloatCssVal());
	}

	public static boolean isElementPresent(WebDriver driver, By locator) {
		try {
			WebElement elm = driver.findElement(locator);
			return elm.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	/**
	 * this method waits on the element to be displayed
	 * 
	 * @param driver
	 * @param element
	 * @return boolean indicating if it is or not
	 */
	public static boolean isElementPresent(WebDriver driver, WebElement element) {
		try {
			return element.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * this method doesn't wait on elements to be displayed
	 * 
	 * @param driver
	 * @param element
	 * @return boolean indicating if it is visible or not
	 */
	public static boolean isElementVisible(WebDriver driver, By locator) {
		try {
			return driver.findElements(locator).size() > 0;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public static boolean isVisible(WebElement webElement) {
		try {
			return webElement.isDisplayed();
		} catch (StaleElementReferenceException e) {
			// element got stale since it's been retrieve, just return false
			return false;
		}

		catch (Exception e) {
			// any exception, return false
			return false;
		}

	}

	public static boolean isVisible(WebDriver driver, By locator) {
		try {
			return driver.findElement(locator).isDisplayed();
		} catch (StaleElementReferenceException e) {
			// element got stale since it's been retrieve, just return false
			return false;
		}

		catch (Exception e) {
			// any exception, return false
			return false;
		}

	}

	/**
	 * Checks to see if a specified element contains a specific class of not.
	 * The check is case-insensitive.
	 * 
	 * @param className
	 *            CSS class name to check for
	 * @param element
	 *            element to check
	 * @return <code>true</code>, if <tt>element</tt> has CSS class with given
	 *         <tt>className</tt>
	 */
	public static boolean hasClass(String className, WebElement element) {
		final String classNameLowerCase = className.toLowerCase();
		String classValue;
		try {
			classValue = element.getAttribute("class");
		} catch (Exception e) {
			// SelTestLog.warn("Could not find element", e);
			return false;
		}
		if (StringUtils.isEmpty(classValue)) {
			return false;
		}
		classValue = classValue.toLowerCase();
		if (!classValue.contains(classNameLowerCase)) {
			return false;
		}
		for (String singleClass : classValue.split("\\s+")) {
			// System.out.println(singleClass);
			if (classNameLowerCase.equals(singleClass.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasAttribute(String attribute, WebElement element) {
		String att = element.getAttribute(attribute);
		if (StringUtils.isEmpty(att)) {
			return false;
		}
		return true;
	}

	public static boolean hasAttributeEqualTo(String attribute,
			String attributeString, WebElement element) {
		return element.getAttribute(attribute).equals(attributeString);
	}

	public static void ScrollToTheTop(WebDriver driver) {

		try {

			Actions actions = new Actions(driver);
			actions.sendKeys(Keys.HOME).perform();

		} catch (Exception e) {
//			SelTestLog.error("Could not scroll to the top !");
		}

	}

	public static void ScrollToTheTopJS(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,0);");
	}

	public static void ScrollDownTo(WebDriver driver,int x,int y) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo("+x+","+y+");");
		WebdriverUtils.sleep(1000);
	}

	public static boolean containsClass(String className, WebElement element) {
		final String classNameLowerCase = className.toLowerCase();
		String classValue = element.getAttribute("class");
		if (StringUtils.isEmpty(classValue)) {
			return false;
		}
		classValue = classValue.toLowerCase();
		for (String singleClass : classValue.split("\\s+")) {
			// System.out.println(singleClass);
			if (singleClass.toLowerCase().contains(classNameLowerCase)) {
				return true;
			}
		}
		return false;
	}

	public static boolean waitForElementToBeLocated(WebDriver driver,
			By homeLocator, int i) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, i);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(homeLocator));
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public static String getImageNameFromUrl(String URL) {
		URL = normalizeImageUrl(URL);
		if (URL.lastIndexOf("/") != -1) {
			URL = URL.substring(URL.lastIndexOf("/"));
		}
		URL = URL.replaceAll(".png", "");
		URL = URL.replaceAll("\"", "");
		return URL;

	}

	private static String normalizeImageUrl(String name) {
		if (name.contains("(")) {
			name = name.substring(name.indexOf("(") + 1, name.lastIndexOf(")"));
		}
		return name;
	}

	public static boolean waitForTextToAppear(String text, int time,
			By textFieldLocator, WebDriver driver) {
		time = time * 100 - 500;
		sleep(500);
		while (time > 0) {
			sleep(300);
			if (driver.findElement(textFieldLocator).getText().equals(text))
				return true;
			time = time - 300;
		}
		return false;
	}

	public static Dimension getDimensionFromString(String res) {
		String[] parts = res.split("x");
		if (parts.length != 2) {
			throw new IllegalArgumentException("Not a valid size string: "
					+ res);
		}
		return new Dimension(Integer.parseInt(parts[0]),
				Integer.parseInt(parts[1]));
	}

	public static String getBackgroundImageName(WebElement ele) {
		return (ele.getCssValue("background-image").substring(
				ele.getCssValue("background-image").lastIndexOf("/") + 1, ele
						.getCssValue("background-image").lastIndexOf(".")));
	}

	public static boolean WaitUntilClassDissappear(String className,
			WebElement element, int time) {
		time = time * 100 - 500;
		sleep(500);
		while (time > 0) {
			sleep(300);
			if (!hasClass(className, element))
				return true;
			time = time - 300;
		}
		return false;

	}

	public static boolean waitForInvisibilityOfElement(WebDriver driver,
			WebElement elm, int waitTime) {
		int tries = 0;
		int wait = waitTime * 2;
		while (tries < wait) {
			try {
				if (elm.isDisplayed()) {
					sleep(500);
					tries++;
				} else
					return true;
			} catch (Exception e) {
				return true;
			}
		}
		return false;
	}

}

