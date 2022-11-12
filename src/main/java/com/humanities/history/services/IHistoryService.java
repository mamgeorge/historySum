package com.humanities.history.services;

import com.humanities.history.model.History;

import java.util.List;

public interface IHistoryService {

	History findById(Long id);

	List<History> findByDateBeg(String datebeg);

	List<History> findByReferenced(String referenced);

	History save(History history);

	List<History> findAll( );

	void delete(History history);
}
