package com.musala.drone.boundery.helper.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DroneBatteryHistoryDto {

	private Long id;

	private Timestamp creationDate;

	private BigDecimal remainingBatteryPercent;

}
