package com.example.breadmap.notice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.breadmap.review.question.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NoticeService {

	private final NoticeRepository noticeRepository;

	public List<Notice> getList() {
		return this.noticeRepository.findAll();
	}

	public Notice getnoNotice(Integer id) {  
        Optional<Notice> notice = this.noticeRepository.findById(id);
        if (notice.isPresent()) {
            return notice.get();
	        } else {
	           throw new DataNotFoundException("question not found");
	        }
	}
}
