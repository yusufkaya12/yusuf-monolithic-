package com.threepounds.caseproject.controller.mapper;

import com.threepounds.caseproject.controller.dto.CategoryDto;
import com.threepounds.caseproject.controller.resource.CategoryResource;
import com.threepounds.caseproject.data.entity.Category;
import java.util.List;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category dtoToEntity(CategoryDto dto);


    CategoryResource categoryDto(Category category);

    @Mapping(source = "advert" , target = "advert", ignore = true)
    @Mapping(source = "features" , target = "features", ignore = true)
    List<CategoryResource> categoryDtoList(List<Category> category);
}
