package com.lms.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.api.model.LearningTrack;

public interface LearningTrackRepository extends JpaRepository<LearningTrack, Long> {

}
