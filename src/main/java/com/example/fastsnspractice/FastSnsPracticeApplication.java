package com.example.fastsnspractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class FastSnsPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FastSnsPracticeApplication.class, args);
	}

}
