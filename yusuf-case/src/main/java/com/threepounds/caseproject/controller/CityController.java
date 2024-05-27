package com.threepounds.caseproject.controller;

import com.threepounds.caseproject.controller.dto.CityDto;
import com.threepounds.caseproject.controller.mapper.CityMapper;
import com.threepounds.caseproject.controller.resource.CityResource;
import com.threepounds.caseproject.controller.response.ResponseModel;
import com.threepounds.caseproject.data.entity.adress.City;
import com.threepounds.caseproject.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/city")
public class CityController {
    private final CityService cityService;
    private final CityMapper cityMapper;

    public CityController(CityService cityService, CityMapper cityMapper) {
        this.cityService = cityService;
        this.cityMapper = cityMapper;
    }


    @PostMapping("")
    public ResponseModel<CityResource> createCity(@RequestBody CityDto cityDto) {
        try {
            City city = cityMapper.cityDtoToEntity(cityDto);
            City savedCity = cityService.save(city);
            CityResource cityResource = cityMapper.entityToCityResource(savedCity);


            return new ResponseModel<>(HttpStatus.OK.value(), cityResource, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        cityService.delete(id);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/{id}")
    public ResponseModel<CityResource> getOneCity(@PathVariable String id) {
        City city = cityService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException());
        CityResource cityResource = cityMapper.entityToCityResource(city);
        return new ResponseModel<>(HttpStatus.OK.value(), cityResource, null);
    }

    @GetMapping("")
    public ResponseEntity<List<CityResource>> listCities() {
        List<CityResource> cityResources = cityMapper.entityToCityResource(cityService.getAllCities());

        return ResponseEntity.ok(cityResources);
    }


}