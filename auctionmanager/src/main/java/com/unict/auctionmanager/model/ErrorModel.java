package com.unict.auctionmanager.model;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ErrorModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8480258681156205932L;
	private HttpStatus errorCode;
	private String errorMessage;
	
	public ErrorModel (HttpStatus code, String message) {
		this.errorCode=code;
		this.errorMessage=message;
	}
	
	public ErrorModel () {
	}
	
	@Override
	public String toString() {
		return "ErrorModel [errorCode=" + errorCode.value() + ", errorMessage=" + errorMessage + "]";
	}
}
