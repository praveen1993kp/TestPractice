package com.qa.base;

import java.io.IOException;

public interface NonSeleniumKeywords {

	int takeScreenShot() throws IOException;

	String getProperty(String propertyName);
	
}
