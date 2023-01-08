package com.musala.drone.exception.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceErrorResponse
{

	private String errorMsg;
	private String errorCode;

}
