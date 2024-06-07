package org.acme.mappers;

import org.acme.domain.Schedule;
import org.acme.models.entities.ScheduleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface ScheduleMapper {
	Schedule toDomain(ScheduleEntity entity);
	ScheduleEntity toEntity(Schedule domain);
}
