package sbnz.integracija.backend.service;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import sbnz.integracija.backend.facts.Card;
import sbnz.integracija.backend.facts.Match;
import sbnz.integracija.backend.facts.User;
import sbnz.integracija.backend.repository.UserRepository;

@Service
public class CardService {

	private static Logger log = LoggerFactory.getLogger(CardService.class);

	private final KieContainer kieContainer;

	@Autowired
	private UserRepository userRepository;

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

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		KieSession kieSession = kieContainer.newKieSession("matchHistoryRulesSession");

		User user = userRepository.findAll().get(0);
		for (Match m : user.getPersonalMatchHistory()) {
			System.out.println(m.getSecondPlayer().name());
		}
		kieSession.insert(user);
		int fired = kieSession.fireAllRules();
		System.out.println("Number of Rules executed = " + fired);
		kieSession.dispose();
	}
}
