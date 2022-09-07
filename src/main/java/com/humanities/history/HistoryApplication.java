package com.humanities.history;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition( info = @Info( title = "History API", version = "1.0314",
	description = "Some very general Information" ) )
public class HistoryApplication {
	//
	public static void main(String[] args) {
		//
		System.out.println("HELLO from HistoryApplication"); //  \u001B[31m
		SpringApplication.run(HistoryApplication.class, args);
	}
}
