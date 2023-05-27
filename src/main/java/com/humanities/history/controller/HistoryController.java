package com.humanities.history.controller;

import com.humanities.history.model.History;
import com.humanities.history.model.HistoryView;
import com.humanities.history.services.IHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.logging.Logger;

@RestController
public class HistoryController {

	@Autowired private IHistoryService historyService;
	@Autowired private HistoryView historyView;

	private static final Logger LOGGER = Logger.getLogger(HistoryController.class.getName());
	private static final String EOL = "\n";
	private static final int MAX_DISPLAY = 20;
	private static final int SAMPLE_ITEM = 1;
	private static final boolean IS_ACTIVE = true;

	@GetMapping( { "/", "/index", "/home" } ) public ModelAndView home( ) {
		//
		LOGGER.info("home()");
		return new ModelAndView("home", new HashMap<>());
	}

	@GetMapping( "/listing" ) public ModelAndView showListView() {

		LOGGER.info("showListView()");
		List<History> histories = historyService.findAll();
		LOGGER.info("histories.size: " + histories.size());
		if ( histories.size() > MAX_DISPLAY ) { histories = histories.subList(0, MAX_DISPLAY); }
		historyView.setHistory(histories.get(0));

		StringBuilder stringBuilder = new StringBuilder(EOL);
		histories.forEach(history -> stringBuilder.append(history.showHistory()).append(EOL));
		LOGGER.info(stringBuilder.toString());

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("listing");
		modelAndView.addObject("histories", histories);
		modelAndView.addObject("historyView", historyView);
		return modelAndView;
	}

	@PostMapping( "/listParms" ) public ModelAndView showListParms(@ModelAttribute HistoryView historyView) {

		LOGGER.info("showListParms()");
		Map<String, String> eralist = historyView.getEralist();
		Set set = eralist.keySet();
		String dateBeg = (String) set.stream().toArray()[0];

		List<History> histories = historyService.findByDateBeg(dateBeg);
		LOGGER.info("histories.size: " + histories.size());
		if ( histories.size() > MAX_DISPLAY ) { histories = histories.subList(0, MAX_DISPLAY); }

		StringBuilder stringBuilder = new StringBuilder(EOL);
		histories.forEach(hst -> stringBuilder.append(hst.showHistory()).append(EOL));
		LOGGER.info(stringBuilder.toString());

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("listing");
		modelAndView.addObject("histories", histories);
		return modelAndView;
	}

	//#### Inputs
	@GetMapping( "/showInputs" ) public ModelAndView showInputs( ) {

		LOGGER.info("showInputs()");
		History history = new History();
		historyView.setHistory(history);
		return getInputsMAV(history);
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
		catch (NoSuchElementException ex) { LOGGER.severe(ex.getMessage()); }

		historyView.setHistory(history);
		return getInputsMAV(history);
	}

	@PostMapping( "/posted" ) // ModelAttribute or RequestBody?
	public ModelAndView posted(
		@ModelAttribute History history,
		@RequestParam( "nav" ) String nav) {

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
		if ( nav != null && nav.equals("list") ) { modelAndView = showListView(); }
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
			catch (NoSuchElementException ex) { LOGGER.info(ex.getMessage()); }
		}
		historyView.setHistory(history);
		LOGGER.info("showHistory: " + history.showHistory());

		return getInputsMAV(history);
	}

	@PostMapping( "/saver" ) public ModelAndView saver(@ModelAttribute History history) {
		//
		// 	@PostMapping(path = "/posted",
		// 	consumes = {APPLICATION_FORM_URLENCODED_VALUE}, produces = { APPLICATION_FORM_URLENCODED_VALUE})
		LOGGER.info("saver(history)");
		Long longId = history.getId();
		if ( longId == null || longId <= 1 ) { history = new History(); } else {
			try {
				if ( IS_ACTIVE ) {
					history.setDatemod(Timestamp.from(Instant.now()));
					history.setUser(System.getenv("USERNAME"));
					history = historyService.save(history);
				}
			}
			catch (NoSuchElementException ex) {
				LOGGER.info(ex.getMessage());
			}
		}
		historyView.setHistory(history);
		LOGGER.info("history: " + history.showHistory());
		//
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("inputs");
		modelAndView.addObject("history", history);
		modelAndView.addObject("historyView", historyView);
		modelAndView.addObject("historySum", history.showHistory());
		return modelAndView;
	}

	@PostMapping( "/delete" ) public ModelAndView deleter(@ModelAttribute History history) {
		//
		LOGGER.info("deleter(history): " + history.showHistory());
		try {
			if ( IS_ACTIVE ) { historyService.delete(history); }
		}
		catch (Exception ex) { LOGGER.severe(ex.getMessage()); }
		return showInputs();
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
	private ModelAndView getInputsMAV(History history) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("inputs");
		modelAndView.addObject("history", history);
		modelAndView.addObject("historyView", historyView);

		LOGGER.info("history: " + history.showHistory());
		return modelAndView;
	}
}
