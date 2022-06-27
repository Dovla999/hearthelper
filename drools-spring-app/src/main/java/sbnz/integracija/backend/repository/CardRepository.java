package sbnz.integracija.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sbnz.integracija.backend.facts.Card;

import java.util.List;
import java.util.Set;

public interface CardRepository extends JpaRepository<Card, Long> {
    public List<Card> findAllByIsCenterpieceEquals(Boolean isCenterpiece);
    Set<Card> findCardsByIsCenterpieceTrue();
}
