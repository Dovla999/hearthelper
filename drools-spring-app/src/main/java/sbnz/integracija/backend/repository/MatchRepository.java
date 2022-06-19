package sbnz.integracija.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sbnz.integracija.backend.facts.Match;
import sbnz.integracija.backend.facts.User;

public interface MatchRepository extends JpaRepository<Match, Long> {

}
