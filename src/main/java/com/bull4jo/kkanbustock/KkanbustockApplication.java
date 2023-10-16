package com.bull4jo.kkanbustock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class KkanbustockApplication {

	public static void main(String[] args) {
		SpringApplication.run(KkanbustockApplication.class, args);
	}

}
