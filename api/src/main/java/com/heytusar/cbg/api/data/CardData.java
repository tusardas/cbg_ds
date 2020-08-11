package com.heytusar.cbg.api.data;

import com.heytusar.cbg.core.models.Card;

public class CardData {
	private Long cardId;
	private String cardName;
	private String imageUrl;
	
	public CardData() {
		
	}
	
	public CardData(Card card) {
		this.cardId = card.getId();
		this.cardName = card.getCardAttribute("name").getAttributeValue();
		this.imageUrl = card.getCardAttribute("imageUrl").getAttributeValue();
	}
}
