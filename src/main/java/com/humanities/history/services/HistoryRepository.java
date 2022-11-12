package com.humanities.history.services;

import com.humanities.history.model.History;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

// Spring pulls History data from: app.yml: spring.datasource.url
@Repository public interface HistoryRepository
	extends CrudRepository<History, Long>, QueryByExampleExecutor<History> { }
