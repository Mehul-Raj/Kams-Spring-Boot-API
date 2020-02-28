package com.aroha.kams.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aroha.kams.model.TagEntity;

public interface TagNameRepository extends JpaRepository<TagEntity, Integer> {
	
	boolean existsBytagName(String tagName);

}
