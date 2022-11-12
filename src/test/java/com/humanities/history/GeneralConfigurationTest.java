package com.humanities.history;

import com.humanities.history.configuration.GeneralConfiguration;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class GeneralConfigurationTest {

	private static final String resourcePath = "src/main/resources/static/";
	private static final String fileNameEra = resourcePath + "inp_eramain.txt";
	private static final String fileNameLoc = resourcePath + "inp_locales.txt";
	private static final String EOL = "\n";

	@Test void parseOptions_era( ) {

		StringBuilder stringBuilder;

		stringBuilder = parseOptions(fileNameEra);
		System.out.println("fileNameEra: " + stringBuilder.toString().split(EOL).length);
		assertNotNull(stringBuilder);

		stringBuilder = parseOptions(fileNameLoc);
		System.out.println("fileNameLoc: " + stringBuilder.toString().split(EOL).length );
		assertNotNull(stringBuilder);
	}

	// #### STATICS
	private static StringBuilder parseOptions(String file) {

		StringBuilder stringBuilder = new StringBuilder();
		String txtLines = "";
		try { txtLines = new String(Files.readAllBytes(Paths.get(file))); }
		catch (IOException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		Map<String, String> map = GeneralConfiguration.parseOptions(txtLines);
		Set<Map.Entry<String, String>> set = map.entrySet();

		AtomicInteger ai = new AtomicInteger();
		String FRMT = "\t%02d %-8s | %s\n";
		set.forEach(pair -> stringBuilder.append(
			String.format(FRMT, ai.incrementAndGet(), pair.getKey(), pair.getValue())));

		return stringBuilder;
	}
}