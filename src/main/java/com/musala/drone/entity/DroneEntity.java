package com.musala.drone.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.musala.drone.entity.enums.DroneModelEnum;
import com.musala.drone.entity.enums.DroneStateEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Setter
@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(
		name = "DRONE")
public class DroneEntity extends BaseEntity
{

	@Column(
			length = 100,
			nullable = false,
			name = "SERIAL_NUMBER")
	private String serialNumber;

	@Column(
			nullable = false,
			name = "MODEL")
	@Enumerated(EnumType.STRING)
	private DroneModelEnum model;

	@Column(
			nullable = false,
			name = "WEIGHT_LIMIT_IN_GRAM")
	private BigDecimal weightLimitInGram;

	@Column(
			nullable = false,
			name = "REMAINING_BATTERY_PERCENT")
	private BigDecimal remainingBatteryPercent;

	@Column(
			nullable = false,
			name = "STATE")
	@Enumerated(EnumType.STRING)
	private DroneStateEnum state;

	@OneToMany(
			mappedBy = "drone",
			orphanRemoval = true)
	@Builder.Default
	private Set<DroneBatteryHistoryEntity> batteryPercentLogs = new HashSet<>();

	@OneToMany(
			fetch = FetchType.EAGER,
			mappedBy = "drone")
	@NotFound(
			action = NotFoundAction.IGNORE)
	@Builder.Default
	private Set<DronePackageEntity> dronePacakeges = new HashSet<>();

}
