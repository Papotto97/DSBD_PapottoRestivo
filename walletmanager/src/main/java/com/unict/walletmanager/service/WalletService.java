package com.unict.walletmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unict.walletmanager.entity.UserEntity;
import com.unict.walletmanager.entity.WalletEntity;
import com.unict.walletmanager.model.AuctionBean;
import com.unict.walletmanager.model.BaseModel;
import com.unict.walletmanager.model.BaseModelBuilder;
import com.unict.walletmanager.repository.WalletRepository;

@Service
public class WalletService {
	
	@Autowired
	private WalletRepository walletRepository;
	
	
	public BaseModel<?> set(AuctionBean bean){
		UserEntity user = new UserEntity();
		user.setId(bean.getUserId());
		WalletEntity en = walletRepository.findByUser(user);

		en.setDisponsability(en.getDisponsability() - bean.getStake());
		en.setActualStaked(en.getActualStaked() + bean.getStake());
		
		walletRepository.save(en);
		
		return BaseModelBuilder.success("Wallet updated");
	}
	
	public BaseModel<?> rollback(AuctionBean bean){
		UserEntity user = new UserEntity();
		user.setId(bean.getUserId());
		WalletEntity en = walletRepository.findByUser(user);

		en.setDisponsability(en.getDisponsability() + bean.getStake());
		en.setActualStaked(en.getActualStaked() - bean.getStake());
		
		walletRepository.save(en);
		
		return BaseModelBuilder.success("Wallet rollback");
	}
	
}
