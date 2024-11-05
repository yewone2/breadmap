package com.example.breadmap.review.question;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.breadmap.review.answer.Answer;
import com.example.breadmap.review.answer.AnswerForm;
import com.example.breadmap.review.answer.AnswerService;
import com.example.breadmap.review.category.Category;
import com.example.breadmap.review.category.CategoryService;
import com.example.breadmap.user.SiteUser;
import com.example.breadmap.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class QuestionController {

	private final QuestionService questionService;
	private final AnswerService answerService;
	private final UserService userService;
	private final CategoryService categoryService;

	@GetMapping("/breadmap/review")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
				@RequestParam(value = "kw", defaultValue = "") String kw) {
		Page<Question> paging = this.questionService.getList(page, kw);
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		return "question_list";
	}

	@GetMapping(value = "/breadmap/review/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm,
			@RequestParam(value="ans-page", defaultValue = "0") int answerPage) {
		this.questionService.viewUp(id);
		Question question = this.questionService.getQuestion(id);
		Page<Answer> answerPaging = this.answerService.getAnswerList(question, answerPage);
			model.addAttribute("question", question);
			model.addAttribute("ans_paging", answerPaging);
		return "question_detail";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/breadmap/review/create")
	public String questionCreate(QuestionForm questionForm) {
		return "question_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/breadmap/review/create")
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
		if (bindingResult.hasErrors()) {
			return "question_form";
		}
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser,
				questionForm.getCategory());
		return "redirect:/breadmap/review";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/review/modify/{id}")
	public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
		Question question = this.questionService.getQuestion(id);
		if (!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		questionForm.setCategory(question.getCategory());
		questionForm.setSubject(question.getSubject());
		questionForm.setContent(question.getContent());
		
		return "question_form";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/review/modify/{id}")
	public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal,
			@PathVariable("id") Integer id) {
		if (bindingResult.hasErrors()) {
			return "question_form";
		}
		Question question = this.questionService.getQuestion(id);
		if (!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		this.questionService.modify(question, questionForm.getCategory(), questionForm.getSubject(), questionForm.getContent());
		question.setModifyDate(LocalDateTime.now());
		return String.format("redirect:/breadmap/review/%s", id);
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/review/delete/{id}")
	public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
		}
		this.questionService.delete(question);
			return "redirect:/breadmap/review";
		}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/review/vote/{id}")
	public String questionVote(Principal principal, @PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.vote(question, siteUser);
        return String.format("redirect:/breadmap/review/%s", id);
	}
}