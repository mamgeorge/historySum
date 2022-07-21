package com.humanities.history.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service public class HistoryService implements IHistoryService {

	@Autowired private HistoryRepository historyRepository;

	@Override public History findById(Long id) {
		//
		History history = null;
		if ( id == null || id.equals(0L) ) { history = new History().getSample(); } else {
			Optional<History> optional = historyRepository.findById(id);
			history = optional.get();
		}
		return history;
	}

	@Override public History save(History history) { return historyRepository.save(history); }

	@Override public List<History> findAll( ) { return (List<History>) historyRepository.findAll(); }

	@Override public void delete(History history) {
		//
		System.out.println("deletion request for: " + history.showHistory());
		// historyRepository.delete(history);
	}
}