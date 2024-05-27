package com.threepounds.caseproject.controller.dto;

import com.threepounds.caseproject.data.entity.Advert;
import com.threepounds.caseproject.data.entity.ESTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EsDto {
    private String keyword;
}
