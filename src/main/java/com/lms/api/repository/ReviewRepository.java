package com.lms.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lms.api.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{
	@Query("select r from Review r join r.course c where c.id=?1")
	List<Review> findByCourseId(long cid);

	List<Review> getByCourseId(Long cid);

	  
	  
}
