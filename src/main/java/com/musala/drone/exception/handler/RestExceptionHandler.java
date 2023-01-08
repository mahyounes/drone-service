package com.musala.drone.exception.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.musala.drone.exception.MusalaException;
import com.musala.drone.exception.model.ServiceErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler
{

	@ExceptionHandler(
			value =
			{ MusalaException.class })
	protected ResponseEntity<ServiceErrorResponse> handleMusalaException(final MusalaException ex,
			final WebRequest request)
	{
		log.error(ex.getMessage(), ex);
		return new ResponseEntity<>(
				ServiceErrorResponse.builder().errorCode(ex.getErrorCode()).errorMsg(ex.getMessage()).build(),
				new HttpHeaders(), ex.getHttpStatus());
	}

	@ExceptionHandler(
			value =
			{ Exception.class })
	protected ResponseEntity<ServiceErrorResponse> handleGeneralException(final Exception ex, final WebRequest request)
	{
		log.error(ex.getMessage(), ex);
		return new ResponseEntity<>(
				ServiceErrorResponse.builder().errorCode(null).errorMsg("Unexpected exception").build(),
				new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}