package com.lms.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lms.api.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>{

	List<Question> findByTopicId(Long tid);

	@Query("select q from Question q join q.topic t where t.id=?1")
	List<Question> getQuestion(Long tid);

	List<Question> getByTopicId(Long tid);

}
