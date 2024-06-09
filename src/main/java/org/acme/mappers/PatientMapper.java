package org.acme.mappers;

import org.acme.models.entities.AppointmentEntity;
import org.acme.models.entities.PatientEntity;

import java.util.List;
import java.util.stream.Collectors;

import org.acme.models.dto.PatientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PatientMapper {
    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    @Mapping(target = "appointmentIds", source = "appointments")
    PatientDTO toDTO(PatientEntity entity);

    @Mapping(target = "appointments", ignore = true)
    PatientEntity toEntity(PatientDTO dto);
    
    default List<Long> mapAppointments(List<AppointmentEntity> appointments) {
        return appointments.stream()
                .map(AppointmentEntity::getId)
                .collect(Collectors.toList());
    }
}
