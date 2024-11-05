package com.example.breadmap.notice;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.breadmap.review.answer.AnswerForm;
import com.example.breadmap.review.question.Question;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class NoticeController {
	
	private final NoticeService noticeService;
		
	@GetMapping("/breadmap/notice")
	public String list(Model model) {
		List<Notice> noticeList = this.noticeService.getList();
		model.addAttribute("noticeList", noticeList);
		return "notice_list";
	}
	
	@GetMapping(value = "/breadmap/notice/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Notice notice = this.noticeService.getnoNotice(id);
		model.addAttribute("notice", notice);
		return "notice_detail";
	}
	

}
