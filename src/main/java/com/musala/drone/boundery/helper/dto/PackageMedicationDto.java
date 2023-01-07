package com.musala.drone.boundery.helper.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class PackageMedicationDto
{

	private Long id;

	@NotNull(
			message = "Medication count is required")
	@Min(
			value = 1,
			message = "Medication count value have to be greater than 0")
	private Long medicationItemsCount;

	@NotNull(
			message = "Medication id is required")
	@Min(
			value = 1,
			message = "Medication id value have to be greater than 0")
	private Long medicationId;

	private MedicationDto medication;

}
