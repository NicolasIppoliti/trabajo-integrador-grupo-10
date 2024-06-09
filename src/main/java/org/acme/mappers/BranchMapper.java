package org.acme.mappers;

import org.acme.domain.Branch;
import org.acme.models.entities.BranchEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jakarta-cdi")
public interface BranchMapper {
	Branch toDomain(BranchEntity entity);
	BranchEntity toEntity(Branch domain);
}
