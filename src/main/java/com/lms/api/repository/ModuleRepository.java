package com.lms.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lms.api.model.Modules;

public interface ModuleRepository extends JpaRepository<Modules, Long> {

	@Query("select m from Modules m where m.course.id=?1")
	List<Modules> findByCourseId(Long cid);

}
