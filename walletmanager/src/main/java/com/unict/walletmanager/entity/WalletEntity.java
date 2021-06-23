package com.unict.walletmanager.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "WalletEntity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "WALLET")
@Builder
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class WalletEntity implements Serializable {

	private static final long serialVersionUID = -606171657978054297L;

	@Id
	@Column(name = "ID", columnDefinition = "integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 )")
	private Integer id;

	@Column(name = "USER_ID", columnDefinition = "integer NOT NULL")
	private Integer userId;
	
	@Column(name = "CURRENCY", columnDefinition = "integer NOT NULL")
	private Integer currencyId;

	@Column(name = "DISPOSABILITY", columnDefinition = "real NOT NULL DEFAULT 0")
	private Float disponsability;

	@Column(name = "ACTUAL_STAKED", columnDefinition = "real DEFAULT 0")
	private Float actualStaked;
	
	//JPA RELATIONS
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID", insertable = false, updatable = false, nullable = false)
	@JsonBackReference
	private UserEntity user;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "CURRENCY", referencedColumnName = "ID", insertable = false, updatable = false, nullable = false)
	@JsonBackReference
	private CurrencyEntity currency;

}
