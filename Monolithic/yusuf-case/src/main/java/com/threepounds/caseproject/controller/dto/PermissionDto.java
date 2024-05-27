package com.threepounds.caseproject.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDto {
    private String name;
    private String displayName;
}
