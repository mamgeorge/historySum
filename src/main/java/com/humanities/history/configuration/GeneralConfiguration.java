package com.humanities.history.configuration;

import com.humanities.history.model.HistoryView;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

/*
	This attempt to enforce a StandardCharsets in the ThymeleafViewResolver did not work.
	I believe it is more efficiently handled in apps.yml: spring.thymeleaf.encoding: UTF-8
	https://stackoverflow.com/questions/39262370/spring-boot-how-to-correctly-define-template-locations
 */

@Configuration
@Getter @Setter
public class GeneralConfiguration {

	private static final Logger LOGGER = Logger.getLogger(GeneralConfiguration.class.getName());

	@Autowired private Environment environment;
	@Autowired private ServletContext servletContext;
	@Value( "${core.source}" ) private String coreSource;

	// https://stackoverflow.com/questions/44399422/read-file-from-resources-folder-in-spring-boot
	// https://www.baeldung.com/thymeleaf-select-option
	private static final String RESOURCE_PATH = "src/main/resources/static/";
	private static final String FILENAME_ERA = RESOURCE_PATH + "inp_eramain.txt";
	private static final String FILENAME_LOC = RESOURCE_PATH + "inp_locales.txt";
	private static final String GROUP_TEXT = "none politics battles leaders language arts technology science "
		+ "natural global apologetic";

	private static final String EOL = "\n";
	private HistoryView historyView;

	public GeneralConfiguration( ) {

		LOGGER.info("#### init ####: ");
		historyView = new HistoryView();
		try {
			String txtLinesEra = new String(Files.readAllBytes(Paths.get(FILENAME_ERA)));
			String txtLinesLoc = new String(Files.readAllBytes(Paths.get(FILENAME_LOC)));

			historyView.setEralist(parseOptions(txtLinesEra));
			historyView.setLocalelist(parseOptions(txtLinesLoc));
			historyView.setTaglist(Arrays.asList(GROUP_TEXT.split(" ")));
		}
		catch (IOException ex) { LOGGER.info("ERROR: " + ex.getMessage()); }
	}

	public static Map<String, String> parseOptions(String txt) {

		Map<String, String> map = new LinkedHashMap<>();
		String[] txtArray = txt.split(EOL);
		Arrays.stream(txtArray).forEach(pair -> {
				int mdl = pair.indexOf("|");
				map.put(pair.substring(0, mdl).trim(), pair.substring(mdl + 1).trim());
			}
		);
		return map;
	}

	@Bean HistoryView getHistoryView() { return historyView; }
}
