package com.musala.drone.control;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.musala.drone.entity.MedicationEntity;
import com.musala.drone.entity.repository.MedicationRepository;
import com.musala.drone.exception.MusalaException;

@Service
@Transactional(
		isolation = Isolation.READ_COMMITTED)
public class MedicationService
{
	private MedicationRepository medicationRepository;

	public MedicationService(final MedicationRepository medicationRepository)
	{
		this.medicationRepository = medicationRepository;
	}

	public MedicationEntity retrieveMedication(final Long id)
	{
		return this.medicationRepository.findById(id)
				.orElseThrow(() -> new MusalaException("Medication not found", HttpStatus.NOT_FOUND));
	}

	public Map<Long, List<MedicationEntity>> retrieveMedications(final List<Long> ids)
	{
		return this.medicationRepository.findAllById(ids).stream()
				.collect(Collectors.groupingBy(MedicationEntity::getId));
	}

}
