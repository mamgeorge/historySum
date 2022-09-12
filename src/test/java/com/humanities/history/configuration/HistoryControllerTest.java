package com.humanities.history.configuration;

import com.humanities.history.controller.HistoryController;
import com.humanities.history.services.History;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
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
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@TestInstance( TestInstance.Lifecycle.PER_CLASS )
public class HistoryControllerTest {

	@Autowired private HistoryController historyController;

	@LocalServerPort int localServerPort_RND;
	private static final String FRMT = "\t%-5s %s\n";

	@BeforeAll void setup( ) { }

	@Test void test_root( ) {
		//
		String txtLines = "";
		//
		ModelAndView MAV = historyController.root();
		View view = MAV.getView();
		txtLines += String.format(FRMT, "historyController", historyController);
		txtLines += String.format(FRMT, "modelAndView", MAV);
		txtLines += String.format(FRMT, "getViewName", MAV.getViewName());
		txtLines += String.format(FRMT, "view", view);
		//
		System.out.println(txtLines);
		assertNotNull(historyController);
	}

	@Test void test_listing( ) {
		//
		String txtLines = "";
		//
		ModelAndView MAV = historyController.listing();
		HashMap<String, Object> hashMap = (HashMap<String, Object>) MAV.getModel();
		List<History> histories = (List<History>) hashMap.get("histories");
		AtomicInteger ai = new AtomicInteger();
		for ( History history : histories ) {
			txtLines += String.format(FRMT, ai.incrementAndGet(), history.getPersonname());
		}
		//
		System.out.println(txtLines);
		assertNotNull(historyController);
	}

	@Test void test_listing_call( ) {
		//
		System.out.println("localServerPort_RND: " + localServerPort_RND);
		String url = "http://localhost:" + localServerPort_RND + "/listing";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = null;
		//
		// headers not needed for this call; included to show relationship to POST call
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(CONTENT_TYPE, TEXT_HTML_VALUE);
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

	@Test void test_inputs_num( ) {
		//
		ModelAndView MAV = historyController.inputs("5");
		HashMap<String, Object> hashMap = (HashMap<String, Object>) MAV.getModel();
		History history = (History) hashMap.get("history");
		String txtLines = String.format(FRMT, "getPersonname:", history.getPersonname());
		//
		System.out.println(txtLines);
		assertNotNull(historyController);
	}

	@Test void test_posted( ) {
		//
		History history = History.getSample();
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
		History history = History.getSample();
		MultiValueMap<String, Object> MVM = new LinkedMultiValueMap<>();
		MVM.add("nav", "frwd");
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(CONTENT_TYPE, APPLICATION_FORM_URLENCODED_VALUE);
		//
		// HttpEntity<History> httpEntity = new HttpEntity<>(history, httpHeaders);
		HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(MVM, httpHeaders);
		ResponseEntity<String> responseEntity = null;
		RestTemplate restTemplate = new RestTemplate();
		try {
			// responseEntity = restTemplate.exchange(url, POST, httpEntity, String.class);
			// responseEntity = restTemplate.postForObject(url, httpEntity, ResponseEntity.class);
			responseEntity = restTemplate.postForEntity(url, httpEntity, String.class);
		}
		catch (HttpClientErrorException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		//
		String body = responseEntity.getBody();
		String txtLines = Unix4j.fromString(body).grep("summary").toStringResult();
		System.out.println("txtLines: " + txtLines);
		assertNotNull(responseEntity);
	}

	@Test void test_traverse( ) {
		//
		History history = History.getSample();
		ModelAndView MAV = historyController.traverse(history, 0L);
		HashMap<String, Object> hashMap = (HashMap<String, Object>) MAV.getModel();
		history = (History) hashMap.get("history");
		String txtLines = String.format(FRMT, "getPersonname:", history.getPersonname());
		//
		System.out.println(txtLines);
		assertNotNull(historyController);
	}

	@Test void test_saver( ) {

		History historySample = History.getSample();
		System.out.println("getEventmain(): " + historySample.getEventmain());
		historySample.setEventmain(historySample.getEventmain() + " / " + Instant.now());

		ModelAndView MAV = new ModelAndView();
		MAV.addObject("history", historySample);
		// MAV = historyController.saver(history);

		HashMap<String, Object> hashMap = (HashMap<String, Object>) MAV.getModel();
		History history = (History) hashMap.get("history");
		String txtLines = "getEventmain(): " + history.getEventmain();

		System.out.println(txtLines);
		assertNotNull(historyController);
	}

	@Test void test_deleter( ) {
		//
		History history = History.getSample();
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
