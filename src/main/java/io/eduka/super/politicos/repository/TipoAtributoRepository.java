package io.eduka.super.politicos.repository;

import io.eduka.super.politicos.domain.TipoAtributo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TipoAtributo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoAtributoRepository extends JpaRepository<TipoAtributo, Long> {

}
