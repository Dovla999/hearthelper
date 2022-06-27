package sbnz.integracija.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sbnz.integracija.backend.facts.Card;

import java.util.Set;


public interface CardRepository extends JpaRepository<Card, Long> {
    Set<Card> findCardsByIsCenterpieceTrue();
}
