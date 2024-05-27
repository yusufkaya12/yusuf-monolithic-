package com.threepounds.caseproject.data.repository;


import com.threepounds.caseproject.data.entity.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PriceRepository extends JpaRepository<PriceHistory, String> {


}
