package org.acme.mappers;

import org.acme.models.entities.DoctorEntity;
import org.acme.models.entities.AppointmentEntity;
import org.acme.models.dto.DoctorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface DoctorMapper {
    DoctorMapper INSTANCE = Mappers.getMapper(DoctorMapper.class);

    @Mapping(target = "appointmentIds", source = "appointments")
    DoctorDTO toDTO(DoctorEntity entity);

    @Mapping(target = "appointments", ignore = true)
    DoctorEntity toEntity(DoctorDTO dto);

    default List<Long> mapAppointments(List<AppointmentEntity> appointments) {
        return appointments.stream()
                .map(AppointmentEntity::getId)
                .collect(Collectors.toList());
    }
}
