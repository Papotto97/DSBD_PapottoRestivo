package com.unict.auctionmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unict.auctionmanager.entity.ItemEntity;

public interface ItemRepository extends JpaRepository<ItemEntity, Integer> {

	List<ItemEntity> findAll();
	
	Optional<ItemEntity> findById(Integer id);
	
}
