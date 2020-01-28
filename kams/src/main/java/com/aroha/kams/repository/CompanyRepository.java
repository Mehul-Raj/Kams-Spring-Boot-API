package com.aroha.kams.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aroha.kams.model.CompanyEntity;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Integer> {

	boolean existsBycompanyName(String companyName);
	
	
}