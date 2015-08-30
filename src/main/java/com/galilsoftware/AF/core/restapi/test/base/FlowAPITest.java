package com.galilsoftware.AF.core.restapi.test.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.galilsoftware.AF.core.logging.SelTestLog;
import com.galilsoftware.AF.core.restapi.RestObject;

public abstract class FlowAPITest extends BaseRestAPITest{

	Map<String, String> parametersMap = new HashMap<String, String>();

	protected void runFlow(String action) throws JsonProcessingException, IOException {
		
		parametersMap.clear();
		
		List<String> params = getActionParams(action);

		for(String param: params){
			String[] keyVal = param.split("=");
			parametersMap.put(keyVal[0], keyVal[1]);
			action = action.replaceAll(keyVal[1], "").replaceFirst("=", "");
		}
		
		replaceRestObjectParameters(action);
		
		Collection<RestObject> objs = keywordsMap.get(action);
		
		for(RestObject restObj : objs) {
			restClient.processCall(restObj.getRestCall());
			restValidator.validate(restObj.getRestResponse());
		}
	}

//	private void mapParams(List<String> list) {
//		for( String param : list ){
//			parametersMap
//		}
//	}

	public List<String> getActionParams(String action) {
		List<String> params = new ArrayList<String>();
		Pattern pattern = Pattern.compile("\\<(.*?)\\>"); 
		Matcher matcher = pattern.matcher(action); 
		while(matcher.find()) {
			params.add(matcher.group(1).toString().replaceAll("<", "").replaceAll(">", "")); 
		}
		return params;

	}
	
	private void replaceRestObjectParameters(String action){
		
		Collection<RestObject> objs = keywordsMap.get(action);
		
		if(objs.size()==0)
			SelTestLog.warn("Could not find action: "+action+" from the pre-defined keywords");
		
		for(RestObject restObj : objs){
			restObj.getRestCall().processParameters(parametersMap);
			restObj.getRestResponse().processParameters(parametersMap);
		}
	}
	
}