package sbnz.integracija.backend.service;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import sbnz.integracija.backend.dto.MatchHistoryOutputDTO;
import sbnz.integracija.backend.facts.Card;
import sbnz.integracija.backend.facts.Hero;
import sbnz.integracija.backend.facts.Match;
import sbnz.integracija.backend.facts.User;
import sbnz.integracija.backend.repository.MatchRepository;
import sbnz.integracija.backend.repository.UserRepository;

@Service
public class CardService {

	private static Logger log = LoggerFactory.getLogger(CardService.class);

	private final KieContainer kieContainer;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MatchRepository matchRepository;

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
//		for (Match m : user.getPersonalMatchHistory()) {
//			System.out.println(m.getSecondPlayer().name());
//		}
		kieSession.insert(user);
		for (Match m : matchRepository.findAll()) {
			kieSession.insert(m);
//			if (m.getFirstPlayer() == Hero.SHAMAN && m.getSecondPlayer() == Hero.DRUID)
//				System.out.println("First: SHAMAN || Second: DRUID || First won: " + m.getFirstPlayerWon());
//			if (m.getFirstPlayer() == Hero.DRUID && m.getSecondPlayer() == Hero.SHAMAN)
//				System.out.println("First: DRUID || Second: SHAMAN || First won: " + m.getFirstPlayerWon());
		}
		MatchHistoryOutputDTO dto = new MatchHistoryOutputDTO();
		kieSession.insert(dto);
		int fired = kieSession.fireAllRules();
		System.out.println("Number of Rules executed = " + fired);
		kieSession.dispose();
		System.out.println("lol " + dto.getHero().name());
		System.out.println("lol2 " + dto.getCenterpieceCard().getName());
	}
}
