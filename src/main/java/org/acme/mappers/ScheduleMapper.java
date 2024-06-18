package org.acme.mappers;

import org.acme.domain.Schedule;
import org.acme.models.entities.ScheduleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jakarta-cdi")
public interface ScheduleMapper {
	Schedule toDomain(ScheduleEntity entity);
	ScheduleEntity toEntity(Schedule domain);
}
