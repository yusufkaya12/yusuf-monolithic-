package com.threepounds.caseproject.service;

import com.threepounds.caseproject.data.entity.Category;
import com.threepounds.caseproject.data.entity.Permission;
import com.threepounds.caseproject.data.entity.Role;
import com.threepounds.caseproject.data.repository.PermissionsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PermissionService {
    private final PermissionsRepository permissionsRepository;

    public PermissionService(PermissionsRepository permissionsRepository) {
        this.permissionsRepository = permissionsRepository;
    }
    public Permission save(Permission permission){
        return permissionsRepository.save(permission);
    }
    public void delete(String id){
        permissionsRepository.deleteById(id);
    }
    public List<Permission> list(){
        return permissionsRepository.findAll();
    }
    public Optional<Permission> getById(String id){
        return permissionsRepository.findById(id);
    }

    public List<Permission> list(List<String> permissions){
        return permissionsRepository.findAllById(permissions);
    }
    public Optional <Permission> getByName(String name){
        return permissionsRepository.findByName(name);
    }
    public Page<Permission> listByPage(int pageNumber, int pageSize)
    {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return permissionsRepository.findAll(pageable);
    }
}
