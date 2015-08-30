package com.galilsoftware.AF.core.listeners;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * Custom Annotation which declares a test to run in at the end of a test class.
 * place on top of a test method.
 * @author Amir Najjar
 *
 */
@Retention(value=RetentionPolicy.RUNTIME)
public @interface TestLast {

}
