package com.lms.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.api.model.Article;



public interface ArticleRepository extends JpaRepository<Article, Long>{

}
