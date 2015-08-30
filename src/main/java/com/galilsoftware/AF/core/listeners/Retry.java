package com.galilsoftware.AF.core.listeners;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;


/**
 * implements the IRetryAnalyzer interface and sets the number of times you would like to retry a failed test.
 * @author Amir Najjar
 */
public class Retry implements IRetryAnalyzer {
    private int retryCount         = 0;
    private int maxRetryCount     = 1;   // retry a failed test

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount <maxRetryCount) {
            retryCount++;
            return true;
        }
        return false;
    }
}