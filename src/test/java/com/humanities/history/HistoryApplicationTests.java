package com.humanities.history;

import com.humanities.history.model.History;
import com.humanities.history.services.IHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpHeaders.USER_AGENT;

@SpringBootTest class HistoryApplicationTests {

	@Autowired private Environment environment;
	@Autowired private IHistoryService historyService;

	private static final String FRMT = "\t%-5s %s\n";
	private static final String HOST_EXT = "https://httpbin.org/";
	private static final String ENCODED = "历史 | &#21382;&#21490; | \u5386\u53f2 | \\u5386\\u53f2";

	@Test void test_contextLoads( ) {

		String txtLines = Instant.now().toString();
		System.out.println("#### " + txtLines);
		assertNotNull(txtLines);
	}

	@Test void test_Terminal( ) {
		/*
			A lot of unknowns. Typically, displaying any encoded charset results in "??" characters.
			The wildcard is: spring.jpa.properties.hibernate.hbm2ddl.charset_name: UTF-8.
			Clearly the chinese characters display IN THE LOGGING when this field is inactive.
			The Terminal is obviously able to handle the characters.
			However, using the charset_name property alters the encoding available to the terminal.
		*/
		String txtLines = ENCODED;
		System.out.println(txtLines);
		assertNotNull(ENCODED);
	}

	@Test void test_HttpCient( ) {
		//
		String txtLines = "";
		String url = HOST_EXT + "get?id=1234";
		//
		HttpResponse<String> httpResponse = sample_HttpCient(url);
		txtLines += String.format(FRMT, "statusCode", httpResponse.statusCode());
		txtLines += httpResponse.body().replaceAll("\\s+", " ");
		//
		System.out.println(txtLines);
		assertNotNull(httpResponse);
	}

	@Test void test_history_showHistory( ) {
		//
		History history = new History();
		String txtLines = String.format(FRMT, "history", history.showHistory());
		//
		System.out.println(txtLines);
		assertTrue(txtLines.contains("history"));
	}

	@Test void test_historyService_showHistory( ) {
		//
		History history = historyService.findById(2L);
		String txtLines = String.format(FRMT, "history", history.showHistory());
		//
		System.out.println(txtLines);
		assertTrue(history.getDatebeg().contains("00"));
	}

	@Test void test_environment( ) {
		//
		String keyValue = environment.getProperty("CORE_SOURCE");
		System.out.println("keyValue: " + keyValue);
		assertTrue(keyValue.contains("George"));
	}

	//#### helpers
	private static HttpResponse<String> sample_HttpCient(String url) {
		//
		HttpRequest httpRequest = HttpRequest.newBuilder()
			.GET()
			.uri(URI.create(url))
			.setHeader(USER_AGENT, "Java11Client Bot")
			.build();
		HttpClient httpClient = HttpClient.newBuilder()
			.version(HttpClient.Version.HTTP_2)
			.build();
		HttpResponse<String> httpResponse = null;
		try {
			HttpResponse.BodyHandler<String> bodyHandlers = HttpResponse.BodyHandlers.ofString();
			httpResponse = httpClient.send(httpRequest, bodyHandlers);
		}
		catch (IOException | InterruptedException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		//
		return httpResponse;
	}
}
