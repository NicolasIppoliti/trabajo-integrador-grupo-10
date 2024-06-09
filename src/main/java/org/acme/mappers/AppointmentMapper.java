package org.acme.mappers;

import org.acme.domain.Appointment;
import org.acme.models.entities.AppointmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "jakarta-cdi")
public interface AppointmentMapper {
    @Mappings({
        @Mapping(source = "patient.id", target = "patient_id"),
        @Mapping(source = "doctor.id", target = "doctor_id")
    })
    Appointment toDomain(AppointmentEntity entity);
    @Mappings({
        @Mapping(source = "patient_id", target = "patient.id"),
        @Mapping(source = "doctor_id", target = "doctor.id")
    })
    AppointmentEntity toEntity(Appointment domain);
}
