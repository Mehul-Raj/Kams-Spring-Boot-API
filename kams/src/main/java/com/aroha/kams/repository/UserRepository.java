package com.aroha.kams.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aroha.kams.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {

	UserEntity findByeMail(String getEMailId);

	Boolean existsByeMail(String eMail);

}
