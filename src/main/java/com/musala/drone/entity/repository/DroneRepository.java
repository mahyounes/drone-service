package com.musala.drone.entity.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.musala.drone.entity.DroneEntity;
import com.musala.drone.entity.enums.DroneStateEnum;

@Repository
public interface DroneRepository extends JpaRepository<DroneEntity, Long>
{

	Optional<DroneEntity> findBySerialNumber(String serialNumber);

	Page<DroneEntity> findByState(DroneStateEnum state, Pageable pageable);
}
