package com.galilsoftware.AF.core.listeners;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom Annotation for providing parameters to the Data Providers which are provided from TestNG for DDT usage.
 * @author Amir Najjar
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataProviderParams {

	String sheet();
	
	String area();
	
}
