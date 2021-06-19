package com.unict.walletmanager.entity;

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

@Entity (name = "CurrencyEntity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CURRENCY")
@Builder
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class CurrencyEntity  implements Serializable {

	private static final long serialVersionUID = 4406814940077633926L;
	
	@Id
	@Column(name = "ID", columnDefinition = "integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 )")
	private Integer id;
	
	@Column(name = "SYMBOL", columnDefinition = "character varying(5) NOT NULL")
	private String symbol;
	
    @Column(name = "DESCRIPTION", columnDefinition = "character varying(256) NOT NULL")
    private String description;
	
    //JPA RELATIONS
	@OneToMany(mappedBy = "currency", fetch = FetchType.LAZY)
	private List<WalletEntity> wallets;
	
	@OneToMany(mappedBy = "currency", fetch = FetchType.LAZY)
	private List<AuctionEntity> auctions;

}
