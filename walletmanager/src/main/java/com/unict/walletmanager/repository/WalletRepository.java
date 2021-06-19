package com.unict.walletmanager.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unict.walletmanager.entity.UserEntity;
import com.unict.walletmanager.entity.WalletEntity;

public interface WalletRepository extends JpaRepository<WalletEntity, UUID> {

	List<WalletEntity> findAll();
	WalletEntity findByUser(UserEntity user);
	
}
