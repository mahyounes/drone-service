package com.musala.util;

import java.util.List;

import org.mapstruct.Mapping;

public interface GenericMapper<D, E>
{

	D toDto(E entity);

	List<D> toDtos(List<E> entities);

	@Mapping(
			target = "creationDate",
			ignore = true)
	@Mapping(
			target = "lastModifiedDate",
			ignore = true)
	E toEntity(D dto);

	List<E> toEntities(List<D> dtos);

}
