package com.musala.drone.boundery;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musala.config.DroneConfiguration;
import com.musala.drone.DroneServiceApplicationTests;
import com.musala.drone.boundery.helper.dto.DroneDto;
import com.musala.drone.entity.DroneEntity;
import com.musala.drone.entity.enums.DroneModelEnum;
import com.musala.drone.entity.enums.DroneStateEnum;
import com.musala.drone.entity.repository.DroneRepository;
import com.musala.exception.model.MusalaErrorCodeEnum;
import com.musala.medication.entity.MedicationEntity;
import com.musala.medication.entity.repository.MedicationRepository;
import com.musala.util.Constants;
import com.musala.util.TestBuilder;

@DirtiesContext
@AutoConfigureMockMvc
@SpringBootTest(
		classes = DroneServiceApplicationTests.class,
		webEnvironment = WebEnvironment.RANDOM_PORT)
class DroneRestApiTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private DroneRepository droneRepository;

	@Autowired
	private DroneConfiguration droneConfiguration;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TestBuilder testBuilder;

	@BeforeAll
	static void setUpBeforeClass(@Autowired final MedicationRepository medicationRepository,
			@Autowired final DroneRepository droneRepository) {

		MedicationEntity medicationEntity = MedicationEntity.builder().code("NDC").imageBase64(
				"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")
				.name("centrum").weightInGram(BigDecimal.valueOf(500)).build();
		medicationRepository.saveAndFlush(medicationEntity);

		DroneEntity droneEntity = DroneEntity.builder().model(Constants.FIRST_DRONE_MODEL)
				.remainingBatteryPercent(Constants.FIRST_DRONE_BATTERY_PERCENT)
				.serialNumber(Constants.FIRST_DRONE_SERIAL).state(Constants.FIRST_DRONE_STATE)
				.weightLimitInGram(Constants.FIRST_DRONE_MAX_WEIGHT).build();
		droneRepository.saveAndFlush(droneEntity);

	}

	//	--------------------Retrieve test Start-------------------------//

	@Test
	void when_retrieveDrone_thenOk() throws Exception {

		final ResultActions resultActions = this.mvc
				.perform(get("/drone/" + Constants.FIRST_DRONE_SERIAL).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		DroneDto droneDto = this.objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(),
				DroneDto.class);
		DroneDto expectedDroneDto = this.testBuilder.createDroneDto(Constants.FIRST_DRONE_SERIAL,
				Constants.FIRST_DRONE_STATE, Constants.FIRST_DRONE_MODEL, Constants.FIRST_DRONE_BATTERY_PERCENT,
				Constants.FIRST_DRONE_MAX_WEIGHT);
		expectedDroneDto.setCreationDate(droneDto.getCreationDate());
		expectedDroneDto.setLastModifiedDate(droneDto.getLastModifiedDate());
		expectedDroneDto.setId(droneDto.getId());
		Assertions.assertEquals(expectedDroneDto, droneDto);
	}

	@Test
	void when_retrieveDrone_thenNotFound() throws Exception {

		this.mvc.perform(get("/drone/dummy").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.errorCode").value(MusalaErrorCodeEnum.DNF.name()));
	}

	//	--------------------Retrieve test End-------------------------//

	//	--------------------List test Start-------------------------//

	@Test
	void when_listAvailableDroneAnd_thenOnlyIdelStateIsReturned() throws Exception {

		this.testBuilder.createDroneEntity("serial2", DroneStateEnum.IDLE, Constants.FIRST_DRONE_MODEL,
				Constants.FIRST_DRONE_BATTERY_PERCENT, Constants.FIRST_DRONE_MAX_WEIGHT);
		this.testBuilder.createDroneEntity("serial3", DroneStateEnum.LOADED, DroneModelEnum.LIGHTWEIGHT,
				Constants.FIRST_DRONE_BATTERY_PERCENT, BigDecimal.valueOf(100));

		ResultActions resultActions = this.mvc
				.perform(get("/drone/available?pageNo=0&pageSize=1000").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		List<DroneDto> droneDtos = this.objectMapper.readValue(this.objectMapper
				.readTree(resultActions.andReturn().getResponse().getContentAsString()).path("content").toString(),
				new TypeReference<>() {
				});

		List<DroneEntity> idleDroneEntites = this.droneRepository
				.findAll(Example.of(DroneEntity.builder().state(DroneStateEnum.IDLE).build()));

		Assertions.assertEquals(idleDroneEntites.size(), droneDtos.size());
		Assertions.assertTrue(idleDroneEntites.stream().allMatch(d -> DroneStateEnum.IDLE.equals(d.getState())));
	}
	//	--------------------List test End-------------------------//

	//	--------------------Register drone test Start-------------------------//

	@Test
	void when_RegisterDrone_created() throws Exception {

		String serialNumber = "serial4";
		ResultActions resultActions = this.mvc
				.perform(post("/drone")
						.content(this.objectMapper.writeValueAsString(this.testBuilder.createDroneDto(serialNumber,
								Constants.FIRST_DRONE_STATE, Constants.FIRST_DRONE_MODEL,
								Constants.FIRST_DRONE_BATTERY_PERCENT, Constants.FIRST_DRONE_MAX_WEIGHT)))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.serialNumber").value(serialNumber))
				.andExpect(jsonPath("$.state").value(Constants.FIRST_DRONE_STATE.name()));

		DroneDto droneDto = this.objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(),
				DroneDto.class);

		Optional<DroneEntity> createdDroneEntity = this.droneRepository.findById(droneDto.getId());

		Assertions.assertTrue(createdDroneEntity.isPresent());
		Assertions.assertEquals(serialNumber, createdDroneEntity.get().getSerialNumber());
	}

	@Test
	void when_RegisterDroneAndModelLightAndWeightLimitIs150_thenConflict() throws Exception {

		String serialNumber = "serial-1";
		this.mvc.perform(
				post("/drone")
						.content(this.objectMapper.writeValueAsString(this.testBuilder.createDroneDto(serialNumber,
								Constants.FIRST_DRONE_STATE, DroneModelEnum.LIGHTWEIGHT,
								Constants.FIRST_DRONE_BATTERY_PERCENT, BigDecimal.valueOf(150))))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict());
	}

	@Test
	void when_RegisterDroneAndModelMiddleAndWeightLimitIs300_thenConflict() throws Exception {

		String serialNumber = "serial-1";
		this.mvc.perform(
				post("/drone")
						.content(this.objectMapper.writeValueAsString(this.testBuilder.createDroneDto(serialNumber,
								Constants.FIRST_DRONE_STATE, DroneModelEnum.MIDDLEWEIGHT,
								Constants.FIRST_DRONE_BATTERY_PERCENT, BigDecimal.valueOf(300))))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict());
	}

	@Test
	void when_RegisterDroneAndModelCruiserAndWeightLimitIs355_thenConflict() throws Exception {

		String serialNumber = "serial-1";
		this.mvc.perform(post("/drone")
				.content(this.objectMapper.writeValueAsString(this.testBuilder.createDroneDto(serialNumber,
						Constants.FIRST_DRONE_STATE, DroneModelEnum.CRUISERWEIGHT,
						Constants.FIRST_DRONE_BATTERY_PERCENT, BigDecimal.valueOf(355))))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isConflict());
	}

	@Test
	void when_RegisterDroneWithAlreadyExistSerial_thenConflict() throws Exception {

		this.mvc.perform(post("/drone")
				.content(this.objectMapper.writeValueAsString(this.testBuilder.createDroneDto(
						Constants.FIRST_DRONE_SERIAL, Constants.FIRST_DRONE_STATE, Constants.FIRST_DRONE_MODEL,
						Constants.FIRST_DRONE_BATTERY_PERCENT, Constants.FIRST_DRONE_MAX_WEIGHT)))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isConflict())
				.andExpect(jsonPath("$.errorCode").value(MusalaErrorCodeEnum.DWSAR.name()));
	}

	@Test
	void when_RegisterDroneWithSerialMoreThan100Char_thenBadRequest() throws Exception {

		this.mvc.perform(post("/drone")
				.content(this.objectMapper
						.writeValueAsString(this.testBuilder.createDroneDto(RandomStringUtils.randomAlphabetic(101),
								Constants.FIRST_DRONE_STATE, Constants.FIRST_DRONE_MODEL,
								Constants.FIRST_DRONE_BATTERY_PERCENT, Constants.FIRST_DRONE_MAX_WEIGHT)))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}

	@Test
	void when_RegisterDroneWithWeightMoreThan500_thenBadRequest() throws Exception {

		this.mvc.perform(post("/drone")
				.content(this.objectMapper.writeValueAsString(this.testBuilder.createDroneDto(
						RandomStringUtils.randomAlphabetic(10), Constants.FIRST_DRONE_STATE,
						Constants.FIRST_DRONE_MODEL, Constants.FIRST_DRONE_BATTERY_PERCENT, BigDecimal.valueOf(501))))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}

	@Test
	void when_RegisterDroneWithBatteryPercentMoreThan100_thenBadRequest() throws Exception {

		this.mvc.perform(post("/drone")
				.content(this.objectMapper.writeValueAsString(this.testBuilder.createDroneDto(
						RandomStringUtils.randomAlphabetic(10), Constants.FIRST_DRONE_STATE,
						Constants.FIRST_DRONE_MODEL, BigDecimal.valueOf(101), Constants.FIRST_DRONE_MAX_WEIGHT)))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}

	//	--------------------Register drone test End-------------------------//

	//	--------------------Load drone test Start-------------------------//

	@Test
	void when_loadDroneWithExistMedication_thenNoContent() throws Exception {

		String serialNumber = "serial5";
		this.testBuilder.createDroneEntity(serialNumber, Constants.FIRST_DRONE_STATE, Constants.FIRST_DRONE_MODEL,
				Constants.FIRST_DRONE_BATTERY_PERCENT, Constants.FIRST_DRONE_MAX_WEIGHT);
		long medicationCount = 1L;

		this.mvc.perform(patch("/drone/" + serialNumber)
				.content(this.objectMapper
						.writeValueAsString(this.testBuilder.createDronePackageDto(1L, medicationCount)))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

		Optional<DroneEntity> droneEntityOp = this.droneRepository.findBySerialNumber(serialNumber);

		Assertions.assertTrue(droneEntityOp.isPresent());

		DroneEntity droneEntity = droneEntityOp.get();
		Assertions.assertEquals(DroneStateEnum.LOADED, droneEntity.getState());
		Assertions.assertEquals(1, droneEntity.getDronePacakeges().size());
		Assertions.assertTrue(new ArrayList<>(droneEntity.getDronePacakeges()).get(0).getPackageMedications().stream()
				.allMatch(p -> p.getMedication().getId().equals(1L)
						&& p.getMedicationItemsCount().equals(medicationCount)));

	}

	@Test
	void when_loadDroneWithNotExistMedication_thenNotFound() throws Exception {

		String serialNumber = "serial6";
		this.testBuilder.createDroneEntity(serialNumber, Constants.FIRST_DRONE_STATE, Constants.FIRST_DRONE_MODEL,
				Constants.FIRST_DRONE_BATTERY_PERCENT, Constants.FIRST_DRONE_MAX_WEIGHT);
		long medicationCount = 1L;

		this.mvc.perform(patch("/drone/" + serialNumber)
				.content(this.objectMapper
						.writeValueAsString(this.testBuilder.createDronePackageDto(1000L, medicationCount)))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.errorCode").value(MusalaErrorCodeEnum.MNF.name()));

	}

	@Test
	void when_loadDroneThatNotExists_thenNotFound() throws Exception {

		long medicationCount = 1L;

		this.mvc.perform(patch("/drone/serial-test")
				.content(this.objectMapper
						.writeValueAsString(this.testBuilder.createDronePackageDto(1000L, medicationCount)))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.errorCode").value(MusalaErrorCodeEnum.DNF.name()));

	}

	@Test
	void when_loadDroneWhileBatteryPercentNotAlowing_thenConflict() throws Exception {

		String serialNumber = "serial7";
		this.testBuilder.createDroneEntity(serialNumber, Constants.FIRST_DRONE_STATE, Constants.FIRST_DRONE_MODEL,
				this.droneConfiguration.getMinBatteryPercentForLoad().min(BigDecimal.valueOf(.1d)),
				Constants.FIRST_DRONE_MAX_WEIGHT);
		long medicationCount = 1L;

		this.mvc.perform(patch("/drone/" + serialNumber)
				.content(this.objectMapper
						.writeValueAsString(this.testBuilder.createDronePackageDto(1L, medicationCount)))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isConflict())
				.andExpect(jsonPath("$.errorCode").value(MusalaErrorCodeEnum.DBL.name()));

	}

	@Test
	void when_loadDroneAndItIsNotIdel_thenConflict() throws Exception {

		String serialNumber = "serial8";
		this.testBuilder.createDroneEntity(serialNumber, DroneStateEnum.LOADING, Constants.FIRST_DRONE_MODEL,
				Constants.FIRST_DRONE_BATTERY_PERCENT, Constants.FIRST_DRONE_MAX_WEIGHT);
		long medicationCount = 1L;

		this.mvc.perform(patch("/drone/" + serialNumber)
				.content(this.objectMapper
						.writeValueAsString(this.testBuilder.createDronePackageDto(1L, medicationCount)))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isConflict())
				.andExpect(jsonPath("$.errorCode").value(MusalaErrorCodeEnum.DAL.name()));

	}

	@Test
	void when_loadDroneWithGreaterWeightThanItsLimit_thenConflict() throws Exception {

		String serialNumber = "serial9";
		this.testBuilder.createDroneEntity(serialNumber, DroneStateEnum.IDLE, DroneModelEnum.LIGHTWEIGHT,
				Constants.FIRST_DRONE_BATTERY_PERCENT, BigDecimal.valueOf(100));
		long medicationCount = 1L;

		this.mvc.perform(patch("/drone/" + serialNumber)
				.content(this.objectMapper
						.writeValueAsString(this.testBuilder.createDronePackageDto(1L, medicationCount)))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isConflict())
				.andExpect(jsonPath("$.errorCode").value(MusalaErrorCodeEnum.DMWE.name()));

	}

	//	--------------------Register drone test End-------------------------//

}
