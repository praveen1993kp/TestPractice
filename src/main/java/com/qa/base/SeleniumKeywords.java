package com.qa.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface SeleniumKeywords {

	public void getDriverInstance();
	
	public void initializeDriver();
	
	public WebDriver getDriver();
	
	public void loadUrl(String urlToBeLoaded);
	
	public void closeBrowser();
	
	public void setWebDriverWait();
	
	public WebElement waitUntilElementIsPresent(By by);
	
	public WebElement waitUntilElementIsClickable(By by);
	
	public WebElement waitUntilElementIsVisible(By by);
	
	public WebElement getElement(By by);
	
	public WebElement getElement(String locatorType, String locator);
	
	public void clickElement(By by);
	
	public void enterText(By by, String textToBeEntered);
	
	public String getTextFromElement(By by);
}
