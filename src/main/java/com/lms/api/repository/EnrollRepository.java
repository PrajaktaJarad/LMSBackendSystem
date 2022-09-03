package com.lms.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lms.api.model.Enroll;
import com.lms.api.model.LearningTrack;

public interface EnrollRepository extends JpaRepository<Enroll, Long> {

	@Query("select lt from Enroll e join e.user u join e.learningTrack lt where u.username=?1")
	List<LearningTrack> getLearningTrackByUserID(String username);
}
