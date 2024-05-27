package com.threepounds.caseproject.controller;

import com.threepounds.caseproject.controller.dto.FeaturesDto;
import com.threepounds.caseproject.controller.mapper.FeaturesMapper;
import com.threepounds.caseproject.controller.resource.FeaturesResource;
import com.threepounds.caseproject.controller.response.ResponseModel;
import com.threepounds.caseproject.data.entity.Category;
import com.threepounds.caseproject.data.entity.Features;
import com.threepounds.caseproject.exceptions.NotFoundException;
import com.threepounds.caseproject.service.CategoryService;
import com.threepounds.caseproject.service.FeaturesService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/features")
public class FeaturesController {
    private final FeaturesService featuresService;
    private final FeaturesMapper featuresMapper;
    private final CategoryService categoryService;

    public FeaturesController(FeaturesService featuresService, FeaturesMapper featuresMapper, CategoryService categoryService) {
        this.featuresService = featuresService;
        this.featuresMapper = featuresMapper;
        this.categoryService = categoryService;
    }

    @PostMapping("")
    public ResponseModel<FeaturesResource> create(@RequestBody FeaturesDto featuresDto) {
        Features featuresToSave = featuresMapper.dtoToEntity(featuresDto);
        Category category = categoryService.getById(featuresDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException());
        Features savedFeature = featuresService.save(featuresToSave);
        savedFeature.setCategory(category);
        featuresService.save(savedFeature);
        FeaturesResource featuresResource = featuresMapper.featureToResource(savedFeature);
        return new ResponseModel<>(HttpStatus.OK.value(), featuresResource, null);

    }

    @CacheEvict(value = "features", key = "#id")
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        featuresService.delete(id);
        return ResponseEntity.ok("Ok");
    }

    @PutMapping("{id}")
    @CachePut(value = "features", key = "#id")
    public ResponseModel<FeaturesResource> update(@PathVariable String id, @RequestBody FeaturesDto featuresDto) {
        Features existingFeature = featuresService.getById(id)
                .orElseThrow(() -> new NotFoundException("Feature not found"));
        Features mappedFeature = featuresMapper.dtoToEntity(featuresDto);
        mappedFeature.setId(existingFeature.getId());
        Features updateFeature = featuresService.save(mappedFeature);
        FeaturesResource featuresResource = featuresMapper.featureToResource(updateFeature);
        return new ResponseModel<>(HttpStatus.OK.value(), featuresResource, null);

    }

    @Cacheable(value = "features", key = "#id")
    @GetMapping("{id}")
    public ResponseModel<FeaturesResource> getOneFeature(@PathVariable String id) {
        Features features = featuresService.getById(id)
                .orElseThrow(() -> new RuntimeException());
        FeaturesResource featuresResource = featuresMapper.featureToResource(features);
        return new ResponseModel(HttpStatus.OK.value(), featuresResource, null);
    }

    @GetMapping("")
    public ResponseModel<List<FeaturesResource>> list() {
        List<FeaturesResource> featuresResources = featuresMapper.featuresToResourceList(
                featuresService.getAllFeatures());
        return new ResponseModel(HttpStatus.OK.value(), featuresResources, null);
    }

    @GetMapping("/page")
    public ResponseModel<List<FeaturesResource>> listByPage(@RequestParam int pageNumber, @RequestParam int pageSize) {

        Page<Features> features = featuresService.listByPage(pageNumber, pageSize);

        List<FeaturesResource> featuresResources = featuresMapper.featuresToResourceList(
                features.toList());

        return new ResponseModel<>(HttpStatus.OK.value(), featuresResources, null,
                (int) features.getTotalElements(), features.getTotalPages());
    }


}
