package com.threepounds.caseproject.data.repository;

import com.threepounds.caseproject.data.entity.Advert;
import com.threepounds.caseproject.data.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AdvertRepository extends JpaRepository<Advert,String> {


    List<Advert> findByCategory(Category category);

}
