package com.heytusar.cbg.core.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Card {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	public Long getId() {
		return id;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)  
	private List<CardAttribute> cardAttributes;

	public List<CardAttribute> getCardAttributes() {
		return cardAttributes;
	}

	public void setCardAttributes(List<CardAttribute> cardAttributes) {
		this.cardAttributes = cardAttributes;
	}  
	
	public CardAttribute getCardAttribute(String key) {
		for(CardAttribute ca : this.cardAttributes) {
			if(ca.getAttributeKey().equals(key)) {
				return ca;
			}
		}
		return null;
	}
	
	private Integer rank;
	
	private String shortTag;
	
	@Column(columnDefinition="TEXT", nullable = true)
	private String wiki;
	
	@Column(columnDefinition="tinyint(1) default 1", nullable = false)
	private Boolean isDeleted;

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getShortTag() {
		return shortTag;
	}

	public void setShortTag(String shortTag) {
		this.shortTag = shortTag;
	}

	public String getWiki() {
		return wiki;
	}

	public void setWiki(String wiki) {
		this.wiki = wiki;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
}
