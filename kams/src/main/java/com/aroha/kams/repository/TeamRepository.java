package com.aroha.kams.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aroha.kams.model.TeamEntity;

public interface TeamRepository extends JpaRepository<TeamEntity, Integer> {

	boolean existsBycompanyName(String companyName);

	boolean existsBydepartmentName(String departmentName);

	boolean existsByprojectName(String projectName);

	boolean existsByteamName(String teamName);

	List<TeamEntity> findAllByCompanyNameAndDepartmentNameAndProjectName(String companyName, String departmentName,
			String projectName);

}
