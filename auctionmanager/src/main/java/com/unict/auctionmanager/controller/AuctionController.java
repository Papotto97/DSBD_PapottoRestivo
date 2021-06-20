package com.unict.auctionmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unict.auctionmanager.model.AuctionBean;
import com.unict.auctionmanager.model.BaseModel;
import com.unict.auctionmanager.service.AuctionService;

@RestController
@RequestMapping("auction")
public class AuctionController {

	@Autowired
	private AuctionService auctionService;

	@PostMapping("/set")
	public ResponseEntity<?> set(AuctionBean bean) {
		BaseModel<?> resp = auctionService.set(bean);

		return getResponse(resp);

	}

	@PostMapping("/rollback")
	public ResponseEntity<?> rollback(AuctionBean bean) {
		BaseModel<?> resp = auctionService.rollback(bean);

		return getResponse(resp);
	}

	private ResponseEntity<?> getResponse(BaseModel<?> resp) {

		if (resp.isSuccess()) {
			return ResponseEntity.ok(resp.getData());
		} else
			return ResponseEntity.status(resp.getError().getErrorCode()).body(resp.getError().getErrorMessage());
	}

//	@GetMapping("/create")
//	public String bulkcreate() {
//		// save a single Customer
//		auctionRepository.save(AuctionEntity.builder()
//				.id(UUID.randomUUID())
//				.itemId(1)
//				.userId(1)
//				.currencyId(1)
//				.startPrice(Float.parseFloat("52.35"))
//				.lastOffer(Float.parseFloat("53.21"))
//				.build());
//
//		return "Auction created";
//	}
//	
//	@GetMapping("/getall")
//	public Object getAll() {
//
//		List<AuctionEntity> res = auctionRepository.findAll();
//		
//		List<ItemEntity> ret = new ArrayList<ItemEntity>();
//		
//		for (AuctionEntity entity : res) {
//			ret.add(entity.getItem());
//		}
//		
//		return ret;
//	}

}
