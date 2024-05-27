package com.threepounds.caseproject.data.repository;

import com.threepounds.caseproject.data.entity.adress.City;
import com.threepounds.caseproject.data.entity.adress.County;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CountyRepository extends JpaRepository<County,String> {

    List<County> findById(County county);

}
