//package com.unict.sagaorchestration.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import com.unict.sagaorchestration.model.BaseModelBuilder;
//
//@ControllerAdvice
//public class ExchangeExceptionHandler extends ResponseEntityExceptionHandler {
//	private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeExceptionHandler.class);
//	HttpStatus status;
//
//	@ExceptionHandler(value = { ExchangeException.class })
//	protected ResponseEntity<Object> handleConflict(ExchangeException ex, WebRequest request) {
//		LOGGER.error("Handling Error");
//		Object bodyOfResponse = null;
//		if (ex.getStatus() != null) {
//			status = ex.getStatus();
//		} else {
//			status = HttpStatus.INTERNAL_SERVER_ERROR;
//		}
//		bodyOfResponse = BaseModelBuilder.error(status, ex.getMessage(), null);
//		LOGGER.error(String.format("Following exception has been raised: HttpStatus %d , Message %s", HttpStatus.BAD_REQUEST.value(), bodyOfResponse));
//		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), status, request);
//	}
//
//}