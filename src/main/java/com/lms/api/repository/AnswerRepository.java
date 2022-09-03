package com.lms.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.api.model.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long>{

	List<Answer> getByQuestionId(Long qid);

	List<Answer> findByQuestionId(Long id);

}
