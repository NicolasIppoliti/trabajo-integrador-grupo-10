package org.acme.mappers;

import org.acme.domain.DoctorRequestDTO;
import org.acme.models.entities.DoctorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "jakarta-cdi")
public interface DoctorRequestMapper {

	@Mappings({
        @Mapping(source = "branch.id", target = "branch_id")
    })
	DoctorRequestDTO toDomain(DoctorEntity entity);

	@Mappings({
        @Mapping(source = "branch_id", target = "branch.id"),
        @Mapping(target = "schedules", ignore = true),
        @Mapping(target = "appointments", ignore = true),
        @Mapping(target = "id", ignore = true)
    })
    DoctorEntity toEntity(DoctorRequestDTO domain);
    }
