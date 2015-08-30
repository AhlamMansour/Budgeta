package com.galilsoftware.AF.core.listeners;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;

/**
 * This class implements the IAnnotationTransformer interface and redirects tests to the proper Retry class.
 * @author Amir Najjar
 */
public class RetryListener implements IAnnotationTransformer {
	
    @SuppressWarnings("rawtypes")
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        IRetryAnalyzer retry = annotation.getRetryAnalyzer();
        if (retry == null) {
            annotation.setRetryAnalyzer(Retry.class);
        }
    }
}