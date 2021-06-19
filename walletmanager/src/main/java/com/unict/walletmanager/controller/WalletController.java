package com.unict.walletmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
	public ResponseEntity<BaseModel<?>> set(AuctionBean bean){
		return ResponseEntity.ok(walletService.set(bean));
		
	}
	
	@PostMapping("/rollback")
	public ResponseEntity<BaseModel<?>> rollback(AuctionBean bean){
		return ResponseEntity.ok(walletService.rollback(bean));
	}
	
	
}
