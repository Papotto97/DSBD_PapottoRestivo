package com.unict.model;

import lombok.Data;

@Data
public class AuctionBean {
	
	private String auctionId=null;
	private String userId;
	private Float stake;
	private String currency;
	
}
