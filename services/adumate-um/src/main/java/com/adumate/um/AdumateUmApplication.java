package com.adumate.um;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("com.adumate.um")
public class AdumateUmApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdumateUmApplication.class, args);
	}

}
