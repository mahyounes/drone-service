package com.musala.drone.control;

import java.util.ArrayList;
import java.util.List;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.musala.drone.entity.DroneBatteryHistoryEntity;
import com.musala.drone.entity.DroneEntity;
import com.musala.drone.entity.repository.DroneBatteryHistoryRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(
		isolation = Isolation.READ_COMMITTED)
public class DroneBatteryScheduler
{

	private DroneService droneService;
	private DroneBatteryHistoryRepository droneBatteryHistoryRepository;

	public DroneBatteryScheduler(final DroneService droneService,
			final DroneBatteryHistoryRepository droneBatteryHistoryRepository)
	{
		this.droneService = droneService;
		this.droneBatteryHistoryRepository = droneBatteryHistoryRepository;
	}

	@Scheduled(
			cron = "${drone.job.batteryHistory.cron}")
	@Retryable(
			value =
			{ Exception.class },
			maxAttemptsExpression = "${drone.job.batteryHistory.retry.attemptsNumber}",
			backoff = @Backoff(
					delayExpression = "${drone.job.batteryHistory.retry.delay}"))
	public void auditDroneBattery()
	{
		log.info("Start audit drone battery job");
		List<DroneBatteryHistoryEntity> droneBatteryHistoryEntities = new ArrayList<>();
		List<DroneEntity> drones = this.droneService.listDrones();

		for (DroneEntity droneEntity : drones)
		{
			droneBatteryHistoryEntities.add(DroneBatteryHistoryEntity.builder().drone(droneEntity)
					.remainingBatteryPercent(droneEntity.getRemainingBatteryPercent()).build());
		}

		if (!droneBatteryHistoryEntities.isEmpty())
		{
			log.debug("drones remaing battery percent {}", droneBatteryHistoryEntities);
			this.droneBatteryHistoryRepository.saveAllAndFlush(droneBatteryHistoryEntities);
		}

	}

}
