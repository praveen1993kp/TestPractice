package com.qa.reporter;

import org.testng.ITestResult;

import com.aventstack.extentreports.Status;

public class ReportListener {
	public void onTestFailure(ITestResult result) {
        ExtentReport.log(Status.FAIL, "Test Execution Failed");
    }

    public void onTestSkipped(ITestResult result) {
        ExtentReport.log(Status.SKIP, "Test Execution Skipped");
    }
}
