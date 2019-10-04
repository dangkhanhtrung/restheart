package com.fds.config;

import java.util.Arrays;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import lombok.RequiredArgsConstructor;

/**
 * @author Marcos Barbero
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(MultipleMongoProperties.class)
public class MultipleMongoConfig {

    private final MultipleMongoProperties mongoProperties;

    @Primary
    @Bean(name = CoredbMongoConfig.MONGO_TEMPLATE)
    public MongoTemplate coredbMongoTemplate() throws Exception {
        return new MongoTemplate(coredbFactory(this.mongoProperties.getCoredb()));
    }
    
    @Bean(name = QlvtdbMongoConfig.MONGO_TEMPLATE)
    public MongoTemplate qlvtdbMongoTemplate() throws Exception {
        return new MongoTemplate(qlvtdbFactory(this.mongoProperties.getQlvtdb()));
    }

    @Bean
    @Primary
    public MongoDbFactory coredbFactory(final MongoProperties mongo) throws Exception {
    	MongoCredential credential = MongoCredential.createCredential(mongo.getUsername(), mongo.getDatabase(), mongo.getPassword());
        return new SimpleMongoDbFactory(new MongoClient(new ServerAddress(mongo.getHost(), mongo.getPort()), Arrays.asList(credential)), mongo.getDatabase());
    }
    @Bean
    public MongoDbFactory qlvtdbFactory(final MongoProperties mongo) throws Exception {
    	MongoCredential credential = MongoCredential.createCredential(mongo.getUsername(), mongo.getDatabase(), mongo.getPassword());
        return new SimpleMongoDbFactory(new MongoClient(new ServerAddress(mongo.getHost(), mongo.getPort()), Arrays.asList(credential)), mongo.getDatabase());
    }
}