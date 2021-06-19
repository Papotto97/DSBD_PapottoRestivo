package com.unict.sagaorchestration.model;

import java.io.Serializable;

public class ErrorModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8480258681156205932L;
	private String errorCode;
	private String errorMessage;
	
	public ErrorModel (String code,String message) {
		this.errorCode=code;
		this.errorMessage=message;
	}
	public ErrorModel () {
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	
	@Override
	public String toString() {
		return "ErrorModel [errorCode=" + errorCode + ", errorMessage=" + errorMessage + "]";
	}
}
