package org.acme.mappers;

import org.acme.models.entities.RecipeEntity;
import org.acme.models.dto.RecipeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RecipeMapper {
    RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);

    @Mapping(source = "appointment.id", target = "appointmentId")
    RecipeDTO toDTO(RecipeEntity entity);

    @Mapping(source = "appointmentId", target = "appointment.id")
    RecipeEntity toEntity(RecipeDTO dto);
}
