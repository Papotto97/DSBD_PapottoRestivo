package com.unict.auctionmanager.job;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.unict.auctionmanager.entity.AuctionEntity;
import com.unict.auctionmanager.entity.OfferHistoryEntity;
import com.unict.auctionmanager.entity.WalletEntity;
import com.unict.auctionmanager.factory.RepositoryFactory;


@Component
public class CustomQuartzJob implements Job {

	private static final Logger logger = LoggerFactory.getLogger(CustomQuartzJob.class);

	@Autowired
	private RepositoryFactory repositoryFactory;

	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("Running Quartz Job");

		List<AuctionEntity> auctions = repositoryFactory.getAuctionRepository().findByCompletedFalse();
		
		if(auctions.size() != 0) {
			auctions.forEach(f ->{
				if(!f.getEndTimestamp().after(new Date())) {					
					f.setCompleted(true);
					WalletEntity wallet = repositoryFactory.getWalletRepository().findByUser(f.getUser());
					wallet.setActualStaked(wallet.getActualStaked() - f.getLastOffer());
				}
				
			});
		}

	}
	
}