package com.unict.walletmanager.model;

public final class BaseModelBuilder {

	private BaseModelBuilder() {
	}

	public static <T> BaseModel<T> success( final T data ) {
		BaseModel<T> base = new BaseModel<T>();
		base.setSuccess(true);
		base.setData( data );
		return base;
	}

	public static <T> BaseModel<T> error( final String errorCode, final String errorMessage ) {
		BaseModel<T> base = new BaseModel<T>();
		ErrorModel error = new ErrorModel(errorCode,errorMessage);
		base.setErrorCode(error);
		base.setSuccess(false);
		return base;
	}
}