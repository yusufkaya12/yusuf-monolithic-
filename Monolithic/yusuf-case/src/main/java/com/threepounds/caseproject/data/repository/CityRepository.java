package com.threepounds.caseproject.data.repository;

import com.threepounds.caseproject.data.entity.adress.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CityRepository extends JpaRepository<City,String> {

    List<City> findById(City city);

}
