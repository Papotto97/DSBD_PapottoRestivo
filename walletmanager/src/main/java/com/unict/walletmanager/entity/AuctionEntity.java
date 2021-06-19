package com.unict.walletmanager.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "AuctionEntity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "WALLET")
@Builder
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class AuctionEntity implements Serializable {

	private static final long serialVersionUID = -8143826819306355990L;

	@Id
	@Column(name = "ID", columnDefinition = "uuid NOT NULL")
	private UUID id;

	@Column(name = "ITEM_ID", columnDefinition = "integer")
	private Integer itemId;

	@Column(name = "USER_ID", columnDefinition = "integer")
	private Integer userId;

	@Column(name = "CURRENCY_ID", columnDefinition = "integer")
	private Integer currencyId;

	@Column(name = "START_PRICE", columnDefinition = "real")
	private Float startPrice;

	@Column(name = "LAST_OFFER", columnDefinition = "real")
	private Float lastOffer;

	@Column(name = "UPDATE_TIMESTAMP", columnDefinition = "timestamp with time zone DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	@Builder.Default
	private Date updateTimestamp = new Date();

	@Column(name = "END_TIMESTAMP", columnDefinition = "timestamp with time zone")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTimestamp;

	@Column(name = "COMPLETED", columnDefinition = "boolean NOT NULL DEFAULT false")
	private boolean completed;
	
	//JPA RELATIONS
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ITEM_ID", referencedColumnName = "ID", insertable = false, updatable = false, nullable = false)
	@JsonBackReference
	private ItemEntity item;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "CURRENCY_ID", referencedColumnName = "ID", insertable = false, updatable = false, nullable = false)
	@JsonBackReference
	private CurrencyEntity currency;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID", insertable = false, updatable = false, nullable = false)
	@JsonBackReference
	private UserEntity user;
	
	@OneToMany(mappedBy = "auction", fetch = FetchType.LAZY)
	private List<OfferHistory> offersHistory;
	
//	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "auction")
//	private List<OfferHistory> offersHistory;

}
