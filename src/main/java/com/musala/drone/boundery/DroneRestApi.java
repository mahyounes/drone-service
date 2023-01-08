package com.musala.drone.boundery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musala.drone.boundery.helper.dto.DroneDto;
import com.musala.drone.boundery.helper.dto.DronePackageDto;
import com.musala.drone.control.DroneService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/drone")
@Tag(
		name = "DroneRest",
		description = "Drone Rest Api")
public class DroneRestApi
{

	private DroneService droneService;

	public DroneRestApi(final DroneService droneService)
	{
		this.droneService = droneService;
	}

	@Operation(
			summary = "Retrieve Drone",
			description = "Retrieve Drone End-Point",
			tags =
			{ "DroneRest" })
	@ApiResponses(
			value =
			{ @ApiResponse(
					responseCode = "200",
					description = "Drone Found",
					content = @Content(
							schema = @Schema(
									implementation = DroneDto.class))),
					@ApiResponse(
							responseCode = "404",
							description = "Drone not Found",
							content = @Content),
					@ApiResponse(
							responseCode = "500",
							description = "Retrieval failed",
							content = @Content) })
	@GetMapping("/{seriaNumber}")
	public ResponseEntity<DroneDto> retrieveDrone(@Parameter(
			description = "Drone seria number",
			required = true) @PathVariable("seriaNumber") final String serialNumber) throws Exception
	{

		log.info("Retrieving Drone with serial {}", serialNumber);

		DroneDto droneDto = this.droneService.retrieveDrone(serialNumber);
		log.info("Drone found ({})", droneDto);
		return new ResponseEntity<>(droneDto, HttpStatus.OK);
	}

	@Operation(
			summary = "List available Drone",
			description = "List available Drone End-Point",
			tags =
			{ "DroneRest" })
	@ApiResponses(
			value =
			{ @ApiResponse(
					responseCode = "200",
					description = "Drones Found",
					content = @Content(
							schema = @Schema(
									implementation = DroneDto.class))),
					@ApiResponse(
							responseCode = "500",
							description = "Retrieval failed",
							content = @Content) })
	@GetMapping("/available")
	public ResponseEntity<Page<DroneDto>> listAvailableDrones(@RequestParam("pageNo") final int pageNo,
			@RequestParam("pageSize") final int pageSize)
	{

		log.info("Retrieving available drones");

		Page<DroneDto> droneDtos = this.droneService.listAvailableDrones(PageRequest.of(pageNo, pageSize));
		log.info("Drones found ({})", droneDtos);
		return new ResponseEntity<>(droneDtos, HttpStatus.OK);
	}

	@Operation(
			summary = "Register Drone",
			description = "Register drone End-Point",
			tags =
			{ "DroneRest" })
	@ApiResponses(
			value =
			{ @ApiResponse(
					responseCode = "201",
					description = "Drone created",
					content = @Content(
							schema = @Schema(
									implementation = DroneDto.class))),
					@ApiResponse(
							responseCode = "404",
							description = "Medication not Found",
							content = @Content),
					@ApiResponse(
							responseCode = "409",
							description = "Drone with this serial number already exists (errorCode=DWSAR)",
							content = @Content),
					@ApiResponse(
							responseCode = "500",
							description = "Creation failed",
							content = @Content) })
	@PostMapping
	public ResponseEntity<DroneDto> registerDrone(@RequestBody final DroneDto droneDto) throws Exception
	{

		log.info("Registering a drone with serial {}", droneDto.getSerialNumber());

		DroneDto savedDroneDto = this.droneService.registerDrone(droneDto);
		return new ResponseEntity<>(savedDroneDto, HttpStatus.CREATED);
	}

	@Operation(
			summary = "Load Drone",
			description = "Load drone End-Point",
			tags =
			{ "DroneRest" })
	@ApiResponses(
			value =
			{ @ApiResponse(
					responseCode = "202",
					description = "Drone Loaded",
					content = @Content),
					@ApiResponse(
							responseCode = "404",
							description = "Medication not Found \n Drone not found",
							content = @Content),
					@ApiResponse(
							responseCode = "500",
							description = "Loading failed",
							content = @Content) })
	@PatchMapping("/{droneSerialNumber}")
	public ResponseEntity<DroneDto> LoadDrone(@Parameter(
			description = "Drone seria number",
			required = true) @PathVariable("droneSerialNumber") final String droneSerialNumber,
			@RequestBody final DronePackageDto dronePackageDto) throws Exception
	{

		log.info("Loading a drone with serial {}", droneSerialNumber);

		this.droneService.loadDrone(droneSerialNumber, dronePackageDto);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
