package com.aroha.kams.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aroha.kams.model.ProjectEntity;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer> {

	boolean existsBycompanyName(String companyName);

	boolean existsBydepartmentName(String departmentName);

	boolean existsByprojectName(String projectName);

	public List<ProjectEntity> findAllByCompanyNameAndDepartmentName(String companyName,String departmentName);

}
