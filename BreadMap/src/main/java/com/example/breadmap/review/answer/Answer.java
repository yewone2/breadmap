package com.example.breadmap.review.answer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.example.breadmap.review.question.Question;
import com.example.breadmap.user.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Answer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private LocalDateTime createDate;		

	@ManyToOne
	private Question question;
	
	@ManyToOne
	private SiteUser author;
	
	//답글 수정 시간
	private LocalDateTime modifyDate;
	
	@ManyToMany
	Set<SiteUser> voter;
	
}