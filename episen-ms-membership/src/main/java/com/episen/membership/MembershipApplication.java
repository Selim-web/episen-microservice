package com.episen.membership;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class MembershipApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(MembershipApplication.class, args);
		System.out.println("Start membership ms project");
		// sout = > system.out.println
	
	}
}
