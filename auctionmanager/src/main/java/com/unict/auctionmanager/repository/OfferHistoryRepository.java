package com.unict.auctionmanager.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.unict.auctionmanager.entity.OfferHistoryEntity;

public interface OfferHistoryRepository extends JpaRepository<OfferHistoryEntity, Integer> {

	List<OfferHistoryEntity> findAll();
	
	Optional<OfferHistoryEntity> findById(Integer id);

	Optional<OfferHistoryEntity> findByAuctionIdAndUserIdAndStake(UUID auctionId, Integer userId, Float stake);
	
	@Query("select oh from OfferHistoryEntity oh where oh.auctionId = ?1 and oh.stake < ?2 order by oh.timestamp")
	Optional<OfferHistoryEntity> findFirstByAuctionIdAndStakeLessThanAndOderderByTimestamp(UUID auctionId, Float stake);	
	
//	Optional<OfferHistoryEntity> findFirstByAuctionIdAndStakeLessThan(UUID auctionId, Float stake);	
	
}
