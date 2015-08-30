package com.galilsoftware.AF.core.utilities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

/**
 * static class containing webelement utilities
 * @author Amir Najjar
 *
 */
public class WebElementUtils {

	/**
	 * try clicking a stale element, if that throws an exception, relocate and
	 * click again. note: this only works if the element was previously found by
	 * it's XPath.
	 * 
	 * @param staleElement
	 *            , the problematic element that might throw a
	 *            StaleElementReference Exception.
	 * @param index
	 *            : the index of the element in case it was in a list. if index
	 *            = 0; then it locates the first one in a list, or the only one
	 *            if its a unique locator.
	 */
	public static void tryClickingStaleElement(WebElement staleElement, int index, WebDriver driver) {
		try {
			staleElement.click();
			WebdriverUtils.waitForLoad(driver);
			return;
		} catch (StaleElementReferenceException ex) {
			staleElement = relocateStaleElement(staleElement, index, driver);
			staleElement.click();
		}
		WebdriverUtils.waitForLoad(driver);
	}

	/**
	 * relocates a stale element and uses as anchor, from anchor, locates
	 * another element and clicks it. note: this only works if the element was
	 * previously found by it's XPath.
	 * 
	 * @param staleElement
	 *            , the problematic element that might throw a
	 *            StaleElementReference Exception.
	 * @param index
	 *            : the index of the element in case it was in a list. if index
	 *            = 0; then it locates the first one in a list, or the only one
	 *            if its a unique locator.
	 */
	public static void tryClickingStaleElement(WebElement staleElement, int index, WebDriver driver, By locator) {
		try {
			staleElement.findElement(locator).click();
			WebdriverUtils.waitForLoad(driver);
			return;
		} catch (StaleElementReferenceException ex) {
			staleElement = relocateStaleElement(staleElement, index, driver);
			staleElement.findElement(locator).click();
		}
		WebdriverUtils.waitForLoad(driver);
	}

	public static void doubleClickElement(WebDriver driver, WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
		WebdriverUtils.sleep(800);
		action.click().build().perform();
		action.doubleClick(element).build().perform(); // double click on the
														// widget
		WebdriverUtils.sleep(2000);
	}

	public static void hoverOverFieldAndClickInnerElement(WebDriver driver, WebElement mainElement, By innerElementLocator) {
		Actions action = new Actions(driver);
		action.moveToElement(mainElement).build().perform();
		WebdriverUtils.sleep(800);
		mainElement.findElement(innerElementLocator).click();
		WebdriverUtils.sleep(1000);
	}

	public static WebElement relocateStaleElement(WebElement staleElement, int index, WebDriver driver) {
		String relocatePath = "";
		relocatePath = getElementXPath(driver, staleElement);
		if (index == 0)
			staleElement = driver.findElement(By.xpath(relocatePath));
		else
			staleElement = driver.findElements(By.xpath(relocatePath)).get(index);
		return staleElement;
	}

	private static String getElementXPath(WebDriver driver, WebElement element) {
		System.out.println(element.toString());
		String path = substringXpath(element.toString());
		return path;
		// return
		// (String)((JavascriptExecutor)driver).executeScript("gPt=function(c){if(c.id!==''){return'id(\"'+c.id+'\")'}if(c===document.body){return c.tagName}var a=0;var e=c.parentNode.childNodes;for(var b=0;b<e.length;b++){var d=e[b];if(d===c){return gPt(c.parentNode)+'/'+c.tagName+'['+(a+1)+']'}if(d.nodeType===1&&d.tagName===c.tagName){a++}}};return gPt(arguments[0]).toLowerCase();",
		// element);
	}

	private static String substringXpath(String pathToFind) {
		int firstIndex = pathToFind.indexOf("xpath");
		String subString = pathToFind.substring(firstIndex + 7, pathToFind.length() - 1);
		return subString;
	}

