package com.unict.auctionmanager.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class BaseModel<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6330269478388971675L;
	 
	private boolean success;
	private T data;
	private ErrorModel error;
	
	public BaseModel() {
		super();
		this.data = null;
	}
	
	public BaseModel(T data) {
		super();
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "BaseModel [data=" + data + ", errorCode=" + error + "]";
	}

}