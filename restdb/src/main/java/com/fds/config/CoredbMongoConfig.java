package com.fds.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import static com.fds.config.CoredbMongoConfig.MONGO_TEMPLATE;

@Configuration
@EnableMongoRepositories(basePackages = "com.fds.repository.coredb",
        mongoTemplateRef = MONGO_TEMPLATE)
public class CoredbMongoConfig {
	protected static final String MONGO_TEMPLATE = "coredbMongoTemplate";
}