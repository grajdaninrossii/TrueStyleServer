package com.truestyle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class TrueStyleApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrueStyleApplication.class, args);
	}
}
