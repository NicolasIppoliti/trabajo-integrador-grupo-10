package org.acme.mappers;

import org.acme.domain.DoctorResponseDTO;
import org.acme.models.entities.DoctorEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jakarta-cdi", uses = { ScheduleMapper.class, AppointmentMapper.class })
public interface DoctorResponseMapper {
    DoctorResponseDTO toDomain(DoctorEntity entity);
	DoctorEntity toEntity(DoctorResponseDTO domain);
}