	/**
	 * Hovers over a field, and bypasses the hovering issues on firefox
	 * 
	 * @param field
	 * @param driver
	 * @param locator
	 *            - if u wanna hover to a locator within the field.
	 */
	public static void hoverOverField(WebElement field, WebDriver driver, By locator) {
		Actions builder = new Actions(driver);
		if (driver instanceof ChromeDriver) {
			Actions hoverOverRegistrar = builder.moveToElement(field);
			hoverOverRegistrar.perform();
			hoverOverRegistrar.moveToElement(field).build().perform();
		} else {
			changeVisibilityOfElement(driver, field, locator);
		}
	}

	/**
	 * changes the visibiliy of an element. if By is null, then it changes the
	 * element's visibility without locating further elements within the
	 * element.
	 * 
	 * @param driver
	 * @param elm
	 * @param locator
	 */
	public static void changeVisibilityOfElement(WebDriver driver, WebElement elm, By locator) {
		WebElement elmToChangeVisibility;
		if (locator == null)
			elmToChangeVisibility = elm;
		else
			elmToChangeVisibility = elm.findElement(locator);
		String js = "arguments[0].style.height='auto'; arguments[0].style.visibility='visible';";
		((JavascriptExecutor) driver).executeScript(js, elmToChangeVisibility);
		WebdriverUtils.sleep(1200);
	}

	public static void dragAndDropSliderToPercentage(WebDriver driver, WebElement slider, String targetPercent) {
		targetPercent = targetPercent.replace("%", "");
		Float lastPrecent = 0F;
		int xOffSetFix = 1;
		WebElement sliderWidthhandle = slider.findElement(By.className("ui-slider-handle"));
		while (sliderWidthhandle.getAttribute("style").replaceAll(".*left: ", "").replaceAll("%.*", "").replaceAll("\\.[0-9].*", "").compareTo(targetPercent) != 0) {

			float sWidth = Float.parseFloat(slider.getCssValue("width").replace("px", ""));
			float currentPercent = Float.parseFloat(sliderWidthhandle.getAttribute("style").replaceAll(".*left: ", "").replaceAll("%.*", "")
					.replaceAll("\\.[0-9].*", ""));
			float precent = Float.parseFloat(targetPercent) - currentPercent;
			int xOffset = Math.round(sWidth * precent / 100);
			if (currentPercent == lastPrecent) {
				xOffset += xOffSetFix;
				xOffSetFix++;
			}
			new Actions(driver).dragAndDropBy(sliderWidthhandle, xOffset, 0).perform();
			// new
			// Actions(driver).clickAndHold(sliderWidthhandle).moveByOffset(xOffset,
			// 0).release().build().perform();
			lastPrecent = currentPercent;
		}
	}

	public static void moveSliderToPx(WebDriver driver, WebElement sliderDiv, WebElement widthInput, String value) {
		int distanation = Integer.parseInt(value);
		int trackingBox;
		float step = 0f;
		int targetValue;
		WebElement sliderBar = sliderDiv.findElement(By.className("ui-slider-range"));
		WebElement sliderhandler = sliderDiv.findElement(By.className("ui-slider-handle"));

		Actions builder = new Actions(driver);

		sliderBar.getSize().getHeight();
		int width = sliderDiv.getSize().getWidth();
		// Learn the min Value
		builder.dragAndDropBy(sliderhandler, -1 * (width), 0);
		builder.build().perform();
		int minValue = Integer.parseInt(widthInput.getAttribute("value").replaceAll("px", ""));
		// Learn the max value
		builder.dragAndDropBy(sliderhandler, width, 0);
		builder.build().perform();
		int maxValue = Integer.parseInt(widthInput.getAttribute("value").replaceAll("px", ""));
		trackingBox = Math.abs(minValue) + Math.abs(maxValue);
		step = width / trackingBox;
		if (distanation > 0) {
			targetValue = (int) (step) * (Math.abs(distanation) + Math.abs(minValue));
		} else {
			targetValue = (int) (step) * Math.abs(distanation);
		}
		// sliderhandler.click();

		builder.dragAndDropBy(sliderhandler, targetValue, 0);
		builder.build().perform();

	}

