package com.unict.auctionmanager.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unict.auctionmanager.entity.UserEntity;
import com.unict.auctionmanager.entity.WalletEntity;

public interface WalletRepository extends JpaRepository<WalletEntity, UUID> {

	List<WalletEntity> findAll();
	WalletEntity findByUser(UserEntity user);
	
}
