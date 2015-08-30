package com.galilsoftware.AF.core.restapi.test.demo;

import java.io.IOException;
import java.util.Hashtable;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.galilsoftware.AF.core.listeners.DataProviderParams;
import com.galilsoftware.AF.core.listeners.MethodListener;
import com.galilsoftware.AF.core.listeners.TestNGListener;
import com.galilsoftware.AF.core.restapi.test.base.FlowAPITest;


@Listeners({ MethodListener.class, TestNGListener.class })
public class DemoRestTest extends FlowAPITest{
	
	@Test(dataProvider = "ExcelFileLoader", enabled = true)
	@DataProviderParams(sheet = "Flows" , area = "Flows")
	public void loginTest(Hashtable<String, String> data) throws JsonProcessingException, IOException {
		final String TEST_ACTION = data.get("Action");
		runFlow(TEST_ACTION);
	}
	
}

