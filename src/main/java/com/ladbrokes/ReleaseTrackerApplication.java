package com.ladbrokes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReleaseTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReleaseTrackerApplication.class, args);
	}
}
