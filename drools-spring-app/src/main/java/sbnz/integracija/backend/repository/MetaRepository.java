package sbnz.integracija.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sbnz.integracija.backend.facts.Meta;

public interface MetaRepository extends JpaRepository<Meta, Long> {

}
