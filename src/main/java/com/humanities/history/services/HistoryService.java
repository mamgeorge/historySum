package com.humanities.history.services;

import com.humanities.history.model.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service public class HistoryService implements IHistoryService {

	@Autowired private HistoryRepository historyRepository;

	@Override public History findById(Long id) {

		if ( id == null || id.equals(0L) ) { id = 1L; }

		Optional<History> optional = historyRepository.findById(id);
		History history = optional.get();

		return history;
	}

	@Override public List<History> findByDateBeg(String datebeg) {

		if ( datebeg == null || datebeg.equals("") ) { datebeg = "+0000"; }

		History history = new History();
		history.setDatebeg(datebeg);
		Example<History> employeeExample = Example.of(history);

		List<History> histories = new ArrayList<>();
		Iterable<History> iterables = historyRepository.findAll(employeeExample);
		iterables.iterator().forEachRemaining(histories::add);

		return histories;
	}

	@Override public List<History> findByReferenced(String referenced) {

		return historyRepository.findByReferenced(referenced);
	}

	@Override public History save(History history) { return historyRepository.save(history); }

	@Override public List<History> findAll( ) { return (List<History>) historyRepository.findAll(); }

	@Override public void delete(History history) {
		//
		System.out.println("#### DELETION REQUEST: [" + history.showHistory() + "] ####");
		// historyRepository.delete(history);
	}
}