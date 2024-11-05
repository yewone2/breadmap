package com.example.breadmap.notice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.breadmap.review.question.Question;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
	Notice findBySubject(String subject);
    Notice findBySubjectAndContent(String subject, String content);	
}
