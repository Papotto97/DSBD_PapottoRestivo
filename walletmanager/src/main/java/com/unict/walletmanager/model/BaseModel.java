package com.unict.walletmanager.model;

import java.io.Serializable;

public class BaseModel<T> implements Serializable {

	/**
	 * 
	 */
	 
	private boolean success;
	private static final long serialVersionUID = 6330269478388971675L;

	private T data;

	private ErrorModel errorCode;
	
	public BaseModel() {
		super();
		this.data = null;
	}
	
	public BaseModel(T data) {
		super();
		this.data = data;
	}
	
	public T getData() {
		return data;
	}

	public void setData( T data ) {
		this.data = data;
	}



	@Override
	public String toString() {
		return "BaseModel [data=" + data + ", errorCode=" + errorCode + "]";
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public ErrorModel getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorModel errorCode) {
		this.errorCode = errorCode;
	}

}