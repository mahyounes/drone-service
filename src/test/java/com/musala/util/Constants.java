package com.musala.util;

import java.math.BigDecimal;

import com.musala.drone.entity.enums.DroneModelEnum;
import com.musala.drone.entity.enums.DroneStateEnum;

public interface Constants
{

	DroneStateEnum FIRST_DRONE_STATE = DroneStateEnum.IDLE;

	DroneModelEnum FIRST_DRONE_MODEL = DroneModelEnum.HEAVYWEIGHT;

	String FIRST_DRONE_SERIAL = "serial1";

	BigDecimal FIRST_DRONE_MAX_WEIGHT = BigDecimal.valueOf(50000, 2);

	BigDecimal FIRST_DRONE_BATTERY_PERCENT = BigDecimal.valueOf(10000, 2);
}
