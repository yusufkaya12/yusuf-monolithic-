package com.threepounds.caseproject.service;


import com.threepounds.caseproject.data.entity.Advert;
import com.threepounds.caseproject.data.entity.Category;
import com.threepounds.caseproject.data.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
  private final AdvertService advertService;

  private final CategoryRepository repository;

  public CategoryService(AdvertService advertService, CategoryRepository repository) {
    this.advertService = advertService;
    this.repository = repository;
  }

  public Category save(Category category){
    return repository.save(category);
  }

  public Optional<Category> getById(String categoryId){
    return repository.findById(categoryId);
  }

  public List<Category> list(){
    return repository.findAll();
  }

  public void delete(String categoryId){
    repository.deleteById(categoryId);
  }

  public Page<Category> listByPage(int pageNumber, int pageSize)
  {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    return repository.findAll(pageable);
  }


}
