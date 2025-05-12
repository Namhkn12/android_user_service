package com.namhkn.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserserviceApplication {

	public static final String USERNAME_ALREADY_EXIST = "username_already_exist";

	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}

}
