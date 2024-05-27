package com.threepounds.caseproject.service;


import com.threepounds.caseproject.data.entity.Advert;
import com.threepounds.caseproject.data.entity.Favourites;
import com.threepounds.caseproject.data.entity.User;
import com.threepounds.caseproject.data.repository.FavouritesRepository;
import com.threepounds.caseproject.data.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FavouriteService {


    private final FavouritesRepository favouritesRepository;
    private final UserRepository userRepository;

    public FavouriteService(FavouritesRepository favouritesRepository, UserRepository userRepository) {
        this.favouritesRepository = favouritesRepository;
        this.userRepository = userRepository;
    }
    public Favourites save(Favourites favourites){

        return favouritesRepository.save(favourites);
    }
    public void delete(String id){
         favouritesRepository.deleteById(id);

    }
    public Optional<Favourites> getById(String id){
        return favouritesRepository.findById(id);
    
    }
    public List<Favourites> getAllFavourites(){
        return favouritesRepository.findAll();
    }

    public Page<Favourites> listByPage(int pageNumber, int pageSize)
    {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return favouritesRepository.findAll(pageable);
    }

    public Optional<User> getUserId(Principal principal){
        Optional<User> user =userRepository.findByEmail(principal.getName());
        return user;
    }

}
