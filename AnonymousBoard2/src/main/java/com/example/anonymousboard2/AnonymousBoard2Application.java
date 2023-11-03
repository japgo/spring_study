package com.example.anonymousboard2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class AnonymousBoard2Application {

	public static void main( String[] args ) {
		SpringApplication.run( AnonymousBoard2Application.class, args );
	}

}
