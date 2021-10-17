package com.works;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.works.repositories.elastic")
public class CompanyAllProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompanyAllProjectApplication.class, args);
    }

}
