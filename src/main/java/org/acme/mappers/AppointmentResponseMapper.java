package org.acme.mappers;

import org.acme.domain.AppointmentResponseDTO;
import org.acme.models.entities.AppointmentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jakarta-cdi", uses = { PatientResponseMapper.class, DoctorResponseMapper.class })
public interface AppointmentResponseMapper {

    AppointmentResponseDTO toDomain(AppointmentEntity entity);
}