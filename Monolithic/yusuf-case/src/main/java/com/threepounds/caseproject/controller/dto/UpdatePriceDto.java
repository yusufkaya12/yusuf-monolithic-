package com.threepounds.caseproject.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePriceDto {
    private String advertId;
    private BigDecimal price;
}







