package org.acme.mappers;

import org.acme.models.entities.AppointmentEntity;
import org.acme.models.dto.AppointmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AppointmentMapper {
    AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);

    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "patient.id", target = "patientId")
    AppointmentDTO toDTO(AppointmentEntity entity);

    @Mapping(source = "doctorId", target = "doctor.id")
    @Mapping(source = "patientId", target = "patient.id")
    AppointmentEntity toEntity(AppointmentDTO dto);
}
