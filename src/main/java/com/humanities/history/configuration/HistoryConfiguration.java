package com.humanities.history.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.servlet.ServletContext;

/*
	This attempt to enforce a StandardCharsets in the ThymeleafViewResolver did not work.
	I believe it  is more efficiently handled in apps.yml: spring.thymeleaf.encoding: UTF-8
	https://stackoverflow.com/questions/39262370/spring-boot-how-to-correctly-define-template-locations
 */

@Configuration
public class HistoryConfiguration {

	@Autowired private Environment environment;
	@Autowired private ServletContext servletContext;

	@Value( "${core.source}" ) private String core_source;
}
