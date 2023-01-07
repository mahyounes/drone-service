package com.musala.drone.boundery.helper.dto;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DronePackageDto
{

	private Long id;

	@Builder.Default
	private boolean delivered = false;

	private Timestamp pickUpDate;

	private Timestamp deliveryDate;

	@Builder.Default
	@NotEmpty(
			message = "Package must contain at least one medication")
	private Set<PackageMedicationDto> packageMedications = new HashSet<>();
}
