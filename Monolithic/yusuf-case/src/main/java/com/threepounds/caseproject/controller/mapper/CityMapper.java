package com.threepounds.caseproject.controller.mapper;

import com.threepounds.caseproject.controller.dto.CityDto;
import com.threepounds.caseproject.controller.resource.CityResource;
import com.threepounds.caseproject.data.entity.adress.City;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CityMapper {


   City cityDtoToEntity(CityDto cityDto);

   CityResource entityToCityResource(City city);

   List<CityResource> entityToCityResource(List<City> cities);


}
