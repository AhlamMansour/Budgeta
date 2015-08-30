package com.galilsoftware.AF.core.utilities;

import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;

/**
 * This class is used to invoke methods taken from an Excel file as data driven tests with the help of Reflection API.
 * @author Amir Najjar
 *
 */
public class DDTUtils {
	
	public static void runDDTMethod(Hashtable<String, String> data) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException{
		String methodName = (String) data.get("method");
		String className = (String) data.get("className");

		Class<?> clazz = Class.forName(className);
		clazz.getDeclaredMethod(methodName).invoke(clazz.newInstance());
	}
	
}
