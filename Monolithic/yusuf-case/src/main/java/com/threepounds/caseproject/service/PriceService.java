package com.threepounds.caseproject.service;

import com.threepounds.caseproject.data.entity.PriceHistory;
import com.threepounds.caseproject.data.repository.PriceRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PriceService {


    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }
    public PriceHistory save(PriceHistory priceHistory){

        return priceRepository.save(priceHistory);
    }
    public void delete(String id){
         priceRepository.deleteById(id);

    }


    public Optional<PriceHistory> getById(String id){
        return priceRepository.findById(id);
    
    }

    public List<PriceHistory> getAllPrice(){
        return priceRepository.findAll();
    }



}
