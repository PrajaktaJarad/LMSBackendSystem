package com.lms.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lms.api.model.Author;
import com.lms.api.model.Course;
import com.lms.api.repository.AuthorRepository;
import com.lms.api.repository.CourseRepository;

@RestController
public class AuthorController {

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private AuthorRepository authorRepository;
	/*
	 * Author Post API
	 */
	@PostMapping("/author")
	public Author postAuthor(@RequestBody Author author)
	{
		return authorRepository.save(author);
	}
	
	/*
	 * Assign Author to course : aid,cid
	 */
	@PostMapping("/author/course/{cid}/{aid}")
	public Course assignAuthorToCourse(@PathVariable("cid") Long cid,@PathVariable("aid") Long aid){
		
		Course course=courseRepository.getById(cid);
		Author author=authorRepository.getById(aid);
		
		List<Author> authorList=course.getAuthor();
		authorList.add(author);
		
		course.setAuthor(authorList);
		
		return courseRepository.save(course);
	}
	
	/*
	 * Un-Assign Author to course : aid,cid
	 */
	@PutMapping("/author/course/{cid}/{aid}")
	public Course unassignAuthorToCourse(@PathVariable("cid") Long cid,@PathVariable("aid") Long aid){
		
		Course course=courseRepository.getById(cid);
		Author author=authorRepository.getById(aid);
		
		List<Author> authorList=course.getAuthor();
		authorList.remove(author);
		
		course.setAuthor(authorList);
		
		return courseRepository.save(course);
	}
}