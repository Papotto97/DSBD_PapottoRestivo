package com.unict.walletmanager.model;

import org.springframework.http.HttpStatus;

public final class BaseModelBuilder {

	public static <T> BaseModel<T> success(final T data) {
		BaseModel<T> base = new BaseModel<T>();
		base.setSuccess(true);
		base.setData(data);
		return base;
	}

	public static <T> BaseModel<T> error(final HttpStatus errorCode, final String errorMessage) {
		BaseModel<T> base = new BaseModel<T>();
		ErrorModel error = new ErrorModel(errorCode, errorMessage);
		base.setError(error);
		base.setSuccess(false);
		return base;
	}
	
}