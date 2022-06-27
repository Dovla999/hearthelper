package sbnz.integracija.backend.service;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import sbnz.integracija.backend.dto.DeckDTO;
import sbnz.integracija.backend.dto.MatchHistoryOutputDTO;
import sbnz.integracija.backend.facts.*;
import sbnz.integracija.backend.repository.CardRepository;
import sbnz.integracija.backend.repository.DeckRepository;
import sbnz.integracija.backend.repository.MatchRepository;
import sbnz.integracija.backend.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class CardService {

	private static Logger log = LoggerFactory.getLogger(CardService.class);

	private final KieContainer kieContainer;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MatchRepository matchRepository;

	@Autowired
	private CardRepository cardRepository;
	@Autowired
	private DeckRepository deckRepository;


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

//	@EventListener(ApplicationReadyEvent.class)
	public MatchHistoryOutputDTO matchHistoryForwardChain() {
		KieSession kieSession = kieContainer.newKieSession("matchHistoryRulesSession");

		User user = userRepository.findAll().get(0);
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
		System.out.println("Best hero: " + dto.getHero().name());
		System.out.println("Best centerpiece: " + dto.getCenterpieceCard().getName());
		System.out.println("Best deck category: " + dto.getDeckCategory().name());

		return dto;
	}

	public MatchHistoryOutputDTO ownedCardsChainOutput() {
		KieSession kieSession = kieContainer.newKieSession("ownedCardsRulesSession");

		User user = userRepository.findAll().get(0);
		kieSession.insert(user);
		deckRepository.findAll().forEach(kieSession::insert);
		cardRepository.findAll().forEach(kieSession::insert);
		MatchHistoryOutputDTO dto = new MatchHistoryOutputDTO();
		kieSession.insert(dto);
		int fired = kieSession.fireAllRules();
		System.out.println("Number of Rules executed = " + fired);
		kieSession.dispose();
		System.out.println("Best hero: " + dto.getHero().name());
		System.out.println("Best centerpiece: " + dto.getCenterpieceCard().getName());
		System.out.println("Best deck category: " + dto.getDeckCategory().name());

		return dto;
	}

	public List<Hero> getAllHeroes() {
		return Arrays.asList(Hero.values());
	}

	public List<DeckCategory> getAllDeckCategories() {
		return Arrays.asList(DeckCategory.values());
	}

	public Set<Card> getAllCenterpieceCards() {
		return  this.cardRepository.findCardsByIsCenterpieceTrue();
	}

	public List<MetaRank> getAllMetaRanks() {
		return Arrays.asList(MetaRank.values());
	}

	public DeckDTO getSampleDeck() {

		return new DeckDTO(deckRepository.findAll().get(0));
	}
}
