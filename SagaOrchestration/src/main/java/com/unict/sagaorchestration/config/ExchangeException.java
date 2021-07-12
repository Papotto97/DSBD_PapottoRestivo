package com.unict.sagaorchestration.config;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeException extends RuntimeException {

	/**
	*
	*/
	private static final long serialVersionUID = 1L;
	private String message;
	private Throwable t;
	private HttpStatus status;

	public ExchangeException(String message, Throwable t) {
		super();
		this.message = message;
		this.t = t;
	}

}