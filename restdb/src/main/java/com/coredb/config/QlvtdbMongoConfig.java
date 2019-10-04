package com.coredb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.coredb.qlvt.repository",
        mongoTemplateRef = "qlvtdbMongoTemplate")
public class QlvtdbMongoConfig {

}