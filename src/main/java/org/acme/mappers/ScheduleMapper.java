package org.acme.mappers;

import org.acme.domain.Schedule;
import org.acme.models.entities.ScheduleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "jakarta-cdi")
public interface ScheduleMapper {
	Schedule toDomain(ScheduleEntity entity);
	@Mapping(target = "doctorEntity", ignore = true)
	ScheduleEntity toEntity(Schedule domain);
}
