package sbnz.integracija.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sbnz.integracija.backend.facts.Deck;
import sbnz.integracija.backend.facts.User;

public interface DeckRepository extends JpaRepository<Deck, Long> {

}
