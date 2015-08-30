package com.galilsoftware.AF.core.restapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import com.galilsoftware.AF.core.restapi.client.RestClient.HttpMethod;

/**
 * This class represents a Rest Call Object
 * 
 * @author Waseem Hamshawi & Amir Najjar
 */
public class RestCall {

	String  relativeUrl;

	HttpMethod method;

	Map<String, String> queryParamsMap;  // MAP

	List<String> pathParamsList;   // LIST

	Map<String, String> formParamsMap;  //MAP

	String body;

	MediaType mediaType;

	public RestCall() {
		queryParamsMap = new HashMap<String, String>();
		pathParamsList = new ArrayList<String>();
		formParamsMap = new HashMap<String, String>();
		body = "";
	}

	public String getRelativeUrl() {
		return relativeUrl;
	}

	public void setRelativeUrl(String relativeUrl) {
		this.relativeUrl = relativeUrl;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public Map<String, String> getQueryParamsMap() {
		return queryParamsMap;
	}

	public void setQueryParamsMap(Map<String, String> queryParamsMap) {
		this.queryParamsMap = queryParamsMap;
	}

	public List<String> getPathParamsList() {
		return pathParamsList;
	}

	public void setPathParamsList(List<String> pathParamsList) {
		this.pathParamsList = pathParamsList;
	}

	public Map<String, String> getFormParamsMap() {
		return formParamsMap;
	}

	public void setFormParamsMap(Map<String, String> formParamsMap) {
		this.formParamsMap = formParamsMap;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}

	public void processParameters(Map<String, String> paramsMap){

		//Query
		Set<String> keySet =  queryParamsMap.keySet();
		for(String key : keySet) {
			if(key.contains("$")){
				String oldValue = queryParamsMap.get(key);
				queryParamsMap.remove(key);
				String dollarLessKey = key.replaceAll("\\$", "");	
				queryParamsMap.put(paramsMap.get(dollarLessKey), oldValue);
			}

			if(queryParamsMap.get(key).contains("$")){
				String oldVal = queryParamsMap.get(key);
				queryParamsMap.remove(key);
				String dollarLessVal = oldVal.replaceAll("\\$", "");
				queryParamsMap.put(key, paramsMap.get(dollarLessVal));
			}

		}

		//Form
		Set<String> formKeySet =  formParamsMap.keySet();
		for(String key : formKeySet) {
			if(key.contains("$")){
				String oldValue = formParamsMap.get(key);
				formParamsMap.remove(key);
				String dollarLessKey = key.replaceAll("\\$", "");	
				formParamsMap.put(paramsMap.get(dollarLessKey), oldValue);
			}

			if(formParamsMap.get(key).contains("$")){
				String oldVal = formParamsMap.get(key);
				formParamsMap.remove(key);
				String dollarLessVal = oldVal.replaceAll("\\$", "");
				formParamsMap.put(key, dollarLessVal);
			}

		}

		//Path
		for(String pathParam : pathParamsList) {
			if(pathParam.contains("$")){
				String dollarLessParam = pathParam.replaceAll("\\$", "");
				String newParam = paramsMap.get(dollarLessParam);
				pathParamsList.remove(pathParam);
				pathParamsList.add(newParam);
			}
		}

		// Body
		Set<String> paramsKeySet =  paramsMap.keySet();
		for(String key : paramsKeySet) {
			if(body.contains("$"+key)){
				body = body.replaceAll("\\$"+key, paramsMap.get(key));
			}		
		}
	}

	public enum CallData{
		RELATIVE_URL("Relative URL"), HTTP_METHOD("HTTP Method"), PATH_PARAMETERS("Path Parameters"), QUERY_PARAMETERS("Query Parameters"), FORM_PARAMETERS("Form Parameters"), REQUEST_BODY("Request Body"),REQUEST_TYPE("Request Type");

		String name;

		private CallData(String name) {
			this.name = name;
		}

		public String getName(){
			return name;
		}
	}

}