package com.musala.drone.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.musala.drone.entity.DroneBatteryHistoryEntity;

@Repository
public interface DroneBatteryHistoryRepository extends JpaRepository<DroneBatteryHistoryEntity, Long>
{

}
