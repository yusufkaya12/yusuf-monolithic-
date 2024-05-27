package com.threepounds.caseproject.controller.mapper;

import com.threepounds.caseproject.controller.dto.FeaturesDto;
import com.threepounds.caseproject.controller.resource.FeaturesResource;
import com.threepounds.caseproject.data.entity.Features;
import java.util.List;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface FeaturesMapper {
    Features dtoToEntity(FeaturesDto FeaturesDto);


    FeaturesResource featureToResource(Features features);
    List<FeaturesResource> featuresToResourceList(List<Features> features);
}
