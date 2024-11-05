package com.example.BreadMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.breadmap.notice.Notice;
import com.example.breadmap.notice.NoticeRepository;
import com.example.breadmap.review.answer.AnswerService;
import com.example.breadmap.review.question.Question;
import com.example.breadmap.review.question.QuestionService;
import com.example.breadmap.user.SiteUser;
import com.example.breadmap.user.UserService;

@SpringBootTest
class BreadMapApplicationTests {
	
	@Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private UserService userService;

//@Transactional
    @Test
    void testJpa() {
        List<Question> questionLst = this.questionService.getList();
        Question question = questionLst.get(questionLst.size() - 1);
        SiteUser user = userService.create("temp", "temp@temp.com", "1234");
        for (int i = 0; i < 300; ++i) {
            this.answerService.create(question, String.format("테스트 답변 %s!!", i), user);
        }
    }
}