	public static void moveSliderToPx(WebDriver driver, WebElement sliderDiv, String value) {
		int percent = Integer.parseInt(value);

		WebElement sliderBar = sliderDiv.findElement(By.className("ui-slider-range"));
		WebElement sliderhandler = sliderDiv.findElement(By.className("ui-slider-handle"));

		Actions builder = new Actions(driver);

		sliderBar.getSize().getHeight();
		int width = sliderBar.getSize().getWidth();
		float step = (float) width / 100;

		// sliderhandler.click();
		builder.dragAndDropBy(sliderhandler, -100, 0);
		builder.build().perform();
		builder.dragAndDropBy(sliderhandler, (int) (percent * step), 0);
		builder.build().perform();

	}

	public static void moveSliderToPx(WebDriver driver, WebElement sliderDiv, WebElement widthInput, int value) {
		float step = 0f;
		WebElement sliderBar = sliderDiv.findElement(By.className("ui-slider-range"));
		WebElement sliderhandler = sliderDiv.findElement(By.className("ui-slider-handle"));
		int trackingBox = 0;
		Actions builder = new Actions(driver);

		sliderBar.getSize().getHeight();
		int width = sliderDiv.getSize().getWidth();
		// Learn the Min Value
		builder.dragAndDropBy(sliderhandler, -1 * (width), 0);
		builder.build().perform();
		int minValue;
		if (widthInput.getAttribute("value").equals("")) {
			minValue = 0;
		} else
			minValue = Integer.parseInt(widthInput.getAttribute("value").replaceAll("px", ""));
		System.out.println("Min Value: " + minValue);
		// Learn the max value
		builder.dragAndDropBy(sliderhandler, width, 0);
		builder.build().perform();
		int maxValue = Integer.parseInt(widthInput.getAttribute("value").replaceAll("px", ""));
		System.out.println("Max Value: " + maxValue);
		trackingBox = Math.abs(minValue) + Math.abs(maxValue);
		step = Float.valueOf(width) / Float.valueOf(trackingBox);

		// sliderhandler.click();
		int targetValue = (int) (step) * (Math.abs(value));

		builder.dragAndDropBy(sliderhandler, -1 * (width), 0);
		builder.build().perform();

		sliderhandler = sliderDiv.findElement(By.className("ui-slider-handle"));
		builder = new Actions(driver);

		builder.dragAndDropBy(sliderhandler, targetValue, 0);
		builder.build().perform();

	}

	public static List<String> getTopTabs(WebDriver driver) {
		List<String> names = new ArrayList<String>();
		List<WebElement> tabs = driver.findElements(By.className("rightPanelTab"));
		for (WebElement elm : tabs) {
			names.add(elm.getText());
		}
		return names;
	}

	public static List<String> getTopTabsD1(WebDriver driver) {
		List<String> names = new ArrayList<String>();
		List<WebElement> tabs = driver.findElement(By.className("tabs")).findElements(By.className("tab"));
		for (WebElement elm : tabs) {
			names.add(elm.getText());
		}
		return names;
	}

	public static void moveSliderToPercent(WebDriver driver, WebElement sliderDiv, int percent) {

		sliderDiv.findElement(By.className("ui-slider-range"));
		WebElement sliderhandler = sliderDiv.findElement(By.className("className"));

		Actions builder = new Actions(driver);

		sliderDiv.getSize().getHeight();
		int width = sliderDiv.getSize().getWidth();

		float step = (float) width / 100;

		builder.dragAndDropBy(sliderhandler, -100, 0);
		builder.build().perform();
		builder.dragAndDropBy(sliderhandler, (int) (percent * step), 0);
		builder.build().perform();

	}

	/**
	 * Pick random element.
	 * 
	 * @param list
	 *            the list
	 * @return the web element
	 */
	public static WebElement pickRandomElement(List<WebElement> list) {
		return list.get(getRandomNumberByRange(0, list.size() - 1));
	}

	/**
	 * Gets the random number by range.
	 * 
	 * @param min
	 *            the min
	 * @param max
	 *            the max
	 * @return the random number by range
	 */
	public static int getRandomNumberByRange(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		// System.out.println("picked random number " + randomNum);
		return randomNum;
	}

