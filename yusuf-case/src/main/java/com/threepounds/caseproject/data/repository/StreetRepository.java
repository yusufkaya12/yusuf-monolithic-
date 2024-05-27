package com.threepounds.caseproject.data.repository;

import com.threepounds.caseproject.data.entity.adress.County;
import com.threepounds.caseproject.data.entity.adress.Street;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StreetRepository extends JpaRepository<Street,String> {

    List<Street> findById(Street street);

}
