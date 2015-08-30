package com.galilsoftware.AF.core;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.galilsoftware.AF.core.logging.SelTestLog;


public class SelTestProps {
	private static SelTestProps instance;
	private static String CONF_FILE = "/TestProps.properties";
	Properties properties;
	private static boolean useLocal = true;

	private static String propsFileName;


	public static SelTestProps instance() {
		if (instance == null) {
			instance = new SelTestProps();
		}
		return instance;
	}

	private SelTestProps() {
		try {
			// try to load as resource from classpath first
			InputStream is = this.getClass().getResourceAsStream(CONF_FILE);

			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			Properties p = new Properties();

			SelTestLog.info("Loading TestProps from " +CONF_FILE + "...");
			p.load(isr);
			is.close();

			try {
				// load local props if exists
				String hostPropsFile = "";
				if(useLocal){	
					final String hostName = InetAddress.getLocalHost().getHostName();
					hostPropsFile = "/TestProps_" + hostName + ".properties";
				}else{
					hostPropsFile = "/TestProps_" + propsFileName + ".properties";
				}
				is = this.getClass().getResourceAsStream(hostPropsFile);
				
				if(is != null) {
					String message = "Loading props from " + hostPropsFile + "...";
					SelTestLog.info(message);

					isr = new InputStreamReader(is, "UTF-8");
					p.load(isr);
					is.close();
				}
			} catch (final Throwable t) {
				System.out.println("Failed loading local commonprops file");
				SelTestLog.warn("Failed loading local test props file", t);
			}

			properties = p;
		} catch (final Exception e) {
			System.out.println("Failed to initialize TestProps");
			System.err.println("Failed to initialize TestProps");
			e.printStackTrace();
			SelTestLog.error("Failed to initialize TestProps.", e);
		}
	}

	//	private void loadPropsFromURL(URL fileURL, Properties p) throws IOException {
	//		InputStream fis = null;
	//		File file = null;
	//		if (fileURL != null) {
	//			file = new File(URLDecoder.decode(fileURL.getFile(), "utf-8"));
	//			fis = new FileInputStream(file);
	//		}
	//		if (fis != null && file != null) {
	//			String message = "Loading props from " + file.getAbsolutePath() + "...";
	//			System.out.println(message);
	//			SelTestLog.info(message);
	//
	//			InputStreamReader isr = new InputStreamReader(fis, "UTF8");
	//			p.load(isr);
	//		}
	//	}

	public static String get(final String propertyName) {
		return (instance().getProperty(propertyName));
	}

	public static List<String> getArray(final String propertyName) {
		return Arrays.asList(instance().getProperty(propertyName).split(","));
	}

	public static String get(final String propertyName, final String defaultResult) {
		return instance().getProperty(propertyName, defaultResult);
	}

	public static int getInt(final String propertyName) {
		return instance().getPropertyInt(propertyName);
	}

	public static int getInt(final String propertyName, final int defaultResult) {
		return instance().getPropertyInt(propertyName, defaultResult);
	}

	public static boolean getBoolean(final String propertyName) {
		return instance().getPropertyBoolean(propertyName);
	}

	public static boolean getBoolean(final String propertyName, final boolean defaultResult) {
		return instance().getPropertyBoolean(propertyName, defaultResult);
	}

	public String getProperty(final String propertyName) {
		return getProperty(propertyName, null);
	}

	public String getProperty(final String propertyName, final String defaultResult) {
		String result = properties.getProperty(propertyName);
		if (result == null) {
			return defaultResult;
		}
		return result;
	}

	public int getPropertyInt(final String propertyName) {
		return getPropertyInt(propertyName, -1);
	}

	public int getPropertyInt(final String propertyName, final int defaultResult) {
		String result = properties.getProperty(propertyName);
		if (result == null) {
			return defaultResult;
		}
		return Integer.parseInt(result);
	}

	public boolean getPropertyBoolean(final String propertyName) {
		return getPropertyBoolean(propertyName, false);
	}

	public boolean getPropertyBoolean(final String propertyName, final boolean defaultResult) {
		String result = properties.getProperty(propertyName);
		if (result == null) {
			return defaultResult;
		}
		return Boolean.parseBoolean(result);
	}

	public static void setFileName(String testPropertiesFileName) {
		propsFileName = testPropertiesFileName;
		useLocal = false;
	}
}
