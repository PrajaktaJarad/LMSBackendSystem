package com.lms.api.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lms.api.dto.LearningTrackDto;
import com.lms.api.dto.UserDto;
import com.lms.api.model.Course;
import com.lms.api.model.Enroll;
import com.lms.api.model.LearningTrack;
import com.lms.api.model.User;
import com.lms.api.repository.CourseRepository;
import com.lms.api.repository.EnrollRepository;
import com.lms.api.repository.LearningTrackRepository;
import com.lms.api.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EnrollRepository enrollRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private LearningTrackRepository learningTrackRepository;
	
	//@Autowired
	//private LearningTrackDto learningTrackDto;
	
	@PostMapping("/user/register")
	public User register(@RequestBody User user)
	{
		String plainTextPass = user.getPassword();
		String encodedPass = passwordEncoder.encode(plainTextPass);
		user.setPassword(encodedPass);
		return userRepository.save(user);
	}
	
	@GetMapping("/user/login")
	public UserDto login(Principal principal)
	{
		String username=principal.getName();
		User user = userRepository.findByUsername(username);
		UserDto dto = new UserDto();
		dto = dto.convert(user);
		return dto;
	}
	
	/*
	 * User Profile update:name,mobile,city
	 */
	@PutMapping("/user/update/{id}")
	public User updateUserProfile(@PathVariable("id") Long id,@RequestBody User userNew)
	{
		/*
		 * step 1: take id of user that has to be updated
		 * step 2: go to DB and fetch the record for this id
		 * Step 3: read new user details 
		 * step 4: update userdb with new values
		 */
		
		User userDB = userRepository.getById(id);
		
		if(userNew.getName() !=null)
			userDB.setName(userNew.getName());
		if(userNew.getMobile() !=null)
			userDB.setMobile(userNew.getMobile());
		if(userNew.getCity() !=null)
			userDB.setCity(userNew.getCity());
		
		return userRepository.save(userDB);
	}
	
	/*
	 * Get all learning tracks with courses based on userId
	 */
	@GetMapping("/user/learning-track")
	public List<LearningTrackDto> getLearningTrackByUserID(Principal principal)
	{
		List<LearningTrack> list = enrollRepository.getLearningTrackByUserID(principal.getName());
		List<LearningTrackDto> listDto = new ArrayList<>();
		/*
		 * for each learning track, fetch the list of courses
		 */
		list.stream().forEach(lt-> {
			LearningTrackDto dto = new LearningTrackDto();
			List<Course> listCourse = courseRepository.findByLearningTrackId(lt.getId());
			dto.setId(lt.getId());
			dto.setName(lt.getName());
			dto.setCourse(listCourse);
			listDto.add(dto);
		});
		return listDto;
	}
	/*
	 * enroll API: enroll users in learning track bases on user id & learningTrackId
	 */
	@PostMapping("/enroll/user/learning-track/{uid}/{tid}")
	public Enroll enrollUserInLearningTrack(@PathVariable("uid") Long uid,@PathVariable("tid") Long tid)
	{
		User user = userRepository.getById(uid);
		LearningTrack learningTrack = learningTrackRepository.getById(tid);
		Enroll enroll=new Enroll();
		enroll.setUser(user);
		enroll.setLearningTrack(learningTrack);
		enroll.setEnrollDate(LocalDate.now());
		enroll.setEndDate(LocalDate.now().plusYears(1));
		
		return enrollRepository.save(enroll);
	}
	
	
}