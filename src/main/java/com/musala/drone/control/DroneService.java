package com.musala.drone.control;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.musala.drone.boundery.helper.dto.DroneDto;
import com.musala.drone.boundery.helper.dto.DronePackageDto;
import com.musala.drone.boundery.helper.mapper.DroneMapper;
import com.musala.drone.entity.DroneEntity;
import com.musala.drone.entity.enums.DroneModelEnum;
import com.musala.drone.entity.enums.DroneStateEnum;
import com.musala.drone.entity.repository.DroneRepository;
import com.musala.exception.MusalaException;
import com.musala.exception.model.MusalaErrorCodeEnum;

@Service
@Transactional(
		isolation = Isolation.READ_COMMITTED)
public class DroneService {

	private DroneRepository droneRepository;
	private DroneMapper droneMapper;
	private DronePackageService dronePackageService;

	public DroneService(final DroneRepository droneRepository, final DroneMapper droneMapper,
			final DronePackageService dronePackageService) {
		this.droneRepository = droneRepository;
		this.droneMapper = droneMapper;
		this.dronePackageService = dronePackageService;
	}

	public DroneDto retrieveDrone(final String droneSerialNumber) {

		DroneEntity droneEntity = this.droneRepository.findBySerialNumber(droneSerialNumber)
				.orElseThrow(() -> new MusalaException(MusalaErrorCodeEnum.DNF));
		return this.droneMapper.toDto(droneEntity);
	}

	public List<DroneEntity> listDrones() {

		return this.droneRepository.findAll();
	}

	public Page<DroneDto> listAvailableDrones(final Pageable pageable) {
		Page<DroneEntity> droneEntities = this.droneRepository.findByState(DroneStateEnum.IDLE, pageable);
		List<DroneDto> dtos = this.droneMapper.toDtos(droneEntities.getContent());

		return new PageImpl<>(dtos, pageable, droneEntities.getTotalElements());
	}

	public DroneDto registerDrone(final DroneDto dronedto) {

		if (this.droneRepository.findBySerialNumber(dronedto.getSerialNumber()).isPresent()) {
			throw new MusalaException(MusalaErrorCodeEnum.DWSAR);
		}

		checkWeightAndModel(dronedto.getWeightLimitInGram(), dronedto.getModel());
		DroneEntity droneEntity = this.droneMapper.toEntity(dronedto);
		DroneEntity savedDroneEntity = this.droneRepository.saveAndFlush(droneEntity);
		return this.droneMapper.toDto(savedDroneEntity);
	}

	public void loadDrone(final String droneSerialNumber, final DronePackageDto dronePackageDto) {
		DroneEntity droneEntity = this.droneRepository.findBySerialNumber(droneSerialNumber)
				.orElseThrow(() -> new MusalaException(MusalaErrorCodeEnum.DNF));

		this.dronePackageService.createDronePackage(dronePackageDto, droneEntity);
		droneEntity.setState(DroneStateEnum.LOADED);
		this.droneRepository.saveAndFlush(droneEntity);
	}

	private void checkWeightAndModel(BigDecimal weightLimitInGram, DroneModelEnum model) {

		int modelMaxWeightLimt = 0;

		switch (model) {
		case LIGHTWEIGHT:
			modelMaxWeightLimt = 100;
			break;

		case MIDDLEWEIGHT:
			modelMaxWeightLimt = 250;
			break;

		case CRUISERWEIGHT:
			modelMaxWeightLimt = 350;
			break;

		case HEAVYWEIGHT:
			modelMaxWeightLimt = 500;
			break;

		default:
			throw new MusalaException("Unkown model", HttpStatus.BAD_REQUEST);
		}

		compareWeightLimtAndModelLimit(weightLimitInGram, modelMaxWeightLimt);

	}

	private void compareWeightLimtAndModelLimit(BigDecimal weightLimitInGram, int modelMaxWeightLimt) {
		if (weightLimitInGram.compareTo(BigDecimal.valueOf(modelMaxWeightLimt)) > 0) {
			throw new MusalaException("Model don't match weight limit", HttpStatus.CONFLICT);
		}
	}

}
