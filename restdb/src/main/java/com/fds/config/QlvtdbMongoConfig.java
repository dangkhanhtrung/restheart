package com.fds.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import static com.fds.config.QlvtdbMongoConfig.MONGO_TEMPLATE;

@Configuration
@EnableMongoRepositories(basePackages = "com.fds.repository.qlvtdb",
        mongoTemplateRef = MONGO_TEMPLATE)
public class QlvtdbMongoConfig {
	protected static final String MONGO_TEMPLATE = "qlvtdbMongoTemplate";
}