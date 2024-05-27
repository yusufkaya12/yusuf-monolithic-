package com.threepounds.caseproject.controller.mapper;

import com.threepounds.caseproject.controller.dto.AdvertDto;
import com.threepounds.caseproject.controller.dto.FavouritesDto;
import com.threepounds.caseproject.controller.resource.AdvertResource;
import com.threepounds.caseproject.controller.resource.FavouritesResource;
import com.threepounds.caseproject.data.entity.Advert;
import com.threepounds.caseproject.data.entity.Favourites;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FavouritesMapper {


   Favourites favouriteDtoToEntity(FavouritesDto favouritesDto);

   FavouritesResource entityToFavouriteResource(Favourites favourites);

   List<FavouritesResource> entityToFavouriteResources(List<Favourites> favourites);


}
