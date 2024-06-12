package org.acme.mappers;

import org.acme.domain.Patient;
import org.acme.models.entities.PatientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "jakarta-cdi", uses = { AppointmentMapper.class })
public interface PatientMapper {
	Patient toDomain(PatientEntity entity);
	@Mapping(target = "appointments", ignore = true)
	PatientEntity toEntity(Patient domain);
}
