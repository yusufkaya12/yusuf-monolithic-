package com.threepounds.caseproject.controller.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceResource {

  private String advert_id;

  private BigDecimal oldPrice;

  private BigDecimal newPrice;

}
