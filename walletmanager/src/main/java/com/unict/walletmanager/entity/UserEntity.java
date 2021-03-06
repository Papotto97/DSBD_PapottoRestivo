package com.unict.walletmanager.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "UserEntity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER")
@Builder
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class UserEntity implements Serializable {

	private static final long serialVersionUID = 5558504801122717695L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "EMAIL", columnDefinition = "character varying(125) NOT NULL")
	private String email;

	@Column(name = "USERNAME", columnDefinition = "character varying(50) NOT NULL")
	private String username;

	@Column(name = "NAME", columnDefinition = "character varying(50) NOT NULL")
	private String name;

	@Column(name = "SURNAME", columnDefinition = "character varying(50) NOT NULL")
	private String surname;
	
	//JPA RELATIONS
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<AuctionEntity> auctions;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<OfferHistory> offersHistory;
	
	@OneToOne(mappedBy = "user")
	private WalletEntity wallet;
}
