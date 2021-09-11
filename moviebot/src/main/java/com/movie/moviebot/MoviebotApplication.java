package com.movie.moviebot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
@SpringBootApplication(scanBasePackages = { "com" })
@ServletComponentScan
public class MoviebotApplication {
	public static void main(String[] args) {
		SpringApplication.run(MoviebotApplication.class, args);
	}
}
