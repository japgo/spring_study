package com.team10.backoffice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class BackOfficeApplication {

//	static {
//		System.setProperty( "com.amazonaws.sdk.disableEc2Metadata", "true" );
//	}
	public static void main( String[] args ) {
		SpringApplication.run( BackOfficeApplication.class, args );
	}

}
