package com.lms.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.api.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long>{
	
	

}
