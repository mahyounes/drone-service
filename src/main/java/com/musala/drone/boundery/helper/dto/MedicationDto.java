package com.musala.drone.boundery.helper.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MedicationDto extends BaseDto
{

	@NotBlank(
			message = "Name is required")
	@Pattern(
			regexp = "^[a-zA-Z0-9_-]*$",
			message = "Name only accept letters, numbers, underscore and dash")
	private String name;

	@NotNull(
			message = "Weight is required")
	@Min(
			value = 1,
			message = "Weight should be more than 0")
	private BigDecimal weightInGram;

	@Pattern(
			regexp = "^[A-Z0-9_]*$",
			message = "Code only accept upper case letters, numbers and underscore")
	private String code;

	private String imageBase64;
}
