package com.humanities.history.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;

import static java.nio.charset.StandardCharsets.UTF_8;

/*
	This attempt to enforce a StandardCharsets in the ThymeleafViewResolver did not work.
	I believe it  is more efficiently handled in apps.yml: spring.thymeleaf.encoding: UTF-8
	https://stackoverflow.com/questions/39262370/spring-boot-how-to-correctly-define-template-locations
 */

// @Configuration
public class HistoryConfiguration {

	@Autowired private Environment environment;
	@Autowired private ServletContext servletContext;

	@Value("${core.source}") private String core_source;

	//@Bean
	public ServletContextTemplateResolver servletContextTemplateResolver( ) {
		//
		ServletContextTemplateResolver sctResolver = new ServletContextTemplateResolver(servletContext);
		sctResolver.setPrefix("/templates/");
		sctResolver.setSuffix(".html");
		sctResolver.setTemplateMode("HTML5");
		sctResolver.setCharacterEncoding(UTF_8.displayName());
		sctResolver.setOrder(1);
		return sctResolver;
	}

	//@Bean
	public SpringTemplateEngine springTemplateEngine( ) {
		//
		SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
		springTemplateEngine.setTemplateResolver(servletContextTemplateResolver());
		return springTemplateEngine;
	}

	//@Bean
	public ThymeleafViewResolver thymeleafViewResolver( ) {
		//
		ThymeleafViewResolver tlvResolver = new ThymeleafViewResolver();
		tlvResolver.setTemplateEngine(springTemplateEngine());
		tlvResolver.setCharacterEncoding(UTF_8.displayName());
		return tlvResolver;
	}
}
