package com.unict.sagaorchestration.exception;

public class ApiManagerException extends Exception{

	private static final long serialVersionUID = 5072089993893644580L;
	private String code;
	private String message;
	
	public ApiManagerException(String errorMessage,String errorCode) {
		this.code=errorCode;
		this.message=errorMessage;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
