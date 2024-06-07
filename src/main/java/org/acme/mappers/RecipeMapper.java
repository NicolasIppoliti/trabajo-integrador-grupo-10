package org.acme.mappers;

import org.acme.domain.Recipe;
import org.acme.models.entities.RecipeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi", uses = { AppointmentMapper.class })
public interface RecipeMapper {
	Recipe toDomain(RecipeEntity entity);
	RecipeEntity toEntity(Recipe domain);
}
