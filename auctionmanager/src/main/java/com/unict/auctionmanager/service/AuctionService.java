package com.unict.auctionmanager.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.unict.auctionmanager.entity.AuctionEntity;
import com.unict.auctionmanager.entity.ItemEntity;
import com.unict.auctionmanager.entity.OfferHistoryEntity;
import com.unict.auctionmanager.entity.UserEntity;
import com.unict.auctionmanager.factory.RepositoryFactory;
import com.unict.auctionmanager.model.AuctionBean;
import com.unict.auctionmanager.model.BaseModel;
import com.unict.auctionmanager.model.BaseModelBuilder;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class AuctionService {

	@Autowired
	private RepositoryFactory repositoryFactory;

	public BaseModel<?> set(AuctionBean bean) {
		AuctionEntity auEntity;
		OfferHistoryEntity ohEntity;

		Optional<UserEntity> optUser = repositoryFactory.getUserRepository().findById(bean.getUserId());

		if (optUser.isPresent()) {

			// UPDATE AUCTION
			if (bean.getAuctionId() != null) {
				UUID uuid = UUID.fromString(bean.getAuctionId());
				Optional<AuctionEntity> opt = repositoryFactory.getAuctionRepository().findById(uuid);
				if (opt.isPresent()) {
					auEntity = opt.get();

					if (auEntity.getLastOffer() < bean.getStake()) {

						auEntity.setUpdateTimestamp(new Date());
						auEntity.setLastOffer(bean.getStake());
						auEntity.setUserId(bean.getUserId());

						// INSERT ON OFFER_HISTORY
						ohEntity = OfferHistoryEntity.builder()
								.auctionId(uuid)
								.userId(bean.getUserId())
								.stake(bean.getStake())
								.build();

						repositoryFactory.getOfferHistoryRepository().save(ohEntity);
						repositoryFactory.getAuctionRepository().save(auEntity);

						return BaseModelBuilder.success(bean);
					} else
						return BaseModelBuilder.error(HttpStatus.NOT_MODIFIED, "The current stake is higher than the proposed one");

				} else
					return BaseModelBuilder.error(HttpStatus.NOT_FOUND, "Auction not found");
			}
			// CREATE AUCTION
			else {
				Optional<ItemEntity> optItem = repositoryFactory.getItemRepository().findById(bean.getItemId());
				if (optItem.isPresent()) {

					UUID newUUID = UUID.randomUUID();

					Calendar moment = Calendar.getInstance();
					moment.add(Calendar.HOUR_OF_DAY, optItem.get().getAuctionHour());
					Date endTimestamp = moment.getTime();

					if (optItem.get().getStartPrice() < bean.getStake()) {

						auEntity = AuctionEntity.builder()
								.id(newUUID)
								.itemId(bean.getItemId())
								.startPrice(optItem.get().getStartPrice())
								.lastOffer(bean.getStake())
								.currencyId(bean.getCurrency())
								.userId(bean.getUserId())
								.endTimestamp(endTimestamp)
								.completed(false)
								.build();

						// INSERT ON OFFER_HISTORY
						ohEntity = OfferHistoryEntity.builder()
								.auctionId(newUUID)
								.userId(bean.getUserId())
								.stake(bean.getStake())
								.build();

						repositoryFactory.getOfferHistoryRepository().save(ohEntity);
						repositoryFactory.getAuctionRepository().save(auEntity);

						return BaseModelBuilder.success(bean);

					} else
						return BaseModelBuilder.error(HttpStatus.BAD_REQUEST, "The start price is higher than the proposed one");

				} else
					return BaseModelBuilder.error(HttpStatus.NOT_FOUND, "Item not found");
			}
		} else
			return BaseModelBuilder.error(HttpStatus.NOT_FOUND, "User not found");

	}

	public BaseModel<?> rollback(AuctionBean bean) {
		AuctionEntity auEntity;
		OfferHistoryEntity ohEntity;

		Optional<UserEntity> optUser = repositoryFactory.getUserRepository().findById(bean.getUserId());

		if (optUser.isPresent()) {

			// UPDATE AUCTION
			if (bean.getAuctionId() != null) {
				UUID uuid = UUID.fromString(bean.getAuctionId());
				Optional<AuctionEntity> opt = repositoryFactory.getAuctionRepository().findById(uuid);
				if (opt.isPresent()) {
					auEntity = opt.get();
					
					Optional<OfferHistoryEntity> optOH = repositoryFactory.getOfferHistoryRepository().findByAuctionIdAndUserIdAndStake(uuid, bean.getUserId(), bean.getStake());
					if (optOH.isPresent()) {
						
						ohEntity = optOH.get();
						
						Optional<OfferHistoryEntity> optOld = repositoryFactory.getOfferHistoryRepository().findFirstByAuctionIdAndStakeLessThanAndOderderByTimestamp(uuid, bean.getStake());
						if (!optOld.isPresent()) {
							OfferHistoryEntity oldValue = optOld.get();
							
							auEntity.setUpdateTimestamp(oldValue.getTimestamp());
							auEntity.setLastOffer(oldValue.getStake());
							auEntity.setUserId(oldValue.getUserId());
							
							repositoryFactory.getOfferHistoryRepository().delete(ohEntity);
							repositoryFactory.getAuctionRepository().save(auEntity);

							return BaseModelBuilder.success(bean);
							
						} else
							return BaseModelBuilder.error(HttpStatus.NOT_FOUND, "Old offer values not found");
						
					} else
						return BaseModelBuilder.error(HttpStatus.NOT_FOUND, "Old offer not found");

				} else
					return BaseModelBuilder.error(HttpStatus.NOT_FOUND, "Auction not found");
			}
			// CREATE AUCTION
			else {
				Optional<ItemEntity> optItem = repositoryFactory.getItemRepository().findById(bean.getItemId());
				if (optItem.isPresent()) {
					
					Optional<AuctionEntity> optOldAuction = repositoryFactory.getAuctionRepository()
							.findFirstByItemIdAndUserIdAndCurrencyIdAndLastOfferOderderByUpdateTimestamp(optItem.get().getId(), bean.getUserId(), bean.getCurrency(), bean.getStake());

					if (optOldAuction.isPresent()) {
						
						Optional<OfferHistoryEntity> optOldOH = repositoryFactory.getOfferHistoryRepository()
								.findByAuctionIdAndUserIdAndStake(optOldAuction.get().getId(), bean.getUserId(), bean.getStake());
						
						if (optOldOH.isPresent()) {
							repositoryFactory.getAuctionRepository().delete(optOldAuction.get());
							repositoryFactory.getOfferHistoryRepository().delete(optOldOH.get());
							
							return BaseModelBuilder.success(bean);
						} else
							return BaseModelBuilder.error(HttpStatus.NOT_FOUND, "Old offer not found");

					} else
						return BaseModelBuilder.error(HttpStatus.NOT_FOUND, "Old auction not found");

				} else
					return BaseModelBuilder.error(HttpStatus.NOT_FOUND, "Item not found");
			}
		} else
			return BaseModelBuilder.error(HttpStatus.NOT_FOUND, "User not found");
	}

}
