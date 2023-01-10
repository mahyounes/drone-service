package com.musala.drone.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.musala.drone.entity.DroneBatteryHistoryEntity;
import com.musala.drone.entity.DroneEntity;

@Repository
public interface DroneBatteryHistoryRepository extends JpaRepository<DroneBatteryHistoryEntity, Long>
{

	List<DroneBatteryHistoryEntity> findByDrone(DroneEntity drone);
}
