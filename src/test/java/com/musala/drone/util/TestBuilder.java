package com.musala.drone.util;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.musala.drone.entity.DroneEntity;
import com.musala.drone.entity.enums.DroneModelEnum;
import com.musala.drone.entity.enums.DroneStateEnum;
import com.musala.drone.entity.repository.DroneRepository;

@Component
public class TestBuilder
{

	@Autowired
	private DroneRepository droneRepository;

	public DroneEntity createDroneEntity(final String serialNumber, final DroneStateEnum state,
			final DroneModelEnum model, final BigDecimal remainingBatteryPercent, final BigDecimal maxWeight)
	{
		DroneEntity droneEntity = DroneEntity.builder().model(model).remainingBatteryPercent(remainingBatteryPercent)
				.serialNumber(serialNumber).state(state).weightLimitInGram(maxWeight).build();
		return this.droneRepository.saveAndFlush(droneEntity);
	}

}
