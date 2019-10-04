package com.coredb.config;

import com.mongodb.MongoClient;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
public class MultipleMongoConfig {
    @Primary
    @Bean(name = "coredb")
    @ConfigurationProperties(prefix = "spring.data.mongodb")
    public MongoProperties getCoredb() {
        return new MongoProperties();
    }
    
    @Bean(name = "qlvtdb")
    @ConfigurationProperties(prefix = "mongodb.qlvt")
    public MongoProperties getQlvtdb() {
        return new MongoProperties();
    }
    
    @Primary
    @Bean(name = "coredbMongoTemplate")
    public MongoTemplate coredbMongoTemplate() throws Exception {
        return new MongoTemplate(coredbFactory(getCoredb()));
    }
    
    @Bean(name = "qlvtdbMongoTemplate")
    public MongoTemplate qlvtdbMongoTemplate() throws Exception {
        return new MongoTemplate(qlvtdbFactory(getQlvtdb()));
    }
    
    @Bean
    @Primary
    public MongoDbFactory coredbFactory(final MongoProperties mongo) throws Exception {
        return new SimpleMongoDbFactory(new MongoClient(mongo.getHost(), mongo.getPort()),
                mongo.getDatabase());
    }
    
    @Bean
    public MongoDbFactory qlvtdbFactory(final MongoProperties mongo) throws Exception {
        return new SimpleMongoDbFactory(new MongoClient(mongo.getHost(), mongo.getPort()),
                mongo.getDatabase());
    }
}