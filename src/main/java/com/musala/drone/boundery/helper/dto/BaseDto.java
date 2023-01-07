package com.musala.drone.boundery.helper.dto;

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
public class BaseDto
{

	protected Long id;

	protected Timestamp creationDate;

	protected Timestamp lastModifiedDate;

	protected long version;
}
