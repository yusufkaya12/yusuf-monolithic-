package com.threepounds.caseproject.controller;


import com.threepounds.caseproject.controller.dto.CategoryDto;
import com.threepounds.caseproject.controller.mapper.CategoryMapper;
import com.threepounds.caseproject.controller.resource.CategoryResource;
import com.threepounds.caseproject.controller.response.ResponseModel;
import com.threepounds.caseproject.data.entity.Category;
import com.threepounds.caseproject.exceptions.NotFoundException;
import com.threepounds.caseproject.service.CategoryService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/categories")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }


    @GetMapping("")
    public ResponseModel<List<CategoryResource>> list() {

        List<CategoryResource> categoryResources = categoryMapper.categoryDtoList(
                categoryService.list());
        return new ResponseModel<>(HttpStatus.OK.value(), categoryResources, null);
    }

    @GetMapping("/page")
    public ResponseModel<List<CategoryResource>> listByPage(@RequestParam int pageNumber, @RequestParam int pageSize) {

        Page<Category> categories = categoryService.listByPage(pageNumber, pageSize);

        List<CategoryResource> categoryResources = categoryMapper.categoryDtoList(
                categories.toList());

        return new ResponseModel<>(HttpStatus.OK.value(), categoryResources, null,
                (int) categories.getTotalElements(), categories.getTotalPages());
    }

    @PostMapping("")
    public ResponseModel<CategoryResource> create(@RequestBody CategoryDto categoryDto) {
        Category categoryToSave = categoryMapper.dtoToEntity(categoryDto);
        Category savedCategory = categoryService.save(categoryToSave);
        CategoryResource categoryResource = categoryMapper.categoryDto(savedCategory);
        return new ResponseModel<>(HttpStatus.OK.value(), categoryResource, null);
    }

    // TODO PutMapping mevcut kategoriyi güncellemeli
    // categorinin id si kontrol edilir databasede var mı
    // mapperdan dto entitye çevrilmeli
    // save edilir.
    @PutMapping("{id}")
    @CachePut(value = "category", key = "#id")
    public ResponseModel<CategoryResource> update(@PathVariable String id, @RequestBody CategoryDto dto) {
        Category existingCategory = categoryService.getById(id)
                .orElseThrow(() -> new NotFoundException("Category not found for update"));
        Category mappedCategory = categoryMapper.dtoToEntity(dto);
        mappedCategory.setId(existingCategory.getId());
        Category updatedCategory = categoryService.save(mappedCategory);
        CategoryResource categoryResource = categoryMapper.categoryDto(updatedCategory);
        return new ResponseModel<>(HttpStatus.OK.value(), categoryResource, null);
    }

    @DeleteMapping("{id}")
    @CacheEvict(value = "category", key = "#id")
    public ResponseModel<String> delete(@PathVariable String id) {
        categoryService.delete(id);
        return new ResponseModel<>(HttpStatus.OK.value(), "success", null);
    }

    @GetMapping("{id}")
    @Cacheable(value = "category", key = "#id")
    public ResponseModel<CategoryResource> getOneCategory(@PathVariable String id) {
        Category category = categoryService.getById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        CategoryResource categoryResource = categoryMapper.categoryDto(category);

        return new ResponseModel(HttpStatus.OK.value(), categoryResource, null);
    }



}

