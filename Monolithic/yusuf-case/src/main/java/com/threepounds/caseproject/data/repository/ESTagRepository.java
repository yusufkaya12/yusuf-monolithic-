package com.threepounds.caseproject.data.repository;
import com.threepounds.caseproject.data.entity.ESTag;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ESTagRepository extends ElasticsearchRepository<ESTag,String> {
    List<ESTag> findByTag(String tag);
}
