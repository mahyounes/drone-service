package com.musala.drone.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Where(
		clause = "delivered = false")
@Table(
		name = "DRONE_PACKAGE")
public class DronePackageEntity
{

	@Id
	@GeneratedValue(
			strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(
			name = "DELIVERED")
	private boolean delivered;

	@Column(
			nullable = false,
			updatable = false,
			name = "pickUp_DATE")
	@CreatedDate
	private Timestamp pickUpDate;

	@Column(
			name = "DELIVERY_DATE")
	private Timestamp deliveryDate;

	@ManyToOne(
			fetch = FetchType.LAZY)
	private DroneEntity drone;

	@OneToMany(
			cascade = CascadeType.ALL,
			fetch = FetchType.EAGER,
			mappedBy = "dronePackage")
	@NotFound(
			action = NotFoundAction.IGNORE)
	@Builder.Default
	private Set<PackageMedicationEntity> packageMedications = new HashSet<>();

	@PreUpdate
	protected void preUpdate()
	{
		if (this.delivered && this.deliveryDate == null)
		{
			this.deliveryDate = new Timestamp(System.currentTimeMillis());
		}
	}

	public void addPackageMedication(final PackageMedicationEntity packageMedication)
	{
		this.packageMedications.add(packageMedication);
		packageMedication.setDronePackage(this);
	}

	public void addPackageMedication(final Set<PackageMedicationEntity> packageMedications)
	{
		for (PackageMedicationEntity packageMedicationEntity : packageMedications)
		{
			addPackageMedication(packageMedicationEntity);
		}
	}

	public void removePackageMedication(final PackageMedicationEntity packageMedication)
	{
		this.packageMedications.remove(packageMedication);
		packageMedication.setDronePackage(null);
	}

}
