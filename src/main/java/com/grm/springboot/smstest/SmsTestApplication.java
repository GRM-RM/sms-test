package com.grm.springboot.smstest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author grm
 */
@SpringBootApplication
@EnableCaching
public class SmsTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmsTestApplication.class, args);
	}
}
