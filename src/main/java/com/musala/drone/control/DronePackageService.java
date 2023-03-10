package com.musala.drone.control;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.musala.config.DroneConfiguration;
import com.musala.drone.boundery.helper.dto.DronePackageDto;
import com.musala.drone.boundery.helper.mapper.DronePackageMapper;
import com.musala.drone.entity.DroneEntity;
import com.musala.drone.entity.DronePackageEntity;
import com.musala.drone.entity.enums.DroneStateEnum;
import com.musala.drone.entity.repository.DronePackageRepository;
import com.musala.exception.MusalaException;
import com.musala.exception.model.MusalaErrorCodeEnum;

@Service
@Transactional(
		isolation = Isolation.READ_COMMITTED)
public class DronePackageService {

	private DronePackageRepository dronePackageRepository;
	private DronePackageMapper dronePackageMapper;
	private DroneConfiguration droneConfiguration;

	public DronePackageService(DronePackageRepository dronePackageRepository, DronePackageMapper dronePackageMapper,
			DroneConfiguration droneConfiguration) {
		this.dronePackageRepository = dronePackageRepository;
		this.dronePackageMapper = dronePackageMapper;
		this.droneConfiguration = droneConfiguration;
	}

	public void createDronePackage(final DronePackageDto dronePackageDto, final DroneEntity droneEntity) {
		DronePackageEntity dronePackageEntity = this.dronePackageMapper.toEntity(dronePackageDto);
		dronePackageEntity.setDrone(droneEntity);
		validateLoading(droneEntity, dronePackageEntity);
		this.dronePackageRepository.saveAndFlush(dronePackageEntity);
	}

	private void validateLoading(final DroneEntity droneEntity, final DronePackageEntity dronePackageDto) {
		if (droneEntity.getRemainingBatteryPercent()
				.compareTo(this.droneConfiguration.getMinBatteryPercentForLoad()) < 0) {
			throw new MusalaException(MusalaErrorCodeEnum.DBL);
		}

		if (!droneEntity.getState().equals(DroneStateEnum.IDLE)) {
			throw new MusalaException(MusalaErrorCodeEnum.DAL);
		}
		BigDecimal packageWeight = dronePackageDto.getPackageMedications().stream()
				.map(p -> p.getMedication().getWeightInGram().multiply(BigDecimal.valueOf(p.getMedicationItemsCount())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		if (droneEntity.getWeightLimitInGram().compareTo(packageWeight) < 0) {
			throw new MusalaException(MusalaErrorCodeEnum.DMWE);
		}

	}

}
