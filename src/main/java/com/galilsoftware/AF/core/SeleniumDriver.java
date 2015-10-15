package com.galilsoftware.AF.core;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.galilsoftware.AF.core.logging.SelTestLog;
import com.galilsoftware.AF.core.testConfig.BaseSeleniumTest;
//import org.apache.http.impl.client.DefaultHttpClient;


/**
 * This class is used for creating all kinds of the Selenium Drivers, capabilities, user agents.. etc.
 * @author Amir Najjar
 *
 */
public class SeleniumDriver {

	private static final String DEFAULT_REMOTE_SELENIUM_HUB_URL = "";
	private static URL remoteSeleniumHub;
	private static String remoteHub = System.getProperty("webdriver.test.remoteHub");

	public enum BrowserType {
		CHROME, FIREFOX, IE
	}

	public enum UserAgent {
		IPHONE("Mozilla/5.0 (iPhone; CPU iPhone OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A5376e Safari/8536.25"), ANDROID_PHONE(
				"Mozilla/5.0 (Linux; U; Android 4.0.4; en-gb; GT-I9300 Build/IMM76D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30"), IPAD(
						"Mozilla/5.0 (iPad; CPU OS 7_0 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53"), DESKTOP_CHROME(
								"Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36");

		private String userAgentString;

		private UserAgent(String ua) {
			userAgentString = ua;
		}

		public Dimension getResolution() {
			Dimension dim;
			if (this.name().equals("IPHONE"))
				dim = new Dimension(320, 600);
			else if (this.name().equals("ANDROID_PHONE"))
				dim = new Dimension(320, 600);
			else if (this.name().equals("IPAD"))
				dim = new Dimension(960, 1024);
			else
				dim = new Dimension(0, 0);
			return dim;

		}

		/**
		 * returns the user agent
		 * @return current used user agent
		 */
		public String getUserAgentString() {
			return userAgentString;
		}

		public static UserAgent getUserAgentFor(String ua) {
			UserAgent[] agents = values();
			for (UserAgent agent : agents) {
				String val1 = agent.name().replace("_", " ");
				if (val1.equals(ua) || val1.contains(ua)) {
					return agent;
				}
			}
			return null;
		}

	}

	/**
	 * creates a driver according to the settings in BaseSeleniumTest.java
	 * @param browser
	 * @return the newly created driver.
	 */
	public static WebDriver createDriver(BrowserType browser) {
		return createDriver(browser, null);
	}

	public static WebDriver createDriver(BrowserType browser, UserAgent userAgent) {
		WebDriver driver = null;

		if (!BaseSeleniumTest.local) {
			try {
				remoteSeleniumHub = new URL(gethubUrl() + ":4444/wd/hub");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		
		File dummyFile = new File("");;
		
		switch (browser) {
		case CHROME:

			ChromeOptions options = new ChromeOptions();
			options.addArguments("--start-maximized", "--disable-translate");
			if (userAgent != null) {
				options.addArguments("--user-agent=" + userAgent.getUserAgentString());
			}
			options.addArguments("--test-type");
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability("screen-resolution","1600x900");
			System.setProperty("webdriver.chrome.logfile", "NUL");

			if (!BaseSeleniumTest.local) {
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				try {
					driver = new RemoteWebDriver(remoteSeleniumHub, capabilities);
				} catch (Exception e) {
					e.printStackTrace();
					SelTestLog.info("Failed to create driver", e);
					driver = new RemoteWebDriver(remoteSeleniumHub, capabilities);
				}

			} else {				
				File file = new File(dummyFile.getAbsolutePath()+"/drivers/chromedriver.exe");
				System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
				if (StringUtils.startsWithIgnoreCase(System.getProperty("os.name"), "Mac OS X")) {
					System.setProperty("webdriver.chrome.driver", "drivers/chromedriver-for-macosx");
				}

				if(!file.canExecute())
					System.err.println("could not find chrome driver");
				HashMap<String, Object> myPrefs = new HashMap<String, Object>();
				myPrefs.put("profile.default_content_settings.popups", 0);
				File downloaDir = new File(dummyFile.getAbsolutePath()+"/browserDownloads");
				myPrefs.put("download.default_directory", downloaDir.getAbsolutePath());
				options.setExperimentalOption("prefs", myPrefs);
				
				driver = new ChromeDriver(options);
			}

			break;

		case FIREFOX:

			if (!BaseSeleniumTest.local) {
				driver = new RemoteWebDriver(remoteSeleniumHub, DesiredCapabilities.firefox());
			} else {
				driver = new FirefoxDriver();
			}

			break;

		case IE:
			if (!BaseSeleniumTest.local) {
				driver = new RemoteWebDriver(remoteSeleniumHub, DesiredCapabilities.internetExplorer());
			} else {
				File file = new File(dummyFile.getAbsolutePath()+"/drivers/IEDriverServer.exe");

				if(!file.canExecute())
					System.err.println("could not find IE driver");
				System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
				driver = new InternetExplorerDriver();
			}

			break;

		}

		return driver;
	}

	/**
	 * returns the remote hub url
	 * @return the hub url
	 */
	private static String gethubUrl() {
		if (remoteHub != null && remoteHub.length() > 10) 
			return remoteHub;
		return DEFAULT_REMOTE_SELENIUM_HUB_URL;
	}

	/**
	 * returns a JSONObject containing node data.
	 * @param driver
	 * @return the JSONObject
	 */
//	public static JSONObject getNodeData(WebDriver driver) {
//
//		if (driver instanceof RemoteWebDriver) {
//			RemoteWebDriver remoteWebDriver = (RemoteWebDriver) driver;
//			HttpHost host = new HttpHost(gethubUrl().replace("http://", ""), 4444);
//			// DefaultHttpClient client = new DefaultHttpClient();
//			HttpClient client = HttpClientBuilder.create().build();
//			URL testSessionApi;
//			try {
//				testSessionApi = new URL(gethubUrl() + ":4444" + "/grid/api/testsession?session=" + remoteWebDriver.getSessionId());
//				BasicHttpEntityEnclosingRequest r = new BasicHttpEntityEnclosingRequest("POST", testSessionApi.toExternalForm());
//				HttpResponse response = client.execute(host, r);
//				JsonObject object = new JsonObject(EntityUtils.toString(response.getEntity()));
//				return object;
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} 
//		return null;
//	}
}
