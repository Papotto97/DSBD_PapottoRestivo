package com.unict.auctionmanager.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.unict.auctionmanager.repository.AuctionRepository;
import com.unict.auctionmanager.repository.ItemRepository;
import com.unict.auctionmanager.repository.OfferHistoryRepository;
import com.unict.auctionmanager.repository.UserRepository;

import lombok.Getter;

@Component
@Getter
public class RepositoryFactory {
	
	@Autowired
	private AuctionRepository auctionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OfferHistoryRepository offerHistoryRepository;
	
	@Autowired
	private ItemRepository itemRepository;

}
