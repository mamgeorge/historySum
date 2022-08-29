package com.humanities.history.services;

import java.util.List;

public interface IHistoryService {

	History findById(Long id);

	History save(History history);

	List<History> findAll( );

	void delete(History history);
}
