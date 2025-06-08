package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan("com.example.demo.filter")
public class ExamGateApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamGateApplication.class, args);
	}

}
