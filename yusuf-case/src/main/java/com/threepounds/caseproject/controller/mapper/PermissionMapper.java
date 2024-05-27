package com.threepounds.caseproject.controller.mapper;

import com.threepounds.caseproject.controller.dto.PermissionDto;
import com.threepounds.caseproject.controller.resource.PermissionResource;
import com.threepounds.caseproject.data.entity.Permission;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission permissionDtoToEntity(PermissionDto permissionDto);
    PermissionResource entityToPermissionResource(Permission permission);
    List<PermissionResource> entityToPermissionResource(List<Permission> permissions);

}
