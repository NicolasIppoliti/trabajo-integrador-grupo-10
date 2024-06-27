package org.acme.mappers;

import org.acme.domain.Patient;
import org.acme.models.entities.PatientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "jakarta-cdi")
public interface PatientResponseMapper {
    @Mapping(target = "password", ignore = true)
	Patient toDomain(PatientEntity entity);
}
