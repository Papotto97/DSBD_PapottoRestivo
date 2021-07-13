package com.unict.walletmanager.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.unict.walletmanager.entity.UserEntity;
import com.unict.walletmanager.entity.WalletEntity;
import com.unict.walletmanager.model.AuctionBean;
import com.unict.walletmanager.model.BaseModel;
import com.unict.walletmanager.model.BaseModelBuilder;
import com.unict.walletmanager.repository.UserRepository;
import com.unict.walletmanager.repository.WalletRepository;

@Service
public class WalletService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private WalletRepository walletRepository;
	
	
	public BaseModel<?> set(AuctionBean bean){

		Optional<UserEntity> user = userRepository.findById(bean.getUserId());
		if(user.isPresent()) {
			WalletEntity en = user.get().getWallet();
			en.setDisponsability(en.getDisponsability() - bean.getStake());
			en.setActualStaked(en.getActualStaked() + bean.getStake());
			
			walletRepository.save(en);
			
			return BaseModelBuilder.success(bean);
		}else {
			return BaseModelBuilder.error(HttpStatus.NOT_FOUND, "Error updating wallet", bean);
		}
		
	}
	
	public BaseModel<?> rollback(AuctionBean bean){
		Optional<UserEntity> user = userRepository.findById(bean.getUserId());
			if(user.isPresent()) {
				
			WalletEntity en = user.get().getWallet();

			en.setDisponsability(en.getDisponsability() + bean.getStake());
			en.setActualStaked(en.getActualStaked() - bean.getStake());
			
			walletRepository.save(en);
			
			return BaseModelBuilder.success(bean);
		}else {
			return BaseModelBuilder.error(HttpStatus.NOT_FOUND, "Error updating wallet", bean);
		}
		
	}
	
}
