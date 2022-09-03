package com.lms.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.api.model.Course;



public interface CourseRepository extends JpaRepository<Course, Long>{

	

	List<Course> findByLearningTrackId(Long id);

}
