package com.threepounds.caseproject.controller;

import com.threepounds.caseproject.controller.dto.PermissionDto;
import com.threepounds.caseproject.controller.mapper.PermissionMapper;
import com.threepounds.caseproject.controller.resource.PermissionResource;
import com.threepounds.caseproject.controller.response.ResponseModel;
import com.threepounds.caseproject.data.entity.Permission;
import com.threepounds.caseproject.service.PermissionService;
import com.threepounds.caseproject.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/permissions")
public class PermissionController {
    private final PermissionService permissionService;
    private final PermissionMapper permissionMapper;

    public PermissionController(PermissionService permissionService, PermissionMapper permissionMapper, RoleService roleService) {
        this.permissionService = permissionService;
        this.permissionMapper = permissionMapper;
    }

    @PostMapping("")
    public ResponseEntity<PermissionResource> create(@RequestBody PermissionDto permissionDto) {
        Permission permissionToSave = permissionMapper.permissionDtoToEntity(permissionDto);
        Permission savedPermission = permissionService.save(permissionToSave);
        PermissionResource permissionResource = permissionMapper.entityToPermissionResource(savedPermission);
        return ResponseEntity.ok(permissionResource);
    }

    @PutMapping("{id}")
    public ResponseEntity<PermissionResource> update(@PathVariable String id, @RequestBody PermissionDto permissionDto) {
        Permission existingPermission = permissionService.getById(id)
                .orElseThrow(() -> new RuntimeException());
        Permission mappedPermission = permissionMapper.permissionDtoToEntity(permissionDto);
        mappedPermission.setId(existingPermission.getId());
        Permission updatePermission = permissionService.save(mappedPermission);
        PermissionResource permissionResource = permissionMapper.entityToPermissionResource(updatePermission);
        return ResponseEntity.ok(permissionResource);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        permissionService.delete(id);
        return ResponseEntity.ok("success");
    }

    @GetMapping("{id}")
    public ResponseEntity<PermissionResource> getOnePermission(@PathVariable String id) {
        Permission permission = permissionService.getById(id)
                .orElseThrow(() -> new RuntimeException());
        PermissionResource permissionResource = permissionMapper.entityToPermissionResource(permission);
        return ResponseEntity.ok(permissionResource);
    }

    @GetMapping("")
    public ResponseEntity<List<PermissionResource>> list() {
        List<PermissionResource> permissionResources = permissionMapper.entityToPermissionResource(permissionService.list());
        return ResponseEntity.ok(permissionResources);
    }

    @GetMapping("/page")
    public ResponseModel<List<PermissionResource>> listByPage(@RequestParam int pageNumber, @RequestParam int pageSize) {

        Page<Permission> permissions = permissionService.listByPage(pageNumber, pageSize);

        List<PermissionResource> permissionResources = permissionMapper.entityToPermissionResource(
                permissions.toList());

        return new ResponseModel<>(HttpStatus.OK.value(), permissionResources, null,
                (int) permissions.getTotalElements(), permissions.getTotalPages());
    }
}
