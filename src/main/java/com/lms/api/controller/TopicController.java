package com.lms.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lms.api.dto.ForumDto;
import com.lms.api.dto.QueDto;
import com.lms.api.dto.TopicRecordsDto;
import com.lms.api.model.Answer;
import com.lms.api.model.Question;
import com.lms.api.model.Topic;
import com.lms.api.model.User;
import com.lms.api.repository.AnswerRepository;
import com.lms.api.repository.QuestionRepository;
import com.lms.api.repository.TopicRepository;
import com.lms.api.repository.UserRepository;

@RestController
public class TopicController {
	
	@Autowired
	private TopicRepository topicRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/topic")
	public Topic postForum(@RequestBody Topic topic) {
		return topicRepository.save(topic);
	}
	
	/*
	 * 10. forum stats api totalNumberOfTopics:_____ totalNumberOfQuestions:____
	 * totalNumberOfAnswers:______
	 * totalNumberOfUsers:______[
	 * that have post edeither QorA]
	 */

    @GetMapping("/forumstats")
	public ForumDto forumStats() {
		List<Topic> topics = topicRepository.findAll();
		List<Question> que = questionRepository.findAll();
		List<Answer> ans = answerRepository.findAll();
		List<User> users = userRepository.findAll();
		
		ForumDto dto = new ForumDto();
		
		dto.setTotalNoOfTopics(topics.size());
		dto.setTotalNoOfQue(que.size());
		dto.setTotalNoOfAns(ans.size());
		
		
		List<String> list = que.stream().map(a->a.getUsername()).collect(Collectors.toList());
		List<String> list1 = ans.stream().map(a->a.getUsername()).collect(Collectors.toList());
		
		list.addAll(list1);
		long userCount = list.stream().distinct().count();
		
		dto.setTotalNoOfUsers((int)userCount);
		return dto;		
	}
    
  //3. create api to display all the topic records in paged manner. 
   //also add number of que available in the DB for each topic 
    
    @GetMapping("/topic/records")
    public List<TopicRecordsDto> getAllTopic(@RequestParam(name = "page",required = false, defaultValue = "0") Integer page,
    @RequestParam(name = "size",required = false, defaultValue = "3") Integer size){
   
    	Pageable pageable = PageRequest.of(page, size);
    
    List<TopicRecordsDto> questionDto= new ArrayList<>();
    List<Topic> topics= topicRepository.findAll(pageable).getContent();

    for(Topic t: topics) {
    	TopicRecordsDto dto= new TopicRecordsDto();
    List<Question> questions= questionRepository.findByTopicId(t.getId());
    dto.setId(t.getId());
    dto.setTopic(t.getTopic());
    dto.setNumOfQue(questions.size());
    questionDto.add(dto);
    }
   return questionDto;

    }
    
    // display the quetext and the username of questions that belong to given topic id
    
    @GetMapping("/que/{tid}")
    public List<QueDto> getAllQue(@PathVariable("tid") Long tid) {
    	
    	
    	List<Question> queList1 = questionRepository.getByTopicId(tid);
    	
    //	List<String> list = queList.stream().map(q->q.getQuestionText()).collect(Collectors.toList());
    //	List<String> list1 = queList.stream().map(q->q.getUsername()).collect(Collectors.toList());
    //	list.addAll(list1);
    	QueDto dto = new QueDto();
    	
    	List<QueDto> dtoList = new ArrayList<>();
    	for(Question q : queList1) {
    		dto.setQuestionText(q.getQuestionText());
    		dto.setUsername(q.getUsername());
    		dtoList.add(dto);
    	}
    	
    	
    	return dtoList;
    	
    }
}
