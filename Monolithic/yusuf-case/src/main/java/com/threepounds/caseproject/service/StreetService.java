package com.threepounds.caseproject.service;

import com.threepounds.caseproject.data.entity.adress.County;
import com.threepounds.caseproject.data.entity.adress.Street;
import com.threepounds.caseproject.data.repository.CountyRepository;
import com.threepounds.caseproject.data.repository.StreetRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StreetService {

    private final StreetRepository streetRepository;

    public StreetService(StreetRepository streetRepository) {
        this.streetRepository = streetRepository;
    }
    public Street save(Street street){
        return streetRepository.save(street);
    }
    public void delete(String id){
         streetRepository.deleteById(id);

    }
    public Optional<Street> getById(String id){
        return streetRepository.findById(id);
    
    }
    public List<Street> getAllStreets(){
        return streetRepository.findAll();
    }

    public Page<Street> listByPage(int pageNumber, int pageSize)
    {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return streetRepository.findAll(pageable);
    }
    
}
