package com.musala.medication.boundery;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musala.medication.boundery.helper.dto.MedicationDto;
import com.musala.medication.control.MedicationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/medication")
@Tag(
		name = "MedicationRest",
		description = "Medication Rest Api")
public class MedicationRestApi
{

	private MedicationService medicationService;

	public MedicationRestApi(final MedicationService medicationService)
	{
		this.medicationService = medicationService;
	}

	@Operation(
			summary = "Create medication",
			description = "Create medication End-Point",
			tags =
			{ "MedicationRest" })
	@ApiResponses(
			value =
			{ @ApiResponse(
					responseCode = "201",
					description = "Medication created",
					content = @Content(
							schema = @Schema(
									implementation = MedicationDto.class))),
					@ApiResponse(
							responseCode = "409",
							description = "Medication with the same name exists or medication with the same code exists",
							content = @Content),
					@ApiResponse(
							responseCode = "500",
							description = "Creation failed",
							content = @Content) })
	@PostMapping
	public ResponseEntity<MedicationDto> createMedicatin(@Valid @RequestBody final MedicationDto medicationDto)
	{

		log.info("Creating a medication with name {}", medicationDto.getName());

		MedicationDto savedMedicationDto = this.medicationService.createMedication(medicationDto);
		return new ResponseEntity<>(savedMedicationDto, HttpStatus.CREATED);
	}
}
