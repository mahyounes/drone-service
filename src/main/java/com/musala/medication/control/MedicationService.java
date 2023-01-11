package com.musala.medication.control;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.musala.exception.MusalaException;
import com.musala.exception.model.MusalaErrorCodeEnum;
import com.musala.medication.boundery.helper.dto.MedicationDto;
import com.musala.medication.boundery.helper.mapper.MedicationMapper;
import com.musala.medication.entity.MedicationEntity;
import com.musala.medication.entity.repository.MedicationRepository;

@Service
@Transactional(
		isolation = Isolation.READ_COMMITTED)
public class MedicationService
{

	private MedicationRepository medicationRepository;
	private MedicationMapper medicationMapper;

	public MedicationService(final MedicationRepository medicationRepository, final MedicationMapper medicationMapper)
	{
		this.medicationRepository = medicationRepository;
		this.medicationMapper = medicationMapper;
	}

	public Map<Long, List<MedicationEntity>> retrieveMedications(final List<Long> ids)
	{
		return this.medicationRepository.findAllById(ids).stream()
				.collect(Collectors.groupingBy(MedicationEntity::getId));
	}

	public MedicationDto createMedication(final MedicationDto medicationDto)
	{
		if (!this.medicationRepository.findByNameOrCode(medicationDto.getName(), medicationDto.getCode()).isEmpty())
		{
			throw new MusalaException(MusalaErrorCodeEnum.MAE);
		}

		MedicationEntity medicationEntity = this.medicationRepository
				.saveAndFlush(this.medicationMapper.toEntity(medicationDto));
		return this.medicationMapper.toDto(medicationEntity);
	}

}
