package org.acme.mappers;

import org.acme.domain.Patient;
import org.acme.models.entities.PatientEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi", uses = { AppointmentMapper.class })
public interface PatientMapper {
	Patient toDomain(PatientEntity entity);
	PatientEntity toEntity(Patient domain);
}
