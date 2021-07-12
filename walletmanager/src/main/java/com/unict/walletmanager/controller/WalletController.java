package com.unict.walletmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unict.walletmanager.model.AuctionBean;
import com.unict.walletmanager.model.BaseModel;
import com.unict.walletmanager.service.WalletService;

@RestController
@RequestMapping("wallet")
public class WalletController {
	
	@Autowired
	private WalletService walletService;

	@PostMapping("/set")
	public ResponseEntity<?> set(@RequestBody AuctionBean bean){
		BaseModel<?> baseModel = walletService.set(bean);

		return getResponse(baseModel);		
	}
	
	@PostMapping("/rollback")
	public ResponseEntity<?> rollback(@RequestBody AuctionBean bean){
		BaseModel<?> baseModel = walletService.rollback(bean);

		return getResponse(baseModel);
	}

	private ResponseEntity<?> getResponse(BaseModel<?> resp) {

		if (resp.isSuccess()) {
			return ResponseEntity.ok(resp);
		} else
			return new ResponseEntity<BaseModel>(resp, resp.getError().getErrorCode());
	}
	
}
