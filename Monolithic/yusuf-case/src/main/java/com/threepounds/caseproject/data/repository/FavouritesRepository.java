package com.threepounds.caseproject.data.repository;

import com.threepounds.caseproject.data.entity.Favourites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FavouritesRepository extends JpaRepository<Favourites, String> {

}
