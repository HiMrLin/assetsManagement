package com.fjnu.assetsManagement.exception;

import com.fjnu.assetsManagement.entity.ResponseData;

public class RequestFailureException extends RuntimeException{
	
	private ResponseData responseData;

	public ResponseData getResponseData() {
		return responseData;
	}

	public void setResponseData(ResponseData responseData) {
		this.responseData = responseData;
	}

	private static final long serialVersionUID = 1L;
	
}
