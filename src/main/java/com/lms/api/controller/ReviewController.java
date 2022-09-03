package com.lms.api.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lms.api.dto.ReviewStatsDto;
import com.lms.api.dto.ReviewStatsDto.ReviewDto;
import com.lms.api.model.Course;
import com.lms.api.model.Review;
import com.lms.api.model.User;
import com.lms.api.repository.CourseRepository;
import com.lms.api.repository.ReviewRepository;
import com.lms.api.repository.UserRepository;

@RestController
public class ReviewController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@PostMapping("/review/{cid}")
	public Review PostReview(@RequestBody Review review, @PathVariable("cid") Long cid, Principal  principal ) {
		
		//go to courserepository and fetch course by id 
		Course course = courseRepository.getById(cid);
		
		//go to userrepo and fetch user by user id
		User user = userRepository.findByUsername(principal.getName());
		
		review.setCourse(course);           //attach course to review
		review.setUser(user);            //attach user to review obj
		
		//save review in DB
		return reviewRepository.save(review);
		
	}
	@GetMapping("/reviews")
	public List<Review> getallReviews(){
		return reviewRepository.findAll();
	}
	
	/*
	Get all by course id
	{
	id
	content
	rating
	userid
	name
	email
	course id
	course name
	learningtrackid
	learningtrack
	*/
	
	@GetMapping("/review/{cid}")
	public List<ReviewStatsDto.ReviewDto> getAllByCourseId(@PathVariable("cid") long cid) {
		List<Review> list = reviewRepository.getByCourseId(cid);
		
		List<ReviewStatsDto.ReviewDto> dtoList = new ArrayList<>();
		
		for(Review temp : list) {
			ReviewStatsDto.ReviewDto dto = new ReviewStatsDto.ReviewDto();
			
			dto.setId(temp.getId());
			dto.setContent(temp.getContent());
			dto.setRating(temp.getRating());
			dto.setUserId(temp.getUser().getId());
			dto.setUsername(temp.getUser().getUsername());
			dto.setName(temp.getUser().getName());
			dto.setEmail(temp.getUser().getEmail());
			dto.setCourseId(temp.getCourse().getId());
			dto.setCourseName(temp.getCourse().getName());
			dto.setLearningTrackId(temp.getCourse().getLearningTrack().getId());
			dto.setLearningTrack(temp.getCourse().getLearningTrack().getName());
			
			dtoList.add(dto);
		}
		return dtoList;
	}
	
	
	
	// get all review by course id
	/*
	 * @GetMapping("/review/{cid}") public List<Review>
	 * getByCourseId(@PathVariable("cid") Long cid) { List<Review> list =
	 * reviewRepository.findByCourseId(cid); return list; }
	 */
	// get review by course id sort by rating(1-5) desc
	
	@GetMapping("/course/sort-rating/{cid}")
	public List<Review> getReviewByCourseIdSortByRatingDesc(@PathVariable("cid") Long cid) {
		List<Review> list = reviewRepository.getByCourseId(cid);
		List<Review> sortedList = list.stream()
				                 .sorted(Comparator.comparingDouble(Review::getRating)
				                  .reversed())
				                 .collect(Collectors.toList());
		return sortedList;
		
	}
	
	@PutMapping("/review/{rid}")
	public Review updateReview(Principal principal, @RequestBody Review review, @PathVariable("rid") Long rid) {
		String username = principal.getName();
		
		Review reviewDB =  reviewRepository.getById(rid);
		
		if(review.getRating() !=0)
			reviewDB.setRating(review.getRating());
		
		if(review.getContent() != null)
			reviewDB.setContent(review.getContent());
		
		String userOwner = reviewDB.getUser().getUsername();
		
		if(! username.equalsIgnoreCase(userOwner))
			throw new RuntimeException("user not allowed to edit the review");
		
		return reviewRepository.save(reviewDB);
		
	}
	/*
	 * Statistical api
	 * {
	 * totalReview: 24
	 * 5star:4
	 * 4star:5
	 * 3star:4
	 * ...
	 * }
*/
	@GetMapping("/review/stats/{cid}")
	public ReviewStatsDto getReviewStatsByCourseId(@PathVariable("cid") Long cid) {
		
		List<Review> list =  reviewRepository.getByCourseId(cid);
		ReviewStatsDto dto = new ReviewStatsDto();
		
		dto.setTotalReviews(list.size());
		dto.setFivestar(list.stream().filter(r->r.getRating()==5).collect(Collectors.toList()).size());
	
		
		dto.setFourstar(list.stream().filter(r->r.getRating()==4).collect(Collectors.toList()).size());
	
		
		dto.setThreestar(list.stream().filter(r->r.getRating()==3).collect(Collectors.toList()).size());
		
		dto.setTwostar(list.stream().filter(r->r.getRating()==2).collect(Collectors.toList()).size());
		
		dto.setOnestar(list.stream().filter(r->r.getRating()==1).collect(Collectors.toList()).size());
		return dto;
	}
}
