package sbnz.integracija.backend.service;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sbnz.integracija.backend.dto.DeckResultDTO;
import sbnz.integracija.backend.facts.*;
import sbnz.integracija.backend.repository.CardRepository;
import sbnz.integracija.backend.repository.DeckRepository;
import sbnz.integracija.backend.repository.MatchRepository;
import sbnz.integracija.backend.repository.MetaRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Service
public class DeckService {
    private static Logger log = LoggerFactory.getLogger(DeckService.class);

    private final KieContainer kieContainer;

    @Autowired
    DeckRepository deckRepository;

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    MetaRepository metaRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    public DeckService(KieContainer kieContainer) {
        log.info("Initialising a new example session.");
        this.kieContainer = kieContainer;
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public DeckResultDTO recommendDeckForwardChain(MetaRank metaRank, List<DeckCategory> categories, List<Hero> heroes, List<Card> centerpieces ){
        //fire chain
        /*
        centerpieces = new ArrayList<>();
        for(Card card: cardRepository.findAllByIsCenterpieceEquals(true)){
            if(getRandomNumber(0,2) == 1){
                centerpieces.add(card);
            }
        }
        heroes = new ArrayList<>();
        categories = new ArrayList<>();
        heroes.add(Hero.MAGE);
        heroes.add(Hero.ROGUE);
       // heroes.add(Hero.WARLOCK);
        categories.add(DeckCategory.MIDGAME);
        categories.add(DeckCategory.AGGRO);
        categories.add(DeckCategory.ATTRITION);
        */

        KieSession kieSession = kieContainer.newKieSession("deckRecommendingRulesSession");
        kieSession.setGlobal("deckw", new HashMap<>());
        kieSession.insert(metaRank);
        for(Meta meta: metaRepository.findAll()){
            kieSession.insert(meta);
        }

        for(DeckCategory category: categories){
            kieSession.insert(category);
        }
        for(Hero hero:heroes){
            kieSession.insert(hero);
        }
        for(Card centerpiece:centerpieces){
            kieSession.insert(centerpiece);
        }
        DeckResultDTO deckResultDTO = new DeckResultDTO();
        kieSession.insert(deckResultDTO);
        int fired = kieSession.fireAllRules();
        System.out.println("Number of Rules executed = " + fired);
        kieSession.dispose();
        //System.out.println(deckRepository.findAll().size());
        System.out.println(deckResultDTO.getDeck().getName());
        return deckResultDTO;
    }
}
