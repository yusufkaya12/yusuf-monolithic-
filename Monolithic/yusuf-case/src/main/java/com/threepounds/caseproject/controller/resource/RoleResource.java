package com.threepounds.caseproject.controller.resource;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleResource {

    private String id;
    private String name;
    private String displayName;

    private List<PermissionResource> permissions;

}
