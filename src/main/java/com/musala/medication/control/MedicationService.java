package com.musala.medication.control;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.musala.exception.MusalaException;
import com.musala.exception.model.MusalaErrorCodeEnum;
import com.musala.medication.entity.MedicationEntity;
import com.musala.medication.entity.repository.MedicationRepository;

@Service
@Transactional(
		isolation = Isolation.READ_COMMITTED)
public class MedicationService {
	private MedicationRepository medicationRepository;

	public MedicationService(final MedicationRepository medicationRepository) {
		this.medicationRepository = medicationRepository;
	}

	public MedicationEntity retrieveMedication(final Long id) {
		return this.medicationRepository.findById(id).orElseThrow(() -> new MusalaException(MusalaErrorCodeEnum.MNF));
	}

	public Map<Long, List<MedicationEntity>> retrieveMedications(final List<Long> ids) {
		return this.medicationRepository.findAllById(ids).stream()
				.collect(Collectors.groupingBy(MedicationEntity::getId));
	}

}
