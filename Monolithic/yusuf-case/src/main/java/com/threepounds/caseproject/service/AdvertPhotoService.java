package com.threepounds.caseproject.service;

import com.threepounds.caseproject.data.entity.Photo;
import com.threepounds.caseproject.data.repository.PhotoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AdvertPhotoService {

    private final PhotoRepository photoRepository;

    public AdvertPhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public Photo save(Photo photo){
        return photoRepository.save(photo);
    }

    public List<Photo> getAllPhotos(){
        return photoRepository.findAll();
    }


}