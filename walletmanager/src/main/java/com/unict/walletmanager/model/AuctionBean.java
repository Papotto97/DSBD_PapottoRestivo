package com.unict.walletmanager.model;

import lombok.Data;

@Data
public class AuctionBean {
	
	private String auctionId=null;
	private Integer userId;
	private Float stake;
	private String currency;
	
}
