package com.moohee.board.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.moohee.board.entity.Question;
import com.moohee.board.repository.QuestionRepository;
import com.moohee.board.service.AnswerService;
import com.moohee.board.service.QuestionService;

@Controller
public class BoardController {
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private AnswerService answerService;
	
	@RequestMapping(value = "/")
	public String home() {
		return "redirect:questionList";
	}
	
	@RequestMapping(value = "/index")
	public String index() {
		return "redirect:questionList";
	}
	
	@RequestMapping(value = "/question_form")
	public String question_form() {
		return "question_form";
	}
	
	@RequestMapping(value = "/questionCreate")
	public String create(HttpServletRequest request) {
		
		questionService.questionCreate(request.getParameter("subject"), request.getParameter("content"));
		
		return "redirect:questionList";
	}
	
	@RequestMapping(value = "/questionList")
	public String questionList(Model model) {
		
//		List<Question> questionList = questionRepository.findAll();
		//SELECT * FROM question
		
		List<Question> questionList = questionService.getQuestionList();
		
		model.addAttribute("questionList", questionList);
		
		return "question_list";
	}
	
	@RequestMapping(value = "/questionContentView/{id}")
	public String quesitonView(@PathVariable("id") Integer id, Model model) {
		
//		System.out.print(id);//질문리스트에서 유저가 클릭한 글의 번호
		
		Question question = questionService.getQuestion(id);
		
		model.addAttribute("question", question);
		
		return "question_view";
	}
	
	@RequestMapping(value = "/answerCreate/{id}") 
	public String answerCreate(@PathVariable("id") Integer id, HttpServletRequest request) {
		
		Question question = questionService.getQuestion(id);
		
		answerService.answerCreate(request.getParameter("content"), question);
		
		return String.format("redirect:/questionContentView/%s", id);
		
	}
	
}
