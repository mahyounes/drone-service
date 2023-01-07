package com.musala.drone.entity;

import java.math.BigDecimal;
import java.sql.Blob;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Where(
		clause = "deleted = false")
@Table(
		name = "MEDICATION")
public class MedicationEntity extends BaseEntity
{

	@Column(
			nullable = false,
			name = "NAME")
	private String name;

	@Column(
			nullable = false,
			name = "WEIGHT_IN_GRAM")
	private BigDecimal weightInGram;

	@Column(
			name = "code")
	private String code;

	@Column(
			name = "IMAGE_BASE_64")
	@Basic(
			fetch = FetchType.LAZY)
	private Blob imageBase64;
}
