package com.threepounds.caseproject.service;

import com.threepounds.caseproject.data.entity.Tag;
import com.threepounds.caseproject.data.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdvertTagService {

    private final TagRepository tagRepository;

    public AdvertTagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag save(Tag tag){
        return tagRepository.save(tag);
    }

    public List<Tag> getAllTags(){
        return tagRepository.findAll();
    }


}