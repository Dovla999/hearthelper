package sbnz.integracija.backend.service;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbnz.integracija.backend.facts.Card;

@Service
public class CardService {

	private static Logger log = LoggerFactory.getLogger(CardService.class);

	private final KieContainer kieContainer;

	@Autowired
	public CardService(KieContainer kieContainer) {
		log.info("Initialising a new example session.");
		this.kieContainer = kieContainer;
	}

	public Card getClassifiedItem(Card card) {
		KieSession kieSession = kieContainer.newKieSession();
		kieSession.insert(card);
		kieSession.fireAllRules();
		kieSession.dispose();
		return card;
	}
}
