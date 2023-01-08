package com.musala.drone.boundery.helper.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.musala.drone.boundery.helper.dto.DronePackageDto;
import com.musala.drone.boundery.helper.dto.PackageMedicationDto;
import com.musala.drone.control.MedicationService;
import com.musala.drone.entity.DronePackageEntity;
import com.musala.drone.entity.MedicationEntity;
import com.musala.drone.entity.PackageMedicationEntity;
import com.musala.drone.exception.MusalaException;
import com.musala.drone.exception.model.MusalaErrorCodeEnum;

@Mapper(
		componentModel = "spring")
public abstract class DronePackageMapper
{
	@Autowired
	MedicationService medicationService;

	public abstract DronePackageDto toDto(DronePackageEntity entity);

	public DronePackageEntity toEntity(final DronePackageDto dto)
	{

		if (dto == null)
		{
			return null;
		}

		DronePackageEntity entity = DronePackageEntity.builder().id(dto.getId()).pickUpDate(dto.getPickUpDate())
				.build();

		entity.addPackageMedication(packageMedicationDtosToEntities(dto.getPackageMedications()));
		return entity;
	}

	Set<PackageMedicationEntity> packageMedicationDtosToEntities(final Set<PackageMedicationDto> packageMedicationDtos)
	{
		Set<PackageMedicationEntity> packageMedicationEntites = new HashSet<>();

		List<Long> medicationIds = packageMedicationDtos.stream().map(PackageMedicationDto::getMedicationId)
				.collect(Collectors.toList());
		Map<Long, List<MedicationEntity>> medicationMap = this.medicationService.retrieveMedications(medicationIds);

		for (PackageMedicationDto packageMedicationDto : packageMedicationDtos)
		{
			List<MedicationEntity> medications = medicationMap.get(packageMedicationDto.getMedicationId());

			if (medications == null || medications.isEmpty())
			{
				throw new MusalaException(MusalaErrorCodeEnum.MNF);
			}
			packageMedicationEntites.add(
					PackageMedicationEntity.builder().id(packageMedicationDto.getId()).medication(medications.get(0))
							.medicationItemsCount(packageMedicationDto.getMedicationItemsCount()).build());
		}
		return packageMedicationEntites;
	}

}
