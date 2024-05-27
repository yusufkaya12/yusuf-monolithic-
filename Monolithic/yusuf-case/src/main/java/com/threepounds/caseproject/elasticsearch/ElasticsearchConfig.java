package com.threepounds.caseproject.elasticsearch;
import com.threepounds.caseproject.data.entity.ESTag;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.Arrays;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.threepounds.caseproject.data.repository")
@ComponentScan(basePackages = "com.threepounds.caseproject")
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    @Override
    public ClientConfiguration clientConfiguration() {
        return  ClientConfiguration.builder().connectedTo("localhost:9200").build();
    }

}
