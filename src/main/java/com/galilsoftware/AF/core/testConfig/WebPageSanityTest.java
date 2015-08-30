package com.galilsoftware.AF.core.testConfig;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;

import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestNGListener;

/**
 * @author Nael Abd Aljawad
 */

@Listeners({ MethodListener.class, TestNGListener.class })
public class WebPageSanityTest extends TearDown {

	private HttpURLConnection http;

	/**
	 * This method checks the verification of images in each page
	 */
	public void imagesVerificationTest() throws ClientProtocolException, IOException {

		List<WebElement> elements = driver.findElements(By.tagName("img"));

		for (WebElement elm : elements) {

			String src = elm.getAttribute("src");
			if (src != null && (src.toCharArray().length!=0)) {

				HttpClient client = HttpClientBuilder.create().build();
				HttpResponse response = null;
				
				try {
					response = client.execute(new HttpGet(src));
				} catch (HttpHostConnectException connectionException) {
					Assert.fail("Failed to initiate connection with host: " + connectionException.getMessage(), connectionException);
				}

				int returnedStatusCode = response.getStatusLine().getStatusCode();
				if (returnedStatusCode != 200) {
					Assert.assertTrue(false, "Image with class name " + elm.getClass() + "and source name " + elm.getAttribute("src") + "returned Error Code[" + returnedStatusCode
							+ "]");
				}
			}
		}
	}

	public void validateHttpStatusCode() throws IOException {
		Assert.assertTrue(isValidPageStatusCode(), "The page has invalid status code" + http.getResponseMessage());
	}

	/**
	 * Private methods
	 */


	private boolean isValidPageStatusCode() {
		try {
			if (getHttpResponseStatusCode(driver.getCurrentUrl()) != 302) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private int getHttpResponseStatusCode(String URL) throws IOException {
		URL url = new URL(URL);
		http = (HttpURLConnection) url.openConnection();
		return http.getResponseCode();
	}

}
