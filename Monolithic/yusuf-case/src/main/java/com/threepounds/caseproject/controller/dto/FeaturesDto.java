package com.threepounds.caseproject.controller.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeaturesDto {
    private String categoryId;

    private String id;

    private String title;

    private boolean active;



}
