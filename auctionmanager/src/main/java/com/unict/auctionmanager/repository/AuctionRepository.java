package com.unict.auctionmanager.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.unict.auctionmanager.entity.AuctionEntity;

public interface AuctionRepository extends JpaRepository<AuctionEntity, UUID> {

	List<AuctionEntity> findAll();
	
	Optional<AuctionEntity> findById(UUID id);
	
	@Query("select au from AuctionEntity au where au.itemId = ?1 and au.userId = ?2 and au.currencyId = ?3 and au.lastOffer = ?4 order by au.updateTimestamp")
	Optional<AuctionEntity> findFirstByItemIdAndUserIdAndCurrencyIdAndLastOfferOderderByUpdateTimestamp(Integer itemId, Integer userId, Integer currencyId, Float lastOffer);	

//	Optional<AuctionEntity> findFirstByItemIdAndUserIdAndCurrencyIdAndLastOffer(Integer itemId, Integer userId, Integer currencyId, Float lastOffer);
	
}
