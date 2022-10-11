package com.humanities.history.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/*
	This attempt to enforce a StandardCharsets in the ThymeleafViewResolver did not work.
	I believe it is more efficiently handled in apps.yml: spring.thymeleaf.encoding: UTF-8
	https://stackoverflow.com/questions/39262370/spring-boot-how-to-correctly-define-template-locations
 */

@Configuration
public class GeneralConfiguration {

	@Autowired private Environment environment;
	@Autowired private ServletContext servletContext;

	@Value( "${core.source}" ) private String core_source;

	// https://stackoverflow.com/questions/44399422/read-file-from-resources-folder-in-spring-boot
	// https://www.baeldung.com/thymeleaf-select-option
	private static final  String resourcePath = "src/main/resources/static/" ;
	private static final  String fileNameEra = resourcePath + "inp_eramain.txt";
	private static final  String fileNameLoc = resourcePath + "inp_locales.txt";
	private static final  String EOL = "\n";
	public List<String> eramain = null;
	public List<String> locales = null;

	public GeneralConfiguration() {

		System.out.println("#### init ####: ");
		try {
			String txtLinesEra = new String(Files.readAllBytes(Paths.get(fileNameEra)));
			eramain = Arrays.asList(txtLinesEra.split(EOL));

			String txtLinesLoc = new String(Files.readAllBytes(Paths.get(fileNameLoc)));
			locales = Arrays.asList(txtLinesLoc.split(EOL));
		}
		catch (IOException ex) { System.out.println("ERROR: " + ex.getMessage()); }
	}
}
