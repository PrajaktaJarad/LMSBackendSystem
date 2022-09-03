package com.lms.api.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lms.api.dto.CourseStatsDto;
import com.lms.api.dto.ModulesDto;
import com.lms.api.model.Course;
import com.lms.api.model.Modules;
import com.lms.api.model.Video;
import com.lms.api.repository.CourseRepository;
import com.lms.api.repository.ModuleRepository;
import com.lms.api.repository.VideoRepository;

@RestController
public class VideoController {

	@Autowired
	private ModuleRepository moduleRepository;

	@Autowired
	private VideoRepository videoRepository;

	@Autowired
	private CourseRepository courseRepository;

	/*
	 * module post API
	 */
	@PostMapping("/modules/{cid}")
	public Modules addModule(@RequestBody Modules modules, @PathVariable("cid") Long cid) {
		Course course = courseRepository.getById(cid);
		modules.setCourse(course);
		return moduleRepository.save(modules);
	}

	/*
	 * Video post API
	 */

	@PostMapping("/video/{mid}")
	public Video postVideo(@RequestBody Video video, @PathVariable("mid") Long mid) {
		Modules modules = moduleRepository.getById(mid);
		video.setModule(modules);
		return videoRepository.save(video);

	}

	/*
	 * Get all Modules by CourseId Along with module, give video info as well
	 * 
	 * [ { id: name: , sequence: , videos:[ { id: title: duration: }, {
	 * 
	 * } ]
	 * 
	 * }
	 */

	
	 @GetMapping("/module/alternate/{cid}")
	public List<ModulesDto> getModulesByCourseId(@PathVariable("cid") Long cid) {
		List<Modules> list = moduleRepository.findByCourseId(cid);
		List<ModulesDto> listdto = new ArrayList<>();
		
		//fetch all videos for the given course
		List<Video> listVideos =videoRepository.getByCourseId(cid);
		
		//for each modules, fetch the list of videos
		list.stream().forEach(m->{
			ModulesDto dto = new ModulesDto();
			//fetch videos for each moduleId
			List<Video> listVideo = listVideos.parallelStream()
					                          .filter(v->v.getModule().getId().equals(m.getId()))
					                          .collect(Collectors.toList());
			dto.setId(m.getId());
			dto.setName(m.getName());
			dto.setSequence(m.getSequence());
			dto.setVideo(listVideo);
			listdto.add(dto);
		});
		return listdto;

	}
	/*
	 {
	 numModules:
	 numVideos:
	 contentDuration: display in hours and min= 136mins=2hrs, 16 mins 
	*/
	 @GetMapping("/course/video/stats/{cid}")
	 public CourseStatsDto courseStatsByVideo(@PathVariable("cid") Long cid) {
		 
		 //fetch all modules based on courseId
		 List<Modules> listModules = moduleRepository.findByCourseId(cid);
		 
		 //fetch all videos based on courseId
		 List<Video> listVideos =videoRepository.getByCourseId(cid);
	
		 //calculate total duration of the videos
		List<String> durationList = listVideos.stream().map(v->v.getDuration()).collect(Collectors.toList());
		
		int totalHours = 0;
		int totalMinutes=0;
		int totalSeconds = 0;
		
		for(String d : durationList) {
			totalMinutes = totalMinutes + Integer.parseInt(d.split("\\.")[0]);
			totalSeconds = totalSeconds + Integer.parseInt(d.split("\\.")[1]);    //30+30+30+30=120
		}
		totalMinutes = totalMinutes + (totalSeconds / 60);  //30+2=32
		totalSeconds = totalSeconds % 60;                 //120 % 60 = 0
		
		totalHours = (int) totalMinutes / 60;   //32/60=0
		totalMinutes = (int)totalMinutes % 60;   //32
		
		//set value in dtd
		CourseStatsDto dto = new CourseStatsDto();
		if(listModules != null)
			dto.setNumModules(listModules.size());
		if(listVideos != null)
			dto.setNumVideos(listVideos.size());
		
		dto.setContentDuration(totalHours + "hrs" + totalMinutes + "mins" + totalSeconds + "secs" );
		
		return dto;
	 }
	 
	 
}
