package io.eduka.super.politicos.repository;

import io.eduka.super.politicos.domain.PoliticoAtributo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PoliticoAtributo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PoliticoAtributoRepository extends JpaRepository<PoliticoAtributo, Long> {

}
