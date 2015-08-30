package com.galilsoftware.AF.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestParamsThreadPool {

	/**
	 * A thread friendly HashMap Mapping types of objects as a string to their relevant Objects.
	 */
	private static ThreadLocal<Map<String, Object>> testParameters = new ThreadLocal<Map<String, Object>>();

	// keys for putting or getting parameters from TestParameters
	public static final String KEY_WEB_DRIVER = "driver";
	public static final String KEY_SECOND_DRIVER = "secondDriver";
	public static final String METHOD_ORDER = "methodeOrder";
	public static final String TEST_NAME = "testName";
	public static final String DATABASE = "db";

	private static Map<String, Object> getTestParameters() {
		if (testParameters.get() == null) {
			testParameters.set(new ConcurrentHashMap<String, Object>(10));
		}
		return testParameters.get();
	}

	/**
	 * return the Thread Friendly Map holding instances of relevant test data.
	 * @return Map<String, Object> - The map.
	 */
	public static Map<String, Object> instance() {
		return getTestParameters();
	}

	/**
	 * puts a new object in the map.
	 * @param key - The Object's name
	 * @param object - the Object
	 */
	public static void put(String key, Object object) {
		getTestParameters().put(key, object);
	}

	/**
	 * puts a new object in the map.
	 * @param key - The Object's name
	 * @return object - the Object
	 */
	public static Object get(String key) {
		return getTestParameters().get(key);
	}

	/**
	 *  removes an object from the map.
	 * @param key - The Object's name
	 */
	public static void remove(String key) {
		getTestParameters().remove(key);
	}
	
	/**
	 * clears the Map.
	 */
	public static void clear() {
		getTestParameters().clear();
	}
}
