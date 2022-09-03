package com.lms.api.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lms.api.dto.QuestionDto;
import com.lms.api.model.Answer;
import com.lms.api.model.Question;
import com.lms.api.repository.AnswerRepository;
import com.lms.api.repository.QuestionRepository;

@RestController
public class AnswerController {
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	
	//6.create an api to post answer
	@PostMapping("/answer")
	public Answer postAnswer(@RequestBody Answer answer, Principal principal) {
		String username = principal.getName();
		answer.setUsername(username);
		
		LocalDate dateOfPost = LocalDate.now();
		answer.setDateOfPost(dateOfPost);
		return answerRepository.save(answer);
	}
	  
	//2.put sample data in relationship table
	  @PostMapping("/question/answer/{qid}/{aid}")
	  public Question assignAnswerToQuestion(@PathVariable("qid") Long qid, @PathVariable("aid") Long aid) {
	  
	//fetch the question and answer by id   
	  Question question = questionRepository.getById(qid);
	  Answer answer = answerRepository.getById(aid);
	 
	  List<Answer> anslist = question.getAnswer();
	  
	  anslist.add(answer);
	  question.setAnswer(anslist);
		return questionRepository.save(question);
	  }
	  
		
	 
	//9.PUT api for question and answer. Only those Users who have posted these questions and answers must be able to edit
	 @PutMapping("/answer/{aid}")
	 public Answer updateAnswer(@PathVariable("aid") Long aid, @RequestBody Answer ansNew, Principal principal) {
		 String username = principal.getName();
		 
		 Answer ansDB = answerRepository.getById(aid);
		 
		 if(ansNew.getAnswerText()!=null)
			 ansDB.setAnswerText(ansNew.getAnswerText());
			
			String userOwner = ansDB.getUsername();
			
			if(! username.equalsIgnoreCase(userOwner))
				throw new RuntimeException("user not allowed to edit the answer");
			
			return answerRepository.save(ansDB);	 
	 }
	 
	//8.create an api for updating likes of answer
		@PutMapping("/answer/likes/{aid}")
		public Answer updateAnsLikes(@PathVariable("aid") long aid) {
			Answer ansDB = answerRepository.getById(aid);
			
			int i = ansDB.getLikes()+1;
			ansDB.setLikes(i);
			
			return answerRepository.save(ansDB);
		}
		

}
