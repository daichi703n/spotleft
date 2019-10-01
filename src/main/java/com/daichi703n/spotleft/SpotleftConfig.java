package com.daichi703n.spotleft;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpotleftConfig {

	@Bean
	public AmazonS3 amazonS3() {
		return AmazonS3ClientBuilder.defaultClient();
	}

}
