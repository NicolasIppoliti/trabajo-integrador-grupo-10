package org.acme.mappers;

import org.acme.domain.Doctor;
import org.acme.models.entities.DoctorEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi", uses = { ScheduleMapper.class, AppointmentMapper.class })
public interface DoctorMapper {
	Doctor toDomain(DoctorEntity entity);
	DoctorEntity toEntity(Doctor domain);
}
