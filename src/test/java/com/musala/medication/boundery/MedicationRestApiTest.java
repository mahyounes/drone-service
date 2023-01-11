package com.musala.medication.boundery;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musala.drone.DroneServiceApplicationTests;
import com.musala.exception.model.MusalaErrorCodeEnum;
import com.musala.medication.boundery.helper.dto.MedicationDto;
import com.musala.medication.entity.MedicationEntity;
import com.musala.medication.entity.repository.MedicationRepository;
import com.musala.util.TestBuilder;

@DirtiesContext
@AutoConfigureMockMvc
@SpringBootTest(
		classes = DroneServiceApplicationTests.class,
		webEnvironment = WebEnvironment.RANDOM_PORT)
class MedicationRestApiTest
{

	@Autowired
	private MockMvc mvc;

	@Autowired
	private MedicationRepository medicationRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TestBuilder testBuilder;

	@Test
	void when_createMedication_created() throws Exception
	{

		String name = "medication1";
		String code = "CODE1";
		ResultActions resultActions = this.mvc
				.perform(post("/medication")
						.content(this.objectMapper.writeValueAsString(this.testBuilder.createMedicationDto(name, code)))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.name").value(name))
				.andExpect(jsonPath("$.code").value(code));

		MedicationDto medicationDto = this.objectMapper
				.readValue(resultActions.andReturn().getResponse().getContentAsString(), MedicationDto.class);

		Optional<MedicationEntity> createdMedicationEntity = this.medicationRepository.findById(medicationDto.getId());

		Assertions.assertTrue(createdMedicationEntity.isPresent());
		Assertions.assertEquals(name, createdMedicationEntity.get().getName());
	}

	@Test
	void when_createMedicationWithAlreadyExistName_thenConflict() throws Exception
	{
		String name = "medication1";
		String code = "CODE2";
		this.mvc.perform(post("/medication")
				.content(this.objectMapper.writeValueAsString(this.testBuilder.createMedicationDto(name, code)))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isConflict())
				.andExpect(jsonPath("$.errorCode").value(MusalaErrorCodeEnum.MAE.name()));
	}

	@Test
	void when_createMedicationWithAlreadyExistCode_thenConflict() throws Exception
	{
		String name = "medication2";
		String code = "CODE1";
		this.mvc.perform(post("/medication")
				.content(this.objectMapper.writeValueAsString(this.testBuilder.createMedicationDto(name, code)))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isConflict())
				.andExpect(jsonPath("$.errorCode").value(MusalaErrorCodeEnum.MAE.name()));
	}

	@Test
	void when_createMedicationWithBadName_thenConflict() throws Exception
	{
		String name = "medication.2";
		String code = "CODE3";
		this.mvc.perform(post("/medication")
				.content(this.objectMapper.writeValueAsString(this.testBuilder.createMedicationDto(name, code)))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}

	@Test
	void when_createMedicationWithBadCode_thenConflict() throws Exception
	{
		String name = "medication2";
		String code = "code-3";
		this.mvc.perform(post("/medication")
				.content(this.objectMapper.writeValueAsString(this.testBuilder.createMedicationDto(name, code)))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}

}
