package com.coredb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import com.coredb.property.FileStorageProperties;

@SpringBootApplication
public class RestdbApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestdbApplication.class, args);
	}

}
