package com.example.breadmap.review.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.example.breadmap.review.answer.Answer;
import com.example.breadmap.user.SiteUser;

import groovy.lang.Category;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 200)
	private String subject;
	
	@Column(columnDefinition = "TEXT")
	private String content;

	private LocalDateTime createDate;
	
	//게시글 수정 시간
	private LocalDateTime modifyDate;
	
	@PrePersist
	public void prePersist() {
		if (createDate == null) {
			createDate = LocalDateTime.now();
        }
	}
	
	//수정 날짜를 현재 시간으로 설정
	@PreUpdate
	public void preUpdate() {
		/* modifyDate = LocalDateTime.now(); */
	}
	
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<Answer> answerList;
	
	//글쓴이 저장
	@ManyToOne //사용자 한 명이 질문을 여러 개 작성할 수 있음
	private SiteUser author;
	
	//카테고리 추가
	private String category;
	
	@ManyToMany
	Set<SiteUser> voter;
	
	@Column(columnDefinition = "Integer default 0")
	@NotNull
	private Integer views = 0;
	
}