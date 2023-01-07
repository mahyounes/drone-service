package com.musala.drone.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@Table(
		name = "PACKAGE_MEDICATION")
public class PackageMedicationEntity
{

	@Id
	@GeneratedValue(
			strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(
			name = "MEDICATION_ITEMS_COUNT")
	private Long medicationItemsCount;

	@ManyToOne(
			fetch = FetchType.LAZY)
	private DronePackageEntity dronePackage;

	@OneToOne(
			fetch = FetchType.EAGER)
	@JoinColumn(
			name = "MEDICATION_ID")
	private MedicationEntity medication;

}
