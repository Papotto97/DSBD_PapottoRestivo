package com.unict.auctionmanager.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

@Entity(name = "OfferHistoryEntity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "OFFER_HISTORY")
@Builder
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class OfferHistoryEntity implements Serializable {

	private static final long serialVersionUID = -3621477088994698332L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "AUCTION_ID", columnDefinition = "uuid NOT NULL")
	private UUID auctionId;

	@Column(name = "USER_ID", columnDefinition = "integer NOT NULL")
	private Integer userId;

	@Column(name = "TIMESTAMP", columnDefinition = "timestamp with time zone DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	@Column(name = "STAKE", columnDefinition = "real")
	private Float stake;

	// JPA RELATIONS
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "AUCTION_ID", referencedColumnName = "ID", insertable = false, updatable = false, nullable = false)
	@JsonBackReference
	private AuctionEntity auction;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID", insertable = false, updatable = false, nullable = false)
	@JsonBackReference
	private UserEntity user;

}
