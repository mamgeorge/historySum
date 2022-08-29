package com.humanities.history.services;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// Spring pulls History data from: app.yml: spring.datasource.url
@Repository public interface HistoryRepository extends CrudRepository<History, Long> { }
