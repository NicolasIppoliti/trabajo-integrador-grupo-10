package org.acme.mappers;

import org.acme.domain.Appointment;
import org.acme.models.entities.AppointmentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi", uses = { DoctorResponseMapper.class, PatientMapper.class })
public interface AppointmentMapper {
    Appointment toDomain(AppointmentEntity entity);
    AppointmentEntity toEntity(Appointment domain);
}
