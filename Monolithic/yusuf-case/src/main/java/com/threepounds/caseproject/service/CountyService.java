package com.threepounds.caseproject.service;

import com.threepounds.caseproject.data.entity.adress.City;
import com.threepounds.caseproject.data.entity.adress.County;
import com.threepounds.caseproject.data.repository.CityRepository;
import com.threepounds.caseproject.data.repository.CountyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CountyService {

    private final CountyRepository countyRepository;

    public CountyService(CountyRepository countyRepository) {
        this.countyRepository = countyRepository;
    }
    public County save(County county){
        return countyRepository.save(county);
    }
    public void delete(String id){
         countyRepository.deleteById(id);

    }
    public Optional<County> getById(String id){
        return countyRepository.findById(id);
    
    }
    public List<County> getAllCounties(){
        return countyRepository.findAll();
    }

    public Page<County> listByPage(int pageNumber, int pageSize)
    {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return countyRepository.findAll(pageable);
    }
    
}
