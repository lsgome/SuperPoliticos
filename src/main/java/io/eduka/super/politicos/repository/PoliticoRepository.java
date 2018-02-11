package io.eduka.super.politicos.repository;

import io.eduka.super.politicos.domain.Politico;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Politico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PoliticoRepository extends JpaRepository<Politico, Long> {

}
