package com.unict.sagaorchestrator;

import java.math.BigInteger;

public class SagaException extends Exception{

	private static final long serialVersionUID = -4773159568328688297L;
	
	private String errorDescription;
	private String errorCode;
	private BigInteger relationId;
	
	
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
	public BigInteger getRelationId() {
		return relationId;
	}
	public void setRelationId(BigInteger relationId) {
		this.relationId = relationId;
	}



}
