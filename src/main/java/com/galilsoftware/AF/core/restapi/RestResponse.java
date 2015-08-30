package com.galilsoftware.AF.core.restapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * This class represents a rest response Object.
 * 
 * @author Waseem Hamshawi & Amir Najjar
 */
public class RestResponse {

	int responseCode; //response code of status line (i.e: 200)

	String reasonPhrase; //response message of status line (i.e: OK)

	Map<String, String> expectedHeaders; //expected headers to be found in response

	List<String> expectedBody; //expected body to be found in response

	public RestResponse() {
		expectedHeaders = new HashMap<String, String>();
		expectedBody = new ArrayList<String>();
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getReasonPhrase() {
		return reasonPhrase;
	}

	public void setReasonPhrase(String reasonPhrase) {
		this.reasonPhrase = reasonPhrase;
	}

	public Map<String, String> getExpectedHeaders() {
		return expectedHeaders;
	}

	public void setExpectedHeaders(Map<String, String> expectedHeaders) {
		this.expectedHeaders = expectedHeaders;
	}

	public List<String> getExpectedBody() {
		return expectedBody;
	}

	public void setExpectedBody(List<String> expectedBody) {
		this.expectedBody = expectedBody;
	}

	public enum ResponseData{
		HTTP_RESPONSE("HTTP Response"), EXPECTED_HEADERS("Expected Headers"), EXPECTED_BODY("Expected Body"), REQUEST_SCHEMA("Request Schema"), RESPONSE_SCHEMA("Response Schema");

		String name;

		private ResponseData(String name) {
			this.name = name;
		}

		public String getName(){
			return name;
		}
	}

	public void processParameters(Map<String, String> parametersMap) {

		//Headers
		Set<String> formKeySet = expectedHeaders.keySet();
		for(String key : formKeySet) {
			if(key.contains("$")){
				String oldValue = expectedHeaders.get(key);
				expectedHeaders.remove(key);
				String dollarLessKey = key.replaceAll("$", "");	
				expectedHeaders.put(parametersMap.get(dollarLessKey), oldValue);
			}

			if(expectedHeaders.get(key).contains("$")){
				String oldVal = expectedHeaders.get(key);
				expectedHeaders.remove(key);
				String dollarLessVal = oldVal.replaceAll("$", "");
				expectedHeaders.put(key, dollarLessVal);
			}

		}

		//Body
		for(String bodyParam : expectedBody) {
			String oldBody = bodyParam;
			//if(pathParam.contains("$")){
			//String dollarLessParam = pathParam.replaceAll("\\$", "");
			//String newParam = parametersMap.get(dollarLessParam);
			Set<String> paramsKeySet =  parametersMap.keySet();
			for(String key : paramsKeySet) {
				if(bodyParam.contains("$"+key)){
					bodyParam = bodyParam.replaceAll("\\$"+key, parametersMap.get(key));
				}
			}
			expectedBody.remove(oldBody);
			expectedBody.add(bodyParam);
			//}
		}



	}

}
