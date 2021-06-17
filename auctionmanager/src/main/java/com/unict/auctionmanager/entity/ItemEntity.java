package com.unict.auctionmanager.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity (name = "ItemEntity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ITEM")
@Builder
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class ItemEntity implements Serializable {

	private static final long serialVersionUID = 4990432313707133361L;
	
	@Id
	@Column(name = "ID", columnDefinition = "integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 )")
	private Integer id;
	
	@Column(name = "NAME", columnDefinition = "character varying(100) NOT NULL")
	private String name;
	
	@Column(name = "DESCRIPTION", columnDefinition = "character varying(256) NOT NULL")
	private String description;
	
	@Column(name = "STOCK_QTY", columnDefinition = "integer NOT NULL DEFAULT 0")
	private Integer stockQuantity;
	
	@Column(name = "START_PRICE", columnDefinition = "real DEFAULT 0")
	private Float startPrice;
	
	@Column(name = "CURRENCY", columnDefinition = "integer NOT NULL")
	private Integer currency;
	
	@Column(name = "AUCTION_HOUR", columnDefinition = "integer NOT NULL")
	private Integer auctionHour;
	
	//JPA RELATIONS
	@OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
	private List<AuctionEntity> auctions;

}
