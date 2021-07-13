package com.unict.sagaorchestration.exception;

public class SagaException extends Exception {

	private static final long serialVersionUID = -4773159568328688297L;

	private String errorDescription;
	private String errorCode;

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
