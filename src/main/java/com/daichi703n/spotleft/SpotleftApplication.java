package com.daichi703n.spotleft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpotleftApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotleftApplication.class, args);
	}

}
