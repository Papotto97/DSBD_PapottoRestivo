package com.unict.auctionmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unict.auctionmanager.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	List<UserEntity> findAll();
	
	Optional<UserEntity> findById(Integer id);
	
}
