package com.threepounds.caseproject.controller.mapper;

import com.threepounds.caseproject.controller.dto.CityDto;
import com.threepounds.caseproject.controller.dto.CountyDto;
import com.threepounds.caseproject.controller.resource.CityResource;
import com.threepounds.caseproject.controller.resource.CountyResource;
import com.threepounds.caseproject.data.entity.adress.City;
import com.threepounds.caseproject.data.entity.adress.County;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountyMapper {


   County countyDtoToEntity(CountyDto countyDto);

   CountyResource entityToCountyResource(County county);

   List<CountyResource> entityToCountyResource(List<County> counties);


}
