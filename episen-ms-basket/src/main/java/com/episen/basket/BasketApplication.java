package com.episen.basket;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Date;

@SpringBootApplication
@EnableConfigurationProperties
public class BasketApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(BasketApplication.class, args);
		System.out.println("Start basket ms project");
	}
}
