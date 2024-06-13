package org.acme.mappers;

import org.acme.domain.Recipe;
import org.acme.models.entities.RecipeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "jakarta-cdi", uses = { AppointmentMapper.class })
public interface RecipeMapper {
	@Mapping(source = "appointment.id", target = "appointment_id")
	Recipe toDomain(RecipeEntity entity);
	@Mapping(source = "appointment_id", target = "appointment.id")
	@Mapping(target = "id", ignore = true)
	RecipeEntity toEntity(Recipe domain);
}
