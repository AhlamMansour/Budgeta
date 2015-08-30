package com.galilsoftware.AF.core.restapi;

/**
 * This class represents a whole Rest Object which consists of both a call and a response.
 * 
 * @author Waseem Hamshawi & Amir Najjar
 */
public class RestObject {

	private RestCall restCall;
	
	private RestResponse restResponse;
	
	public RestObject(RestCall restCall, RestResponse restResponse){	
		this.restCall = restCall;
		this.restResponse = restResponse;
	}

	public RestCall getRestCall() {
		return restCall;
	}

	public void setRestCall(RestCall restCall) {
		this.restCall = restCall;
	}

	public RestResponse getRestResponse() {
		return restResponse;
	}

	public void setRestResponse(RestResponse restResponse) {
		this.restResponse = restResponse;
	}

}
