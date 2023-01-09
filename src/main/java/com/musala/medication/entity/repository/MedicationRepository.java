package com.musala.medication.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.musala.medication.entity.MedicationEntity;

@Repository
public interface MedicationRepository extends JpaRepository<MedicationEntity, Long>
{

}
