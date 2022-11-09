package com.humanities.history.controller;

import com.humanities.history.configuration.GeneralConfiguration;
import com.humanities.history.model.History;
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
		LOGGER.info("histories: " + histories.size());
		if ( histories.size() > MAX_DISPLAY ) { histories = histories.subList(0, MAX_DISPLAY); }
		//
		HashMap<String, List<History>> hashMap = new HashMap<>();
		hashMap.put("histories", histories);
		StringBuilder stringBuilder = new StringBuilder();
		histories.forEach(hst -> stringBuilder.append(hst.showHistory()+EOL));
		LOGGER.info(stringBuilder.toString());
		//
		return new ModelAndView("listing", hashMap);
	}

	@GetMapping( "/showInputs" ) public ModelAndView showInputs( ) {

		LOGGER.info("showInputs()");
		History history = new History();
		return  getHistoryMAV(history);
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
		return getHistoryMAV(history);
	}

	@PostMapping( "/posted" ) // ModelAttribute or RequestBody?
	public ModelAndView posted(@ModelAttribute History history, @RequestParam( "nav" ) String nav) {

		LOGGER.info("posted(history, nav)");
		ModelAndView modelAndView = new ModelAndView();
		if ( history == null || history.getId() == null ) {
			history = new History();
			String msg = "WARNING! HISTORY OBJECT WAS EMPTY!";
			LOGGER.info(EOL + "#".repeat(msg.length()) + EOL + msg + EOL + "#".repeat(msg.length()) + EOL);
		}
		Long longId = history.getId();
		if ( nav != null && nav.equals("back") ) {
			--longId;
			modelAndView = traverse(history, longId);
		}
		if ( nav != null && nav.equals("frwd") ) {
			++longId;
			modelAndView = traverse(history, longId);
		}
		if ( nav != null && nav.equals("list") ) { modelAndView = showListing(); }
		if ( nav != null && nav.equals("clear") ) { modelAndView = showInputs(); }
		if ( nav != null && nav.equals("save") ) { modelAndView = saver(history); }
		if ( nav != null && nav.equals("delete") ) { modelAndView = deleter(history); }
		//
		return modelAndView;
	}

	@PostMapping( "/traverse" ) public ModelAndView traverse(@ModelAttribute History history, Long longId) {

		LOGGER.info("traverse(history, longId)");
		if ( longId == null || longId < 1 ) { LOGGER.info(""); } else {
			try { history = historyService.findById(longId); }
			catch (NoSuchElementException ex) {
				LOGGER.info(ex.getMessage());
			}
		}
		LOGGER.info("history: " + history.showHistory());

		return getHistoryMAV(history);
	}

	@PostMapping( "/saver" ) public ModelAndView saver(@ModelAttribute History history) {
		//
		// 	@PostMapping(path = "/posted",
		// 	consumes = {APPLICATION_FORM_URLENCODED_VALUE}, produces = { APPLICATION_FORM_URLENCODED_VALUE})
		LOGGER.info("saver(history)");
		Long longId = history.getId();
		if ( longId == null || longId <= 1 ) { history = new History(); } else {
			try {
			//	history historyService save(history)
			}
			catch (NoSuchElementException ex) {
				LOGGER.info(ex.getMessage());
			}
		}
		LOGGER.info("history: " + history.showHistory());
		//
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("inputs");
		modelAndView.addObject("history", history);
		modelAndView.addObject("historySum", history.showHistory());
		return modelAndView;
	}

	@PostMapping( "/delete" ) public ModelAndView deleter(@ModelAttribute History history) {
		//
		LOGGER.info("deleter(history): " + history.showHistory());
		try {
			// historyService delete(history)
		}
		catch (Exception ex) { LOGGER.severe(ex.getMessage()); }
		return  showInputs();
	}

	//#### extras
	@GetMapping( "/appendix" ) public ModelAndView appendix( ) {

		LOGGER.info("appendix()");
		return new ModelAndView("appendix", new HashMap<>());
	}

	@GetMapping( "/errors" ) public void errors( ) {

		LOGGER.info("errors()");
		throw new RuntimeException("RuntimeException!");
	}

	//#### STATICS ####
	private ModelAndView getHistoryMAV(History history) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("inputs");
		modelAndView.addObject("history", history);
		modelAndView.addObject("historySum", history.showHistory());

		modelAndView.addObject("genConfig", genConfig);
		modelAndView.addObject("eralist", genConfig.getEralist());
		modelAndView.addObject("localelist", genConfig.getLocalelist());
		modelAndView.addObject("taglist", genConfig.getTaglist());

		LOGGER.info("history: " + history.showHistory());
		return modelAndView;
	}
}
