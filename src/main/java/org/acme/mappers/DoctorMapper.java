package org.acme.mappers;

import org.acme.models.entities.DoctorEntity;
import org.acme.models.dto.DoctorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {AppointmentMapper.class})
public interface DoctorMapper {
    DoctorMapper INSTANCE = Mappers.getMapper(DoctorMapper.class);

    @Mapping(source = "appointments", target = "appointments")
    DoctorDTO toDTO(DoctorEntity entity);

    @Mapping(source = "appointments", target = "appointments")
    DoctorEntity toEntity(DoctorDTO dto);
}
