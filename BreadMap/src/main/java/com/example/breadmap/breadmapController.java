package com.example.breadmap;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.breadmap.review.question.QuestionForm;

import jakarta.validation.Valid;

@Controller
public class breadmapController {
	
	@GetMapping("/breadmap")
	public String main() {
		return "breadmap";
	}
	
}
