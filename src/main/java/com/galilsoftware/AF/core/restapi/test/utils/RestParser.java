package com.galilsoftware.AF.core.restapi.test.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import com.galilsoftware.AF.core.restapi.RestCall;
import com.galilsoftware.AF.core.restapi.RestCall.CallData;
import com.galilsoftware.AF.core.restapi.RestResponse;
import com.galilsoftware.AF.core.restapi.RestResponse.ResponseData;
import com.galilsoftware.AF.core.restapi.client.RestClient.HttpMethod;

/**
 * This is the parser class which interprets a rest call from an excel row to an
 * actual rest call.
 * 
 * @author Amir Najjar
 */
public class RestParser {

	static List<String> listParams;

	public static RestCall parseCall(Hashtable<String, String> rowData) {
		RestCall call = new RestCall();

		for (CallData callData : CallData.values()) {
			String data = rowData.get(callData.getName());

			switch (callData) {
			
			case REQUEST_BODY:
				call.setBody(data);
				break;
				
			case FORM_PARAMETERS:
				call.setFormParamsMap(createParamMap(data));
				break;

			case PATH_PARAMETERS:
				call.setPathParamsList(createParamList(data));
				break;

			case QUERY_PARAMETERS:
				call.setQueryParamsMap(createParamMap(data));
				break;

			case HTTP_METHOD:
				call.setMethod(HttpMethod.valueOf(data.toUpperCase()));
				break;

			case RELATIVE_URL:
				call.setRelativeUrl(data);
				break;
				
			case REQUEST_TYPE:
				call.setMediaType(MediaType.valueOf(data));
				break;
				
			default:
				break;

			}

		}
		return call;
	}
	
	public static RestResponse parseResponse(Hashtable<String, String> rowData) {
		RestResponse response = new RestResponse();

		for (ResponseData callData : ResponseData.values()) {
			String data = rowData.get(callData.getName());

			switch (callData) {
			case HTTP_RESPONSE:
				response.setResponseCode(parseStatusCode(data));
				response.setReasonPhrase(parseStatusMessage(data));
				break;
			case EXPECTED_BODY:
				response.setExpectedBody(createParamList(data));
				break;
			case EXPECTED_HEADERS:
				response.setExpectedHeaders(createParamMap(data));
				break;
			case REQUEST_SCHEMA:
				break;
			case RESPONSE_SCHEMA:
				break;
			}

		}
		return response;
	}

	private static List<String> createParamList(String params) {
		listParams = new ArrayList<String>();
		if(params.isEmpty())
			return listParams;
		
		String[] paramsArr = params.split(";");

		for (String param : paramsArr) {
			listParams.add(param);
		}
		return listParams;
	}
	
	private static Map<String, String> createParamMap(String data){
		splitPairsParams(data);
		Map<String,String> paramsMap = new HashMap<String, String>();
		for(int i = 0; i< listParams.size() ; i=i+2 ){
			paramsMap.put(listParams.get(i), listParams.get(i+1));
		}
		return paramsMap;
	}

	private static List<String> splitPairsParams(String params) {
		listParams = new ArrayList<String>();
		if(params.isEmpty())
			return listParams;
		
		String[] pairs = params.split(";");

		for (String pair : pairs) {
			String[] parameters = pair.split("=");
			listParams.add(parameters[0]);
			listParams.add(parameters[1]);
		}
		return listParams;
	}
	
	private static int parseStatusCode(String statusLine) {
		String[] statusLineArr = statusLine.split("\\s+");
		return Integer.valueOf(statusLineArr[0]);
	}
	
	private static String parseStatusMessage(String statusLine) {
		String[] statusLineArr = statusLine.split("\\s+");
		return statusLineArr[1];
	}


}
