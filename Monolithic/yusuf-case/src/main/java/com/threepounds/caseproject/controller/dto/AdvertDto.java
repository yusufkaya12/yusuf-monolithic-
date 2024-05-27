package com.threepounds.caseproject.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;


import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvertDto {
    private String id;
    private String categoryId;
    private String title;
    private ZonedDateTime lastUpdated;
    private String description;
    private boolean active;
    private List<String> tags;
    private ZonedDateTime createdDate;
    private BigDecimal price;
    private String cityId;
    private String countyId;
    private String streetId;
    private Double latitude;
    private Double longitude;

}







