package com.musala.medication.boundery.helper.mapper;

import org.mapstruct.Mapper;

import com.musala.medication.boundery.helper.dto.MedicationDto;
import com.musala.medication.entity.MedicationEntity;
import com.musala.util.GenericMapper;

@Mapper(
		componentModel = "spring")
public interface MedicationMapper extends GenericMapper<MedicationDto, MedicationEntity>
{

}
