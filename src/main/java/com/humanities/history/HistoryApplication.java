package com.humanities.history;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HistoryApplication {
	//
	public static void main(String[] args) {
		//
		System.out.println("HELLO from HistoryApplication"); //  \u001B[31m
		SpringApplication.run(HistoryApplication.class, args);
	}
}
