package com.unict.auctionmanager.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unict.auctionmanager.entity.AuctionEntity;

public interface AuctionRepository extends JpaRepository<AuctionEntity, UUID> {

	List<AuctionEntity> findAll();
}
