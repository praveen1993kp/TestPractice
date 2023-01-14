package com.qa.base;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GenericKeywords implements SeleniumKeywords, NonSeleniumKeywords{

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	
	private static ThreadLocal<WebDriverWait> wait  = new ThreadLocal<WebDriverWait>();
	
	public static Properties prop;
	
	
	@Override
	public void getDriverInstance() {
		String browser = "chrome";
		switch(browser) {
		case "chrome":
			driver.set(new ChromeDriver());
		case "firefox":
			driver.set(new FirefoxDriver());
		}	
	}
	
	@Override
	public void initializeDriver() {
		getDriverInstance();
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
	}
	
	@Override
	public WebDriver getDriver() {
		return driver.get();
	}
	
	@Override
	public void loadUrl(String url) {
		getDriver().get(url);
	}
	
	@Override
	public void closeBrowser() {
		getDriver().close();
	}
	
	@Override
	public void setWebDriverWait() {
		wait.set(new WebDriverWait(getDriver(),Duration.ofSeconds(15)));
	}
	
	@Override
	public WebElement waitUntilElementIsVisible(By by) {
		return wait.get().until(ExpectedConditions.visibilityOfElementLocated(by));
	}
	
	@Override
	public WebElement waitUntilElementIsClickable(By by) {
		return wait.get().until(ExpectedConditions.elementToBeClickable(by));
	}
	
	@Override
	public WebElement waitUntilElementIsPresent(By by) {
		return wait.get().until(ExpectedConditions.presenceOfElementLocated(by));
	}
	
	@Override
	public WebElement getElement(By by) {
		return waitUntilElementIsPresent(by);
	}
	
	@Override
	public WebElement getElement(String locatorType, String locator) {
		switch(locatorType) {
		case "xpath":
			return getElement(By.xpath(locator));
		case "name":
			return getElement(By.name(locator));
		case "id":
			return getElement(By.id(locator));
		default:
			throw new RuntimeException("Invalid Locator type");	
		}
	}
	
	@Override
	public void clickElement(By by) {
		try {
			waitUntilElementIsClickable(by).click();
		} catch(ElementClickInterceptedException e) {
			try {
				new Actions(getDriver()).click(waitUntilElementIsClickable(by));
			} catch(ElementClickInterceptedException e2) {
				((JavascriptExecutor)getDriver()).executeScript("arguments[0].click()", waitUntilElementIsClickable(by));
			}	
		} 		
	}
	
	@Override
	public void enterText(By by,String text) {
		waitUntilElementIsPresent(by).sendKeys(text);
	}
	
	@Override
	public String getTextFromElement(By by) {
		return waitUntilElementIsPresent(by).getText();
	}
	
	@Override
	public int takeScreenShot() throws IOException {
		int ranNum = (int) (Math.random()*999999+1000000);

		TakesScreenshot screenshot = (TakesScreenshot)driver;
		File source = screenshot.getScreenshotAs(OutputType.FILE);
		File target = new File("./snaps/img"+ranNum+".png");
		FileUtils.copyFile(source, target);

		return ranNum;
	}
	
	@Override
	public String getProperty(String propertyName) {
		return prop.getProperty(propertyName);
	}
	
	
	
	
	
	
}
