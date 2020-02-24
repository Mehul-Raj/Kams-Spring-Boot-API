package com.aroha.kams.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aroha.kams.model.DepartmentEntity;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Integer> {

	boolean existsBycompanyName(String companyName);

	boolean existsBydepartmentName(String departmentName);

	List<DepartmentEntity> findBycompanyName(String companyName);
}
