package com.musala.exception.model;

import org.springframework.http.HttpStatus;

public enum MusalaErrorCodeEnum
{

	DNF("A drone not found", HttpStatus.NOT_FOUND), DBL("Drone battery low", HttpStatus.CONFLICT),
	DMWE("Drone max weight exceeded", HttpStatus.CONFLICT), MNF("Medication not found", HttpStatus.NOT_FOUND),
	DAL("Drone already has a package", HttpStatus.CONFLICT),
	MAE("Medication with the same name or code already exists", HttpStatus.CONFLICT),
	DWSAR("A drone with this serial already registered", HttpStatus.CONFLICT);

	private String message;
	private HttpStatus httpStatus;

	MusalaErrorCodeEnum(final String message, final HttpStatus httpStatus)
	{
		this.message = message;
		this.httpStatus = httpStatus;
	}

	public String getMessage()
	{
		return this.message;
	}

	public HttpStatus getHttpStatus()
	{
		return this.httpStatus;
	}

}
