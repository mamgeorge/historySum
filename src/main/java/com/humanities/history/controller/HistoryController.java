package com.humanities.history.controller;

import com.humanities.history.configuration.GeneralConfiguration;
import com.humanities.history.services.History;
import com.humanities.history.services.IHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

@RestController
public class HistoryController {

	@Autowired private IHistoryService historyService;
	@Autowired private GeneralConfiguration genConfig;

	private static final Logger LOGGER = Logger.getLogger(HistoryController.class.getName());
	private static final String EOL = "\n";
	private static final int MAX_DISPLAY = 20;
	private static final int SAMPLE_ITEM = 1;

	@GetMapping( { "/", "/index", "/home" } ) public ModelAndView home( ) {
		//
		LOGGER.info("home()");
		return new ModelAndView("home", new HashMap<>());
	}

	@GetMapping( "/listing" ) public ModelAndView showListing( ) {

		LOGGER.info("showListing()");
		List<History> histories = historyService.findAll();
		System.out.println("histories: " + histories.size());
		if ( histories.size() > MAX_DISPLAY ) { histories = histories.subList(0, MAX_DISPLAY); }
		//
		HashMap<String, List<History>> hashMap = new HashMap<>();
		hashMap.put("histories", histories);
		histories.forEach(hst -> System.out.println(hst.showHistory()));
		//
		ModelAndView MAV = new ModelAndView("listing", hashMap);
		return MAV;
	}

	@GetMapping( "/showInputs" ) public ModelAndView showInputs( ) {

		LOGGER.info("showInputs()");
		History history = new History();
		ModelAndView MAV = getHistoryMAV(history);
		return MAV;
	}

	@GetMapping( "/showInputs/{id}" ) public ModelAndView showInputs(@PathVariable String id) {

		LOGGER.info("showInputs(" + id + ")");
		long longId;
		try { longId = Long.parseLong(id); }
		catch (Exception ex) {
			LOGGER.info(ex.getMessage());
			longId = SAMPLE_ITEM;
		}
		if ( longId < 1 ) { longId = 0L; }
		//
		History history = new History();
		try { history = historyService.findById(longId); }
		catch (NoSuchElementException ex) {
			LOGGER.severe(ex.getMessage());
		}
		ModelAndView MAV = getHistoryMAV(history);
		return MAV;
	}

	@PostMapping( "/posted" ) // ModelAttribute or RequestBody?
	public ModelAndView posted(@ModelAttribute History history, @RequestParam( "nav" ) String nav) {

		LOGGER.info("posted(history, nav)");
		ModelAndView MAV = new ModelAndView();
		if ( history == null || history.getId() == null ) {
			history = new History();
			String msg = "WARNING! HISTORY OBJECT WAS EMPTY!";
			System.out.println(
				EOL + "#".repeat(msg.length()) + EOL + msg + EOL + "#".repeat(msg.length()) + EOL);
		}
		Long longId = history.getId();
		if ( nav != null && nav.equals("back") ) {
			--longId;
			MAV = traverse(history, longId);
		}
		if ( nav != null && nav.equals("frwd") ) {
			++longId;
			MAV = traverse(history, longId);
		}
		if ( nav != null && nav.equals("list") ) { MAV = showListing(); }
		if ( nav != null && nav.equals("clear") ) { MAV = showInputs(); }
		if ( nav != null && nav.equals("save") ) { MAV = saver(history); }
		if ( nav != null && nav.equals("delete") ) { MAV = deleter(history); }
		//
		return MAV;
	}

	@PostMapping( "/traverse" ) public ModelAndView traverse(@ModelAttribute History history, Long longId) {

		LOGGER.info("traverse(history, longId)");
		if ( longId == null || longId < 1 ) { } else {
			try { history = historyService.findById(longId); }
			catch (NoSuchElementException ex) {
				LOGGER.info(ex.getMessage());
			}
		}
		System.out.println("history: " + history.showHistory());
		//
		ModelAndView MAV = new ModelAndView();
		MAV.setViewName("inputs");
		MAV.addObject("history", history);
		MAV.addObject("historySum", history.showHistory());
		return MAV;
	}

	@PostMapping( "/saver" ) public ModelAndView saver(@ModelAttribute History history) {
		//
		// 	@PostMapping(path = "/posted",
		// 	consumes = {APPLICATION_FORM_URLENCODED_VALUE}, produces = { APPLICATION_FORM_URLENCODED_VALUE})
		LOGGER.info("saver(history)");
		Long longId = history.getId();
		if ( longId == null || longId <= 1 ) { history = new History(); } else {
			try {
			//	history = historyService.save(history);
			}
			catch (NoSuchElementException ex) {
				LOGGER.info(ex.getMessage());
			}
		}
		System.out.println("history: " + history.showHistory());
		//
		ModelAndView MAV = new ModelAndView();
		MAV.setViewName("inputs");
		MAV.addObject("history", history);
		MAV.addObject("historySum", history.showHistory());
		return MAV;
	}

	@PostMapping( "/delete" ) public ModelAndView deleter(@ModelAttribute History history) {
		//
		LOGGER.info("deleter(history)");
		System.out.println("deleting: " + history.showHistory());
		try {
			//	historyService.delete(history);
		}
		catch (Exception ex) { LOGGER.severe(ex.getMessage()); }
		ModelAndView MAV = showInputs();
		return MAV;
	}

	//#### extras
	@GetMapping( "/appendix" ) public ModelAndView appendix( ) {

		LOGGER.info("appendix()");
		return new ModelAndView("appendix", new HashMap<>());
	}

	@GetMapping( "/errors" ) public void errors( ) {
		// throw new Exception("Exception!");
		LOGGER.info("errors()");
		throw new RuntimeException("RuntimeException!");
	}

	//#### STATICS ####
	private ModelAndView getHistoryMAV(History history) {

		ModelAndView MAV = new ModelAndView();
		MAV.setViewName("inputs");
		MAV.addObject("history", history);
		MAV.addObject("historySum", history.showHistory());

		MAV.addObject("genConfig",genConfig);
		MAV.addObject("eramain", genConfig.getEramain());
		MAV.addObject("locations", genConfig.getLocales());
		MAV.addObject("groupings", genConfig.getGrouplist());

		System.out.println("history: " + history.showHistory());
		return MAV;
	}
}
