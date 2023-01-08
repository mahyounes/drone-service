package com.musala.drone.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
		name = "DRONE_BATTERY_HISTORY")
public class DroneBatteryHistoryEntity
{

	@Id
	@GeneratedValue(
			strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(
			nullable = false,
			updatable = false,
			name = "CREATION_DATE")
	private Timestamp creationDate;

	@Column(
			nullable = false,
			name = "REMAINING_BATTERY_PERCENT")
	private BigDecimal remainingBatteryPercent;

}
