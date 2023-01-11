package com.musala.drone.boundery.helper.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.musala.drone.boundery.helper.dto.DroneDto;
import com.musala.drone.entity.DroneEntity;
import com.musala.util.GenericMapper;

@Mapper(
		componentModel = "spring")
public abstract class DroneMapper implements GenericMapper<DroneDto, DroneEntity>
{

	@Override
	@Mapping(
			target = "creationDate",
			ignore = true)
	@Mapping(
			target = "lastModifiedDate",
			ignore = true)
	@Mapping(
			target = "batteryPercentLogs",
			ignore = true)
	@Mapping(
			target = "dronePacakeges",
			ignore = true)
	public abstract DroneEntity toEntity(DroneDto dto);

}
