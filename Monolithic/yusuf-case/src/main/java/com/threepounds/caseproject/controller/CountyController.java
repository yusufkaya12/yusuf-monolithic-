package com.threepounds.caseproject.controller;

import com.threepounds.caseproject.controller.dto.CountyDto;
import com.threepounds.caseproject.controller.mapper.CountyMapper;
import com.threepounds.caseproject.controller.resource.CountyResource;
import com.threepounds.caseproject.controller.response.ResponseModel;
import com.threepounds.caseproject.data.entity.adress.City;
import com.threepounds.caseproject.data.entity.adress.County;
import com.threepounds.caseproject.exceptions.NotFoundException;
import com.threepounds.caseproject.service.CityService;
import com.threepounds.caseproject.service.CountyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/county")
public class CountyController {
    private final CountyService countyService;
    private final CountyMapper countyMapper;

    private final CityService cityService;

    public CountyController(CountyService countyService, CountyMapper countyMapper, CityService cityService) {
        this.countyService = countyService;
        this.countyMapper = countyMapper;
        this.cityService = cityService;
    }


    @PostMapping("")
    public ResponseModel<CountyResource> createCounty(@RequestBody CountyDto countyDto) {
        try {
            County county = countyMapper.countyDtoToEntity(countyDto);

            City city = cityService.getById(countyDto.getCityId()).orElseThrow(() -> new NotFoundException("City Not Found"));
            County savedCounty = countyService.save(county);

            city.getCounties().add(savedCounty);
            cityService.save(city);

            CountyResource countyResource = countyMapper.entityToCountyResource(savedCounty);

            return new ResponseModel<>(HttpStatus.OK.value(), countyResource, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        countyService.delete(id);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/{id}")
    public ResponseModel<CountyResource> getOneCounty(@PathVariable String id) {
        County county = countyService.getById(id).orElseThrow(() -> new IllegalArgumentException());
        CountyResource countyResource = countyMapper.entityToCountyResource(county);
        return new ResponseModel<>(HttpStatus.OK.value(), countyResource, null);
    }

    @GetMapping("")
    public ResponseEntity<List<CountyResource>> listCounties() {
        List<CountyResource> countyResource = countyMapper.entityToCountyResource(countyService.getAllCounties());

        return ResponseEntity.ok(countyResource);
    }


}