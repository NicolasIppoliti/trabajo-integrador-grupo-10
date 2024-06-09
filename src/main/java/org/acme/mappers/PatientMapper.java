package org.acme.mappers;

import org.acme.models.entities.PatientEntity;
import org.acme.models.dto.PatientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {AppointmentMapper.class})
public interface PatientMapper {
    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    @Mapping(source = "appointments", target = "appointments")
    PatientDTO toDTO(PatientEntity entity);

    @Mapping(source = "appointments", target = "appointments")
    PatientEntity toEntity(PatientDTO dto);
}
