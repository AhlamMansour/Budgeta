package com.galilsoftware.AF.core.listeners;

import java.util.ArrayList;
import java.util.List;

import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.galilsoftware.AF.core.TestParamsThreadPool;

/**
 * Method listener for implementing the TestFirst and TestLast annotations and to sort the methods order
 * @author Amir Najjar
 *
 */
public class MethodListener implements IMethodInterceptor {

	@SuppressWarnings("unchecked")
	@Override
	public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext arg1) {
		ArrayList<IMethodInstance> testFirstList = new ArrayList<IMethodInstance>();
		ArrayList<IMethodInstance> testLastList = new ArrayList<IMethodInstance>();
		ArrayList<IMethodInstance> withoutDependenciesList = new ArrayList<IMethodInstance>();
		ArrayList<IMethodInstance> dependsOnMethodsList = new ArrayList<IMethodInstance>();
		ArrayList<IMethodInstance> result = new ArrayList<IMethodInstance>();

		if (!"true".equals(arg1.getCurrentXmlTest().getParameter("Sanity"))) {

			// The test methods list is saved in the TestParameters in order to enable the use of dependsOnMethods
			if (TestParamsThreadPool.get(TestParamsThreadPool.METHOD_ORDER) == null) {

				for (IMethodInstance method : methods) {
					if (method.getMethod().getConstructorOrMethod().getMethod().isAnnotationPresent(TestFirst.class)) {
						method.getMethod().setPriority(Short.MIN_VALUE);
						testFirstList.add(method);
					} else if (method.getMethod().getConstructorOrMethod().getMethod().isAnnotationPresent(TestLast.class)) {
						method.getMethod().setPriority(Short.MAX_VALUE);
						testLastList.add(method);
					} else if (method.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class).dependsOnMethods().length == 0) {
						withoutDependenciesList.add(method);
					} else {
						dependsOnMethodsList.add(method);
					}
				}
				for (IMethodInstance method : testFirstList) {
					result.add(method);
				}
				for (IMethodInstance method : withoutDependenciesList) {
					result.add(method);
				}

				ArrayList<String> resultMethodNames = new ArrayList<String>();
				for (IMethodInstance method : result) {
					resultMethodNames.add(method.getMethod().getMethodName());
				}
				int counter = 0;
				int maxCount = dependsOnMethodsList.size();
				ArrayList<IMethodInstance> dependsOnMethodsTempList = new ArrayList<IMethodInstance>(dependsOnMethodsList);

				while (!dependsOnMethodsTempList.isEmpty() && counter < maxCount) {
					for (IMethodInstance method : dependsOnMethodsTempList) {
						String[] dependsOnMethods = method.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class).dependsOnMethods();
						boolean add = true;
						for (String d : dependsOnMethods) {
							if (!resultMethodNames.contains(d)) {
								add = false;
								break;
							}
						}
						if (add) {
							result.add(method);
							resultMethodNames.add(method.getMethod().getMethodName());
							dependsOnMethodsList.remove(method);
						} else {

						}
					}
					counter++;
					dependsOnMethodsTempList.clear();
					dependsOnMethodsTempList.addAll(dependsOnMethodsList);
				}

				for (IMethodInstance method : testLastList) {
					result.add(method);
				}

				TestParamsThreadPool.put(TestParamsThreadPool.METHOD_ORDER, result);
			} else {
				result = (ArrayList<IMethodInstance>) TestParamsThreadPool.get(TestParamsThreadPool.METHOD_ORDER);
				TestParamsThreadPool.put(TestParamsThreadPool.METHOD_ORDER, new ArrayList<IMethodInstance>());
				/*
				 * for (IMethodInstance iMethodInstance : result) {
				 * System.out.println("Test Class : " + iMethodInstance.getMethod().getTestClass().getName());
				 * System.out.println("Real Test Class : " + iMethodInstance.getMethod().getTestClass().getRealClass());
				 * System.out.println("Is After suite Configuration " + iMethodInstance.getMethod().isAfterSuiteConfiguration());
				 * System.out.println("Is Before suite Configuration " + iMethodInstance.getMethod().isBeforeSuiteConfiguration());
				 * System.out.println("Is Enabled " + iMethodInstance.getMethod().getEnabled());
				 * System.out.println("Test Name : " + iMethodInstance.getMethod().getMethodName());
				 * }
				 */
			}
			if (TestParamsThreadPool.get(TestParamsThreadPool.METHOD_ORDER).toString().length() > 2) {
				System.out.println("===== Running " + result.size() + " Test Methods of " + TestParamsThreadPool.get(TestParamsThreadPool.TEST_NAME).toString() + " =====");
				System.out.println("Methods Order:");
				for (IMethodInstance iMethodInstance : result) {
					/*
					 * System.out.println("Test Class : " + iMethodInstance.getMethod().getTestClass().getName());
					 * System.out.println("Real Test Class : " + iMethodInstance.getMethod().getTestClass().getRealClass());
					 * System.out.println("Is After suite Configuration " + iMethodInstance.getMethod().isAfterSuiteConfiguration());
					 * System.out.println("Is Before suite Configuration " + iMethodInstance.getMethod().isBeforeSuiteConfiguration());
					 * System.out.println("Is Enabled " + iMethodInstance.getMethod().getEnabled());
					 * System.out.println("Test Name : " + iMethodInstance.getMethod().getMethodName());
					 * System.out.println(iMethodInstance.getMethod().getTestClass().getName());
					 */
					System.out.println(iMethodInstance.getMethod().getMethodName());
				}
			}
			return result;
		}
		return methods;
	}

}
