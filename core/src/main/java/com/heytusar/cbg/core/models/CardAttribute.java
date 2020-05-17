package com.heytusar.cbg.core.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CardAttribute {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	public Long getId() {
		return id;
	}
	
	@Column(nullable = false)
	private String attributeKey;
	
	@Column(nullable = false)
	private String attributeValue;

	public String getAttributeKey() {
		return attributeKey;
	}
	public void setAttributeKey(String attributeKey) {
		this.attributeKey = attributeKey;
	}
	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="card_id_FK", nullable=false)
    private Card card;

	public Card getCard() {
		return card;
	}
	public void setCard(Card card) {
		this.card = card;
	}
}
