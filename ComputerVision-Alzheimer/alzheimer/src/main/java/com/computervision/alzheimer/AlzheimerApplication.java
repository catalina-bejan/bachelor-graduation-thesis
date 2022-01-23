package com.computervision.alzheimer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class AlzheimerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlzheimerApplication.class, args);
	}

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
	}

}
