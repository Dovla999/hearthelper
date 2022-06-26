package sbnz.integracija.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sbnz.integracija.backend.facts.Card;


public interface CardRepository extends JpaRepository<Card, Long> {

}
