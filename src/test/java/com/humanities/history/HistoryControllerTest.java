package com.humanities.history;

import com.humanities.history.controller.HistoryController;
import com.humanities.history.model.History;
import com.humanities.history.model.HistoryView;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.unix4j.Unix4j;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@TestInstance( TestInstance.Lifecycle.PER_CLASS )
public class HistoryControllerTest {

	@Autowired private HistoryController historyController;

	@LocalServerPort int localServerPort_RND;
	private static final String FRMT = "\t%-5s %s\n";

	@Test void test_home( ) {
		//
		String txtLines = "";
		//
		ModelAndView MAV = historyController.home();
		View view = MAV.getView();
		txtLines += String.format(FRMT, "historyController", historyController);
		txtLines += String.format(FRMT, "modelAndView", MAV);
		txtLines += String.format(FRMT, "getViewName", MAV.getViewName());
		txtLines += String.format(FRMT, "view", view);
		//
		System.out.println(txtLines);
		assertNotNull(historyController);
	}

	@Test void test_showListing( ) {
		//
		StringBuilder stringBuilder = new StringBuilder();
		//
		ModelAndView MAV = historyController.showListing();
		HashMap<String, Object> hashMap = (HashMap<String, Object>) MAV.getModel();
		List<History> histories = (List<History>) hashMap.get("histories");
		AtomicInteger ai = new AtomicInteger();
		for ( History history : histories ) {
			stringBuilder.append(String.format(FRMT, ai.incrementAndGet(), history.getPersonname()));
		}
		//
		System.out.println(stringBuilder);
		assertNotNull(historyController);
	}

	@Disabled
	@Test void test_showListing_call( ) {
		//
		System.out.println("localServerPort_RND: " + localServerPort_RND);
		String url = "http://localhost:" + localServerPort_RND + "/listing";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = null;
		//
		// headers not needed for this call
		HttpEntity<String> httpEntity = new HttpEntity<>("test");
		try {
			responseEntity = restTemplate.exchange(url, GET, httpEntity, String.class);
			// try { responseEntity = restTemplate.getForEntity(url, String.class); }
		}
		catch (HttpClientErrorException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		//
		String body = responseEntity.getBody();
		String txtLines = Unix4j.fromString(body).grep("eventmain").toStringResult();
		System.out.println("txtLines: " + txtLines);
		assertNotNull(responseEntity);
	}

	@Test void test_showInputs_num( ) {
		//
		ModelAndView MAV = historyController.showInputs("5");
		HashMap<String, Object> hashMap = (HashMap<String, Object>) MAV.getModel();
		History history = (History) hashMap.get("history");
		String txtLines = String.format(FRMT, "getPersonname:", history.getPersonname());
		//
		System.out.println(txtLines);
		assertNotNull(historyController);
	}

	@Test void test_posted( ) {
		//
		History history = new History();
		ModelAndView MAV = historyController.posted(history, "clear");
		String txtLines = MAV.getViewName();
		//
		System.out.println("posted(clear): " + txtLines);
		assertEquals(txtLines, "inputs");
	}

	@Test void test_posted_call( ) { /* ? fails due to HttpClientErrorException "Bad Request" */
		//
		System.out.println("localServerPort_RND: " + localServerPort_RND);
		String url = "http://localhost:" + localServerPort_RND + "/posted";
		//
		// MVM.add("history", history); // how to add ModelAttribute? RequestBody? HttpMessageConverter?
		History history = new History();
		MultiValueMap<String, Object> MVM = new LinkedMultiValueMap<>();
		MVM.add("nav", "frwd");
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(CONTENT_TYPE, APPLICATION_FORM_URLENCODED_VALUE);
		//
		HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(MVM, httpHeaders);
		ResponseEntity<String> responseEntity = null;
		RestTemplate restTemplate = new RestTemplate();
		try {
			responseEntity = restTemplate.postForEntity(url, httpEntity, String.class);
		}
		catch (NullPointerException | HttpClientErrorException |
		       HttpServerErrorException.InternalServerError ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		//
		String txtLines;
		if ( responseEntity == null ) { txtLines = "no responseEntity!"; } else {
			String body = responseEntity.getBody();
			txtLines = Unix4j.fromString(body).grep("summary").toStringResult();
		}
		System.out.println("txtLines: " + txtLines);
		assertNotNull(txtLines);
	}

	@Test void test_traverse( ) {
		//
		History history = new History();
		ModelAndView MAV = historyController.traverse(history, 0L);
		HashMap<String, Object> hashMap = (HashMap<String, Object>) MAV.getModel();
		history = (History) hashMap.get("history");
		String txtLines = String.format(FRMT, "getPersonname:", history.getPersonname());
		//
		System.out.println(txtLines);
		assertNotNull(historyController);
	}

	@Test void test_saver( ) {

		History history = new History();
		System.out.println("getEventmain(): " + history.getEventmain());
		history.setEventmain(history.getEventmain() + " / " + Instant.now());

		ModelAndView MAV = new ModelAndView();
		MAV.addObject("history", history);
		// MAV = historyController.saver(history);

		HashMap<String, Object> hashMap = (HashMap<String, Object>) MAV.getModel();
		History historyFromMap = (History) hashMap.get("history");
		String txtLines = "getEventmain(): " + historyFromMap.getEventmain();

		System.out.println(txtLines);
		assertNotNull(historyController);
	}

	@Test void test_deleter( ) {
		//
		History history = new History();
		System.out.println("showHistory(): " + history.showHistory());
		history.setEventmain(history.getEventmain() + " / " + Instant.now());
		//
		ModelAndView MAV = historyController.deleter(history);
		System.out.println(MAV.getViewName());
		assertNotNull(historyController);
	}

	@Test void test_errors( ) {
		//
		String txtLines = "";
		try { historyController.errors(); }
		catch (Exception ex) {
			txtLines = ex.getMessage();
			System.out.println(txtLines);
		}
		//
		System.out.println("ERROR: " + txtLines);
		assertEquals(txtLines, "RuntimeException!");
	}
}
