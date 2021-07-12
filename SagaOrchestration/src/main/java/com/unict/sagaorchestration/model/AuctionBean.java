package com.unict.sagaorchestration.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuctionBean {
	
	@JsonProperty(value = "auctionId")
	private String auctionId;
	
	@JsonProperty(value = "userId")
	private Integer userId;
	
	@JsonProperty(value = "stake")
	private Float stake;
	
	@JsonProperty(value = "currency")
	private Integer currency=1;
	
	@JsonProperty(value = "itemId")
	private Integer itemId;	
	
}
