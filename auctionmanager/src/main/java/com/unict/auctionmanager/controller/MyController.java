package com.unict.auctionmanager.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unict.auctionmanager.entity.AuctionEntity;
import com.unict.auctionmanager.entity.CurrencyEntity;
import com.unict.auctionmanager.entity.ItemEntity;
import com.unict.auctionmanager.repository.AuctionRepository;

@RestController
@RequestMapping("MyController")
public class MyController {

	@Autowired
	private AuctionRepository auctionRepository;

	@GetMapping("/create")
	public String bulkcreate() {
		// save a single Customer
		auctionRepository.save(AuctionEntity.builder()
				.id(UUID.randomUUID())
				.itemId(1)
				.userId(1)
				.currencyId(1)
				.startPrice(Float.parseFloat("52.35"))
				.lastOffer(Float.parseFloat("53.21"))
				.build());

		return "Auction created";
	}
	
	@GetMapping("/getall")
	public Object getAll() {

		List<AuctionEntity> res = auctionRepository.findAll();
		
		List<ItemEntity> ret = new ArrayList<ItemEntity>();
		
		for (AuctionEntity entity : res) {
			ret.add(entity.getItem());
		}
		
		return ret;
	}
	
}
