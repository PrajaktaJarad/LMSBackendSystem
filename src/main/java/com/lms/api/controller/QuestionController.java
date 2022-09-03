package com.lms.api.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lms.api.model.Topic;
import com.lms.api.dto.AnswerDto;
import com.lms.api.dto.QuestionDto;
import com.lms.api.dto.TopicDto;
import com.lms.api.model.Answer;
import com.lms.api.model.Question;
import com.lms.api.repository.TopicRepository;
import com.lms.api.repository.AnswerRepository;
import com.lms.api.repository.QuestionRepository;

@RestController
public class QuestionController {

	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private TopicRepository topicRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	//7.create an api to post the question 
	@PostMapping("/question")
	public Question postQuestion(@RequestBody Question question, Principal principal) {
		String username = principal.getName();
		question.setUsername(username);
		
		LocalDate dateOfPost = LocalDate.now();
		question.setDateOfPost(dateOfPost);
		return questionRepository.save(question);
	}
	
	//2.add sample data in relationship table
	@PostMapping("/topic/question/{tid}/{qid}")
	public Topic assignQuestionToTopic(@PathVariable("tid") Long tid, @PathVariable("qid") Long qid) {
		
		Topic topic = topicRepository.getById(tid);
		Question question = questionRepository.getById(qid);
		
		List<Question> quelist =  topic.getQuestion();
		
		quelist.add(question);
		topic.setQuestion(quelist);
		return topicRepository.save(topic);
	}

	/*
	 * 5.GET api for List of Answers based on questionId having following response format
	 * questionId:____ questionText:___ username:_____ dateOfPost:____
	 * numberOfAnswer:____ answers:[ answerId:____ answerText:___ likes:________
	 * username:______ dateOfPost:_____ }
	 */
	//5.sort the answer by dateOfPost - descending order-latest first
	@GetMapping("/answer/question/{qid}")
	public AnswerDto getAnswerByQuestionId(@PathVariable("qid") Long qid) {
		List<Answer> ansList = answerRepository.getByQuestionId(qid);
		
		Question question = questionRepository.getById(qid);
		AnswerDto dto = new AnswerDto();
		dto.setQuestionId(qid);
		dto.setQuestionText(question.getQuestionText());
		dto.setUsername(question.getUsername());
		dto.setDateOfPost(question.getDateOfPost());
		dto.setNumberOfAnswer(ansList.size());
		
		List<Answer> list = ansList.stream()
				        .sorted(Comparator.comparing(Answer::getDateOfPost)
						.reversed())
				        .collect(Collectors.toList());
				
		dto.setAnswer(list);
		return dto;
	}
	
	
	//9.PUT api for question and answer. Only those Users who have posted these questions and answers must be able to edit

	@PutMapping("/question/{qid}")
	public Question updateQuestion(Principal principal, @PathVariable("qid") Long qid, @RequestBody Question queNew) {
		
		String username = principal.getName();
		Question queDB = questionRepository.getById(qid);
		
		if(queNew.getQuestionText()!=null)
			queDB.setQuestionText(queNew.getQuestionText());
		
		String userOwner = queDB.getUsername();
		
		if(! username.equalsIgnoreCase(userOwner))
			throw new RuntimeException("user not allowed to edit the question");
		
		return questionRepository.save(queDB);	
	}
	
	//4.create get api to display list of question based on topic id
	@GetMapping("/question/{tid}")
	public TopicDto getQuestionByTopicId(@PathVariable("tid") Long tid) {
		
		List<TopicDto.QuestionDtoNew> dtoList = new ArrayList<>();	
		List<Question> queList = questionRepository.findByTopicId(tid);
		Topic topics = topicRepository.getById(tid);
		TopicDto dto = new TopicDto();
		
		queList.stream().forEach(q->{
			TopicDto.QuestionDtoNew quedto = new TopicDto.QuestionDtoNew();
			
			List<Answer> ansList = answerRepository.findByQuestionId(q.getId());
			quedto.setId(q.getId());
			quedto.setQuestionText(q.getQuestionText());
			quedto.setLikes(q.getLikes());
			quedto.setUsername(q.getUsername());
			quedto.setNumOfAns(ansList.size());
			dtoList.add(quedto);			
		});
		
		dto.setId(tid);
		dto.setTopic(topics.getTopic());
		dto.setQuestion(dtoList);
		
		return dto;
				
	}
	//8.create an api for updating likes
	@PutMapping("/question/likes/{qid}")
	public Question updateQueLikes(@PathVariable("qid") long qid) {
		Question queDB = questionRepository.getById(qid);
		
		int i = queDB.getLikes()+1;
		queDB.setLikes(i);
		
		
		return questionRepository.save(queDB);
	}
	
}
