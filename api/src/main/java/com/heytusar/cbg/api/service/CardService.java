package com.heytusar.cbg.api.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heytusar.cbg.api.persistence.CardRepository;
import com.heytusar.cbg.core.models.Card;

@Service
@Transactional
public class CardService {
	
	private static final Logger log = LoggerFactory.getLogger(CardService.class);
	
	private CardRepository  cardRepository;
	
	@Autowired
	public CardService(CardRepository  cardRepository) {
		this.cardRepository = cardRepository;
	}
	
	public List<Card> getAllCards() {
		List<Card> cardList = new ArrayList<Card>();
		Iterable<Card> iusers = cardRepository.findAll();
		log.info("iusers -------->" + iusers);
		iusers.forEach(cardList::add);
		//return cardList;
		return new ArrayList<Card>(cardList.subList(0, 8));
	}
	
	
}
