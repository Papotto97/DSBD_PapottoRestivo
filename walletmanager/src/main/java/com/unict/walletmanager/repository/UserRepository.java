package com.unict.walletmanager.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unict.walletmanager.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

	Optional<UserEntity> findById(Integer id);
	
}
