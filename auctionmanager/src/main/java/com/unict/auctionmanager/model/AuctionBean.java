package com.unict.auctionmanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuctionBean {
	
	@JsonProperty("auctionId")
	private String auctionId=null;
	@JsonProperty("userId")
	private Integer userId;
	@JsonProperty("stake")
	private Float stake;
	@JsonProperty("currency")
	private Integer currency=1;
	@JsonProperty("itemId")
	private Integer itemId=null;
	
}
