package com.threepounds.caseproject.service;

import com.threepounds.caseproject.data.entity.Category;
import com.threepounds.caseproject.data.entity.Permission;
import com.threepounds.caseproject.data.entity.Role;
import com.threepounds.caseproject.data.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    public Role save(Role role){
        return roleRepository.save(role);
    }
    public void delete(String id){
        roleRepository.deleteById(id);
    }
    public Optional <Role> getById(String id){
        return roleRepository.findById(id);
    }
    public List <Role> getRoles(){
        return roleRepository.findAll();
    }
    public List<Role> list(List<String> roles){
        return roleRepository.findAllById(roles);}

    public Optional <Role> getByName(String name){
        return roleRepository.findByName(name);
    }
    public Page<Role> listByPage(int pageNumber, int pageSize)
    {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return roleRepository.findAll(pageable);
    }

}
