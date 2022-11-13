package com.humanities.history.services;

import com.humanities.history.model.History;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

// Spring pulls History data from: app.yml: spring.datasource.url
@Repository public interface HistoryRepository
	extends CrudRepository<History, Long>, QueryByExampleExecutor<History> {

	@Query(value = "SELECT * FROM history h WHERE h.referenced LIKE %?1%", nativeQuery = true)
	List<History> findByReferenced(String referenced);
}
