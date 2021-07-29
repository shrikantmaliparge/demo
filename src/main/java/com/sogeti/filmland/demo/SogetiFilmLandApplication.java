package com.sogeti.filmland.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableScheduling
@EnableSwagger2
@ComponentScan(basePackages = "com.sogeti.*")
@SpringBootApplication
public class SogetiFilmLandApplication {

	public static void main(String[] args) {
		SpringApplication.run(SogetiFilmLandApplication.class, args);
	}

}
