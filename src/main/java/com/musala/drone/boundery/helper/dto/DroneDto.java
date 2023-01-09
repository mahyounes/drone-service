package com.musala.drone.boundery.helper.dto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.musala.drone.entity.enums.DroneModelEnum;
import com.musala.drone.entity.enums.DroneStateEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode(
		callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DroneDto extends BaseDto {

	@NotBlank(
			message = "Serial number is required")
	@Length(
			min = 1,
			max = 100,
			message = "serialNumber lenght must be greater than 0 and less than or equal than 100")
	private String serialNumber;

	@NotNull(
			message = "Model is required")
	private DroneModelEnum model;

	@NotNull(
			message = "Weight limit is required")
	@Max(
			value = 500,
			message = "Max weight limit is 500 gr")
	@Min(
			value = 1,
			message = "Weight limit should be more than 0")
	private BigDecimal weightLimitInGram;

	@Max(
			value = 100,
			message = "Max battery percent is 100")
	@Min(
			value = 0,
			message = "Min battery percent is 0")
	@NotNull(
			message = "Remaining battery percent is required")
	private BigDecimal remainingBatteryPercent;

	@NotNull(
			message = "Drone state is required")
	private DroneStateEnum state;

	@Builder.Default
	private Set<DronePackageDto> dronePacakeges = new HashSet<>();

	@Builder.Default
	private Set<DroneBatteryHistoryDto> batteryPercentLogs = new HashSet<>();

}
