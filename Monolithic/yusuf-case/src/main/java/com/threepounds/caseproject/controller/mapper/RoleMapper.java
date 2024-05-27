package com.threepounds.caseproject.controller.mapper;

import com.threepounds.caseproject.controller.dto.RoleDto;
import com.threepounds.caseproject.controller.resource.RoleResource;
import com.threepounds.caseproject.data.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(source = "permissions" , target = "permissions", ignore = true)
    Role dtoToEntity(RoleDto roleDto);
    RoleResource roleDto(Role role);
    @Mapping(source = "permissions" , target = "permissions", ignore = true)
    List<RoleResource> roleDtoToList(List<Role> roles);

}
