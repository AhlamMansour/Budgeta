package com.galilsoftware.AF.core.restapi.client;

import java.util.Map.Entry;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.galilsoftware.AF.core.logging.SelTestLog;
import com.galilsoftware.AF.core.restapi.RestCall;

/**
 * This the rest client class which provides several api calls to rest.
 * 
 * @author Waseem Hamshawi
 */
public class RestClient {

	private Client restClient;
	private WebTarget rootTarget;
	private WebTarget currentTarget;
	private Response response;
	private String responseString;
	private Form form = new Form();

	private MediaType mediaType = MediaType.APPLICATION_JSON_TYPE;
	private String requestPayload;

	public enum HttpMethod {
		GET, PUT, DELETE, POST;
	}

	public enum ParameterType {
		PATH, QUERY, FORM;
	}

	public RestClient(String rootContextUrl) {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(JacksonJsonProvider.class);
		restClient = ClientBuilder.newClient(clientConfig);
		// set context root and current URI
		rootTarget = restClient.target(rootContextUrl);
		currentTarget = rootTarget;
	}

	/**
	 * Set the media type - (default is JSON).
	 */
	public void setMediaType(MediaType type) {
		mediaType = type;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	public void setResourceUrl(String url) {
		currentTarget = rootTarget.path(url);
	}

	public void addParameter(ParameterType type, String... params){
		switch (type) {
		case FORM:
			if(isKeyValue(params))
				addFormParameter(params[0], params[1]);
			else
				SelTestLog.error("The parameter type Form accepts 2 arguments as an input - a key and a value");
			break;
		case QUERY:
			if(isKeyValue(params))
				addQueryParameter(params[0], params[1]);
			else
				SelTestLog.error("The parameter type Query accepts 2 arguments as an input - a key and a value");
			break;
		case PATH:
			addPathParameter(params);
		default:
			break;
		}
	}

	public void send(HttpMethod method) {
		try {
			switch (method) {
			case GET:
				sendGet();
				break;
			case POST:
				sendPost();
				break;
			case PUT:
				sendPut();
				break;
			case DELETE:
				sendDelete();
			default:
				break;
			} 
		} finally {
			afterResponse();
			System.out.println("RESPONSE:");
			System.out.println(responseString);
		}
	}

	public String getResponseBodyAsString() {
		return responseString;
	}

	public Response getResponse() {
		return response;
	}

	public int getStatusCode() {
		return response.getStatus();
	}

	public String getReasonPhrase() {
		return response.getStatusInfo().getReasonPhrase();
	}

	public String getRequestPayload() {
		return requestPayload;
	}

	public void setRequestPayload(String payload) {
		this.requestPayload = payload;
	}

	private void afterResponse() {
		responseString = response.readEntity(String.class);
		form = new Form();
	}


	/************** private methods ***************/ 

	private boolean isKeyValue(String... params){
		if(params.length != 2)
			return false;
		return true;
	}

	private void addQueryParameter(String key, String value) {
		currentTarget = currentTarget.queryParam(key, value);
	}

	private void addPathParameter(String... params) {
		for (String param : params) {
			currentTarget = currentTarget.path(param);
		}
	}

	private void addFormParameter(String key, String value) {
		form.param(key, value);
	}

	private void sendGet() {
		response = currentTarget.request(mediaType).get(Response.class);
	}

	private void sendPost() {
		Entity<?> entity = null;
		// check if entity to be sent is form
		if (!form.asMap().isEmpty()) {
			mediaType = MediaType.APPLICATION_FORM_URLENCODED_TYPE;
			entity = Entity.form(form);
		}
		else 
			entity = Entity.entity(requestPayload, mediaType);
		response = currentTarget.request(mediaType).accept(mediaType).post(entity);
	}

	private void sendPut() {
		Entity<?> entity = null;
		// check if entity to be sent is form
		if (!form.asMap().isEmpty()) {
			entity = Entity.form(form);
		}
		else 
			entity = Entity.entity(requestPayload, mediaType);
		response = currentTarget.request(mediaType).accept(mediaType).put(entity);
	}

	private void sendDelete() {
		currentTarget.request().delete();
	}

	public void processCall(RestCall call) {

		//set resource relative URL
		this.setResourceUrl(call.getRelativeUrl());

		//add path parameters
		for(String path : call.getPathParamsList())
			this.addParameter(ParameterType.PATH,path);

		//add query parameters
		for (Entry<String, String> entry : call.getQueryParamsMap().entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			this.addParameter(ParameterType.QUERY, key, value);
		}

		//add form parameters
		for (Entry<String, String> entry : call.getFormParamsMap().entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			this.addParameter(ParameterType.FORM, key, value);
		}

		//add body
		this.setRequestPayload(call.getBody());
		this.setMediaType(call.getMediaType());

		//finally, send request
		this.send(call.getMethod());
	}
	

}