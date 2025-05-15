package com.namhkn.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class UserserviceApplication {

	public static final Logger logger = Logger.getLogger("APPLICATION");

	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}

}
