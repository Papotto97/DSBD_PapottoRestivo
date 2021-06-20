package com.unict.auctionmanager.model;

import lombok.Data;

@Data
public class AuctionBean {
	
	private String auctionId=null;
	private Integer userId;
	private Float stake;
	private Integer currency=1;
	private Integer itemId=null;
	
}
