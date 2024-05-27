package com.threepounds.caseproject.service;
import com.threepounds.caseproject.controller.resource.AdvertResource;
import com.threepounds.caseproject.data.entity.Advert;

import com.threepounds.caseproject.data.entity.Category;
import com.threepounds.caseproject.data.repository.AdvertRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class AdvertService {


    private final AdvertRepository advertRepository;

    public AdvertService(AdvertRepository advertRepository) {
        this.advertRepository = advertRepository;
    }
    public Advert save(Advert advert){

        return advertRepository.save(advert);
    }
    public void delete(String id){
         advertRepository.deleteById(id);

    }


    public Optional<Advert> getById(String id){
        return advertRepository.findById(id);
    
    }
    public List<Advert> getAllAdvert(){
        return advertRepository.findAll();
    }

    public double findDistance(double lat1Rad,double lat2Rad,double lon1Rad,double lon2Rad){
        double x = (lon2Rad - lon1Rad) * Math.cos((lat1Rad + lat2Rad) / 2);
        double y = (lat2Rad - lat1Rad);
        return Math.sqrt(x * x + y * y) * 6371;
    }

    public List<Advert> advertsByCategory(Category category){
        return advertRepository.findByCategory(category);
    }
    public Page<Advert> listByPage(int pageNumber, int pageSize)
    {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return advertRepository.findAll(pageable);
    }

    public Page<Advert> listByPageSort(int pageNumber, int pageSize, Sort sort)
    {
        Pageable pageable = PageRequest.of(pageNumber,pageSize, sort);
        return advertRepository.findAll(pageable);
    }
    
}
