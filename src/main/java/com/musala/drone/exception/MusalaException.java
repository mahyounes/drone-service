package com.musala.drone.exception;

import org.springframework.http.HttpStatus;

import com.musala.drone.exception.model.MusalaErrorCodeEnum;

import lombok.Getter;

@Getter
public class MusalaException extends RuntimeException
{

	private static final long serialVersionUID = -7479990794646974430L;

	private String message;

	private HttpStatus httpStatus;

	private String errorCode;

	public MusalaException(final String msg, final HttpStatus httpStatus)
	{
		this.message = msg;
		this.httpStatus = httpStatus;
	}

	public MusalaException(final MusalaErrorCodeEnum musalaErrorCodeEnum)
	{
		this.message = musalaErrorCodeEnum.getMessage();
		this.httpStatus = musalaErrorCodeEnum.getHttpStatus();
		this.errorCode = musalaErrorCodeEnum.name();
	}

}
