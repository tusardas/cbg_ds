package com.heytusar.cbg.core.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Turn {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long playerId;
	
	@Column(nullable = true)
	private Long cardId;
	@Column(nullable = true)
	private String attributeKeyPlayed;
	
    private Long roundId;
    
    @Column(columnDefinition = "TINYINT", length = 1)
	private Boolean isPlayed;
    
	public Boolean getIsPlayed() {
		return isPlayed;
	}
	public void setIsPlayed(Boolean isPlayed) {
		this.isPlayed = isPlayed;
	}
	public Long getRoundId() {
		return roundId;
	}
	public void setRoundId(Long roundId) {
		this.roundId = roundId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	public Long getCardId() {
		return cardId;
	}
	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}
	public String getAttributeKeyPlayed() {
		return attributeKeyPlayed;
	}
	public void setAttributeKeyPlayed(String attributeKeyPlayed) {
		this.attributeKeyPlayed = attributeKeyPlayed;
	}
    
}
