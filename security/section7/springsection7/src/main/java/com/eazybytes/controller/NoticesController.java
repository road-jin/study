package com.eazybytes.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.domain.Notice;
import com.eazybytes.domain.NoticeRepository;

@RestController
public class NoticesController {

	private final NoticeRepository noticeRepository;

	public NoticesController(NoticeRepository noticeRepository) {
		this.noticeRepository = noticeRepository;
	}

	@GetMapping("/notices")
	public ResponseEntity<List<Notice>> getNotices() {
		List<Notice> notices = noticeRepository.findAllActiveNotices();
		return ResponseEntity.ok()
			.cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
			.body(notices);
	}


}
