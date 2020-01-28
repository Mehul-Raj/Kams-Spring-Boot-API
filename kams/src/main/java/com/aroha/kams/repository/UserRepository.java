package com.aroha.kams.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aroha.kams.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {

	List<UserEntity> findByeMail(String emailId);

	Boolean existsByeMail(String eMail);

}