	/**
	 * Gets the random color.
	 * 
	 * @return the random color
	 */
	public static int getRandomColor() {
		return getRandomNumberByRange(0, 255);
	}

	public static String getImageSrc(WebElement imageElement) {
		if (imageElement.getTagName() == "img") {
			return imageElement.getAttribute("src");
		} else {
			return imageElement.findElement(By.tagName("img")).getAttribute("src");
		}

	}

	public static boolean sameColor(WebElement a, WebElement b, String cssAttribute) {
		String aColor = getColorAsRgb(a.getCssValue(cssAttribute));
		String bColor = getColorAsRgb(b.getCssValue(cssAttribute));
		return aColor.equals(bColor);
	}

	public static boolean sameColor(String color1, String color2) {
		String aColor = getColorAsRgb(color1);
		String bColor = getColorAsRgb(color2);
		return aColor.equals(bColor);
	}

	public static String getColorAsRgb(String color1) {

		String colorprefix = "rgba";
		if (!color1.contains("rgba")) {
			colorprefix = "rgb";
		}
		Splitter extractParams = Splitter.on(colorprefix).omitEmptyStrings().trimResults();
		Splitter splitParams = Splitter.on(CharMatcher.anyOf("(),").or(CharMatcher.WHITESPACE)).omitEmptyStrings().trimResults();

		List<String> rgba = new ArrayList<String>();

		for (String s : splitParams.split(Iterables.getOnlyElement(extractParams.split(color1)))) {
			rgba.add(s);
		}
		int r = Integer.parseInt(rgba.get(0));
		int g = Integer.parseInt(rgba.get(1));
		int b = Integer.parseInt(rgba.get(2));
		return new Color(r, g, b).toString();
	}

	public static void blurElement(WebDriver driver, WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("return arguments[0].blur();", element);
		// JavascriptLibrary javascript = new JavascriptLibrary();
		// javascript.callEmbeddedSelenium(driver, "triggerEvent", element,
		// "blur");
	}

	public static void clickElementEvent(WebDriver driver, WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
		/*
		 * JavascriptLibrary javascript = new JavascriptLibrary();
		 * javascript.callEmbeddedSelenium(driver, "triggerEvent", element,
		 * "click");
		 */
	}

	/**
	 * Attempts to click on an element multiple times (to avoid stale element
	 * exceptions caused by rapid DOM refreshes due to jquery/GWT and other
	 * toolkits)
	 * 
	 * @param driver
	 *            The WebDriver
	 * @param elm
	 *            By element locator
	 */
	/*
	 * public static void Click(WebDriver driver, By elm) {
	 * 
	 * final int MAXIMUM_WAIT_TIME = 2; final int MAX_STALE_ELEMENT_RETRIES = 3;
	 * 
	 * WebDriverWait wait = new WebDriverWait(driver, MAXIMUM_WAIT_TIME); int
	 * retries = 0; while (true) { try {
	 * wait.until(ExpectedConditions.elementToBeClickable(elm)).click();
	 * 
	 * return; } catch (StaleElementReferenceException e) { if (retries <
	 * MAX_STALE_ELEMENT_RETRIES) { retries++; continue; } else { throw e; } } }
	 * }
	 */

	/**
	 * Attempts to click on an element multiple times (to avoid stale element
	 * exceptions caused by rapid DOM refreshes due to jquery/GWT and other
	 * toolkits)
	 * 
	 * @param driver
	 *            The WebDriver
	 * @param elm
	 *            By element locator
	 */
	/*
	 * public static void Click(WebDriver driver, WebElement elm) {
	 * Click(driver,getWebElementLocator(elm)); }
	 */

	/*
	 * private static By getWebElementLocator(WebElement elm){ String foundBy =
	 * elm.toString(); String locatorType = foundBy.substring(0,
	 * foundBy.indexOf(':')-1); String locatorQuery =
	 * foundBy.substring(foundBy.indexOf(' ')+1, foundBy.length()-1); switch
	 * (locatorType){ case "id": return By.id(locatorQuery); case "class name":
	 * return By.className(locatorQuery); case "xpath": return
	 * By.xpath(locatorQuery); } return null; }
	 */
}
