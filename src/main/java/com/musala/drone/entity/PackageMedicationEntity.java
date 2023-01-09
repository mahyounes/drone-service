package com.musala.drone.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.musala.medication.entity.MedicationEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(
		name = "PACKAGE_MEDICATION")
@EntityListeners(AuditingEntityListener.class)
public class PackageMedicationEntity {

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
