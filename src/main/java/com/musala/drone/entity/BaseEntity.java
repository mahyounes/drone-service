package com.musala.drone.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity
{

	@Id
	@GeneratedValue(
			strategy = GenerationType.IDENTITY)
	protected Long id;

	@Column(
			nullable = false,
			updatable = false,
			name = "CREATION_DATE")
	@CreatedDate
	protected Timestamp creationDate;

	@Column(
			name = "LAST_MODIFIED_DATE")
	@LastModifiedDate
	protected Timestamp lastModifiedDate;

	@Version
	protected long version;
}
