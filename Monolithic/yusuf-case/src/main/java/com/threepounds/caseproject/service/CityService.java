package com.threepounds.caseproject.service;

import com.threepounds.caseproject.data.entity.adress.City;
import com.threepounds.caseproject.data.repository.CityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }
    public City save(City city){
        return cityRepository.save(city);
    }
    public void delete(String id){
         cityRepository.deleteById(id);

    }
    public Optional<City> getById(String id){
        return cityRepository.findById(id);
    
    }

    public List<City> getAllCities(){
        return cityRepository.findAll();
    }

    public Page<City> listByPage(int pageNumber, int pageSize)
    {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return cityRepository.findAll(pageable);
    }
    
}
