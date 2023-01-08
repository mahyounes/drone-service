package com.musala.drone.boundery.helper.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
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
