package com.rest.client.surveillance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class SurveillanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurveillanceApplication.class, args);
	}

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
	}

}
