package com.fds;

import java.text.ParseException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.fds.property.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class RestdbApplication {

	public static void main(String[] args) throws ParseException {
		SpringApplication.run(RestdbApplication.class, args);
	}

}
