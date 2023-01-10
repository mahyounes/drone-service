package com.musala.drone.job;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import com.musala.drone.DroneServiceApplicationTests;
import com.musala.drone.entity.DroneBatteryHistoryEntity;
import com.musala.drone.entity.DroneEntity;
import com.musala.drone.entity.repository.DroneBatteryHistoryRepository;
import com.musala.drone.entity.repository.DroneRepository;
import com.musala.drone.util.Constants;
import com.musala.drone.util.TestBuilder;

@Transactional
@DirtiesContext
@AutoConfigureMockMvc
@SpringBootTest(
		classes = DroneServiceApplicationTests.class,
		webEnvironment = WebEnvironment.RANDOM_PORT)
class DroneBatterySchedulerTest
{

	@Autowired
	private DroneBatteryScheduler droneBatteryScheduler;

	@Autowired
	private DroneRepository droneRepository;

	@Autowired
	private DroneBatteryHistoryRepository droneBatteryHistoryRepository;

	@Autowired
	private TestBuilder testBuilder;

	@Test
	void auditDroneBattery()
	{
		String serialNumber = "serialNumber-1";
		this.testBuilder.createDroneEntity(serialNumber, Constants.FIRST_DRONE_STATE, Constants.FIRST_DRONE_MODEL,
				Constants.FIRST_DRONE_BATTERY_PERCENT, Constants.FIRST_DRONE_MAX_WEIGHT);
		this.droneBatteryScheduler.auditDroneBattery();

		DroneEntity droneEntity = this.droneRepository.findBySerialNumber(serialNumber).get();
		List<DroneBatteryHistoryEntity> batteryPercentLogs = this.droneBatteryHistoryRepository
				.findByDrone(droneEntity);

		Assertions.assertEquals(1, batteryPercentLogs.size());
		Assertions.assertEquals(Constants.FIRST_DRONE_BATTERY_PERCENT,
				batteryPercentLogs.get(0).getRemainingBatteryPercent());

		droneEntity.setRemainingBatteryPercent(BigDecimal.valueOf(50));
		this.droneRepository.saveAndFlush(droneEntity);

		this.droneBatteryScheduler.auditDroneBattery();

		List<DroneBatteryHistoryEntity> batteryPercentLogsWithTwoBatteryAudit = this.droneBatteryHistoryRepository
				.findByDrone(droneEntity);

		Assertions.assertEquals(2, batteryPercentLogsWithTwoBatteryAudit.size());
		Assertions.assertEquals(BigDecimal.valueOf(50),
				batteryPercentLogsWithTwoBatteryAudit.get(1).getRemainingBatteryPercent());
	}

}
