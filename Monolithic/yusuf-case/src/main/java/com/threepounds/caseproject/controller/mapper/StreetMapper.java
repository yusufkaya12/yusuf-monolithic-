package com.threepounds.caseproject.controller.mapper;

import com.threepounds.caseproject.controller.dto.StreetDto;
import com.threepounds.caseproject.controller.resource.StreetResource;
import com.threepounds.caseproject.data.entity.adress.Street;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StreetMapper {


   Street streetDtoToEntity(StreetDto streetDto);

   StreetResource entityToStreetResource(Street street);

   List<StreetResource> entityToStreetResource(List<Street> streets);


}
