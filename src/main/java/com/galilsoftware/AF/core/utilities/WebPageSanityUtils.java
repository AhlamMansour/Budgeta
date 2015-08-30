package com.galilsoftware.AF.core.utilities;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;

import com.galilsoftware.AF.core.TestParamsThreadPool;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestNGListener;

/**
 * @author Nael Abd Aljawad
 */

@Listeners({ MethodListener.class, TestNGListener.class })
public class WebPageSanityUtils {

	private static HttpURLConnection http;

	public void imagesVerificationTest() {
		imagesVerification();
	}

	/**
	 * click an element and validate the response code is 200
	 * 
	 * @param locator
	 *            - locator to the element. null if you don't wanna click an
	 *            element.
	 * @throws IOException
	 */
	 public static void clickElementAndvalidateHttpStatusCodeTest(By locator)
	 throws IOException {
	 if(locator != null)
	 ((WebDriver)
	 TestParamsThreadPool.get(TestParamsThreadPool.KEY_WEB_DRIVER)).findElement(locator).click();
	 Assert.assertTrue(isValidPageStatusCode(),
	 "The page has invalid status code" + http.getResponseMessage());
	 }
	

	/**
	 * Private methods
	 * 
	 * @return
	 */

	 private static boolean isValidPageStatusCode() {
	 try {
	 if (getHttpResponseStatusCode(((WebDriver)
	 TestParamsThreadPool.get(TestParamsThreadPool.KEY_WEB_DRIVER)).getCurrentUrl())
	 < 400) {
	 return true;
	 }
	 } catch (IOException e) {
	 e.printStackTrace();
	 }
	 return false;
	 }

	public static int getHttpResponseStatusCode(String URL) throws IOException {
		if (URL.contains("https"))
			URL = URL.replace("https", "http");
		URL url = new URL(URL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.connect();

		return connection.getResponseCode();

	}

	private void imagesVerification() {
		/**
		 * This method checks the verification of images in each page
		 */
		List<WebElement> elements = ((WebDriver) TestParamsThreadPool
				.get(TestParamsThreadPool.KEY_WEB_DRIVER)).findElements(By
				.tagName("img"));
		try {
			for (WebElement elm : elements) {

				if (elm.getAttribute("src") != null) {

					HttpClient client = HttpClientBuilder.create().build();
					HttpResponse response = client.execute(new HttpGet(elm
							.getAttribute("src")));

					int returnedStatusCode = response.getStatusLine()
							.getStatusCode();
					if (returnedStatusCode != 200) {
						Assert.assertTrue(
								false,
								"Image with class name " + elm.getClass()
										+ "and source name "
										+ elm.getAttribute("src")
										+ "returned Error Code["
										+ returnedStatusCode + "]");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// public String clickAndGetHttpRequestURL(WebElement element) {
	// HttpServletRequest request = null;
	// StringBuffer url = request.getRequestURL();
	// WebdriverUtils.sleep(500);
	// element.click();request.getRequestURL();
	// WebdriverUtils.sleep(500);
	// return request.getHeader("Request URL");
	//
	// }
}
