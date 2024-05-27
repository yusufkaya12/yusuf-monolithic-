package com.threepounds.caseproject;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableRabbit
@SpringBootApplication
@EnableCaching
@EnableElasticsearchRepositories
public class CaseProjectApplication {

  public static void main(String[] args) {
    SpringApplication.run(CaseProjectApplication.class, args);

  }

}
