package com.humanities.history.services;

import com.humanities.history.model.History;

import java.util.List;

public interface IHistoryService {

	History findById(Long id);

	List<History> findByDateBeg(String datebeg);

	History save(History history);

	List<History> findAll( );

	void delete(History history);
}
