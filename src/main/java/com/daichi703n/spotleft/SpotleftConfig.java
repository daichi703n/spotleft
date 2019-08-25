package com.daichi703n.spotleft;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpotleftConfig {

	@Bean
	public AmazonS3 amazonS3() {
		return AmazonS3ClientBuilder.defaultClient();
	}

}
