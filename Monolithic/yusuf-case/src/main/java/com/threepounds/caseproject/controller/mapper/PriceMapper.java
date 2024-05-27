package com.threepounds.caseproject.controller.mapper;


import com.threepounds.caseproject.controller.dto.UpdatePriceDto;
import com.threepounds.caseproject.controller.resource.PriceResource;
import com.threepounds.caseproject.data.entity.PriceHistory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PriceMapper {


    PriceHistory priceDtoToEntity(UpdatePriceDto updatePriceDto);

    PriceResource entityToPriceResource(PriceHistory priceHistory);

    List<PriceResource> entityToPriceResource(List<PriceHistory> priceHistories);


}
