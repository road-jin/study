package com.eazybytes.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface NoticeRepository extends CrudRepository<Notice, Long> {

	@Query(value = "select n from Notice n where CURDATE() BETWEEN noticBegDt AND noticEndDt")
	List<Notice> findAllActiveNotices();

}
